import * as bitgojs from 'bitgo';
import { Request, Response } from 'express';

import config from '../config/config';

import { publishTx } from './publish';

const bitgo = new bitgojs.BitGo({ env: config.env, accessToken: config.token });

const wallets: { [coin: string]: any } = {};

for (const coin of Object.keys(config.coins)) {
  bitgo.coin(coin).wallets().get({ id: config.coins[coin].wallet }).then((wallet: any) => wallets[coin] = wallet);
}

interface Webhook {
  type: 'transfer';
  wallet: string;
  hash: string;
  transfer: string;
  coin: string;
}

export default async function webhook(req: Request, res: Response) {
  console.log('webhook received');
  const webhook = req.body as Webhook;
  console.log(webhook);

  const coin = webhook.coin;
  const walletId = webhook.wallet;

  if (!Object.keys(config.coins).includes(coin)) {
    res.status(400).send('invalid coin');
    return;
  }
  if (config.coins[coin].wallet !== walletId) {
    res.status(400).send('invalid wallet');
    return;
  }

  const wallet = wallets[coin];

  let transfer;
  try {
    transfer = await wallet.getTransfer({ id: webhook.transfer });
  } catch (e) {
    res.status(400).send('nice fucking try buddy');
  }

  if (!transfer) {
    res.status(400).send('nice fucking try buddy');
    return;
  }

  const confirmed = transfer.confirmations >= config.coins[coin].confirmations;

  const entry = transfer.entries.filter((entry: any) => entry.wallet === walletId)[0];

  publishTx(confirmed, transfer.coin, entry.address, BigInt(entry.valueString), transfer.txid);

  res.status(200).send({ status: 'success' });
}
