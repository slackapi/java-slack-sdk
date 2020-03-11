package test_with_remote_apis.sample_json_generation;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.util.List;

@Slf4j
public class ObjectInitializer {

    private static final SecureRandom RANDOM = new SecureRandom();

    private ObjectInitializer() {
    }

    public static <T> T initProperties(T obj) {
        Field currentField = null;
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                currentField = field;
                if (Modifier.isStatic(field.getModifiers()) ||
                        Modifier.isFinal(field.getModifiers()) ||
                        Modifier.isAbstract(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);

                Class<?> type = field.getType();
                if (field.getName().startsWith("initial")) {
                    // set as null
                } else if (field.getName().startsWith("image")) {
                    // set as null
                } else if (field.getName().equals("fallback")) {
                    // set as null
                } else if (field.getName().equals("url")) {
                    field.set(obj, "https://www.example.com/test-url");
                } else if (field.getName().equals("actionId") && type.equals(String.class)) {
                    field.set(obj, "action-" + System.currentTimeMillis() + "-" + RANDOM.nextInt(1024));
                } else if (field.getName().equals("blockId") && type.equals(String.class)) {
                    field.set(obj, "block-" + System.currentTimeMillis() + "-" + RANDOM.nextInt(1024));
                } else if (field.getName().equals("style") && type.equals(String.class)) {
                    field.set(obj, "primary");
                } else if (field.getName().equals("initialDate") && type.equals(String.class)) {
                    field.set(obj, "2020-03-11");
                } else if (type.equals(String.class)) {
                    field.set(obj, "str");
                } else if (type.equals(Integer.class)) {
                    field.set(obj, 123);
                } else if (type.equals(Long.class)) {
                    field.set(obj, 123L);
                } else if (type.equals(Double.class)) {
                    field.set(obj, 12.3D);
                } else if (type.equals(Boolean.class)) {
                    field.set(obj, false);
                } else if (type.equals(List.class)) {
                    List listObj = (List) field.get(obj);
                    if (listObj != null) {
                        for (Object o : listObj) {
                            initProperties(o);
                        }
                    } else {
                        log.info("null List object detected {} in {}", field.getName(), obj.getClass().getSimpleName());
                    }
                } else {
                    if (field.get(obj) != null) {
                        initProperties(field.get(obj));
                        continue;
                    }
                    for (Constructor<?> constructor : type.getDeclaredConstructors()) {
                        if (constructor.getParameterCount() == 0) {
                            Object childObj = constructor.newInstance();
                            initProperties(childObj);
                            field.set(obj, childObj);
                            continue;
                        }
                    }
                    log.info("Skipped a field which doesn't have non arg constructor: {} in {}", field.getName(), obj.getClass().getSimpleName());
                }
            }
            return obj;

        } catch (Exception e) {
            // because this method can be used in initializer code
            String message = "Failed to instantiate " + currentField.getType().getName() + " " + currentField.getName()
                    + " in " + obj.getClass().getSimpleName();
            throw new RuntimeException(message, e);
        }
    }

}
