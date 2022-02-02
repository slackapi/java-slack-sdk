package util.sample_json_generation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.slack.api.model.*;
import com.slack.api.model.admin.AppIcons;
import com.slack.api.model.block.*;
import com.slack.api.model.block.composition.*;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ImageElement;
import com.slack.api.model.block.element.PlainTextInputElement;
import com.slack.api.model.block.element.RadioButtonsElement;
import com.slack.api.util.json.GsonFactory;

import java.util.*;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static util.ObjectInitializer.initProperties;

public class SampleObjects {

    private SampleObjects() {
    }

    public static class Json {
        private Json() {
        }

        public static List<JsonElement> Attachments = Arrays.asList(
                GsonFactory.createSnakeCase().toJsonTree(SampleObjects.Attachments.get(0))
        );

        private static List<OptionObject> Options = Arrays.asList(initProperties(
                OptionObject.builder().text(plainText("")).description(plainText("")).build()
        ));
        private static List<OptionGroupObject> OptionGroups = Arrays.asList(initProperties(
                OptionGroupObject.builder().options(Options).label(plainText("")).build()
        ));

        public static List<JsonElement> SectionBlocksWithAccessory = new ArrayList<>();

        static {
            for (BlockElement element : BlockElements) {
                SectionBlocksWithAccessory.add(GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        SectionBlock.builder()
                                .accessory(element)
                                .text(TextObject)
                                .fields(SectionBlockFieldElements)
                                .build())));
            }
        }

        public static List<JsonElement> InputBlocks = new ArrayList<>();

        static {
            for (BlockElement element : BlockElements) {
                InputBlocks.add(GsonFactory.createSnakeCase().toJsonTree(initProperties(
                        InputBlock.builder()
                                .element(element)
                                .hint(TextObject)
                                .label(TextObject)
                                .build())));
            }
        }

        public static List<JsonElement> Blocks = initBlocks();

        private static List<JsonElement> initBlocks() {
            List<JsonElement> blocks = new ArrayList<>();
            Gson gson = GsonFactory.createSnakeCase();

            CallBlock.CallData callData = initProperties(new CallBlock.CallData());
            CallBlock.Call call = initProperties(new CallBlock.Call());
            call.setAllParticipants(Arrays.asList(initProperties(new CallParticipant())));
            call.setActiveParticipants(Arrays.asList(initProperties(new CallParticipant())));
            call.setAppIconUrls(initProperties(new AppIcons()));
            call.setChannels(Arrays.asList(""));
            callData.setV1(call);
            CallBlock callBlock = CallBlock.builder().call(initProperties(callData)).build();

            PlainTextObject textObject = initProperties(new PlainTextObject());

            HeaderBlock headerBlock = initProperties(new HeaderBlock());
            headerBlock.setText(textObject);

            ImageBlock imageBlock = initProperties(ImageBlock.builder().build());
            imageBlock.setTitle(textObject);

            blocks.addAll(
                    Arrays.asList(
                            gson.toJsonTree(initProperties(ActionsBlock.builder().elements(BlockElements).build())),
                            gson.toJsonTree(initProperties(ContextBlock.builder().elements(ContextBlockElements).build())),
                            gson.toJsonTree(initProperties(callBlock)),
                            gson.toJsonTree(initProperties(DividerBlock.builder().build())),
                            gson.toJsonTree(initProperties(new FileBlock())),
                            gson.toJsonTree(headerBlock),
                            gson.toJsonTree(imageBlock)
                    )
            );

            blocks.addAll(SectionBlocksWithAccessory);
            blocks.addAll(InputBlocks);
            return blocks;
        }
    }

    public static List<Attachment> Attachments = Arrays.asList(
            initProperties(Attachment.builder()
                    .fields(Arrays.asList(initProperties(Field.builder().build())))
                    .actions(Arrays.asList(initProperties(
                            Action.builder()
                                    .optionGroups(Arrays.asList(initProperties(Action.OptionGroup.builder()
                                            .options(Arrays.asList(initProperties(Action.Option.builder().build())))
                                            .build(), true)))
                                    .options(Arrays.asList(initProperties(Action.Option.builder().build(), true)))
                                    .selectedOptions(Arrays.asList(initProperties(Action.Option.builder().build(), true)))
                                    .build(),
                            true
                    )))
                    .mrkdwnIn(Arrays.asList(""))
                    .build())
    );

    public static PlainTextObject TextObject = initProperties(PlainTextObject.builder().build());

    public static OptionObject Option = initProperties(OptionObject.builder()
            .text(TextObject)
            .description(PlainTextObject.builder().build())
            .build());

    public static ConfirmationDialogObject Confirm = ConfirmationDialogObject.builder().text(TextObject).build();

    public static List<BlockElement> BlockElements = asElements(
            initProperties(button(b -> b.confirm(Confirm))),
            initProperties(checkboxes(b -> b.initialOptions(Arrays.asList(Option)).confirm(Confirm))),
            initProperties(radioButtons(b -> b.initialOption(Option).confirm(Confirm))),
            initProperties(channelsSelect(c -> c.confirm(Confirm))),
            initProperties(multiChannelsSelect(c -> c.initialChannels(Arrays.asList("")).confirm(Confirm))),
            initProperties(conversationsSelect(c -> c.confirm(Confirm))),
            initProperties(multiConversationsSelect(c -> c.initialConversations(Arrays.asList("")).confirm(Confirm))),
            initProperties(datePicker(d -> d.confirm(Confirm))),
            initProperties(timePicker(d -> d.confirm(Confirm))),
            initProperties(externalSelect(e -> e.initialOption(Option).confirm(Confirm))),
            initProperties(multiExternalSelect(e -> e.initialOptions(Arrays.asList(Option)).confirm(Confirm))),
            initProperties(com.slack.api.model.block.element.BlockElements.image(i -> i)),
            initProperties(overflowMenu(o -> o.confirm(Confirm))),
            initProperties(staticSelect(s -> s.initialOption(Option).confirm(Confirm))),
            initProperties(multiStaticSelect(s -> s.initialOptions(Arrays.asList(Option)).confirm(Confirm))),
            initProperties(usersSelect(u -> u.confirm(Confirm))),
            initProperties(multiUsersSelect(u -> u.initialUsers(Arrays.asList("")).confirm(Confirm)))
    );
    public static List<ContextBlockElement> ContextBlockElements = asContextElements(
            initProperties(ImageElement.builder().build())
    );
    public static List<TextObject> SectionBlockFieldElements = asSectionFields(
            initProperties(plainText(pt -> pt)),
            initProperties(markdownText(m -> m))
    );

    public static List<SectionBlock> SectionBlocksWithAccessory = new ArrayList<>();

    static {
        for (BlockElement element : BlockElements) {
            SectionBlocksWithAccessory.add(initProperties(
                    SectionBlock.builder()
                            .accessory(element)
                            .text(TextObject).fields(SectionBlockFieldElements).build()));
        }
    }

    public static List<LayoutBlock> Blocks = new ArrayList<>();

    static {
        Blocks.addAll(asBlocks(
                initProperties(actions(a -> a.elements(BlockElements))),
                initProperties(context(c -> c.elements(ContextBlockElements))),
                initProperties(divider()),
                initProperties(com.slack.api.model.block.Blocks.image(i -> i)),
                initProperties(section(s -> s
                        .accessory(initProperties(ImageElement.builder().build()))
                        .text(TextObject)
                        .fields(SectionBlockFieldElements)))
        ));
        Blocks.addAll(SectionBlocksWithAccessory);
    }

    public static PlainTextInputElement plainTextInputElement = initProperties(PlainTextInputElement.builder()
            .placeholder(initProperties(PlainTextObject.builder().build()))
            .dispatchActionConfig(initProperties(DispatchActionConfig.builder().triggerActionsOn(Arrays.asList("")).build()))
            .build());
    public static RadioButtonsElement radioButtonsElement = initProperties(RadioButtonsElement.builder()
            .confirm(initProperties(ConfirmationDialogObject.builder()
                    .text(initProperties(PlainTextObject.builder().build()))
                    .build()))
            .options(Arrays.asList(
                    initProperties(OptionObject.builder().text(initProperties(PlainTextObject.builder().build())).build()),
                    initProperties(OptionObject.builder().text(initProperties(MarkdownTextObject.builder().build())).build())
            ))
            .initialOption(initProperties(OptionObject.builder().text(initProperties(PlainTextObject.builder().build())).build()))
            .build());

    public static List<LayoutBlock> ModalBlocks = asBlocks(
            initProperties(input(i -> i.element(plainTextInputElement))),
            initProperties(input(i -> i.element(radioButtonsElement))),
            initProperties(input(i -> i.element(initProperties(button(b -> b.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(channelsSelect(c -> c.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(conversationsSelect(c -> c.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(datePicker(d -> d.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(timePicker(d -> d.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(externalSelect(e -> e.initialOption(Option).confirm(Confirm)))))),
            initProperties(input(ip -> ip.element(initProperties(com.slack.api.model.block.element.BlockElements.image(i -> i))))),
            initProperties(input(i -> i.element(initProperties(overflowMenu(o -> o.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(staticSelect(s -> s.initialOption(Option).confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(usersSelect(u -> u.confirm(Confirm)))))),
            initProperties(actions(a -> a.elements(BlockElements))),
            initProperties(context(c -> c.elements(ContextBlockElements))),
            initProperties(divider()),
            initProperties(com.slack.api.model.block.Blocks.image(i -> i)),
            initProperties(section(s -> s
                    .accessory(initProperties(ImageElement.builder().build()))
                    .text(TextObject)
                    .fields(SectionBlockFieldElements)))
    );

    public static File FileObject = initFileObject();

    public static File initFileObject() {
        File.Shares shares = new File.Shares();
        Map<String, List<File.ShareDetail>> channels = new HashMap<>();
        File.ShareDetail shareDetail = initProperties(new File.ShareDetail());
        shareDetail.setReplyUsers(Arrays.asList(""));
        channels.put("C03E94MKU", Arrays.asList(shareDetail));
        channels.put("C03E94MKU_", Arrays.asList(shareDetail));
        shares.setPrivateChannels(channels);
        shares.setPublicChannels(channels);
        List<String> stringList = Arrays.asList("");
        List<File.Address> addressList = Arrays.asList(initProperties(new File.Address()));
        List<Reaction> reactionList = Arrays.asList(initProperties(Reaction.builder().users(stringList).build()));
        File file = initProperties(File.builder()
                .headers(initProperties(new com.slack.api.model.File.Headers()))
                .shares(shares)
                .channels(stringList)
                .groups(stringList)
                .ims(stringList)
                .to(addressList)
                .from(addressList)
                .cc(addressList)
                .pinnedTo(stringList)
                .reactions(reactionList)
                .attachments(Attachments)
                .blocks(Blocks)
                .build());
        return file;
    }

    public static Message Message = new Message();

    static {
        Message.setAttachments(Attachments);
        Message.setBlocks(Blocks);
        Message.setFile(FileObject);
        Message.setFiles(Arrays.asList(FileObject));
        Message.setPinnedTo(Arrays.asList(""));
        Message.setReactions(Arrays.asList(initProperties(new Reaction())));
        Message.setReplyUsers(Arrays.asList(""));
    }

}
