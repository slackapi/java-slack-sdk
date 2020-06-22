package test_locally.api.model.dialog;

import com.google.gson.Gson;
import com.slack.api.model.dialog.Dialog;
import com.slack.api.model.dialog.DialogDataSourceType;
import com.slack.api.model.dialog.DialogSelectElement;
import com.slack.api.model.dialog.DialogSubType;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static com.slack.api.model.dialog.Dialogs.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DialogsTest {

    @Test
    public void test() {
        Dialog dialog = dialog(d -> d.elements(asElements(
                dialogSelect(ds ->
                        ds.label("aaa").name("nnnn").options(asOptions(dialogOption(opt -> opt.label("l").value("v"))))
                ),
                dialogText(dt -> dt.name("foo")),
                dialogTextArea(dta -> dta.subtype(DialogSubType.NUMBER))
        )));
        assertThat(dialog, is(notNullValue()));
    }

    @Test
    public void testDataSource() {
        Dialog dialog = dialog(d -> d.elements(asElements(
                dialogSelect(ds ->
                        ds.label("aaa")
                                .name("nnnn")
                                .dataSource(DialogDataSourceType.CONVERSATIONS)
                ),
                dialogText(dt -> dt.name("foo")),
                dialogTextArea(dta -> dta.subtype(DialogSubType.NUMBER))
        )));
        assertThat(dialog, is(notNullValue()));
    }

    String expected = "{" +
            "\"callback_id\":\"ryde-46e2b0\"," +
            "\"elements\":[" +
            "{\"label\":\"Pickup Location\",\"name\":\"loc_origin\",\"type\":\"text\",\"optional\":false,\"max_length\":0,\"min_length\":0}," +
            "{\"label\":\"Dropoff Location\",\"name\":\"loc_destination\",\"type\":\"text\",\"optional\":false,\"max_length\":0,\"min_length\":0}" +
            "]," +
            "\"submit_label\":\"Request\"," +
            "\"notify_on_cancel\":true," +
            "\"state\":\"Limo\"" +
            "}";

    Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void testWithSampleInDocument() {
        Dialog dialog = Dialog.builder()
                .callbackId("ryde-46e2b0")
                .submitLabel("Request")
                .notifyOnCancel(true)
                .state("Limo")
                .elements(asElements(
                        dialogText(dt -> dt.label("Pickup Location").name("loc_origin")),
                        dialogText(dt -> dt.label("Dropoff Location").name("loc_destination"))
                ))
                .build();
        String actual = gson.toJson(dialog);
        assertThat(actual, is(equalTo(expected)));
    }

    String externalDataSource = "{\n" +
            "  \"label\": \"Bug ticket\",\n" +
            "  \"name\": \"ticket_list\",\n" +
            "  \"type\": \"select\",\n" +
            "  \"data_source\": \"external\",\n" +
            "  \"min_query_length\": 2,\n" +
            "  \"selected_options\": [\n" +
            "    {\n" +
            "      \"label\": \"[FE-459] Remove the marquee tag\",\n" +
            "      \"value\": \"FE-459\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void externalDataSource() {
        DialogSelectElement element = gson.fromJson(externalDataSource, DialogSelectElement.class);
        assertThat(element.getDataSource(), is(DialogDataSourceType.EXTERNAL));
        assertThat(element.getMinQueryLength(), is(2));
        assertThat(element.getSelectedOptions().size(), is(1));
    }
}
