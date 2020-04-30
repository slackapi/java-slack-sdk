package test_locally.api.model.dialog;

import com.slack.api.model.dialog.Dialog;
import com.slack.api.model.dialog.DialogDataSourceType;
import com.slack.api.model.dialog.DialogSubType;
import org.junit.Test;

import static com.slack.api.model.dialog.Dialogs.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DialogsTest {

    @Test
    public void test() {
        Dialog dialog = dialog(d -> {
            return d.elements(asElements(
                    dialogSelect(ds ->
                            ds.label("aaa")
                                    .name("nnnn")
                                    .options(asOptions(dialogOption(opt -> opt.label("l").value("v"))))
                    ),
                    dialogText(dt -> dt.name("foo")),
                    dialogTextArea(dta -> dta.subtype(DialogSubType.NUMBER))
            ));
        });
        assertThat(dialog, is(notNullValue()));
    }

    @Test
    public void testDataSource() {
        Dialog dialog = dialog(d -> {
            return d.elements(asElements(
                    dialogSelect(ds ->
                            ds.label("aaa")
                                    .name("nnnn")
                                    .dataSource(DialogDataSourceType.CONVERSATIONS)
                    ),
                    dialogText(dt -> dt.name("foo")),
                    dialogTextArea(dta -> dta.subtype(DialogSubType.NUMBER))
            ));
        });
        assertThat(dialog, is(notNullValue()));
    }
}
