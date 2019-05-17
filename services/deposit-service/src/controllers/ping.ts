import { Request, Response } from 'express';

export default function ping(req: Request, res: Response) {
  res.status(200).send('Deposit service is online!');
}
