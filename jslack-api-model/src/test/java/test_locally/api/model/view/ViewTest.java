package test_locally.api.model.view;

import com.github.seratch.jslack.api.model.view.View;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ViewTest {

    @Test
    public void deserialize_open() {
        // https://api.slack.com/methods/views.open
        String json = "{\n" +
                "    \"id\": \"VMHU10V25\",\n" +
                "    \"team_id\": \"T8N4K1JN\",\n" +
                "    \"type\": \"modal\",\n" +
                "    \"title\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Quite a plain modal\"\n" +
                "    },\n" +
                "    \"submit\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Create\"\n" +
                "    },\n" +
                "    \"blocks\": [\n" +
                "        {\n" +
                "            \"type\": \"input\",\n" +
                "            \"block_id\": \"a_block_id\",\n" +
                "            \"label\": {\n" +
                "                \"type\": \"plain_text\",\n" +
                "                \"text\": \"A simple label\",\n" +
                "                \"emoji\": true\n" +
                "            },\n" +
                "            \"optional\": false,\n" +
                "            \"element\": {\n" +
                "                \"type\": \"plain_text_input\",\n" +
                "                \"action_id\": \"an_action_id\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"private_metadata\": \"Shh it is a secret\",\n" +
                "    \"callback_id\": \"identify_your_modals\",\n" +
                "    \"external_id\": \"\",\n" +
                "    \"state\": {\n" +
                "        \"values\": []\n" +
                "    },\n" +
                "    \"hash\": \"156772938.1827394\",\n" +
                "    \"clear_on_close\": false,\n" +
                "    \"notify_on_close\": false,\n" +
                "    \"root_view_id\": \"VMHU10V25\",\n" +
                "    \"app_id\": \"AA4928AQ\",\n" +
                "    \"bot_id\": \"BA13894H\"\n" +
                "}";
        View view = GsonFactory.createSnakeCase().fromJson(json, View.class);
        assertThat(view.getId(), is("VMHU10V25"));
        assertThat(view.getState(), is(notNullValue()));
    }

    @Test
    public void deserialize_push() {
        // https://api.slack.com/methods/views.push
        String json = "{\n" +
                "    \"id\": \"VNM522E2U\",\n" +
                "    \"team_id\": \"T9M4RL1JM\",\n" +
                "    \"type\": \"modal\",\n" +
                "    \"title\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Pushed Modal\",\n" +
                "        \"emoji\": true\n" +
                "    },\n" +
                "    \"close\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Back\",\n" +
                "        \"emoji\": true\n" +
                "    },\n" +
                "    \"submit\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Save\",\n" +
                "        \"emoji\": true\n" +
                "    },\n" +
                "    \"blocks\": [\n" +
                "        {\n" +
                "            \"type\": \"input\",\n" +
                "            \"block_id\": \"edit_details\",\n" +
                "            \"element\": {\n" +
                "                \"type\": \"plain_text_input\",\n" +
                "                \"action_id\": \"detail_input\",\n" +
                "                \"placeholder\": {\n" + // label is invalid here
                "                    \"type\": \"plain_text\",\n" +
                "                    \"text\": \"Edit details\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"private_metadata\": \"\",\n" +
                "    \"callback_id\": \"view_4\",\n" +
                "    \"external_id\": \"\",\n" +
                "    \"state\": {\n" +
                "        \"values\": {}\n" + // "values": [] is invalid
                "    },\n" +
                "    \"hash\": \"1569362015.55b5e41b\",\n" +
                "    \"clear_on_close\": true,\n" +
                "    \"notify_on_close\": false,\n" +
                "    \"root_view_id\": \"VNN729E3U\",\n" +
                "    \"previous_view_id\": null,\n" +
                "    \"app_id\": \"AAD3351BQ\",\n" +
                "    \"bot_id\": \"BADF7A34H\"\n" +
                "}";
        View view = GsonFactory.createSnakeCase().fromJson(json, View.class);
        assertThat(view.getId(), is("VNM522E2U"));
        assertThat(view.getState(), is(notNullValue()));
    }

    @Test
    public void deserialize_update() {
        // https://api.slack.com/methods/views.update
        String json = "{\n" +
                "    \"id\": \"VNM522E2U\",\n" +
                "    \"team_id\": \"T9M4RL1JM\",\n" +
                "    \"type\": \"modal\",\n" +
                "    \"title\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Updated Modal\",\n" +
                "        \"emoji\": true\n" +
                "    },\n" +
                "    \"close\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Close\",\n" +
                "        \"emoji\": true\n" +
                "    },\n" +
                "    \"submit\": null,\n" +
                "    \"blocks\": [\n" +
                "        {\n" +
                "            \"type\": \"section\",\n" +
                "            \"block_id\": \"s_block\",\n" +
                "            \"text\": {\n" +
                "                \"type\": \"plain_text\",\n" +
                "                \"text\": \"I am but an updated modal\",\n" +
                "                \"emoji\": true\n" +
                "            },\n" +
                "            \"accessory\": {\n" +
                "                \"type\": \"button\",\n" +
                "                \"action_id\": \"button_4\",\n" +
                "                \"text\": {\n" +
                "                    \"type\": \"plain_text\",\n" +
                "                    \"text\": \"Click me\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"private_metadata\": \"\",\n" +
                "    \"callback_id\": \"view_2\",\n" +
                "    \"external_id\": \"\",\n" +
                "    \"state\": {\n" +
                "        \"values\": {}\n" + // "values": [] is invalid
                "    },\n" +
                "    \"hash\": \"1569262015.55b5e41b\",\n" +
                "    \"clear_on_close\": true,\n" +
                "    \"notify_on_close\": false,\n" +
                "    \"root_view_id\": \"VNN729E3U\",\n" +
                "    \"previous_view_id\": null,\n" +
                "    \"app_id\": \"AAD3351BQ\",\n" +
                "    \"bot_id\": \"BADF7A34H\"\n" +
                "}";
        View view = GsonFactory.createSnakeCase().fromJson(json, View.class);
        assertThat(view.getId(), is("VNM522E2U"));
        assertThat(view.getState(), is(notNullValue()));
    }
}
