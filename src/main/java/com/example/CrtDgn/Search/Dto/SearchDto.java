package com.example.CrtDgn.Search.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private String email;
    private String title;
    private Long tourKey;
    private String latitude;
    private String longitude;
    private String sort;
    private String option;
    private String token;
}