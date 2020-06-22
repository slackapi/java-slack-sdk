package test_with_remote_apis.sample_json_generation;

import com.google.gson.JsonElement;
import com.slack.api.model.*;
import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ImageElement;
import com.slack.api.util.json.GsonFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static test_with_remote_apis.sample_json_generation.ObjectInitializer.initProperties;

public class SampleObjects {

    private static final String imageUrl = "https://is5-ssl.mzstatic.com/image/thumb/Purple3/v4/d3/72/5c/d3725c8f-c642-5d69-1904-aa36e4297885/source/256x256bb.jpg";

    private SampleObjects() {
    }

    public static class Json {
        private Json() {
        }

        public static List<JsonElement> Attachments = Arrays.asList(
                GsonFactory.createSnakeCase().toJsonTree(SampleObjects.Attachments.get(0))
        );

        public static List<JsonElement> Blocks = Arrays.asList(
                GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        ActionsBlock.builder().elements(BlockElements).build())),
                GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        SectionBlock.builder()
                                .accessory(initProperties(ImageElement.builder().imageUrl(imageUrl).build()))
                                .text(TextObject)
                                .fields(SectionBlockFieldElements)
                                .build()))
        );
    }

    public static List<Attachment> Attachments = Arrays.asList(
            initProperties(Attachment.builder()
                    .fields(Arrays.asList(initProperties(Field.builder().build())))
                    .actions(Arrays.asList(initProperties(Action.builder()
                            .optionGroups(Arrays.asList(initProperties(Action.OptionGroup.builder().build())))
                            .options(Arrays.asList(initProperties(Action.Option.builder().build())))
                            .selectedOptions(Arrays.asList(initProperties(Action.Option.builder().build())))
                            .build())))
                    .mrkdwnIn(Arrays.asList(""))
                    .build())
    );

    public static TextObject TextObject = initProperties(PlainTextObject.builder().build());

    public static ConfirmationDialogObject Confirm = ConfirmationDialogObject.builder().text(TextObject).build();

    public static List<BlockElement> BlockElements = asElements(
            initProperties(button(b -> b.actionId("button-action").confirm(Confirm))),
            initProperties(channelsSelect(c -> c.confirm(Confirm))),
            initProperties(conversationsSelect(c -> c.confirm(Confirm))),
            initProperties(datePicker(d -> d.confirm(Confirm))),
            initProperties(overflowMenu(o -> o
                    .confirm(Confirm)
                    .actionId("overflow-action")
                    .options(asOptions(option(op -> op.text(plainText("l")).value("v"))))
            )),
            initProperties(usersSelect(u -> u.confirm(Confirm)))
    );
    public static List<TextObject> SectionBlockFieldElements = asSectionFields(
            initProperties(plainText(pt -> pt)),
            initProperties(markdownText(m -> m))
    );
    public static List<LayoutBlock> Blocks = asBlocks(
            initProperties(section(s -> s
                    .accessory(initProperties(ImageElement.builder().imageUrl(imageUrl).build()))
                    .text(TextObject)
                    .fields(SectionBlockFieldElements)))
    );

    public static Message Message = new Message();

    static {
        Message.setAttachments(Attachments);
        Message.setBlocks(Blocks);
        File.Shares shares = new File.Shares();
        Map<String, List<File.ShareDetail>> channels = new HashMap<>();
        File.ShareDetail shareDetail = initProperties(new File.ShareDetail());
        shareDetail.setReplyUsers(Arrays.asList(""));
        channels.put("C03E94MKU", Arrays.asList(shareDetail));
        channels.put("C03E94MKU_", Arrays.asList(shareDetail));
        shares.setPrivateChannels(channels);
        shares.setPublicChannels(channels);
        File file = initProperties(File.builder().shares(shares).build());
        Message.setFile(file);
        Message.setFiles(Arrays.asList(file));
        Message.setPinnedTo(Arrays.asList(""));
        Message.setReactions(Arrays.asList(initProperties(new Reaction())));
        Message.setReplyUsers(Arrays.asList(""));
    }

}
