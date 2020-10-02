package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.middleware.builtin.WorkflowStep;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.view.ViewState;
import com.slack.api.model.workflow.WorkflowStepExecution;
import com.slack.api.model.workflow.WorkflowStepInput;
import com.slack.api.model.workflow.WorkflowStepOutput;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.workflow.WorkflowSteps.*;

public class WorkflowStepMiddlewareSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig("appConfig_stepsFromApps.json");
        App app = new App(config);

        WorkflowStep step = WorkflowStep.builder()
                .callbackId("copy_review")
                .executeAutoAcknowledgement(true)
                .edit((req, ctx) -> {
                    ViewsOpenResponse apiResponse = ctx.configure(asBlocks(
                            section(s -> s
                                    .blockId("intro-section")
                                    .text(plainText("Create a task in one of the listed projects. The link to the task and other details will be available as variable data in later steps."))
                            ),
                            input(i -> i
                                    .blockId("task_name_input")
                                    .element(plainTextInput(pti -> pti
                                            .actionId("task_name")
                                            .placeholder(plainText("Write a task name"))))
                                    .label(plainText("Task name"))
                            ),
                            input(i -> i
                                    .blockId("task_description_input")
                                    .element(plainTextInput(pti -> pti
                                            .actionId("task_description")
                                            .placeholder(plainText("Write a description for your task"))))
                                    .label(plainText("Task description"))
                            ),
                            input(i -> i
                                    .blockId("task_author_input")
                                    .element(plainTextInput(pti -> pti
                                            .actionId("task_author")
                                            .placeholder(plainText("Set a task author"))))
                                    .label(plainText("Task author"))
                            )
                    ));
                    return ctx.ack();
                })
                .save((req, ctx) -> {
                    Map<String, Map<String, ViewState.Value>> stateValues =
                            req.getPayload().getView().getState().getValues();
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
        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

    static String extract(Map<String, Map<String, ViewState.Value>> stateValues, String blockId, String actionId) {
        return stateValues.get(blockId).get(actionId).getValue();
    }

}
