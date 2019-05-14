import * as amqp from 'amqplib';

import config from '../config/config';

let channel: amqp.Channel;

(async () => {
  try {
    const connection = await amqp.connect({
      protocol: 'amqp',
      hostname: 'rabbitmq',
      port: 5672,
      username: config.rabbit.username,
      password: config.rabbit.password
    });
    channel = await connection.createChannel();
    await channel.assertExchange('transaction', 'direct');
    await channel.assertQueue('transaction-pending');
    await channel.assertQueue('transaction-confirmed');
    await channel.bindQueue('transaction-pending', 'transaction', 'pending');
    await channel.bindQueue('transaction-confirmed', 'transaction', 'confirmed');
  } catch (e) {
    console.log('RabbitMQ error: ');
    console.log(e.message);
    process.exit(1); // restart until rabbit is up
  }
})();

export function publishTx(confirmed: boolean, coin: string, address: string, amount: bigint, txid: string) {
  const payload = JSON.stringify({
    coin: coin,
    address: address,
    amount: amount.toString(),
    txid: txid
  });

  channel.publish('transaction', confirmed ? 'confirmed' : 'pending', Buffer.from(payload));
}
