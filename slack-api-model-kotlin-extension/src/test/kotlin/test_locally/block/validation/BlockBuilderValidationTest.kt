package test_locally.block.validation

import com.slack.api.model.block.*
import com.slack.api.model.kotlin_extension.block.*
import org.junit.Test
import test_locally.block.validation.ValidationUtil.validateMethodNames

class BlockBuilderValidationTest {

    @Test
    fun testActionsBlockBuilder() {
        validateMethodNames(ActionsBlock::class.java, ActionsBlockBuilder::class.java)
    }

    @Test
    fun testContextBlockBuilder() {
        validateMethodNames(ContextBlock::class.java, ContextBlockBuilder::class.java)
    }

    @Test
    fun testImageBlockBuilder() {
        validateMethodNames(ImageBlock::class.java, ImageBlockBuilder::class.java)
    }

    @Test
    fun testInputBlockBuilder() {
        validateMethodNames(InputBlock::class.java, InputBlockBuilder::class.java)
    }

    @Test
    fun testRichTextBlockBuilder() {
        validateMethodNames(RichTextBlock::class.java, RichTextBlockBuilder::class.java)
    }

    @Test
    fun testSectionBlockBuilder() {
        validateMethodNames(SectionBlock::class.java, SectionBlockBuilder::class.java)
    }

}