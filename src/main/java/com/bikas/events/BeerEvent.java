package com.bikas.events;
import com.bikas.beerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 1229768353798265471L;

    private BeerDto beerDto;
}
