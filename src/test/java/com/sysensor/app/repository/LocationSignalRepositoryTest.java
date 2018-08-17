package com.sysensor.app.repository;

import com.sysensor.app.TestConst;
import com.sysensor.app.model.LocationSignal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocationSignalRepositoryTest {
    @Autowired
    LocationSignalRepo locationSignalRepo;

    @Test
    public void signalShouldReturnTheCorrectAttributes() {
        Optional<LocationSignal> signalOptional = locationSignalRepo.findById(TestConst.SIGNAL_ONE_UUID);
        Assert.assertTrue(signalOptional.isPresent());

        LocationSignal locationSignal = signalOptional.get();
        Assert.assertEquals(TestConst.SIGNAL_ONE_UUID, locationSignal.getUuid());
        Assert.assertEquals(TestConst.BUS_ONE_UUID, locationSignal.getBus_uuid());
        Assert.assertEquals("7.570299", locationSignal.getLat());
        Assert.assertEquals("79.913509", locationSignal.getLng());
    }

    @Test
    public void signalShouldCreatedAndSaveTheCreatedTimeStamp() {
        LocationSignal locationSignal = new LocationSignal();
        locationSignal.setBus_uuid(TestConst.BUS_ONE_UUID);
        locationSignal.setLat("78.09899");
        locationSignal.setLng("56.78990");

        locationSignalRepo.save(locationSignal);
        Assert.assertNotNull(locationSignal.getUuid());
        Assert.assertNotNull(locationSignal.getCreated());
        Optional<LocationSignal> signalAfterSaveOptional = locationSignalRepo.findById(locationSignal.getUuid());
        Assert.assertTrue(signalAfterSaveOptional.isPresent());

        LocationSignal locationSignalAfterSave = signalAfterSaveOptional.get();
        Assert.assertEquals(locationSignal.getUuid(), locationSignalAfterSave.getUuid());
        Assert.assertEquals(TestConst.BUS_ONE_UUID, locationSignalAfterSave.getBus_uuid());
        Assert.assertEquals("78.09899", locationSignalAfterSave.getLat());
        Assert.assertEquals("56.78990", locationSignalAfterSave.getLng());
    }

    @Test
    public void signalShouldFilteredAndReturnTheResultsInDescendingOrder() {
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(0, 5, sort);

        Page<LocationSignal> page = locationSignalRepo.findAllByBusUUID(TestConst.BUS_ONE_UUID, pageable);
        Assert.assertEquals(3, page.getNumberOfElements());

        LocationSignal locationSignalFilteredOne = page.getContent().get(0);
        Assert.assertEquals("lf4cb437-4881-450b-b0fa-4cfe077b4007", locationSignalFilteredOne.getUuid());
        Assert.assertEquals(TestConst.BUS_ONE_UUID, locationSignalFilteredOne.getBus_uuid());
        Assert.assertEquals(16, locationSignalFilteredOne.getCreated().getMinute());
        Assert.assertEquals("10.570299", locationSignalFilteredOne.getLat());
        Assert.assertEquals("79.913509", locationSignalFilteredOne.getLng());

        LocationSignal locationSignalFilteredTwo = page.getContent().get(1);
        Assert.assertEquals("lf4cb437-4881-450b-b0fa-4cfe077b4004", locationSignalFilteredTwo.getUuid());
        Assert.assertEquals(TestConst.BUS_ONE_UUID, locationSignalFilteredTwo.getBus_uuid());
        Assert.assertEquals(13, locationSignalFilteredTwo.getCreated().getMinute());
        Assert.assertEquals("8.570299", locationSignalFilteredTwo.getLat());
        Assert.assertEquals("79.913509", locationSignalFilteredTwo.getLng());

        LocationSignal locationSignalFilteredThree = page.getContent().get(2);
        Assert.assertEquals("lf4cb437-4881-450b-b0fa-4cfe077b4001", locationSignalFilteredThree.getUuid());
        Assert.assertEquals(TestConst.BUS_ONE_UUID, locationSignalFilteredThree.getBus_uuid());
        Assert.assertEquals(10, locationSignalFilteredThree.getCreated().getMinute());
        Assert.assertEquals("7.570299", locationSignalFilteredThree.getLat());
        Assert.assertEquals("79.913509", locationSignalFilteredThree.getLng());
    }

    @Test
    public void signalShouldFilteredAndReturnTheResultsInAscendingOrder() {
        Sort sort = Sort.by(Sort.Direction.ASC, "created");
        Pageable pageable = PageRequest.of(0, 5, sort);

        Page<LocationSignal> page = locationSignalRepo.findAllByBusUUID(TestConst.BUS_ONE_UUID, pageable);
        Assert.assertEquals(3, page.getNumberOfElements());

        LocationSignal locationSignalFilteredThree = page.getContent().get(0);
        Assert.assertEquals("lf4cb437-4881-450b-b0fa-4cfe077b4001", locationSignalFilteredThree.getUuid());
        Assert.assertEquals(TestConst.BUS_ONE_UUID, locationSignalFilteredThree.getBus_uuid());
        Assert.assertEquals(10, locationSignalFilteredThree.getCreated().getMinute());
        Assert.assertEquals("7.570299", locationSignalFilteredThree.getLat());
        Assert.assertEquals("79.913509", locationSignalFilteredThree.getLng());

        LocationSignal locationSignalFilteredTwo = page.getContent().get(1);
        Assert.assertEquals("lf4cb437-4881-450b-b0fa-4cfe077b4004", locationSignalFilteredTwo.getUuid());
        Assert.assertEquals(TestConst.BUS_ONE_UUID, locationSignalFilteredTwo.getBus_uuid());
        Assert.assertEquals(13, locationSignalFilteredTwo.getCreated().getMinute());
        Assert.assertEquals("8.570299", locationSignalFilteredTwo.getLat());
        Assert.assertEquals("79.913509", locationSignalFilteredTwo.getLng());

        LocationSignal locationSignalFilteredOne = page.getContent().get(2);
        Assert.assertEquals("lf4cb437-4881-450b-b0fa-4cfe077b4007", locationSignalFilteredOne.getUuid());
        Assert.assertEquals(TestConst.BUS_ONE_UUID, locationSignalFilteredOne.getBus_uuid());
        Assert.assertEquals(16, locationSignalFilteredOne.getCreated().getMinute());
        Assert.assertEquals("10.570299", locationSignalFilteredOne.getLat());
        Assert.assertEquals("79.913509", locationSignalFilteredOne.getLng());


    }

}
