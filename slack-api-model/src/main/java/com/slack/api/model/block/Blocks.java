package com.slack.api.model.block;

import com.slack.api.model.ModelConfigurator;
import com.slack.api.model.block.element.BlockElement;

import java.util.Arrays;
import java.util.List;

public class Blocks {

    private Blocks() {
    }

    public static List<LayoutBlock> asBlocks(LayoutBlock... blocks) {
        return Arrays.asList(blocks);
    }

    // ActionsBlock

    public static ActionsBlock actions(ModelConfigurator<ActionsBlock.ActionsBlockBuilder> configurator) {
        return configurator.configure(ActionsBlock.builder()).build();
    }

    public static ActionsBlock actions(List<BlockElement> elements) {
        return ActionsBlock.builder().elements(elements).build();
    }

    public static ActionsBlock actions(String blockId, List<BlockElement> elements) {
        return ActionsBlock.builder().blockId(blockId).elements(elements).build();
    }

    // ContextBlock

    public static ContextBlock context(ModelConfigurator<ContextBlock.ContextBlockBuilder> configurator) {
        return configurator.configure(ContextBlock.builder()).build();
    }

    public static ContextBlock context(List<ContextBlockElement> elements) {
        return ContextBlock.builder().elements(elements).build();
    }

    public static ContextBlock context(String blockId, List<ContextBlockElement> elements) {
        return ContextBlock.builder().blockId(blockId).elements(elements).build();
    }

    // DividerBlock

    public static DividerBlock divider(ModelConfigurator<DividerBlock.DividerBlockBuilder> configurator) {
        return configurator.configure(DividerBlock.builder()).build();
    }

    public static DividerBlock divider(String blockId) {
        return DividerBlock.builder().blockId(blockId).build();
    }

    public static DividerBlock divider() {
        return DividerBlock.builder().build();
    }

    // HeaderBlock

    public static HeaderBlock header(ModelConfigurator<HeaderBlock.HeaderBlockBuilder> configurator) {
        return configurator.configure(HeaderBlock.builder()).build();
    }

    // CallBlock

    public static CallBlock call(ModelConfigurator<CallBlock.CallBlockBuilder> configurator) {
        return configurator.configure(CallBlock.builder()).build();
    }

    // FileBlock

    public static FileBlock file(ModelConfigurator<FileBlock.FileBlockBuilder> configurator) {
        return configurator.configure(FileBlock.builder()).build();
    }

    // ImageBlock

    public static ImageBlock image(ModelConfigurator<ImageBlock.ImageBlockBuilder> configurator) {
        return configurator.configure(ImageBlock.builder()).build();
    }

    // InputBlock

    public static InputBlock input(ModelConfigurator<InputBlock.InputBlockBuilder> configurator) {
        return configurator.configure(InputBlock.builder()).build();
    }

    // SectionBlock

    public static SectionBlock section(ModelConfigurator<SectionBlock.SectionBlockBuilder> configurator) {
        return configurator.configure(SectionBlock.builder()).build();
    }

}
