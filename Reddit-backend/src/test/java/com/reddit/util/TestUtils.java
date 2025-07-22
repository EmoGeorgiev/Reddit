package com.reddit.util;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

public final class TestUtils {
    private TestUtils() {
    }

    public static final String BLANK_STRING = "        ";

    public static Object[] getArgs(int firstArg, int secondArg) {
        return new Object[] {
                String.valueOf(firstArg),
                String.valueOf(secondArg)
        };
    }

    public static String getStringWithFixedLength(int length) {
        return "a".repeat(length);
    }

    public static Pageable getPageable(int size, String sort, String direction) {
        Sort.Order order = direction.equalsIgnoreCase("desc")
                            ? Sort.Order.desc(sort)
                            : Sort.Order.asc(sort);

        return PageRequest.of(
                0,
                size,
                Sort.by(order)
        );
    }

    public static <T> Page<T> getPage(T item, int size, Pageable pageable) {
        if (size == 0) {
            return Page.empty();
        }

        List<T> elements = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            elements.add(item);
        }

        return new PageImpl<>(elements, pageable, elements.size());
    }
}
