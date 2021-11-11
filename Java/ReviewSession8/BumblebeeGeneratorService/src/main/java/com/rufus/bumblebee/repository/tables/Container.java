package com.rufus.bumblebee.repository.tables;

import com.rufus.bumblebee.controllers.requests.ReportType;
import com.rufus.bumblebee.services.dto.ContainerStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "containers", schema = "repository")
public class Container implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "containers_id", sequenceName = "repository.containers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "containers_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime date;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "history_on")
    private boolean historyOn;

    @Enumerated(EnumType.STRING)
    private ContainerStatus status;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    @Column(columnDefinition = "UUID")
    private UUID cuid;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "container")
    private List<TestData> data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public boolean getHistoryOn() {
        return historyOn;
    }

    public void setHistoryOn(boolean historyOn) {
        this.historyOn = historyOn;
    }

    public ContainerStatus getStatus() {
        return status;
    }

    public void setStatus(ContainerStatus status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public List<TestData> getData() {
        return data;
    }

    public void setData(List<TestData> data) {
        this.data = data;
    }

    public UUID getCuid() {
        return cuid;
    }

    public void setCuid(UUID cuid) {
        this.cuid = cuid;
    }
}
