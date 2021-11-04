package com.rufus.bumblebee.generators;

import java.io.Serializable;
import java.util.List;

/**
 * Class : Базовый класс для генерации тестовых данных
 *
 * @author : Baldin Timur
 * @version : 0.0.1
 */
public interface DataGenerator extends Serializable {

    /**
     * Запуск генерации тестовых данных
     */
    List<String> build();

    String getGeneratorName();

}
