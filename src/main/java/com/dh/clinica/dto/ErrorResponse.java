package com.dh.clinica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private int statusCode;
    private String message;
    private List<String> moreInfo;
}
