package com.wcl.gtc.dto;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse {

    private int status;
    private String message;
    private List<String> errors;
    private LocalDateTime timestamp;
}

