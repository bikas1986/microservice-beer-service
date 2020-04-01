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
        //String json = "{\"id\":\"639c00ff-9993-4ec1-af12-8277c85f93bb\",\"beerName\":\"BeerName\",\"beerStyle\":\"ALE\",\"upc\":123123123123,\"price\":12.99,\"createdDate\":\"2019-06-02T16:35:58.321001-04:00\",\"lastModifiedDate\":\"2019-06-02T16:35:58.321872-04:00\"}";
        String json = "{\"id\":\"797c08a4-5a01-429c-8b88-40d53c2556c3\",\"version\":null,\"createdDate\":\"2020-04-01T20:10:09+0530\",\"lastModifiedDate\":\"2020-04-01T20:10:09.972363+05:30\",\"beerName\":\"BeerName\",\"beerStyle\":\"ALE\",\"upc\":123123123123,\"price\":\"12.99\",\"quantityOnHand\":null,\"myLocalDate\":\"20200401\"}";

        BeerDto dto = objectMapper.readValue(json, BeerDto.class);

        System.out.println(dto);

    }
}

