package com.rufus.bumblebee.generators.annotation;

import com.rufus.bumblebee.generators.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeneratorAnnotationHandler implements InformationAnnotationHandler<Map<GeneratorDescription, List<GeneratorParameter>>> {

    private final Map<GeneratorDescription, List<GeneratorParameter>> generatorBeans = new HashMap<>();
    private final List<DataGenerator> generators;

    @Autowired
    public GeneratorAnnotationHandler(List<DataGenerator> generators, ApplicationContext context) {
        this.generators = generators;
    }

    @PostConstruct
    @Override
    public void handler() {
        for (DataGenerator generator : generators) {
            Class<?> bean = generator.getClass();
            if (bean.isAnnotationPresent(GeneratorDescription.class) && isGeneratorParameter(bean)) {
                generatorBeans.put(
                        bean.getAnnotation(GeneratorDescription.class),
                        getGeneratorParameters(bean)
                );
            }
        }
    }

    @Override
    public Map<GeneratorDescription, List<GeneratorParameter>> getInformation() {
        return generatorBeans;
    }

    private boolean isGeneratorParameter(Class<?> generatorClass) {
        for (Field field : generatorClass.getDeclaredFields()) {
            if (field.getAnnotation(GeneratorParameter.class) != null) {
                return true;
            }
        }
        return false;
    }

    private List<GeneratorParameter> getGeneratorParameters(Class<?> generatorClass) {
        List<GeneratorParameter> parameters = new ArrayList<>();
        for (Field field : generatorClass.getDeclaredFields()) {
            GeneratorParameter annotation = field.getAnnotation(GeneratorParameter.class);
            if (annotation != null) {
                parameters.add(annotation);
            }
        }
        return parameters;
    }
}
