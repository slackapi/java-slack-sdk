package test_locally.block.validation

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionGroupObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionGroupObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder
import org.junit.Test
import test_locally.block.validation.ValidationUtil.validateMethodNames

class CompositionObjectBuilderValidationTest {

    @Test
    fun testConfirmationDialogObjectBuilder() {
        validateMethodNames(ConfirmationDialogObject::class.java, ConfirmationDialogObjectBuilder::class.java)
    }

    @Test
    fun testOptionGroupObjectBuilder() {
        validateMethodNames(OptionGroupObject::class.java, OptionGroupObjectBuilder::class.java)
    }

    @Test
    fun testOptionObjectBuilder() {
        validateMethodNames(OptionObject::class.java, OptionObjectBuilder::class.java)
    }

}