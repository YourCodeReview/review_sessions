package com.rufus.bumblebee.services.dto;

/**
 * Class Dto для контейнеров
 */
public class ContainerDto {

    /**
     * Уникальный id контейнера
     */
    private String cuid;

    /**
     * Уникальной имя контейнера
     */
    private String name;

    /**
     * Контейнер статус
     */
    private ContainerStatus status;

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContainerStatus getStatus() {
        return status;
    }

    public void setStatus(ContainerStatus status) {
        this.status = status;
    }
}
