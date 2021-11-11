package com.simbirsoft.testwork.helper;

import com.simbirsoft.testwork.exeption.StorageException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SplitHelper {

    private static final Logger logger = LogManager.getLogger(SplitHelper.class.getName());


    public static List<String> splitAndSortText(String text) {
        Optional.ofNullable(text).orElseThrow(() -> {
            logger.info("Not found text in Site");
            throw new StorageException("Not found text in Site");
        });
        return Arrays.stream(text.toUpperCase().split("[ ,.—\\-'!?\";:\\[\\]/()\\n\\r\\t]+")).toList().stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }
}
