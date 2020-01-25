package test_locally.api.model.dialog;

import com.github.seratch.jslack.api.model.dialog.Dialog;
import com.github.seratch.jslack.api.model.dialog.DialogSubType;
import org.junit.Test;

import static com.github.seratch.jslack.api.model.dialog.Dialogs.*;
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
}
