package com.rufus.bumblebee.generators;

import java.util.Map;

public class GeneratorInformation {
    private String generatorName;
    private Map<String, String> values;

    public String getGeneratorName() {
        return generatorName;
    }

    public GeneratorInformation setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
        return this;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
