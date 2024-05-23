package util.sample_json_generation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.slack.api.model.*;
import com.slack.api.model.admin.AppIcons;
import com.slack.api.model.block.*;
import com.slack.api.model.block.composition.*;
import com.slack.api.model.block.element.*;
import com.slack.api.model.manifest.AppManifest;
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

        public static List<JsonElement> Attachments = new ArrayList<>();

        static {
            for (Attachment attachment : SampleObjects.Attachments) {
                Attachments.add(GsonFactory.createSnakeCase().toJsonTree(attachment));
            }
        }

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

        // This list object is used for generating comprehensive blocks for generating JSON data.
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
            imageBlock.setSlackFile(initProperties(SlackFileObject.builder().build()));

            blocks.addAll(
                    Arrays.asList(
                            gson.toJsonTree(initProperties(ActionsBlock.builder().elements(BlockElements).build())),
                            gson.toJsonTree(initProperties(ContextBlock.builder().elements(ContextBlockElements).build())),
                            gson.toJsonTree(initProperties(callBlock)),
                            gson.toJsonTree(initProperties(DividerBlock.builder().build())),
                            gson.toJsonTree(initProperties(new FileBlock())),
                            gson.toJsonTree(headerBlock),
                            gson.toJsonTree(imageBlock),
                            gson.toJsonTree(initProperties(video(v -> v.description(TextObject).title(TextObject)))),
                            gson.toJsonTree(RichTextBlock),
                            gson.toJsonTree(initProperties(ShareShortcutBlock.builder().appCollaborators(Arrays.asList("")).build()))
                    )
            );

            blocks.addAll(SectionBlocksWithAccessory);
            blocks.addAll(InputBlocks);
            return blocks;
        }
    }

    public static PlainTextObject TextObject = initProperties(PlainTextObject.builder().build());

    public static OptionObject Option = initProperties(OptionObject.builder()
            .text(TextObject)
            .description(PlainTextObject.builder().build())
            .build());

    public static ConfirmationDialogObject Confirm = ConfirmationDialogObject.builder().text(TextObject).build();
    public static WorkflowObject Workflow = WorkflowObject.builder()
            .trigger(WorkflowObject.Trigger.builder()
                    .url("")
                    .customizableInputParameters(Arrays.asList(WorkflowObject.Trigger.InputParameter.builder().name("").value("").build()))
                    .build())
            .build();

    public static List<BlockElement> BlockElements = asElements(
            initProperties(button(b -> b.confirm(Confirm))),
            initProperties(workflowButton(b -> b.workflow(Workflow))),
            initProperties(checkboxes(b -> b.initialOptions(Arrays.asList(Option)).confirm(Confirm))),
            initProperties(radioButtons(b -> b.initialOption(Option).confirm(Confirm))),
            initProperties(channelsSelect(c -> c.confirm(Confirm))),
            initProperties(multiChannelsSelect(c -> c.initialChannels(Arrays.asList("")).confirm(Confirm))),
            initProperties(conversationsSelect(c -> c.confirm(Confirm))),
            initProperties(multiConversationsSelect(c -> c.initialConversations(Arrays.asList("")).confirm(Confirm))),
            initProperties(datePicker(d -> d.confirm(Confirm))),
            initProperties(timePicker(d -> d.confirm(Confirm))),
            initProperties(datetimePicker(d -> d.confirm(Confirm))),
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
            initProperties(ImageElement.builder()
                    .slackFile(initProperties(SlackFileObject.builder().build()))
                    .build())
    );
    public static List<TextObject> SectionBlockFieldElements = asSectionFields(
            initProperties(plainText(pt -> pt)),
            initProperties(markdownText(m -> m))
    );

    public static List<SectionBlock> SectionBlocksWithAccessory = new ArrayList<>();

    private static final List<RichTextElement> nestedRichTextElements = Arrays.asList(
            initProperties(RichTextSectionElement.Broadcast.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.Text.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.Channel.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.Color.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.Date.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.Link.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.Team.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.User.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.UserGroup.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build()),
            initProperties(RichTextSectionElement.Emoji.builder()
                    .style(initProperties(RichTextSectionElement.TextStyle.builder().build()))
                    .build())
    );
    private static List<RichTextElement> RichTextElements = asRichTextElements(
            richTextList(r -> r.elements(nestedRichTextElements).indent(123).style("")),
            richTextPreformatted(r -> r.elements(nestedRichTextElements)),
            richTextQuote(r -> r.elements(nestedRichTextElements)),
            richTextSection(r -> r.elements(nestedRichTextElements))
    );

    private static RichTextBlock RichTextBlock = richText(rt -> rt.elements(asElements(
            richTextList(r -> r.indent(123).style("").elements(RichTextElements)),
            richTextPreformatted(r -> r.elements(RichTextElements)),
            richTextQuote(r -> r.elements(RichTextElements)),
            richTextSection(r -> r.elements(RichTextElements))
    )).blockId(""));

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
                        .fields(SectionBlockFieldElements))),
                initProperties(video(v -> v.description(TextObject).title(TextObject))),
                RichTextBlock,
                initProperties(ShareShortcutBlock.builder().appCollaborators(Arrays.asList("")).build())
        ));
        Blocks.addAll(SectionBlocksWithAccessory);
    }

    public static PlainTextInputElement plainTextInputElement = initProperties(PlainTextInputElement.builder()
            .placeholder(initProperties(PlainTextObject.builder().build()))
            .dispatchActionConfig(initProperties(DispatchActionConfig.builder().triggerActionsOn(Arrays.asList("")).build()))
            .build());

    public static RichTextInputElement richTextInputElement = initProperties(RichTextInputElement.builder()
            .placeholder(initProperties(PlainTextObject.builder().build()))
            .initialValue(RichTextBlock)
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
            initProperties(input(i -> i.element(richTextInputElement))),
            initProperties(input(i -> i.element(plainTextInputElement))),
            initProperties(input(i -> i.element(radioButtonsElement))),
            initProperties(input(i -> i.element(initProperties(button(b -> b.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(channelsSelect(c -> c.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(conversationsSelect(c -> c.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(datePicker(d -> d.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(timePicker(d -> d.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(datetimePicker(d -> d.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(externalSelect(e -> e.initialOption(Option).confirm(Confirm)))))),
            initProperties(input(ip -> ip.element(initProperties(com.slack.api.model.block.element.BlockElements.image(i -> i))))),
            initProperties(input(i -> i.element(initProperties(overflowMenu(o -> o.confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(staticSelect(s -> s.initialOption(Option).confirm(Confirm)))))),
            initProperties(input(i -> i.element(initProperties(usersSelect(u -> u.confirm(Confirm)))))),
            initProperties(actions(a -> a.elements(BlockElements))),
            initProperties(context(c -> c.elements(ContextBlockElements))),
            initProperties(divider()),
            initProperties(com.slack.api.model.block.Blocks.image(i -> i)),
            initProperties(video(v -> v.description(TextObject).title(TextObject))),
            initProperties(section(s -> s
                    .accessory(initProperties(ImageElement.builder().build()))
                    .text(TextObject)
                    .fields(SectionBlockFieldElements)))
    );

    public static File FileObject = initFileObject();

    private static Message MessageBlockData = initProperties(new Message());

    static {
        MessageBlockData.setBlocks(Blocks);
    }

    public static List<Attachment.MessageBlock> MessageBlocks = new ArrayList<>();

    static {
        MessageBlocks.add(initProperties(Attachment.MessageBlock.builder()
                .message(MessageBlockData)
                .build()));
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
                    .preview(initProperties(Attachment.Preview.builder()
                            .title(initProperties(PlainTextObject.builder().build()))
                            .subtitle(initProperties(PlainTextObject.builder().build()))
                            .build())
                    )
                    .metadata(initProperties(new Attachment.AttachmentMetadata()))
                    .mrkdwnIn(Arrays.asList(""))
                    .blocks(Blocks)
                    .files(Arrays.asList(FileObject))
                    .messageBlocks(MessageBlocks)
                    .build())
    );

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
                .headers(initProperties(new File.Headers()))
                .mediaProgress(initProperties(new File.MediaProgress()))
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
                .titleBlocks(Blocks)
                .dmMpdmUsersWithFileAccess(Arrays.asList(File.UserWithFileAccess.builder().access("").userId("").build()))
                .editors(Arrays.asList(""))
                .favorites(Arrays.asList(initProperties(new File.Favorite())))
                .build());
        initProperties(file);
        return file;
    }

    public static Message Message = new Message();

    public static Map<String, Object> EmptyHashMapObject = new HashMap<>();


    public static Map<String, AppManifest.Function> Functions = new HashMap<>();
    static {
        AppManifest.ParameterProperty p = initProperties(AppManifest.ParameterProperty.builder().build());
        Map<String, AppManifest.ParameterProperty> properties = new HashMap<>();
        properties.put("Fn0000000000", p);
        properties.put("Fn0000000000_", p);
        AppManifest.Function f = initProperties(AppManifest.Function.builder()
                .inputParameters(properties)
                .outputParameters(properties)
                .build());
        Functions.put("Fn0000000000", f);
        Functions.put("Fn0000000000_", f);
    }
    public static AppManifest AppManifestObject = initProperties(AppManifest.builder()
            .metadata(initProperties(AppManifest.Metadata.builder().build()))
            .displayInformation(initProperties(AppManifest.DisplayInformation.builder().build()))
            .features(initProperties(AppManifest.Features.builder()
                    .appHome(initProperties(AppManifest.AppHome.builder().build()))
                    .botUser(initProperties(AppManifest.BotUser.builder().build()))
                    .shortcuts(Arrays.asList(initProperties(AppManifest.Shortcut.builder().build())))
                    .slashCommands(Arrays.asList(initProperties(AppManifest.SlashCommand.builder().build())))
                    .unfurlDomains(Arrays.asList(""))
                    .build()))
            .settings(initProperties(AppManifest.Settings.builder()
                    .allowedIpAddressRanges(Arrays.asList(""))
                    .interactivity(initProperties(AppManifest.Interactivity.builder().build()))
                    .eventSubscriptions(initProperties(AppManifest.EventSubscriptions.builder()
                            .botEvents(Arrays.asList(""))
                            .userEvents(Arrays.asList(""))
                            .build()))
                    .build()))
            .oauthConfig(initProperties(AppManifest.OAuthConfig.builder()
                    .scopes(initProperties(AppManifest.Scopes.builder()
                            .bot(Arrays.asList(""))
                            .user(Arrays.asList(""))
                            .build()))
                    .redirectUrls(Arrays.asList(""))
                    .build()))
            .functions(Functions)
            .build());

    public static Room Room = initProperties(com.slack.api.model.Room.builder()
            .attachedFileIds(Arrays.asList(""))
            .channels(Arrays.asList(""))
            .participantHistory(Arrays.asList(""))
            .participants(Arrays.asList(""))
            .participantsCameraOn(Arrays.asList(""))
            .participantsCameraOff(Arrays.asList(""))
            .participantsScreenshareOn(Arrays.asList(""))
            .participantsScreenshareOff(Arrays.asList(""))
            .pendingInvitees(EmptyHashMapObject)
            .lastInviteStatusByUser(EmptyHashMapObject)
            .knocks(EmptyHashMapObject)
            .attachedFileIds(Arrays.asList(""))
            .build());

    static {
        Message.setAttachments(Attachments);
        Message.setMetadata(initProperties(new Message.Metadata()));
        Message.setBlocks(Blocks);
        Message.setFile(FileObject);
        Message.setFiles(Arrays.asList(FileObject));
        Message.setPinnedTo(Arrays.asList(""));
        Message.setReactions(Arrays.asList(initProperties(new Reaction())));
        Message.setReplyUsers(Arrays.asList(""));
        Message.setRoom(Room);
    }

}
