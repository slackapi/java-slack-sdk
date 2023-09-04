package test_locally.block

import com.google.gson.JsonElement
import com.slack.api.model.kotlin_extension.block.element.ConversationType
import com.slack.api.model.kotlin_extension.block.withBlocks
import com.slack.api.util.json.GsonFactory
import org.junit.Test
import kotlin.test.assertEquals

class ActionsBlockTest {

    @Test
    fun buttonAndConv() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            actions {
                blockId("b-id")
                button {
                    actionId("a-btn")
                    text("button 1")
                    value("v1")
                }
                conversationsSelect {
                    actionId("a-conv")
                    filter(ConversationType.PUBLIC, ConversationType.IM, excludeExternalSharedChannels = true, excludeBotUsers = true)
                }
            }
        }
        val result = gson.toJson(blocks)
        val expected = """[{"type":"actions","elements":[{"type":"button","text":{"type":"plain_text","text":"button 1"},"action_id":"a-btn","value":"v1"},{"type":"conversations_select","action_id":"a-conv","filter":{"include":["public","im"],"exclude_external_shared_channels":true,"exclude_bot_users":true}}],"block_id":"b-id"}]"""
        assertEquals(expected, result)
    }

    @Test
    fun `multi-selects`() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            actions {
                multiChannelsSelect {
                    actionId("a-id")
                    placeholder("Pick a channel...")
                    initialChannels("general", "random")
                    maxSelectedItems(5)
                }
                multiConversationsSelect {
                    actionId("b-id")
                    filter(ConversationType.PUBLIC, ConversationType.PRIVATE)
                    initialConversations("user1", "user2")
                    placeholder("Pick users to chat with...")
                    maxSelectedItems(3)
                }
                multiExternalSelect {
                    initialOptions {
                        option {
                            value("option1")
                            url("https://www.google.com/")
                            description("Choose Option 1")
                        }
                    }
                    placeholder("Pick an option...")
                    minQueryLength(5)
                    maxSelectedItems(2)
                }
                multiStaticSelect {
                    optionGroups {
                        optionGroup {
                            label("Group 1")
                            option {
                                value("option2")
                                url("https://www.supergoogle.com/")
                                description("Choose option 2")
                            }
                            option {
                                value("option3")
                                url("https://www.google.com/")
                                description("Choose Option 3")
                            }
                        }
                    }
                    initialOptions {
                        option {
                            value("option2")
                            url("https://www.supergoogle.com/")
                            description("Choose option 2")
                        }
                    }
                }
                multiUsersSelect {
                    placeholder("Pick a user...")
                    maxSelectedItems(2)
                    initialUsers("erittenhouse", "seratch")
                }
            }
        }

        val result = gson.toJson(blocks)
        val expected = """[{"type":"actions","elements":[{"type":"multi_channels_select","placeholder":{"type":"plain_text","text":"Pick a channel..."},"action_id":"a-id","initial_channels":["general","random"],"max_selected_items":5},{"type":"multi_conversations_select","placeholder":{"type":"plain_text","text":"Pick users to chat with..."},"action_id":"b-id","initial_conversations":["user1","user2"],"max_selected_items":3,"filter":{"include":["public","private"]}},{"type":"multi_external_select","placeholder":{"type":"plain_text","text":"Pick an option..."},"initial_options":[{"value":"option1","description":{"type":"plain_text","text":"Choose Option 1"},"url":"https://www.google.com/"}],"min_query_length":5,"max_selected_items":2},{"type":"multi_static_select","option_groups":[{"label":{"type":"plain_text","text":"Group 1"},"options":[{"value":"option2","description":{"type":"plain_text","text":"Choose option 2"},"url":"https://www.supergoogle.com/"},{"value":"option3","description":{"type":"plain_text","text":"Choose Option 3"},"url":"https://www.google.com/"}]}],"initial_options":[{"value":"option2","description":{"type":"plain_text","text":"Choose option 2"},"url":"https://www.supergoogle.com/"}]},{"type":"multi_users_select","placeholder":{"type":"plain_text","text":"Pick a user..."},"initial_users":["erittenhouse","seratch"],"max_selected_items":2}]}]"""
        assertEquals(expected, result)
    }

    @Test
    fun `single-selects with date and time pickers`() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            section {
                plainText("Please select the details for your visit.")
                accessory {
                    overflowMenu {
                        options {
                            option {
                                plainText("Cancel this appointment request")
                                url("https://fake-health-company.nett/cancel-appointment")
                                value("appt-cancel")
                            }
                            option {
                                plainText("Leave feedback for this bot!")
                                url("https://fake-health-company.nett/bot-feedback")
                                value("bot-feedback")
                            }
                        }
                    }
                }
            }
            actions {
                image("https://fake-health-company.nett/assets/health-plus-image.jpg")
                staticSelect {
                    actionId("problem-select")
                    placeholder("Reason for your appointment...")

                    optionGroups {
                        optionGroup {
                            label("Physical issues")
                            option {
                                plainText("Skin irritation")
                                value("skin-irrit")
                            }
                            option {
                                plainText("Back pain")
                                value("back-pain")
                            }
                            option {
                                plainText("Sports injury")
                                value("sports-injury")
                            }
                        }
                        optionGroup {
                            label("Wellbeing issues")
                            option {
                                plainText("Have a fever")
                                value("may-be-sick")
                            }
                            option {
                                plainText("Shortness of breath")
                                value("breathing-issues")
                            }
                        }
                    }
                }
                datePicker {
                    actionId("datepick-appt")
                    initialDate("2020-06-01")
                    placeholder("Select appointment date...")
                    confirm {
                        title("Are you sure?")
                        plainText("Check your calendar. Are you sure you are free on this date?")
                        confirm("Yes, I am free.")
                        deny("No, I can't make it.")
                    }
                }
                timePicker {
                    actionId("timepick-appt")
                    initialTime("12:35")
                    placeholder("Select appointment time...")
                    confirm {
                        title("Are you sure?")
                        plainText("Check your calendar. Are you sure you are free at this time?")
                        confirm("Yes, I am free.")
                        deny("No, I can't make it.")
                    }
                }
                datetimePicker {
                    actionId("datetimepick-appt")
                    initialDateTime(12345)
                }

                externalSelect {
                    actionId("appointment-time-select")
                    placeholder("Please select your appointment time...")
                    minQueryLength(4)
                }
                usersSelect {
                    actionId("guest-select")
                    placeholder("Select any users that will be accompanying you.")
                }
                workflowButton {
                    actionId("workflow")
                    text("Click this")
                    workflow {
                        trigger {
                            url("https://example.com")
                            customizableInputParameter {
                                name("foo")
                                value("bar")
                            }
                            customizableInputParameter {
                                name("baz")
                                value("BAZ")
                            }
                        }
                    }
                }
            }
        }

        val original = """{
            "blocks": [
              {
                "type": "section",
                "text": {
                  "type": "plain_text",
                  "text": "Please select the details for your visit."
                },
                "accessory": {
                  "type": "overflow",
                  "options": [
                    {
                      "text": {
                        "type": "plain_text",
                        "text": "Cancel this appointment request"
                      },
                      "value": "appt-cancel",
                      "url": "https://fake-health-company.nett/cancel-appointment"
                    },
                    {
                      "text": {
                        "type": "plain_text",
                        "text": "Leave feedback for this bot!"
                      },
                      "value": "bot-feedback",
                      "url": "https://fake-health-company.nett/bot-feedback"
                    }
                  ]
                }
              },
              {
                "type": "actions",
                "elements": [
                  {
                    "type": "image",
                    "image_url": "https://fake-health-company.nett/assets/health-plus-image.jpg"
                  },
                  {
                    "type": "static_select",
                    "placeholder": {
                      "type": "plain_text",
                      "text": "Reason for your appointment..."
                    },
                    "action_id": "problem-select",
                    "option_groups": [
                      {
                        "label": {
                          "type": "plain_text",
                          "text": "Physical issues"
                        },
                        "options": [
                          {
                            "text": {
                              "type": "plain_text",
                              "text": "Skin irritation"
                            },
                            "value": "skin-irrit"
                          },
                          {
                            "text": {
                              "type": "plain_text",
                              "text": "Back pain"
                            },
                            "value": "back-pain"
                          },
                          {
                            "text": {
                              "type": "plain_text",
                              "text": "Sports injury"
                            },
                            "value": "sports-injury"
                          }
                        ]
                      },
                      {
                        "label": {
                          "type": "plain_text",
                          "text": "Wellbeing issues"
                        },
                        "options": [
                          {
                            "text": {
                              "type": "plain_text",
                              "text": "Have a fever"
                            },
                            "value": "may-be-sick"
                          },
                          {
                            "text": {
                              "type": "plain_text",
                              "text": "Shortness of breath"
                            },
                            "value": "breathing-issues"
                          }
                        ]
                      }
                    ]
                  },
                  {
                    "type": "datepicker",
                    "action_id": "datepick-appt",
                    "placeholder": {
                      "type": "plain_text",
                      "text": "Select appointment date..."
                    },
                    "initial_date": "2020-06-01",
                    "confirm": {
                      "title": {
                        "type": "plain_text",
                        "text": "Are you sure?"
                      },
                      "text": {
                        "type": "plain_text",
                        "text": "Check your calendar. Are you sure you are free on this date?"
                      },
                      "confirm": {
                        "type": "plain_text",
                        "text": "Yes, I am free."
                      },
                      "deny": {
                        "type": "plain_text",
                        "text": "No, I can't make it."
                      }
                    }
                  },
                  {
                    "type": "timepicker",
                    "action_id": "timepick-appt",
                    "placeholder": {
                      "type": "plain_text",
                      "text": "Select appointment time..."
                    },
                    "initial_time": "12:35",
                    "confirm": {
                      "title": {
                        "type": "plain_text",
                        "text": "Are you sure?"
                      },
                      "text": {
                        "type": "plain_text",
                        "text": "Check your calendar. Are you sure you are free at this time?"
                      },
                      "confirm": {
                        "type": "plain_text",
                        "text": "Yes, I am free."
                      },
                      "deny": {
                        "type": "plain_text",
                        "text": "No, I can't make it."
                      }
                    }
                  },
                  {
                    "type": "datetimepicker",
                    "action_id": "datetimepick-appt",
                    "initial_date_time": 12345
                  },
                  {
                    "type": "external_select",
                    "placeholder": {
                      "type": "plain_text",
                      "text": "Please select your appointment time..."
                    },
                    "action_id": "appointment-time-select",
                    "min_query_length": 4
                  },
                  {
                    "type": "users_select",
                    "placeholder": {
                      "type": "plain_text",
                      "text": "Select any users that will be accompanying you."
                    },
                    "action_id": "guest-select"
                  },
                  {
                    "type": "workflow_button",
                    "action_id": "workflow",
                    "text": {
                      "type": "plain_text",
                      "text": "Click this"
                    },
                    "workflow": {
                      "trigger": {
                        "url": "https://example.com",
                        "customizable_input_parameters": [
                          {
                            "name": "foo",
                            "value": "bar"
                          },
                          {
                            "name": "baz",
                            "value": "BAZ"
                          }
                        ]
                      }
                    }
                  }
                ]
              }
            ]
        }""".trimIndent()
        val json = gson.fromJson(original, JsonElement::class.java)
        val expected = json.asJsonObject["blocks"]
        val actual = gson.toJsonTree(blocks)
        assertEquals(expected, actual, "\n$expected\n$actual")
    }

    @Test
    fun `Channel select and checkboxes`() {
        val gson = GsonFactory.createSnakeCase()
        val blocks = withBlocks {
            section {
                plainText("Please accept all legal terms for our server's channels.")
                accessory {
                    image("https://fake-server-does.not-exist.nett/checkmark-image.jpg")
                }
            }
            actions {
                channelsSelect {
                    initialChannel("general")
                    placeholder("Pick the channel to accept legal terms for.")
                    responseUrlEnabled(false)
                }
                checkboxes {
                    options {
                        option {
                            description("I accept the terms and conditions")
                            value("tac-accept")
                        }
                        option {
                            description("I have read the privacy policy")
                            value("privacy-policy-read")
                        }
                    }
                }
            }
        }
        val original = """
            {
              "blocks": [
                {
                  "type": "section",
                  "text": {
                    "type": "plain_text",
                    "text": "Please accept all legal terms for our server's channels."
                  },
                  "accessory": {
                    "type": "image",
                    "image_url": "https://fake-server-does.not-exist.nett/checkmark-image.jpg"
                  }
                },
                {
                  "type": "actions",
                  "elements": [
                    {
                      "type": "channels_select",
                      "placeholder": {
                        "type": "plain_text",
                        "text": "Pick the channel to accept legal terms for."
                      },
                      "initial_channel": "general",
                      "response_url_enabled": false
                    },
                    {
                      "type": "checkboxes",
                      "options": [
                        {
                          "value": "tac-accept",
                          "description": {
                            "type": "plain_text",
                            "text": "I accept the terms and conditions"
                          }
                        },
                        {
                          "value": "privacy-policy-read",
                          "description": {
                            "type": "plain_text",
                            "text": "I have read the privacy policy"
                          }
                        }
                      ]
                    }
                  ]
                }
              ]
            }
        """.trimIndent()
        val json = gson.fromJson(original, JsonElement::class.java)
        val expected = json.asJsonObject["blocks"]
        val actual = gson.toJsonTree(blocks)
        assertEquals(expected, actual, "\n$expected\n$actual")
    }
}
