package com.rufus.bumblebee.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rufus.bumblebee.repository.tables.Container;
import com.rufus.bumblebee.repository.tables.TestData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TestDataRepositoryTest extends BaseRepository {

    @Autowired
    private TestDataRepository repository;

    @Autowired
    private ContainerRepository containerRepository;

    @Test
    public void testSaveTestData() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Container container = containerRepository.save(getTestContainer());
        List<TestData> testData = new ArrayList<>();
        testData.add(new TestData(ow.writeValueAsString("test"), container.getId(), "SymbolGenerator"));
        Iterable<TestData> result = repository.saveAll(testData);
        result.forEach(
                r -> Assert.assertTrue(r.getId() != 0)
        );
    }
}