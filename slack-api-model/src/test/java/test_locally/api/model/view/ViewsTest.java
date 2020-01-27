package test_locally.api.model.view;

import com.slack.api.model.view.View;
import org.junit.Test;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.view.Views.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ViewsTest {

    @Test
    public void testView() {
        View view = view(r -> {
            return r.title(viewTitle(t -> t.text("My App")))
                    .close(viewClose(c -> c.text("Close")))
                    .submit(viewSubmit(s -> s.text("Submit")))
                    .blocks(asBlocks(
                            section(sec -> sec.text(plainText(pt -> pt.text("Hi")))),
                            image(img -> img.imageUrl("https://www.example.com/foo.png"))
                    ));
        });
        assertThat(view, is(notNullValue()));
    }
}
