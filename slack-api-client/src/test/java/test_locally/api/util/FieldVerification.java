package test_locally.api.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class FieldVerification {

    private FieldVerification() {
    }

    public static void verifyIfAllGettersReturnNonNull(Object obj) throws Exception {
        verifyIfAllGettersReturnNonNull(obj, false, topLevelGettersToSkip.toArray(new String[]{}));
    }

    public static void verifyIfAllGettersReturnNonNull(Object obj, String... skipGetterNames) throws Exception {
        verifyIfAllGettersReturnNonNull(obj, false, skipGetterNames);
    }

    public static void verifyIfAllGettersReturnNonNullRecursively(Object obj) throws Exception {
        verifyIfAllGettersReturnNonNull(obj, true);
    }

    public static void verifyIfAllGettersReturnNonNullRecursively(Object obj, String... skipGetterNames) throws Exception {
        verifyIfAllGettersReturnNonNull(obj, true, skipGetterNames);
    }

    private static void verifyIfAllGettersReturnNonNull(Object obj, boolean recursive, String... skipGetterNames) throws Exception {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        List<String> _skipGetterNames = new ArrayList<>();
        for (String name : skipGetterNames) {
            _skipGetterNames.add(name);
        }
        _skipGetterNames.add("getBytes"); // String#getBytes()
        _skipGetterNames.add("getHttpResponseHeaders");

        for (Method method : methods) {
            int m = method.getModifiers();
            if (Modifier.isPublic(m)
                    && Modifier.isStatic(m) == false
                    && method.getName().startsWith("get")
                    && method.getParameterCount() == 0
                    && matches(method.getName(), _skipGetterNames) == false) {
                String label = "class: " + clazz.getCanonicalName() + " method: " + method.getName();
                Object result = method.invoke(obj);

                if (recursive && result != null && hasGetters(result, _skipGetterNames)) {
                    verifyIfAllGettersReturnNonNull(result, true, skipGetterNames);
                }
                assertThat(label, result, is(notNullValue()));
                log.info("label: {}, result: {}", label, result);
            }
        }
    }

    private static boolean hasGetters(Object obj, List<String> skipGetterNames) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            int m = method.getModifiers();
            if (Modifier.isPublic(m)
                    && Modifier.isStatic(m) == false
                    && method.getName().startsWith("get")
                    && method.getParameterCount() == 0
                    && matches(method.getName(), skipGetterNames) == false) {
                return true;
            }
        }
        return false;
    }

    private static boolean matches(String methodName, List<String> exactNameOrRegexp) {
        for (String pattern : exactNameOrRegexp) {
            if (methodName.matches(pattern)) {
                return true;
            }
        }
        return false;
    }

    private static final List<String> topLevelGettersToSkip = Arrays.asList(
            "getError",
            "getWarning",
            "getNeeded",
            "getProvided",
            "getResponseMetadata",
            "getContentHighlightHtmlTruncated"
    );

}
