package com.example.tripmingle.dto.res.count;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class GetCountriesResDTO {
    private String countryName;
    private String continentName;
    private String capitalName;

    private String countryNameEnglish;
    private String continentNameEnglish;
    private String capitalNameEnglish;

}
