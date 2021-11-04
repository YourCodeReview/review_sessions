package com.rufus.bumblebee.repository;

import com.rufus.bumblebee.repository.tables.Container;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class ContainerRepositoryImpl implements CustomContainerRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Container getContainerByCuid(String cuid) {
        return em.createQuery("select c from Container as c where c.cuid=:cuid", Container.class)
                .setParameter("cuid", UUID.fromString(cuid))
                .getSingleResult();
    }

    @Override
    public Container getContainerByName(String name) {
        return em.createQuery("select c from Container as c where c.name=:name", Container.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
