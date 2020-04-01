package com.bikas.beerservice.model;

import com.bikas.beerservice.web.model.BeerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.io.IOException;

@JsonTest
public class BeerDtoTest extends  BaseTest {

    @Test
    void testSerializeDto() throws JsonProcessingException {
        BeerDto beerDto = getDto();

        String jsonString = objectMapper.writeValueAsString(beerDto);

        System.out.println(jsonString);
    }

    @Test
    void testDeserialize() throws IOException {
        String json = "{\"id\":\"f7d9e316-f260-4e35-8ace-0a5a99ba5aa3\",\"version\":null,\"createdDate\":\"2020-04-01T21:59:37+0530\",\"lastModifiedDate\":\"2020-04-01T21:59:37+0530\",\"beerName\":\"BeerName\",\"beerStyle\":\"ALE\",\"upc\":\"0631234200036\",\"price\":\"12.99\",\"quantityOnHand\":null,\"myLocalDate\":\"20200401\"}";
        BeerDto dto = objectMapper.readValue(json, BeerDto.class);

        System.out.println(dto);

    }
}

