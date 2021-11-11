package com.rufus.bumblebee.services.generators;

import com.rufus.bumblebee.controllers.requests.GeneratorRequest;
import com.rufus.bumblebee.generators.GeneratorInformation;
import com.rufus.bumblebee.generators.annotation.GeneratorDescription;
import com.rufus.bumblebee.generators.annotation.GeneratorParameter;
import com.rufus.bumblebee.generators.annotation.InformationAnnotationHandler;
import com.rufus.bumblebee.services.interfaces.InitGeneratorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseInitGeneratorService<R> implements InitGeneratorService<R> {

    private final ApplicationContext context;
    private final InformationAnnotationHandler<Map<GeneratorDescription, List<GeneratorParameter>>> handler;

    @Autowired
    protected BaseInitGeneratorService(ApplicationContext context, InformationAnnotationHandler<Map<GeneratorDescription, List<GeneratorParameter>>> handler) {
        this.context = context;
        this.handler = handler;
    }

    protected List<Field> getFields(Class generatorClass) {
        List<Field> fields = new ArrayList<>();
        for (Field f : generatorClass.getFields()) {
            addFieldToList(f, fields);
        }

        for (Field f : generatorClass.getDeclaredFields()) {
            addFieldToList(f, fields);
        }
        return fields;
    }

    private void addFieldToList(Field field, List<Field> fieldsRes) {
        if (field.getAnnotation(GeneratorParameter.class) != null) {
            fieldsRes.add(field);
        }
    }

    protected Object getGeneratorByName(String generatorName) throws Exception {
        for (GeneratorDescription description : handler.getInformation().keySet()) {
            if (description.generatorName().equals(generatorName)) {
                return context.getBean(description.generatorClass());
            }
        }
        throw new Exception("Generator not found: " + generatorName);
    }

    protected void validateGeneratorsRequest(GeneratorRequest request) throws Exception {
        if (StringUtils.isEmpty(request.getCuid())) {
            throw new Exception("Parameter cuid is null or empty ");
        }
        for (GeneratorInformation information : request.getGeneratorInfo()) {
            if (!checkGenerator(information.getGeneratorName())) {
                throw new Exception("Invalid value passed generatorName: " + information.getGeneratorName());
            }
            if (!checkGeneratorParameters(information.getValues(), information.getGeneratorName())) {
                throw new Exception("Invalid value passed generatorParameters: " + information.getValues());
            }
        }
    }

    private boolean checkGeneratorParameters(Map<String, String> generatorParameters, String generatorName) {
        for (GeneratorDescription description : handler.getInformation().keySet()) {
            if (description.generatorName().equals(generatorName)) {
                return handler.getInformation().get(description)
                        .stream()
                        .filter(s -> generatorParameters.containsKey(s.name()))
                        .count() == handler.getInformation().get(description).size();
            }
        }
        return false;
    }

    private boolean checkGenerator(String generatorName) {
        return handler.getInformation().keySet()
                .stream().anyMatch(s -> s.generatorName().equals(generatorName));
    }
}
