package com.rufus.bumblebee.services.interfaces;

/**
 * Class : сервис получения исторических данных по контейнерам
 */
public interface ContainerHistoryService<R, I> {
    R getHistory(I input);
}
