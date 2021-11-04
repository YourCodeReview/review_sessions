package com.rufus.bumblebee.services.generators;

import com.rufus.bumblebee.generators.DataGenerator;
import com.rufus.bumblebee.generators.annotation.GeneratorParameter;
import com.rufus.bumblebee.services.interfaces.GeneratorParametersService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class GeneratorParametersServiceImpl implements GeneratorParametersService {

    private static final Logger log = LoggerFactory.getLogger(GeneratorParametersServiceImpl.class);

    @Override
    public void setParameters(List<Field> fields, Map<String, String> values, DataGenerator generator) throws Exception {
        for (Field field : fields) {
            GeneratorParameter annotation = field.getAnnotation(GeneratorParameter.class);
            if (annotation != null) {
                String value = values.get(annotation.name());
                if (value == null) {
                    log.error(
                            "For one of the parameters of the {} generator, a name is not specified in the annotation GeneratorParameter",
                            generator.getGeneratorName()
                    );
                    throw new Exception(
                            "The parameter name is not specified in the GeneratorParameter annotation for the generator: "
                                    + generator.getGeneratorName()
                    );
                }

                if (StringUtils.isNotEmpty(annotation.convertMethod())) {
                    Method method = annotation.InClass().getMethod(annotation.convertMethod(), annotation.InClass());
                    field.set(generator, method.invoke(annotation.InClass(), value));
                } else {
                    if (!annotation.InClass().equals(String.class)) {
                        Method method = annotation.InClass().getMethod("valueOf", String.class);
                        field.set(generator, method.invoke(annotation.InClass(), value));
                    } else {
                        field.set(generator, value);
                    }
                }
            } else {
                log.error(
                        "An annotation GeneratorParameter was not found for one of the fields of the generator {}",
                        generator.getGeneratorName()
                );
                throw new Exception(
                        "For one of the parameters , the annotation GeneratorParameter was not found , for the generator: "
                                + generator.getGeneratorName()
                );
            }
        }
    }

}
