package test_locally.docs;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.model.view.View;
import com.slack.api.model.view.ViewState;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.block.element.BlockElements.staticSelect;
import static com.slack.api.model.view.Views.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// https://slack.dev/java-slack-sdk/guides/modals
public class ModalsTest {

    App app = new App(AppConfig.builder().signingSecret("foo").signingSecret("xoxb-xxx").build());

    View buildView() {
        return view(view -> view
                .callbackId("meeting-arrangement")
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text("Meeting Arrangement").emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .privateMetadata("{\"response_url\":\"https://hooks.slack.com/actions/T1ABCD2E12/330361579271/0dAEyLY19ofpLwxqozy3firz\"}")
                .blocks(asBlocks(
                        section(section -> section
                                .blockId("category-block")
                                .text(markdownText("Select a category of the meeting!"))
                                .accessory(staticSelect(staticSelect -> staticSelect
                                        .actionId("category-selection-action")
                                        .placeholder(plainText("Select a category"))
                                        .options(asOptions(
                                                option(plainText("Customer"), "customer"),
                                                option(plainText("Partner"), "partner"),
                                                option(plainText("Internal"), "internal")
                                        ))
                                ))
                        ),
                        input(input -> input
                                .blockId("agenda-block")
                                .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
                                .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
                        )
                ))
        );
    }

    @Test
    public void example() {
        View view = buildView();
        assertNotNull(view);
        assertEquals(2, view.getBlocks().size());
    }

    View buildViewByCategory(String categoryId, String privateMetadata) {
        return null; // TODO
    }

    @Test
    public void boltApp() {
        app.blockAction("category-selection-action", (req, ctx) -> {
            View currentView = req.getPayload().getView();
            String privateMetadata = currentView.getPrivateMetadata();
            Map<String, Map<String, ViewState.Value>> stateValues = currentView.getState().getValues();
            String categoryId = stateValues.get("category-block").get("category-selection-action").getSelectedOption().getValue();
            View viewForTheCategory = buildViewByCategory(categoryId, privateMetadata);
            ViewsUpdateResponse viewsUpdateResp = ctx.client().viewsUpdate(r -> r
                    .viewId(currentView.getId())
                    .hash(currentView.getHash())
                    .view(viewForTheCategory)
            );
            return ctx.ack();
        });

        // when a user clicks "Submit"
        app.viewSubmission("meeting-arrangement", (req, ctx) -> {
            String privateMetadata = req.getPayload().getView().getPrivateMetadata();
            Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
            String agenda = stateValues.get("agenda-block").get("agenda-action").getValue();
            Map<String, String> errors = new HashMap<>();
            if (agenda.length() <= 10) {
                errors.put("agenda-block", "Agenda needs to be longer than 10 characters.");
            }
            if (!errors.isEmpty()) {
                return ctx.ack(r -> r.responseAction("errors").errors(errors));
            } else {
                // TODO: may store the stateValues and privateMetadata

                // Responding with an empty body means closing the modal now.
                // If your app has next steps, respond with other response_action and a modal view.
                return ctx.ack();
            }
        });

        View renewedView = null;
        View newViewInStack = null;
        app.viewSubmission("meeting-arrangement", (req, ctx) -> {
            ctx.ack(r -> r.responseAction("update").view(renewedView));
            return ctx.ack(r -> r.responseAction("push").view(newViewInStack));
        });

        // when a user clicks "Cancel"
        // "notify_on_close": true is required
        app.viewClosed("meeting-arrangement", (req, ctx) -> {
            // Do some cleanup tasks
            return ctx.ack();
        });
    }
}
