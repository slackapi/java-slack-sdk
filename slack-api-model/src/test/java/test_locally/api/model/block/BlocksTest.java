package test_locally.api.model.block;

import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.BlockElements;
import org.junit.Test;

import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.asSectionFields;
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
        List<ContextBlockElement> elements = asContextElements(BlockElements.image(r -> r.imageUrl("https://www.example.com/logo.png")));
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
    public void testImage() {
        assertThat(Blocks.image(i -> i.blockId("block-id").imageUrl("https://www.example.com/")), is(notNullValue()));
    }

    @Test
    public void testInput() {
        assertThat(input(i -> i.blockId("block-id").element(button(b -> b.value("v")))), is(notNullValue()));
    }

    @Test
    public void testRichText() {
        assertThat(richText(i -> i
                .blockId("block-id")
                .elements(asElements(button(b -> b.value("v"))))
        ), is(notNullValue()));
    }

    @Test
    public void testSection() {
        assertThat(section(s -> s.blockId("block-id").fields(asSectionFields(plainText("foo")))), is(notNullValue()));
    }

}