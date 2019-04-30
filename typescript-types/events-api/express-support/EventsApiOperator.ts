import { Request, Response } from 'express';
import { EventsApiOperation } from './EventsApiOperation';
import { EventsApiOperationArgs } from './EventsApiOperationArgs';
import { EventsApiPayload } from './EventsApiPayload';

export class EventsApiOperator {
  private eventTypeAndOperations: { [typeName: string]: EventsApiOperation<EventsApiPayload>[] } = {};

  add<E extends EventsApiPayload>(type: string, operation: EventsApiOperation<E>): void {
    let operations: EventsApiOperation<EventsApiPayload>[] = this.eventTypeAndOperations[type];
    if (operations) {
      operations.push(operation);
    } else {
      operations = [operation];
    }
    this.eventTypeAndOperations[type] = operations;
  }

  removeAll(type: string): void {
    this.eventTypeAndOperations[type] = [];
  }

  dispatch(payload: EventsApiPayload, request: Request, response: Response) {
    if (payload.type === 'url_verification') {
      response.status(200).write(payload.event.challenge);
    } else if (payload.type && payload.type === 'event_callback') {
      const operation = this.eventTypeAndOperations[payload.event.type];
      if (operation) {
        operation.forEach(operation => {
          operation.run(new EventsApiOperationArgs(payload, request, response));
        });
      } else {
        console.log(`No operations registered for type: ${payload.event.type}`)
      }
    }
  }
}
