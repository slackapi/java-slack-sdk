package test_locally.api.audit;

import com.slack.api.audit.Actions;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class ActionsTest {

    static void verifyConstantFields(Class<?> clazz) {
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers()))
                .collect(toList());
        for (Field f : fields) {
            try {
                String value = String.valueOf(f.get(clazz));
                assertThat(value.replaceAll("\\.", "_"), is(f.getName()));
            } catch (IllegalAccessException e) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    public void naming() {
        verifyConstantFields(Actions.WorkspaceOrOrg.class);
        verifyConstantFields(Actions.User.class);
        verifyConstantFields(Actions.File.class);
        verifyConstantFields(Actions.Channel.class);
        verifyConstantFields(Actions.App.class);
    }

}
