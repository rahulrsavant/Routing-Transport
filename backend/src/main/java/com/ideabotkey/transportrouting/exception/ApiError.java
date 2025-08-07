package com.ideabotkey.transportrouting.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String code;
    private String message;
    private String userMessage;
    private java.util.Map<String, String> fieldErrors;

    public ApiError(String code, String message) {
        this(code, message, null, null);
    }

    public ApiError(String code, String message, String userMessage) {
        this(code, message, userMessage, null);
    }
}
