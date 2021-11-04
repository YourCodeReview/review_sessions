package com.rufus.bumblebee.repository.tables;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "test_data", schema = "repository")
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
public class TestData implements Serializable {

    @Id
    @SequenceGenerator(name = "row_id", sequenceName = "repository.test_data_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "row_id")
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Type(type = "jsonb")
    @Column(name = "value")
    private String value;

    @Column(name = "container_ref")
    private long containerRef;

    @Column(name = "generator_name")
    private String generatorName;

    @ManyToOne
    @JoinColumn(name = "container_ref",insertable = false,updatable = false)
    Container container;

    public TestData(String value, long containerRef, String generatorName) {
        this.value = value;
        this.containerRef = containerRef;
        this.generatorName = generatorName;
    }

    public TestData() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getContainerRef() {
        return containerRef;
    }

    public void setContainerRef(long containerRef) {
        this.containerRef = containerRef;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
