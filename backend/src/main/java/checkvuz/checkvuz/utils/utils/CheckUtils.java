package checkvuz.checkvuz.utils.utils;

import checkvuz.checkvuz.utils.erros.FieldCheckingException;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.util.*;

public class CheckUtils {

    public static void checkParams(@NonNull Map<String, Object> params,
                                   @NonNull Class<?> objClass) {

        Map<String, Class<?>> allowedTypes = getStringClassMap(objClass);

        checkParamKeys(params, allowedTypes);
        checkParamValues(params, allowedTypes);
    }

    /**
     * We have to make sure that all class object of which is being checked`s keys are correct
     */
    private static void checkParamKeys(@NonNull Map<String, Object> params,
                                       @NonNull Map<String, Class<?>> allowedTypes) {

        Set<String> seenKeys = new HashSet<>();
        Set<String> allowedKeys = allowedTypes.keySet();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            if (seenKeys.contains(key) || !allowedKeys.contains(key)) {
                throw new FieldCheckingException(key);
            }

            seenKeys.add(key);
        }
    }

    /**
     * We need only specified value types:
     * "fieldTitle": [filedType.class], e.t.c
     */
    private static void checkParamValues(@NonNull Map<String, Object> params,
                                         @NonNull Map<String, Class<?>> allowedTypes) {

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            var value = entry.getValue();

            if (value.getClass() != allowedTypes.get(key)) {
                throw new FieldCheckingException(key, value.getClass());
            }
        }
    }

    @NonNull
    private static Map<String, Class<?>> getStringClassMap(@NonNull Class<?> objClass) {
        Map<String, Class<?>> allowedTypes = new HashMap<>();

        Field[] universityClassFields = objClass.getDeclaredFields();
        for (Field universityClassField : universityClassFields) {
            String fieldName = universityClassField.getName();
            Class<?> fieldType = universityClassField.getType();
            allowedTypes.put(fieldName, fieldType);
        }
        return allowedTypes;
    }
}
