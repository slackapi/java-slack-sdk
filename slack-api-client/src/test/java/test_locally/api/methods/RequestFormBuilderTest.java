package test_locally.api.methods;

import com.slack.api.methods.RequestFormBuilder;
import com.slack.api.model.block.LayoutBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestFormBuilderTest {


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class MyTestBlock implements LayoutBlock {
        public static final String TYPE = "myTestBlock";
        private final String type = TYPE;
        private String blockId;
    }

    @Test
    public void testGetJsonWithGsonAnonymInnerClassHandling() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // SETUP
        Method method = RequestFormBuilder.class.getDeclaredMethod("getJsonWithGsonAnonymInnerClassHandling", List.class);
        method.setAccessible(true);

        List<LayoutBlock> blocks = new ArrayList<LayoutBlock>(){{
            add(MyTestBlock.builder().build());
            add(MyTestBlock.builder().build());
        }};

        // EXECUTE
        String json = (String) method.invoke(null, blocks);

        // VALIDATE
        assertThat(json, is("[{\"type\":\"myTestBlock\"},{\"type\":\"myTestBlock\"}]"));
    }

    @Test
    public void testTraditionalGetJsonWithGsonAnonymInnerClassHandling() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // SETUP
        Method method = RequestFormBuilder.class.getDeclaredMethod("getJsonWithGsonAnonymInnerClassHandling", List.class);
        method.setAccessible(true);

        List<LayoutBlock> blocks = new ArrayList<LayoutBlock>();
        blocks.add(MyTestBlock.builder().build());
        blocks.add(MyTestBlock.builder().build());

        // EXECUTE
        String json = (String) method.invoke(null, blocks);

        // VALIDATE
        assertThat(json, is("[{\"type\":\"myTestBlock\"},{\"type\":\"myTestBlock\"}]"));
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
