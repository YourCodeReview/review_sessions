package com.rufus.bumblebee.services.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.rufus.bumblebee.generators.annotation.GeneratorDescription;
import com.rufus.bumblebee.generators.annotation.GeneratorParameter;

import java.util.List;

/**
 * Class Dto для параметров генератора
 */
public class GeneratorDto {

    /**
     * Имя генератора {@link GeneratorDescription#generatorName}
     */
    private final String generatorName;

    /**
     * Описание генератора {@link GeneratorDescription#description}
     */
    private final String generatorDescription;

    /**
     * Массив параметров генератора {@link GeneratorParameter}
     */
    List<GeneratorParametersDto> parameters;

    public GeneratorDto(String generatorName, String generatorDescription, List<GeneratorParametersDto> parameters) {
        this.generatorName = generatorName;
        this.generatorDescription = generatorDescription;
        this.parameters = parameters;
    }

    @JsonGetter
    public String getGeneratorName() {
        return generatorName;
    }

    @JsonGetter
    public String getGeneratorDescription() {
        return generatorDescription;
    }

    @JsonGetter
    public List<GeneratorParametersDto> getParameters() {
        return parameters;
    }
}
