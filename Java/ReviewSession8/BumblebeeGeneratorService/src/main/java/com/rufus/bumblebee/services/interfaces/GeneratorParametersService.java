package com.rufus.bumblebee.services.interfaces;

import com.rufus.bumblebee.generators.DataGenerator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Class : сервис для инициализации параметров генератора
 */
public interface GeneratorParametersService {
    void setParameters(List<Field> fields, Map<String, String> values, DataGenerator generator) throws Exception;
}
