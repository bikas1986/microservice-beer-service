package com.bikas.beerservice.services.inventory;

import com.bikas.beerservice.bootstrap.BeerLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {
    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getOnhandInventory() {
        Integer qoh = beerInventoryService.getOnhandInventory(BeerLoader.BEER_1_UUID);

        System.out.println(qoh);

    }
}