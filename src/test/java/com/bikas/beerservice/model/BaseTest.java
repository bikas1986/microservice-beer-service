package com.bikas.beerservice.model;

import com.bikas.beerservice.bootstrao.BeerLoader;
import com.bikas.beerservice.web.model.BeerDto;
import com.bikas.beerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {
    @Autowired
    ObjectMapper objectMapper;

    BeerDto getDto(){
        return  BeerDto.builder()
                .beerName("BeerName")
                .beerStyle(BeerStyleEnum.ALE)
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .price(new BigDecimal("12.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .myLocalDate(LocalDate.now())
                .build();
    }
}
