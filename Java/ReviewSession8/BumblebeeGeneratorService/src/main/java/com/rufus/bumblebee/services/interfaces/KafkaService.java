package com.rufus.bumblebee.services.interfaces;

import com.rufus.bumblebee.repository.tables.Container;

/**
 * Class : сервис для работы с kafka
 */
public interface KafkaService<T> {
    void sendTestData(T data, Container container);
}
