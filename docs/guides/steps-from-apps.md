---
layout: default
title: "Workflow Steps"
lang: en
---

# Workflow Steps from Apps

Workflow Steps from apps allow your app to create and process custom workflow steps that users can add using [Workflow Builder](https://api.slack.com/workflows).

A workflow step is made up of three distinct user events:

* Adding or editing the step in a Workflow
* Saving or updating the step's configuration
* The end user's execution of the step

All three events must be handled for a workflow step to function. Read more about workflow steps from apps in the [API documentation](https://api.slack.com/workflows/steps).

### Slack App Configuration

To enable Workflow Steps, visit the [Slack App configuration page](http://api.slack.com/apps), choose the app you're working on, and go to **Features** > **Workflow Steps** on the left pane. There are three things to do on the page.

* Turn on the feature (**Turn on workflow steps**)
* Click the **Add Step** button and set the name and Callback ID

Also, your app requires the **Interactivity**. Go to <a href="{{ site.url | append: site.baseurl }}/guides/interactive-components">Interactive Components</a> and set the Request URL to receive events from Slack.

---
## Creating workflow steps

To create a workflow step, Bolt provides the `WorkflowStep` class.

When instantiating a new `WorkflowStep`, pass in the step's `callback_id` and a configuration object.

The configuration object contains three properties: `edit`, `save`, and `execute`. Each of these properties must be a single listener object. All listeners have access to a `step` object that contains information about the workflow step event.

After instantiating a `WorkflowStep`, you can pass it into `app.step()`. Behind the scenes, your app will listen and respond to the workflow step’s events using the callbacks provided in the configuration object.

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.middleware.builtin.WorkflowStep;

// Initiate the Bolt app as you normally would
App app = new App();

WorkflowStep step = WorkflowStep.builder()
  .callbackId("copy_review")
  .edit((req, ctx) -> { return ctx.ack(); })
  .save((req, ctx) -> { return ctx.ack(); })
  .execute((req, ctx) -> { return ctx.ack(); })
  .build();

app.step(step);
```

---
## Adding or editing workflow steps

When a builder adds (or later edits) your step in their workflow, your app will receive a [`workflow_step_edit` event](https://api.slack.com/reference/workflows/workflow_step_edit). The `edit` callback in your `WorkflowStep` configuration will be run when this event is received.

Whether a builder is adding or editing a step, you need to send them a [workflow step configuration modal](https://api.slack.com/reference/workflows/configuration-view). This modal is where step-specific settings are chosen, and it has more restrictions than typical modals—most notably, it cannot include `title`, `submit`, or `close` properties. By default, the configuration modal's `callback_id` will be the same as the workflow step.

Within the `edit` callback, the `configure()` utility can be used to easily open your step's configuration modal by passing in an object with your view's `blocks`. To disable saving the configuration before certain conditions are met, pass in `submit_disabled` with a value of `true`.

To learn more about opening configuration modals, [read the documentation](https://api.slack.com/workflows/steps#handle_config_view).

```java
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;

WorkflowStep step = WorkflowStep.builder()
  .callbackId("copy_review")
  .edit((req, ctx) -> {
    ctx.configure(asBlocks(
      section(s -> s.blockId("intro-section").text(plainText("text"))),
      input(i -> i
        .blockId("task_name_input")
        .element(plainTextInput(pti -> pti.actionId("task_name")))
        .label(plainText("Task name"))
      ),
      input(i -> i
        .blockId("task_description_input")
        .element(plainTextInput(pti -> pti.actionId("task_description")))
        .label(plainText("Task description"))
      ),
      input(i -> i
        .blockId("task_author_input")
        .element(plainTextInput(pti -> pti.actionId("task_author")))
        .label(plainText("Task author"))
      )
    ));
    return ctx.ack();
  })
  .save((req, ctx) -> { return ctx.ack(); })
  .execute((req, ctx) -> { return ctx.ack(); })
  .build();

app.step(step);
```

---
## Saving step configurations

After the configuration modal is opened, your app will listen for the `view_submission` request. The `save` callback in your `WorkflowStep` configuration will be run when this request is received.

Within the `save` callback, the `update()` method can be used to save the builder's step configuration by passing in the following arguments:

* `inputs` is an object representing the data your app expects to receive from the user upon workflow step execution.
* `outputs` is an array of objects containing data that your app will provide upon the workflow step's completion. Outputs can then be used in subsequent steps of the workflow.
* `stepName` overrides the default Step name
* `stepImageUrl` overrides the default Step image

To learn more about how to structure these parameters, [read the documentation](https://api.slack.com/reference/workflows/workflow_step).

```java
import java.util.*;
import com.slack.api.model.workflow.*;
import static com.slack.api.model.workflow.WorkflowSteps.*;

static String extract(Map<String, Map<String, ViewState.Value>> stateValues, String blockId, String actionId) {
  return stateValues.get(blockId).get(actionId).getValue();
}

WorkflowStep step = WorkflowStep.builder()
  .callbackId("copy_review")
  .edit((req, ctx) -> { return ctx.ack(); })
  .save((req, ctx) -> {
    Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
    Map<String, WorkflowStepInput> inputs = new HashMap<>();
    inputs.put("taskName", stepInput(i -> i.value(extract(stateValues, "task_name_input", "task_name"))));
    inputs.put("taskDescription", stepInput(i -> i.value(extract(stateValues, "task_description_input", "task_description"))));
    inputs.put("taskAuthorEmail", stepInput(i -> i.value(extract(stateValues, "task_author_input", "task_author"))));
    List<WorkflowStepOutput> outputs = asStepOutputs(
      stepOutput(o -> o.name("taskName").type("text").label("Task Name")),
      stepOutput(o -> o.name("taskDescription").type("text").label("Task Description")),
      stepOutput(o -> o.name("taskAuthorEmail").type("text").label("Task Author Email"))
    );
    ctx.update(inputs, outputs);
    return ctx.ack();
  })
  .execute((req, ctx) -> { return ctx.ack(); })
  .build();

app.step(step);
```

---
## Executing workflow steps

When your workflow step is executed by an end user, your app will receive a [`workflow_step_execute` event](https://api.slack.com/events/workflow_step_execute). The `execute` callback in your `WorkflowStep` configuration will be run when this event is received.

Using the `inputs` from the `save` callback, this is where you can make third-party API calls, save information to a database, update the user's Home tab, or decide the outputs that will be available to subsequent workflow steps by mapping values to the `outputs` object.

Within the `execute` callback, your app must either call `complete()` to indicate that the step's execution was successful, or `fail()` to indicate that the step's execution failed.

```java
import java.util.*;
import com.slack.api.model.workflow.*;

WorkflowStep step = WorkflowStep.builder()
  .callbackId("copy_review")
  .edit((req, ctx) -> { return ctx.ack(); })
  .save((req, ctx) -> { return ctx.ack(); })
  .execute((req, ctx) -> {
    WorkflowStepExecution wfStep = req.getPayload().getEvent().getWorkflowStep();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("taskName", wfStep.getInputs().get("taskName").getValue());
    outputs.put("taskDescription", wfStep.getInputs().get("taskDescription").getValue());
    outputs.put("taskAuthorEmail", wfStep.getInputs().get("taskAuthorEmail").getValue());
    try {
      ctx.complete(outputs);
    } catch (Exception e) {
      Map<String, Object> error = new HashMap<>();
      error.put("message", "Something wrong!");
      ctx.fail(error);
    }
    return ctx.ack();
  })
  .build();

app.step(step);
```