package com.bikas.beerservice.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bikas.beerservice.domain.Beer;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID>{

}
