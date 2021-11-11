package com.rufus.bumblebee.controllers.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ContainerRequest {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private ReportType reportType;
    private boolean historyOn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public boolean getHistoryOn() {
        return historyOn;
    }

    public void setHistoryOn(boolean historyOn) {
        this.historyOn = historyOn;
    }
}
