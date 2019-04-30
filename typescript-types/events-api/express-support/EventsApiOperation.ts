import { Request, Response } from 'express';
import { EventsApiOperationArgs } from './EventsApiOperationArgs';

export class EventsApiOperation<E extends EventsApiPayload> {
  private operate: (args: EventsApiOperationArgs<E>) => void
  constructor(operation: (args: EventsApiOperationArgs<E>) => void) {
    this.operate = operation;
  }
  run(args: EventsApiOperationArgs<E>): void {
    this.operate(args);
  }
}