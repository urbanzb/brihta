package si.angleskebesede.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Ovoj za spletno aplikacijo »Brihta«.
 * Aplikacija se naloži z GitHub Pages, zato se besede in igre posodabljajo same.
 * Most AndroidBridge doda izgovorjavo (Android TTS), shranjevanje datotek in deljenje.
 */
public class MainActivity extends Activity {
    private static final int REQ_FILE_CHOOSER = 41;
    private static final int REQ_SAVE_FILE = 42;

    private WebView web;
    private TextToSpeech tts;
    private volatile boolean ttsReady = false;
    private ValueCallback<Uri[]> fileCallback;
    private String pendingSaveContent;
    private int utterance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setTextZoom(100);
        s.setUserAgentString(s.getUserAgentString() + " BrihtaApp/" + BuildConfig.VERSION_CODE);

        web.addJavascriptInterface(new Bridge(), "AndroidBridge");
        web.setWebViewClient(new AppClient());
        web.setWebChromeClient(new ChromeClient());

        tts = new TextToSpeech(this, status -> {
            ttsReady = status == TextToSpeech.SUCCESS;
            runOnUiThread(() -> web.evaluateJavascript("window.__ttsReady&&window.__ttsReady()", null));
        });

        if (savedInstanceState != null) {
            web.restoreState(savedInstanceState);
        } else {
            web.loadUrl(BuildConfig.APP_URL);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        web.saveState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (tts != null) tts.stop();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) tts.shutdown();
        web.destroy();
        super.onDestroy();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBackPressed() {
        web.evaluateJavascript("window.__androidBack?String(window.__androidBack()):'false'", value -> {
            if (!"\"true\"".equals(value)) finish();
        });
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_FILE_CHOOSER) {
            if (fileCallback != null) {
                fileCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                fileCallback = null;
            }
        } else if (requestCode == REQ_SAVE_FILE) {
            String content = pendingSaveContent;
            pendingSaveContent = null;
            if (resultCode != RESULT_OK || data == null || data.getData() == null || content == null) return;
            try (OutputStream out = getContentResolver().openOutputStream(data.getData())) {
                if (out == null) throw new IllegalStateException();
                out.write(content.getBytes(StandardCharsets.UTF_8));
                Toast.makeText(this, "Datoteka je shranjena.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Shranjevanje ni uspelo.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isAppUrl(Uri u) {
        return u != null && u.toString().startsWith(BuildConfig.APP_URL);
    }

    private class AppClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri u = request.getUrl();
            if (isAppUrl(u)) return false;
            // Vse ostale povezave (npr. GitHub) odpri zunaj aplikacije.
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, u));
            } catch (ActivityNotFoundException ignored) {
            }
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if (request.isForMainFrame()) {
                String html = "<!doctype html><meta name=viewport content='width=device-width,initial-scale=1'>"
                        + "<body style='font-family:sans-serif;background:#EAF3FB;color:#1F2A44;text-align:center;padding:40px 20px'>"
                        + "<div style='font-size:64px'>📶</div><h2>Ni povezave</h2>"
                        + "<p>Ob prvem zagonu aplikacija potrebuje internet. Pozneje deluje tudi brez njega.</p>"
                        + "<p><a href='" + BuildConfig.APP_URL + "' style='font-size:20px'>Poskusi znova</a></p></body>";
                view.loadDataWithBaseURL(BuildConfig.APP_URL, html, "text/html", "UTF-8", null);
            }
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> callback, FileChooserParams params) {
            if (fileCallback != null) fileCallback.onReceiveValue(null);
            fileCallback = callback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            try {
                startActivityForResult(Intent.createChooser(i, "Izberi datoteko"), REQ_FILE_CHOOSER);
            } catch (ActivityNotFoundException e) {
                fileCallback = null;
                return false;
            }
            return true;
        }
    }

    /** Metode, ki jih spletna aplikacija kliče prek window.AndroidBridge. */
    private class Bridge {
        /** Vrne prvi jezik s seznama (npr. "sl,hr"), ki ga ima naprava, ali prazen niz. */
        @JavascriptInterface
        public String ttsLang(String csv) {
            if (!ttsReady || tts == null) return "";
            for (String tag : csv.split(",")) {
                tag = tag.trim();
                if (tag.isEmpty()) continue;
                int r = tts.isLanguageAvailable(Locale.forLanguageTag(tag));
                if (r >= TextToSpeech.LANG_AVAILABLE) return tag;
            }
            return "";
        }

        @JavascriptInterface
        public boolean speak(String text, String lang, boolean queue) {
            if (!ttsReady || tts == null) return false;
            synchronized (MainActivity.this) {
                Locale loc = "en-GB".equalsIgnoreCase(lang) ? Locale.UK : Locale.forLanguageTag(lang);
                if (tts.isLanguageAvailable(loc) < TextToSpeech.LANG_AVAILABLE && lang.startsWith("en")) loc = Locale.ENGLISH;
                tts.setLanguage(loc);
                tts.setSpeechRate(lang.startsWith("en") ? 0.85f : 0.9f);
                tts.speak(text, queue ? TextToSpeech.QUEUE_ADD : TextToSpeech.QUEUE_FLUSH, null, "u" + (utterance++));
            }
            return true;
        }

        @JavascriptInterface
        public void stop() {
            if (tts != null) tts.stop();
        }

        /** Ponudi shranjevanje datoteke (izvoz lastnih besed). */
        @JavascriptInterface
        public void saveFile(String name, String content) {
            runOnUiThread(() -> {
                pendingSaveContent = content;
                Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("application/json");
                i.putExtra(Intent.EXTRA_TITLE, name);
                try {
                    startActivityForResult(i, REQ_SAVE_FILE);
                } catch (ActivityNotFoundException e) {
                    pendingSaveContent = null;
                    Toast.makeText(MainActivity.this, "Shranjevanje ni na voljo.", Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface
        public void shareText(String text) {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, text);
                try {
                    startActivity(Intent.createChooser(i, "Deli"));
                } catch (ActivityNotFoundException ignored) {
                }
            });
        }

        @JavascriptInterface
        public int versionCode() {
            return BuildConfig.VERSION_CODE;
        }
    }
}
