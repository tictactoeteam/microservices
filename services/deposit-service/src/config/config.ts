interface CoinConfig {
  wallet: string;
  confirmations: number;
  supportsBech32: boolean;
}

interface Config {
  coins: { [coin: string]: CoinConfig };
  addresses: {
    min: number;
    refreshIntervalMs: number;
  };
  rabbit: {
    username: string;
    password: string;
  }
  redisPassword: string;
  token: string;
  env: 'test' | 'prod';
}

export default {
  coins: {
    tbtc: {
      wallet: '5cdae721a6a995bb036724d22f2c828b',
      confirmations: 2,
      supportsBech32: true
    },
    tltc: {
      wallet: '5cdae7e75f3a208a0324560823085f7f',
      confirmations: 8,
      supportsBech32: true
    },
    txlm: {
      wallet: '5cdae841b9b903c60342944b371ae753',
      confirmations: 120,
      supportsBech32: false
    },
    tzec: {
      wallet: '5cdae816a6a995bb03672ce3baaa9266',
      confirmations: 8,
      supportsBech32: false
    }
  },
  addresses: {
    min: 10,
    refreshIntervalMs: 3000
  },
  rabbit: {
    username: process.env.RABBIT_USER || 'guest',
    password: process.env.RABBIT_PASSWORD || 'guest'
  },
  redisPassword: process.env.REDIS_PASSWORD,
  token: process.env.BG_TOKEN,
  env: 'test'
} as Config;
