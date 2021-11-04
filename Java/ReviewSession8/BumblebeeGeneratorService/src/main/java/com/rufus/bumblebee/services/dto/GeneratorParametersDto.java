package com.rufus.bumblebee.services.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.rufus.bumblebee.generators.annotation.GeneratorParameter;

/**
 * Class Dto для параметров инициализации генератора
 */
public class GeneratorParametersDto {

    /**
     * Имя параметра {@link GeneratorParameter#name}
     */
    private final String parameter;

    /**
     * Описание параметра {@link GeneratorParameter#description}
     */
    private final String parameterDescription;

    public GeneratorParametersDto(String parameter, String parameterDescription) {
        this.parameter = parameter;
        this.parameterDescription = parameterDescription;
    }

    @JsonGetter
    public String getParameter() {
        return parameter;
    }

    @JsonGetter
    public String getParameterDescription() {
        return parameterDescription;
    }

}
