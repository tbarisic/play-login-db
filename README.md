###  Najbolja  play aplikacija ikad

#### Baza
Podeseno je spajanje na postgres bazu
Pogledaj conf/application.conf

#### Security
1. Login
POST na /login, body {"username": "", "password": ""}
2. Logout
GET na /logout
3. Ostale rute
/ je unsecured
/secured je secured

_Princip rada_
- prilikom  logina postavi se auth_cookie na neki generirani token
- ako je ruta osigurana (pogledaj secured  HomeController) tada se iz cookija vadi token i provjerava dal je ok