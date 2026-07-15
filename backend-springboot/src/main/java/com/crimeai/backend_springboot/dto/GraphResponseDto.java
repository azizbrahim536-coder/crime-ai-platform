package com.crimeai.backend_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphResponseDto {
    private List<GraphNodeDto> nodes;
    private List<GraphEdgeDto> edges;
}