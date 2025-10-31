package test_locally.api.model.block;

import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.composition.SlackFileObject;
import com.slack.api.model.block.composition.WorkflowObject;
import com.slack.api.model.block.element.BlockElement;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.confirmationDialog;
import static com.slack.api.model.block.composition.BlockCompositions.feedbackButton;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlocksTest {

    @Test
    public void testActions() {
        List<BlockElement> elements = asElements(button(r -> r.value("v")));
        {
            ActionsBlock block = actions(elements);
            assertThat(block, is(notNullValue()));
        }
        {
            ActionsBlock block = actions("block-id", elements);
            assertThat(block, is(notNullValue()));
        }
    }

    @Test
    public void testContext() {
        List<ContextBlockElement> elements = asContextElements(imageElement(r -> r.imageUrl("https://www.example.com/logo.png")));
        {
            ContextBlock block = context(elements);
            assertThat(block, is(notNullValue()));
        }
        {
            ContextBlock block = context("block-id", elements);
            assertThat(block, is(notNullValue()));
        }
    }

    @Test
    public void testDivider() {
        assertThat(divider(), is(notNullValue()));
        assertThat(divider("block-id"), is(notNullValue()));
    }

    @Test
    public void testFile() {
        assertThat(file(f -> f.blockId("block-id")), is(notNullValue()));
    }

    @Test
    public void testCall() {
        assertThat(call(f -> f.blockId("block-id").callId("R111")), is(notNullValue()));
    }

    @Test
    public void testContextActions() {
        assertThat(contextActions(b -> b
                .elements(asContextActionsElements(
                        feedbackButtons(f -> f
                            .actionId("feedback")
                            .positiveButton(
                                feedbackButton(p -> p
                                    .text(plainText("Good Response"))
                                    .value("good-feedback")
                                )
                            )
                            .negativeButton(
                                feedbackButton(n -> n
                                    .text(plainText("Bad Response"))
                                    .value("bad-feedback")
                                )
                            )
                        ),
                        iconButton(i -> i
                            .icon("trash")
                            .text(plainText("Remove"))
                            .confirm(
                                confirmationDialog(c -> c
                                    .title(plainText("Oops"))
                                    .text(plainText("This response might've been just alright..."))
                                    .style("danger")
                                )
                            )
                            .visibleToUserIds(Arrays.asList("USLACKBOT", "U0123456789"))
                            )
                    ))
                ), is(notNullValue()));
    }

    @Test
    public void testImage() {
        assertThat(Blocks.image(i -> i.blockId("block-id").imageUrl("https://www.example.com/")), is(notNullValue()));
        assertThat(Blocks.image(i -> i
                .blockId("block-id")
                .slackFile(SlackFileObject.builder().id("F111111").build())
        ), is(notNullValue()));
        assertThat(Blocks.image(i -> i
                .blockId("block-id")
                .slackFile(SlackFileObject.builder().url("https://files.slack.com/files-pri/T111-F111/foo.png").build())
        ), is(notNullValue()));
    }

    @Test
    public void testInput() {
        assertThat(input(i -> i.blockId("block-id").element(button(b -> b.value("v")))), is(notNullValue()));
    }

    @Test
    public void testHeader() {
        assertThat(header(h -> h.blockId("block-id").text(plainText("This is the headline!"))), is(notNullValue()));
    }

    @Test
    public void testMarkdown() {
        assertThat(markdown(h -> h.text("**this is bold**")), is(notNullValue()));
    }

    @Test
    public void testRichText() {
        assertThat(richText(i -> i
                .blockId("block-id")
                .elements(asElements(button(b -> b.value("v"))))
        ), is(notNullValue()));
    }

    @Test
    public void testDateTimePicker() {
        assertThat(datetimePicker(h -> h.actionId("block-id").initialDateTime(12345)), is(notNullValue()));
    }

    @Test
    public void testNewTextInputs() {
        assertThat(emailTextInput(h -> h.actionId("block-id").initialValue("foo@example.com")), is(notNullValue()));
        assertThat(urlTextInput(h -> h.actionId("block-id").initialValue("https:/www.example.com")), is(notNullValue()));
        assertThat(numberInput(h -> h.actionId("block-id").initialValue("12345")), is(notNullValue()));
    }

    @Test
    public void testWorkflowButton() {
        assertThat(workflowButton(h -> h.actionId("block-id")
                .workflow(WorkflowObject.builder()
                        .trigger(WorkflowObject.Trigger.builder()
                                .url("https://slack.com/shortcuts/Ft0123ABC456/xyz...zyx")
                                .customizableInputParameters(Arrays.asList(
                                        WorkflowObject.Trigger.InputParameter.builder().name("a").value("b").build()
                                ))
                                .build())
                        .build())
                .text(plainText("Click this"))
                .style("danger")
                .accessibilityLabel("accessibility")
        ), is(notNullValue()));
    }

}
