import { Request, Response } from 'express';
import { EventsApiPayload } from './EventsApiPayload';

export class EventsApiOperationArgs<E extends EventsApiPayload> {
  payload: E;
  request: Request;
  response: Response;

  constructor(payload: E, request: Request, response: Response) {
    this.payload = payload;
    this.request = request;
    this.response = response;
  }
}