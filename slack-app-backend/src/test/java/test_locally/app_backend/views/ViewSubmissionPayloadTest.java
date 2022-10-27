package test_locally.app_backend.views;

import com.google.gson.Gson;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.model.block.InputBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.element.TimePickerElement;
import com.slack.api.model.view.ViewState;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ViewSubmissionPayloadTest {

    private Gson gson = GsonFactory.createSnakeCase();

    private String json = "{\n" +
            "  \"type\": \"view_submission\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T12345678\",\n" +
            "    \"domain\": \"domain-name\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U12345678\",\n" +
            "    \"username\": \"user-name\",\n" +
            "    \"name\": \"user-name\",\n" +
            "    \"team_id\": \"T12345678\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A12345678\",\n" +
            "  \"token\": \"random value\",\n" +
            "  \"trigger_id\": \"768915595217.771150964790.bbc12e4f1483ce782cd891edf9dca94c\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V12345678\",\n" +
            "    \"team_id\": \"T12345678\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"title string\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"close\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Cancel\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"submit\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Submit\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"rating\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Rating\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"static_select\",\n" +
            "          \"action_id\": \"single_action\",\n" +
            "          \"placeholder\": {\n" +
            "            \"type\": \"plain_text\",\n" +
            "            \"text\": \"Select an item\",\n" +
            "            \"emoji\": true\n" +
            "          },\n" +
            "          \"options\": [\n" +
            "            {\n" +
            "              \"text\": {\n" +
            "                \"type\": \"plain_text\",\n" +
            "                \"text\": \":star:\",\n" +
            "                \"emoji\": true\n" +
            "              },\n" +
            "              \"value\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"text\": {\n" +
            "                \"type\": \"plain_text\",\n" +
            "                \"text\": \":star::star:\",\n" +
            "                \"emoji\": true\n" +
            "              },\n" +
            "              \"value\": \"2\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"text\": {\n" +
            "                \"type\": \"plain_text\",\n" +
            "                \"text\": \":star::star::star:\",\n" +
            "                \"emoji\": true\n" +
            "              },\n" +
            "              \"value\": \"3\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"date-of-visit\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Date of visit\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"datepicker\",\n" +
            "          \"action_id\": \"single_action\",\n" +
            "          \"initial_date\": \"2019-09-26\",\n" +
            "          \"placeholder\": {\n" +
            "            \"type\": \"plain_text\",\n" +
            "            \"text\": \"Choose the date\",\n" +
            "            \"emoji\": true\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"comment\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Comment\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"plain_text_input\",\n" +
            "          \"action_id\": \"single_action\",\n" +
            "          \"multiline\": true\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"modal-callback-id\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {\n" +
            "        \"date-of-visit\": {\n" +
            "          \"single_action\": {\n" +
            "            \"type\": \"datepicker\",\n" +
            "            \"selected_date\": \"2019-09-23\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"rating\": {\n" +
            "          \"single_action\": {\n" +
            "            \"type\": \"static_select\",\n" +
            "            \"selected_option\": {\n" +
            "              \"text\": {\n" +
            "                \"type\": \"plain_text\",\n" +
            "                \"text\": \":star::star::star:\",\n" +
            "                \"emoji\": true\n" +
            "              },\n" +
            "              \"value\": \"3\"\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"comment\": {\n" +
            "          \"single_action\": {\n" +
            "            \"type\": \"plain_text_input\",\n" +
            "            \"value\": \"Just amazing!\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"hash\": \"1569562184.10297bc3\",\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": false,\n" +
            "    \"root_view_id\": \"V12345678\",\n" +
            "    \"previous_view_id\": null,\n" +
            "    \"app_id\": \"A12345678\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"bot_id\": \"B12345678\"\n" +
            "  }\n" +
            "}";

    @Test
    public void test() {
        ViewSubmissionPayload payload = gson.fromJson(json, ViewSubmissionPayload.class);
        assertThat(payload.getToken(), is("random value"));
        String keys = payload.getView().getState().getValues().keySet().stream().collect(Collectors.joining(","));
        assertThat(keys, is("date-of-visit,rating,comment"));
    }

    String orgAppPayload = "{\n" +
            "  \"type\": \"view_submission\",\n" +
            "  \"team\": null,\n" +
            "  \"user\": {\n" +
            "    \"id\": \"W111\",\n" +
            "    \"username\": \"primary-owner\",\n" +
            "    \"name\": \"primary-owner\",\n" +
            "    \"team_id\": \"T111\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A111\",\n" +
            "  \"token\": \"j2wEI4Nh7GpSUztLsd0YEO5z\",\n" +
            "  \"trigger_id\": \"111.222.xxx\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V111\",\n" +
            "    \"team_id\": \"T111\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"agenda-block\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Detailed Agenda\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"dispatch_action\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"plain_text_input\",\n" +
            "          \"action_id\": \"agenda-action\",\n" +
            "          \"multiline\": true,\n" +
            "          \"dispatch_action_config\": {\n" +
            "            \"trigger_actions_on\": [\n" +
            "              \"on_enter_pressed\"\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"date-block\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Date\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"dispatch_action\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"datepicker\",\n" +
            "          \"action_id\": \"date-action\"\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"time-block\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Time\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"dispatch_action\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"timepicker\",\n" +
            "          \"action_id\": \"time-action\",\n" +
            "          \"timezone\": \"America/Los_Angeles\"\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"test-view\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {\n" +
            "        \"agenda-block\": {\n" +
            "          \"agenda-action\": {\n" +
            "            \"type\": \"plain_text_input\",\n" +
            "            \"value\": \"test\"\n" +
            "          }\n" +
            "        }," +
            "        \"date-block\": {\n" +
            "          \"date-action\": {\n" +
            "            \"type\": \"datepicker\",\n" +
            "            \"selected_date\": \"2022-06-22\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"time-block\": {\n" +
            "          \"time-action\": {\n" +
            "            \"type\": \"timepicker\",\n" +
            "            \"selected_time\": \"03:00\",\n" +
            "            \"timezone\": \"America/Los_Angeles\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"hash\": \"1606467248.46xb6k1W\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Org App Modal\",\n" +
            "      \"emoji\": false\n" +
            "    },\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": false,\n" +
            "    \"close\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Close\",\n" +
            "      \"emoji\": false\n" +
            "    },\n" +
            "    \"submit\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Submit\",\n" +
            "      \"emoji\": false\n" +
            "    },\n" +
            "    \"previous_view_id\": null,\n" +
            "    \"root_view_id\": \"V111\",\n" +
            "    \"app_id\": \"A111\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"app_installed_team_id\": \"E111\",\n" +
            "    \"bot_id\": \"B111\"\n" +
            "  },\n" +
            "  \"response_urls\": [],\n" +
            "  \"is_enterprise_install\": true,\n" +
            "  \"enterprise\": {\n" +
            "    \"id\": \"E111\",\n" +
            "    \"name\": \"TestOrg\"\n" +
            "  }\n" +
            "}";

    @Test
    public void orgApp() {
        ViewSubmissionPayload payload = gson.fromJson(orgAppPayload, ViewSubmissionPayload.class);
        assertThat(payload.getEnterprise().getId(), is("E111"));
        assertThat(payload.getTeam(), is(nullValue()));
        assertThat(payload.isEnterpriseInstall(), is(true));

        TimePickerElement timepickerBlockElement = null;
        for (LayoutBlock block : payload.getView().getBlocks()) {
            if (block.getType().equals(InputBlock.TYPE)) {
                if (((InputBlock) block).getElement() instanceof TimePickerElement) {
                    timepickerBlockElement = (TimePickerElement) ((InputBlock) block).getElement();
                }
            }
        }
        assertThat(timepickerBlockElement.getTimezone(), is("America/Los_Angeles"));

        ViewState.Value timeStateValue = payload.getView().getState().getValues()
                .get("time-block").get("time-action");
        assertThat(timeStateValue.getSelectedTime(), is("03:00"));
        assertThat(timeStateValue.getTimezone(), is("America/Los_Angeles"));
    }

    @Test
    public void inputElementsAddedInOctober2022() {
        String jsonData = "{\n" +
                "  \"values\": {\n" +
                "    \"agenda-block\": {\n" +
                "      \"agenda-action\": {\n" +
                "        \"type\": \"plain_text_input\",\n" +
                "        \"value\": \"test\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"email-block\": {\n" +
                "      \"email-action\": {\n" +
                "        \"type\": \"email_text_input\",\n" +
                "        \"value\": \"foo@example.com\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"url-block\": {\n" +
                "      \"url-action\": {\n" +
                "        \"type\": \"url_text_input\",\n" +
                "        \"value\": \"https://www.example.com/\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"number-block\": {\n" +
                "      \"number-action\": {\n" +
                "        \"type\": \"number_input\",\n" +
                "        \"value\": \"12345\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"date-block\": {\n" +
                "      \"date-action\": {\n" +
                "        \"type\": \"datepicker\",\n" +
                "        \"selected_date\": \"2022-10-19\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"time-block\": {\n" +
                "      \"time-action\": {\n" +
                "        \"type\": \"timepicker\",\n" +
                "        \"selected_time\": \"01:00\",\n" +
                "        \"timezone\": \"America/Los_Angeles\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"date-time-block\": {\n" +
                "      \"date-time-action\": {\n" +
                "        \"type\": \"datetimepicker\",\n" +
                "        \"selected_date_time\": 1666869900\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        ViewState stateValues = gson.fromJson(jsonData, ViewState.class);
        assertThat(stateValues, is(notNullValue()));
        assertThat(
                stateValues.getValues().get("url-block").get("url-action").getValue(),
                is("https://www.example.com/"));
        assertThat(
                stateValues.getValues().get("email-block").get("email-action").getValue(),
                is("foo@example.com"));
        assertThat(
                stateValues.getValues().get("number-block").get("number-action").getValue(),
                is("12345"));
        assertThat(
                stateValues.getValues().get("date-time-block").get("date-time-action").getSelectedDateTime(),
                is(1666869900));

    }
}
