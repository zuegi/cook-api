package ch.zuegi.cook.cookapi.shared.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class StreamUtil {
    public static <T> Stream<T> getNullSafeStream(List<T> list) {
        return Stream.ofNullable(list).flatMap(Collection::stream);
    }
}
