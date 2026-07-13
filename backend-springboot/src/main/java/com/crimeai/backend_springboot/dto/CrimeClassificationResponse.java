package com.crimeai.backend_springboot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrimeClassificationResponse {
    private String typeCrime;
    private Double confidence;
}