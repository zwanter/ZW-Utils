package ru.zwanter.utils;

import ru.zwanter.utils.database.data.ISQLRequests;
import ru.zwanter.utils.database.data.SQLRequest;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Comparator;

public class AnnotationProcessor {

    public static void sqlRequestProcess(ISQLRequests obj, Connection connection) {
        Method[] methods = obj.getClass().getDeclaredMethods();

        Method[] annotatedMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(SQLRequest.class))
                .toArray(Method[]::new);

        Arrays.sort(annotatedMethods, Comparator.comparingInt(method -> {
            SQLRequest annotation = method.getAnnotation(SQLRequest.class);
            return annotation.priority();
        }));

        for (Method method : annotatedMethods) {
            try {
                method.invoke(obj, connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
