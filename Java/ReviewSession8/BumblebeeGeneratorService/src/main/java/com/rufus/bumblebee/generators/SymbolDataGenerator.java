package com.rufus.bumblebee.generators;

import com.rufus.bumblebee.generators.annotation.GeneratorDescription;
import com.rufus.bumblebee.generators.annotation.GeneratorParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.rufus.bumblebee.generators.SymbolDataGenerator.DataMode.NUMBER;
import static com.rufus.bumblebee.generators.SymbolDataGenerator.DataMode.STRING;

@GeneratorDescription(
        generatorName = "SymbolGenerator",
        generatorClass = SymbolDataGenerator.class,
        description = "Generator for create random values")
public class SymbolDataGenerator implements DataGenerator {

    @GeneratorParameter(
            name = "len",
            description = "The length of the text value, applied if isCascade = false",
            InClass = Integer.class)
    private Integer len;

    @GeneratorParameter(
            name = "count",
            description = "Number of text values in the list",
            InClass = Integer.class)
    private Integer count;

    @GeneratorParameter(
            name = "mode",
            description = "Maybe value STRING or NUMBER",
            InClass = String.class)
    private String mode;

    @GeneratorParameter(
            name = "isNull",
            description = "The presence of a NULL value",
            InClass = Boolean.class)
    private Boolean isNull;

    @GeneratorParameter(
            name = "isCascade",
            description = "Cascading increment of values in a text expression",
            InClass = Boolean.class)
    private Boolean isCascade;

    private final int MIN_ID_STRING = 1;
    private final int MAX_ID_STRING = 192;
    private final int MIN_ID_NUMERIC = 48;
    private final int MAX_ID_NUMERIC = 58;

    @Override
    public List<String> build() {
        List<String> values = new ArrayList<>(count + 1);
        if (DataMode.valueOf(mode).equals(STRING)) {
            generateData(MIN_ID_STRING, MAX_ID_STRING, values);
        }

        if (DataMode.valueOf(mode).equals(NUMBER)) {
            generateData(MIN_ID_NUMERIC, MAX_ID_NUMERIC, values);
        }
        return values;
    }

    @Override
    public String getGeneratorName() {
        return Optional.of(
                this.getClass().getAnnotation(GeneratorDescription.class).generatorName()
        ).orElse("DEFAULT");
    }

    private void generateData(int startSeq, int endSeq, List<String> values) {
        StringBuilder buffer = new StringBuilder();
        if (isNull) {
            String nullValue = null;
            buffer.append(nullValue);
        }
        for (int i = 1; i <= count; i++) {
            if (isCascade) {
                for (int j = 1; j <= i; j++) {
                    buffer.append((char) ThreadLocalRandom.current().nextInt(startSeq, endSeq));
                }
            } else {
                for (int j = 1; j <= len; j++) {
                    buffer.append((char) ThreadLocalRandom.current().nextInt(startSeq, endSeq));
                }
            }
            values.add(buffer.toString());
            buffer.delete(0, i);
        }
    }

    enum DataMode {
        STRING("STRING"),
        NUMBER("NUMBER");
        String key;

        DataMode(String key) {
            this.key = key;
        }
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setNull(Boolean aNull) {
        isNull = aNull;
    }

    public void setCascade(Boolean cascade) {
        isCascade = cascade;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getLen() {
        return len;
    }
}
