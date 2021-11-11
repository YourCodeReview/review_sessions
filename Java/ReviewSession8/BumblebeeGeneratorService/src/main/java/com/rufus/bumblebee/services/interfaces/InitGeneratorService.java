package com.rufus.bumblebee.services.interfaces;

import java.util.Map;

/**
 * Class : сервис инициализации генераторов
 */
public interface InitGeneratorService<T> {
    Map<String, String> initGenerators(T request) throws Exception;
}
