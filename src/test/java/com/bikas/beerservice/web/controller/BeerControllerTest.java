package com.bikas.beerservice.web.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import com.bikas.beerservice.domain.Beer;
import com.bikas.beerservice.repository.BeerRepository;
import com.bikas.beerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

import com.bikas.beerservice.web.model.BeerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.bikas.beerservice.web.mappers")
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
    ObjectMapper objectMapper;

	@MockBean
	BeerRepository beerRepository;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetBeerById() throws Exception {
		given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		mockMvc.perform(
				get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
						.param("iscold", "yes")
				.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
				.andDo(document("v1/beer-get",
						pathParameters(
							parameterWithName("beerId").description("UUID of desired beer to get.")
						),
						requestParameters(
								parameterWithName("iscold").description("Is Beer Cold Query param")
						),
						responseFields(
								fields.withPath("id").description("Id of the Beer").type(UUID.class),
								fields.withPath("version").description("Version number").type(Integer.class),
								fields.withPath("createdDate").description("Date Created").type(OffsetDateTime.class),
								fields.withPath("lastModifiedDate").description("Date Updated").type(OffsetDateTime.class),
								fields.withPath("beerName").description("Beer Name").type(String.class),
								fields.withPath("beerStyle").description("Beer Style").type(BeerStyleEnum.class),
								fields.withPath("upc").description("UPC of Beer").type(Long.class),
								fields.withPath("price").description("Price").type(BigDecimal.class),
								fields.withPath("quantityOnHand").description("Quantity On hand").type(Integer.class)
						)));
	}

	@Test
	void testSaveNewBeer() throws Exception {
		BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
				.andDo(document("v1/beer-post",
						requestFields(
								fields.withPath("id").ignored(),
								fields.withPath("version").ignored(),
								fields.withPath("createdDate").ignored(),
								fields.withPath("lastModifiedDate").ignored(),
								fields.withPath("beerName").description("Name of the beer"),
								fields.withPath("beerStyle").description("Style of Beer"),
								fields.withPath("upc").description("Beer UPC").attributes(),
								fields.withPath("price").description("Beer Price"),
								fields.withPath("quantityOnHand").ignored()
						)));
	}

	@Test
	void testUpdateBeerById() throws Exception {
		BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
	}

	BeerDto getValidBeerDto(){
		return BeerDto.builder()
				.beerName("My Beer")
				.beerStyle(BeerStyleEnum.ALE)
				.price(new BigDecimal("2.99"))
				.upc(123123123123L)
				.build();
	}

	private static class ConstrainedFields {

		private final ConstraintDescriptions constraintDescriptions;

		ConstrainedFields(Class<?> input) {
			this.constraintDescriptions = new ConstraintDescriptions(input);
		}

		private FieldDescriptor withPath(String path) {
			return fieldWithPath(path).attributes(key("constraints").value(StringUtils
					.collectionToDelimitedString(this.constraintDescriptions
							.descriptionsForProperty(path), ". ")));
		}
	}
}

