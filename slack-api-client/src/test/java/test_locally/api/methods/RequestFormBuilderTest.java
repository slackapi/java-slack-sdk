package test_locally.api.methods;

import com.slack.api.methods.RequestFormBuilder;
import com.slack.api.model.block.LayoutBlock;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.divider;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestFormBuilderTest {

    @Test
    public void testGetJsonWithGsonAnonymInnerClassHandling() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // SETUP
        Method method = RequestFormBuilder.class.getDeclaredMethod("getJsonWithGsonAnonymInnerClassHandling", List.class);
        method.setAccessible(true);

        List<LayoutBlock> blocks = new ArrayList<LayoutBlock>(){{
            add(divider());
            add(divider());
        }};

        // EXECUTE
        String json = (String) method.invoke(null, blocks);

        // VALIDATE
        assertThat(json, is("[{\"type\":\"divider\"},{\"type\":\"divider\"}]"));
    }

    @Test
    public void testTraditionalGetJsonWithGsonAnonymInnerClassHandling() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // SETUP
        Method method = RequestFormBuilder.class.getDeclaredMethod("getJsonWithGsonAnonymInnerClassHandling", List.class);
        method.setAccessible(true);

        List<LayoutBlock> blocks = new ArrayList<LayoutBlock>();
        blocks.add(divider());
        blocks.add(divider());

        // EXECUTE
        String json = (String) method.invoke(null, blocks);

        // VALIDATE
        assertThat(json, is("[{\"type\":\"divider\"},{\"type\":\"divider\"}]"));
    }

    @Test
    public void testEmptyGetJsonWithGsonAnonymInnerClassHandling() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // SETUP
        Method method = RequestFormBuilder.class.getDeclaredMethod("getJsonWithGsonAnonymInnerClassHandling", List.class);
        method.setAccessible(true);

        List<LayoutBlock> blocks = new ArrayList<>();

        // EXECUTE
        String json = (String) method.invoke(null, blocks);

        // VALIDATE
        assertThat(json, is("[]"));
    }
}
