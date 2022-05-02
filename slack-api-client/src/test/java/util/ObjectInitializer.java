package util;

import com.slack.api.model.block.composition.OptionGroupObject;
import com.slack.api.model.block.composition.OptionObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.composition.BlockCompositions.asOptions;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Slf4j
public class ObjectInitializer {

    private ObjectInitializer() {
    }

    public static <T> T initProperties(T obj) {
        return initProperties(obj, false);
    }

    public static <T> T initProperties(T obj, boolean attachments) {
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
                if (type.equals(String.class)) {
                    field.set(obj, "");
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
                    if (listObj != null && listObj.size() > 0) {
                        for (Object o : listObj) {
                            initProperties(o);
                        }
                    } else {
                        if (listObj == null) {
                            listObj = new ArrayList();
                            field.set(obj, listObj);
                        }
                        if (attachments) {
                            // log.info("null List object detected {} in {}", field.getName(), obj.getClass().getSimpleName());
                        } else {
                            if (field.getName().equals("options")) {
                                listObj.add(initProperties(OptionObject.builder()
                                        .text(plainText(""))
                                        .description(plainText(""))
                                        .build()
                                ));
                            } else if (field.getName().equals("optionGroups")) {
                                listObj.add(initProperties(OptionGroupObject.builder()
                                        .label(plainText(""))
                                        .options(asOptions(initProperties(OptionObject.builder()
                                                .text(plainText(""))
                                                .description(plainText(""))
                                                .build()
                                        )))
                                        .build()
                                ));
                            } else {
                                // log.info("null List object detected {} in {}", field.getName(), obj.getClass().getSimpleName());
                            }
                        }
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
                    // log.info("Skipped a field which doesn't have non arg constructor: {} in {}", field.getName(), obj.getClass().getSimpleName());
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
