import { EventsApiOperationArgs } from './EventsApiOperationArgs';
import { EventsApiPayload } from './EventsApiPayload';

export class EventsApiOperation<E extends EventsApiPayload> {
  private operate: (args: EventsApiOperationArgs<E>) => void
  constructor(operation: (args: EventsApiOperationArgs<E>) => void) {
    this.operate = operation;
  }
  run(args: EventsApiOperationArgs<E>): void {
    this.operate(args);
  }
}