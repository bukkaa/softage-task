package ru.softage.task.api.tools;

import java.util.Collection;
import java.util.stream.Collectors;

public class CollectionUtils {

    @SuppressWarnings("unchecked")
    public static String collectionToString(Collection collection) {
        return (String) collection.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
