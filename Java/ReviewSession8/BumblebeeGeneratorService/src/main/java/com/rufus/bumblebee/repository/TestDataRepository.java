package com.rufus.bumblebee.repository;

import com.rufus.bumblebee.repository.tables.TestData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDataRepository extends CrudRepository<TestData, Long> {
    @Override
    <S extends TestData> Iterable<S> saveAll(Iterable<S> entities);
}
