---
title: Custom Steps
lang: en
---

Your app can use the `function()` method to listen to incoming [custom step requests](/workflows/workflow-steps). Custom steps are used in Workflow Builder to build workflows. The method requires a step `callback_id` of type `string`. This `callback_id` must also be defined in your [Function](/reference/app-manifest#functions) definition. Custom steps must be finalized using the `complete()` or `fail()` listener arguments to notify Slack that your app has processed the request.

* `complete()` requires one argument: an `outputs` object. It ends your custom step successfully and provides an object containing the outputs of your custom step as per its definition.
* `fail()` requires one argument: `error` of type `string`. It ends your custom step unsuccessfully and provides a message containing information regarding why your custom step failed.

You can reference your custom step's inputs using the `getInputs()` method shown below.

```java
app.function("sample_function", (req, ctx) -> {
  app.executorService().submit(() -> {
    try {
      String userId = req.getEvent().getInputs().get("user_id").asString();
      ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
        .channel(userId) // sending a DM
        .text("Hi! Thank you for submitting the request! We'll let you know once the processing completes.")
      );
      Map<String, Object> outputs = new HashMap<String, Object>();
      outputs.put("channel_id", response.getChannel());
      outputs.put("ts", response.getTs());
      ctx.complete(outputs);
    } catch (Exception e) {
      ctx.logger.error(e.getMessage(), e);
      try {
        ctx.fail("Failed to handle 'sample_function' custom step execution (error: " + e.getMessage() + ")");
      } catch (Exception ex) {
        ctx.logger.error(e.getMessage(), e);
      }
    }
  });
  return ctx.ack();
});
```

The corresponding function definition section of the app manifest for the preceding function might look like this:

```json
...
"functions": {
    "sample_function": {
        "title": "Send a request",
        "description": "Send some request to the backend",
        "input_parameters": {
            "user_id": {
                "type": "slack#/types/user_id",
                "title": "User",
                "description": "Who to send it",
                "is_required": true,
                "hint": "Select a user in the workspace",
                "name": "user_id"
            }
        },
        "output_parameters": {
            "channel_id": {
                "type": "slack#/types/channel_id",
                "title": "DM ID",
                "description": "The DM ID",
                "is_required": true,
                "name": "channel_id"
            },
            "ts": {
                "type": "string",
                "title": "Message timestamp",
                "description": "Sent message timestamp",
                "is_required": true,
                "name": "ts"
            }
        }
    }
}
```

Once your custom step is defined in your app's manifest and implemented in code, it is discoverable in Workflow Builder when you **Add Step** and search for the title of your step or name of your app.