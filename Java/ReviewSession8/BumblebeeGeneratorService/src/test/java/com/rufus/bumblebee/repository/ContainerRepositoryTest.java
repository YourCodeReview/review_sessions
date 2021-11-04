package com.rufus.bumblebee.repository;

import com.rufus.bumblebee.repository.tables.Container;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.NoResultException;

public class ContainerRepositoryTest extends BaseRepository {

    @Autowired
    private ContainerRepository repository;

    @Test
    public void testSaveContainer() {
        Container saveContainer = repository.save(getTestContainer());
        Assert.assertTrue(saveContainer.getId() != 0);
    }

    @Test
    public void testGetContainerById() throws NoResultException {
        Container containerAfterSave = repository.save(getTestContainer());
        Container containerAfterGet = repository.getContainerByCuid(containerAfterSave.getCuid().toString());

        Assert.assertNotNull(containerAfterGet);
        Assert.assertEquals(containerAfterGet.getId(), containerAfterSave.getId());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testRemoveContainer() throws NoResultException {
        Container containerAfterSave = repository.save(getTestContainer());
        repository.delete(containerAfterSave);
        repository.getContainerByCuid(containerAfterSave.getCuid().toString());
    }
}
