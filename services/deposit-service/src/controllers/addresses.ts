import * as bitgojs from 'bitgo';
import Redis from 'ioredis';

import config from '../config/config';

const redis = new Redis({ port: 6379, host: 'redis-master', password: config.redisPassword });
const bitgo = new bitgojs.BitGo({ env: config.env, accessToken: config.token });

const wallets: { [coin: string]: any } = {};

export default async function refreshAddresses(coin: string) {
  const len = await redis.scard(`receive-addresses:${coin}`);
  if (len < config.addresses.min) {
    const addresses: string[] = [];

    for (let i = len; i < config.addresses.min; i++) {
      if (!wallets[coin]) {
        wallets[coin] = await bitgo.coin(coin).wallets().get({ id: config.coins[coin].wallet });
      }

      const chain = config.coins[coin].supportsBech32 ? 20 : 0;
      const address = await wallets[coin].createAddress({ chain });

      addresses.push(address.address);
    }
    await redis.sadd(`receive-addresses:${coin}`, ...addresses);
  }
}
