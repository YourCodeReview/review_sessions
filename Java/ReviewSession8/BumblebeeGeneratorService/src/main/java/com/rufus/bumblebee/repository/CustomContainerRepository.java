package com.rufus.bumblebee.repository;

import com.rufus.bumblebee.repository.tables.Container;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomContainerRepository {

    Container getContainerByCuid(String cuid);

    Container getContainerByName(String name);
}
