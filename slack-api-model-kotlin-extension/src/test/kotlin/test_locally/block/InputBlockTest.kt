package test_locally.block

import com.slack.api.model.kotlin_extension.block.composition.TriggerActionOn
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

class InputBlockTest {

    @Test
    fun fileInput() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            input {
                blockId("b-1")
                element {
                    fileInput {
                        actionId("a-1")
                        maxFiles(2)
                        filetypes("jpg", "png")
                    }
                }
                label("Files 1")
            }
            input {
                blockId("b-2")
                element {
                    fileInput {
                        actionId("a-2")
                        maxFiles(3)
                        filetypes(listOf("jpg", "png"))
                    }
                }
                label("Files 2")
            }
        }
        val result = gson.toJson(blocks)
        val expected = """[{"type":"input","block_id":"b-1","label":{"type":"plain_text","text":"Files 1"},"element":{"type":"file_input","action_id":"a-1","filetypes":["jpg","png"],"max_files":2},"optional":false},{"type":"input","block_id":"b-2","label":{"type":"plain_text","text":"Files 2"},"element":{"type":"file_input","action_id":"a-2","filetypes":["jpg","png"],"max_files":3},"optional":false}]"""
        assertEquals(expected, result)
    }

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
            input {
                blockId("b-3")
                dispatchAction(true)
                element {
                    richTextInput {
                        actionId("a-3")
                        dispatchActionConfig {
                            triggerActionsOn(TriggerActionOn.ON_ENTER_PRESSED, TriggerActionOn.ON_CHARACTER_ENTERED)
                        }
                    }
                }
                label("Test")
            }
        }
        val result = gson.toJson(blocks)
        val expected = """[{"type":"input","block_id":"b-1","label":{"type":"plain_text","text":"Test"},"element":{"type":"plain_text_input","action_id":"a-1","multiline":false,"dispatch_action_config":{"trigger_actions_on":["on_enter_pressed","on_character_entered"]}},"dispatch_action":true,"optional":false},{"type":"input","block_id":"b-2","label":{"type":"plain_text","text":"Test"},"element":{"type":"plain_text_input","action_id":"a-2","multiline":false,"dispatch_action_config":{"trigger_actions_on":["on_enter_pressed","on_character_entered"]}},"dispatch_action":true,"optional":false},{"type":"input","block_id":"b-3","label":{"type":"plain_text","text":"Test"},"element":{"type":"rich_text_input","action_id":"a-3","dispatch_action_config":{"trigger_actions_on":["on_enter_pressed","on_character_entered"]}},"dispatch_action":true,"optional":false}]"""
        assertEquals(expected, result)
    }
}
