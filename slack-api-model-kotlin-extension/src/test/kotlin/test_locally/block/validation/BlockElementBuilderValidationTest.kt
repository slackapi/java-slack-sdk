package test_locally.block.validation

import com.slack.api.model.block.element.*
import com.slack.api.model.kotlin_extension.block.element.*
import org.junit.Test
import test_locally.block.validation.ValidationUtil.validateMethodNames

class BlockElementBuilderValidationTest {

    @Test
    fun testButtonElementBuilder() {
        validateMethodNames(ButtonElement::class.java, ButtonElementBuilder::class.java)
    }

    @Test
    fun testChannelsSelectElementBuilder() {
        validateMethodNames(ChannelsSelectElement::class.java, ChannelsSelectElementBuilder::class.java)
    }

    @Test
    fun testCheckboxesElementBuilder() {
        validateMethodNames(CheckboxesElement::class.java, CheckboxesElementBuilder::class.java)
    }

    @Test
    fun testConversationsSelectElementBuilder() {
        validateMethodNames(ConversationsSelectElement::class.java, ConversationsSelectElementBuilder::class.java)
    }

    @Test
    fun testDatePickerElementBuilder() {
        validateMethodNames(DatePickerElement::class.java, DatePickerElementBuilder::class.java)
    }

    @Test
    fun testExternalSelectElementBuilder() {
        validateMethodNames(ExternalSelectElement::class.java, ExternalSelectElementBuilder::class.java)
    }

    @Test
    fun testMultiChannelsSelectElementBuilder() {
        validateMethodNames(MultiChannelsSelectElement::class.java, MultiChannelsSelectElementBuilder::class.java)
    }

    @Test
    fun testMultiConversationsSelectElementBuilder() {
        validateMethodNames(MultiConversationsSelectElement::class.java, MultiConversationsSelectElementBuilder::class.java)
    }

    @Test
    fun testMultiExternalSelectElementBuilder() {
        validateMethodNames(MultiExternalSelectElement::class.java, MultiExternalSelectElementBuilder::class.java)
    }

    @Test
    fun testMultiStaticSelectElementBuilder() {
        validateMethodNames(MultiStaticSelectElement::class.java, MultiStaticSelectElementBuilder::class.java)
    }

    @Test
    fun testMultiUsersSelectElementBuilder() {
        validateMethodNames(MultiUsersSelectElement::class.java, MultiUsersSelectElementBuilder::class.java)
    }

    @Test
    fun testOverflowMenuElementBuilder() {
        validateMethodNames(OverflowMenuElement::class.java, OverflowMenuElementBuilder::class.java)
    }

    @Test
    fun testPlainTextInputElementBuilder() {
        validateMethodNames(PlainTextInputElement::class.java, PlainTextInputElementBuilder::class.java)
    }

    @Test
    fun testRadioButtonsElementBuilder() {
        validateMethodNames(RadioButtonsElement::class.java, RadioButtonsElementBuilder::class.java)
    }

    @Test
    fun testStaticSelectElementBuilder() {
        validateMethodNames(StaticSelectElement::class.java, StaticSelectElementBuilder::class.java)
    }

    @Test
    fun testUsersSelectElementBuilder() {
        validateMethodNames(UsersSelectElement::class.java, UsersSelectElementBuilder::class.java)
    }

}