package test_locally.block

import com.slack.api.model.kotlin_extension.block.composition.TriggerActionOn
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

class InputBlockTest {

    @Test
    fun dispatchActions() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            input {
                blockId("b-1")
                dispatchAction(true)
                element {
                    plainTextInput {
                        actionId("a-1")
                        dispatchActionConfig {
                            triggerActionsOn("on_enter_pressed", "on_character_entered")
                        }
                    }
                }
                label("Test")
            }
            input {
                blockId("b-2")
                dispatchAction(true)
                element {
                    plainTextInput {
                        actionId("a-2")
                        dispatchActionConfig {
                            triggerActionsOn(TriggerActionOn.ON_ENTER_PRESSED, TriggerActionOn.ON_CHARACTER_ENTERED)
                        }
                    }
                }
                label("Test")
            }
        }
        val result = gson.toJson(blocks)
        val expected = """[{"type":"input","block_id":"b-1","label":{"type":"plain_text","text":"Test"},"element":{"type":"plain_text_input","action_id":"a-1","multiline":false,"dispatch_action_config":{"trigger_actions_on":["on_enter_pressed","on_character_entered"]}},"dispatch_action":true,"optional":false},{"type":"input","block_id":"b-2","label":{"type":"plain_text","text":"Test"},"element":{"type":"plain_text_input","action_id":"a-2","multiline":false,"dispatch_action_config":{"trigger_actions_on":["on_enter_pressed","on_character_entered"]}},"dispatch_action":true,"optional":false}]"""
        assertEquals(expected, result)
    }
}
