import express from 'express';
import bodyParser from 'body-parser';

import config from "./config/config";
import refreshAddresses from "./controllers/addresses";
import ping from './controllers/ping';
import webhook from './controllers/webhook';

const app = express();
app.use(bodyParser.json());

app.get('/ping', ping);
app.post('/deposit-webhook', webhook);

app.listen(8080, () => console.log(' [*] Deposit service started on 0.0.0.0:8080'));

setInterval(async () => {
  await Promise.all(Object.keys(config.coins).map((coin) => refreshAddresses(coin)));
}, config.addresses.refreshIntervalMs);
