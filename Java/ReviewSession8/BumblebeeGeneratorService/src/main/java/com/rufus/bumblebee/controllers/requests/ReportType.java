package com.rufus.bumblebee.controllers.requests;

public enum ReportType {

    EXCEL_TYPE("excel", "application/vnd.ms-excel");

    private final String name;
    private final String mediaType;

    ReportType(String name, String mediaType) {
        this.name = name;
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public String getMediaType() {
        return mediaType;
    }

}
