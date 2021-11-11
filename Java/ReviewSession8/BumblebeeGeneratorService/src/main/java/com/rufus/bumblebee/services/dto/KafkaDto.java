package com.rufus.bumblebee.services.dto;

import com.rufus.bumblebee.controllers.requests.ReportType;

import java.util.List;

public class KafkaDto {

    private String cuid;
    private String containerName;
    private Boolean historyOn;
    private ReportType reportType;
    private List<TestDataDto> data;

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Boolean getHistoryOn() {
        return historyOn;
    }

    public void setHistoryOn(Boolean historyOn) {
        this.historyOn = historyOn;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public List<TestDataDto> getData() {
        return data;
    }

    public void setData(List<TestDataDto> data) {
        this.data = data;
    }
}
