// Service worker: aplikacija se vedno takoj odpre iz shranjene kopije, nova različica se prenese v ozadju
// in velja ob naslednjem odprtju. Besede (besede.json) najprej z omrežja, a največ 3 sekunde, sicer shranjene.
const CACHE = 'ludamus-v47';
const SHELL = ['./', 'index.html', 'manifest.webmanifest', 'besede.json', 'icon-192.png', 'icon-512.png',
  'apple-touch-icon.png', 'grandstander.woff2', 'atkinson-regular.woff2', 'atkinson-bold.woff2'];
const DATA_TIMEOUT = 3000;

self.addEventListener('install', e => {
  e.waitUntil(caches.open(CACHE).then(c => c.addAll(SHELL)).then(() => self.skipWaiting()));
});

self.addEventListener('activate', e => {
  e.waitUntil(caches.keys()
    .then(keys => Promise.all(keys.filter(k => k !== CACHE).map(k => caches.delete(k))))
    .then(() => self.clients.claim()));
});

// Osvežitev vedno preveri strežnik (mimo predpomnilnika brskalnika), da nova različica res pride.
// Preusmerjenih odgovorov brskalnik za navigacijo ne sprejme, zato jih pretvorimo v navaden odgovor.
function fresh(req) {
  return fetch(new Request(req.url, { cache: 'no-cache', credentials: 'same-origin' })).then(res => {
    if (!res.redirected) return res;
    return res.blob().then(b => new Response(b, { status: res.status, statusText: res.statusText, headers: res.headers }));
  });
}

function save(key, res) {
  if (res && res.ok) { const copy = res.clone(); caches.open(CACHE).then(c => c.put(key, copy)); }
  return res;
}

async function fromCache(req, key) {
  const hit = await caches.match(key);
  if (hit) return hit;
  if (req.mode === 'navigate') return caches.match(new URL('./', self.registration.scope).href);
  return undefined;
}

// Datoteke aplikacije: takoj iz shranjene kopije, hkrati osvežitev v ozadju.
function cacheFirst(e, req, key) {
  const net = fresh(req).then(res => save(key, res)).catch(() => null);
  e.waitUntil(net);
  return fromCache(req, key).then(hit => hit || net.then(r => r || Response.error()));
}

// Besede: omrežje z omejenim časom čakanja, nato shranjena kopija.
function networkWithTimeout(e, req, key) {
  const net = fresh(req).then(res => save(key, res)).catch(() => null);
  e.waitUntil(net);
  const timer = new Promise(resolve => setTimeout(() => resolve(null), DATA_TIMEOUT));
  return Promise.race([net, timer]).then(async r => {
    if (r) return r;
    const hit = await fromCache(req, key);
    if (hit) return hit;
    return (await net) || Response.error();
  });
}

self.addEventListener('fetch', e => {
  const req = e.request;
  if (req.method !== 'GET') return;
  const url = new URL(req.url);
  if (url.origin !== location.origin) return;
  const key = url.origin + url.pathname;
  // Druge strani na isti domeni (npr. /apk za prenos) brskalnik naloži sam, mimo shranjene kopije.
  const root = new URL('./', self.registration.scope).pathname;
  if (req.mode === 'navigate' && url.pathname !== root && url.pathname !== root + 'index.html') return;
  if (url.pathname.endsWith('besede.json')) e.respondWith(networkWithTimeout(e, req, key));
  else e.respondWith(cacheFirst(e, req, key));
});
