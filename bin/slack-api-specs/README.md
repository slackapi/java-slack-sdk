# slack-api-specs

This repository contains API specifications of Slack platform features and APIs.

You'll find [OpenAPI specs](https://swagger.io/specification/) for the [Slack Web API](https://api.slack.com/web) and [AsyncAPI](https://www.asyncapi.com/v1/guide/) specs for the [Events API](https://api.slack.com/events-api).

Read more about our open specification strategy in [this announcement](https://medium.com/slack-developer-blog/standard-practice-slack-web-openapi-spec-daaad18c7f8).

### Specification menu

* [Web API](web-api)
    - [OpenAPI 2.0 spec](web-api/slack_web_openapi_v2.json) - covers user and bot user token usage of public [Web API](https://api.slack.com/web) methods
* [Events API](events-api)
    - [AsyncAPI 1.0 spec](events-api/slack_events_api_async_v1.json) - a catalog of JSON schema for basic [Events API](https://api.slack.com/events-api) structure and a handful of detailed event types
    - [JSON Schema](events-api/slack_common_event_wrapper_schema.json) - covers only the basic event wrapper all event types delivered by Slack

We continue to refine and expand schema and example coverage throughout all of our specifications.

### How the specs are made

We use a combination of internal metadata, custom scripting, and old fashioned writing-by-hand to produce these specifications. They don't always tell the whole truth and are subject to author and operator error. They are really useful though.

### Pull requests

Because our specifications are _artifacts_ of an incredible machine, we cannot accept pull requests for this repo. Please file issues with suggestions or bugs with the spec itself. Feedback about the APIs or features the specs describe should be directed to Slack's [developer support team](mailto:feedback@slack.com).