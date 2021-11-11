package com.rufus.bumblebee.repository;

import com.rufus.bumblebee.repository.tables.Container;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends CrudRepository<Container, Long>, CustomContainerRepository {
    @Override
    <S extends Container> S save(S entity);
}
