package com.turingbots.webhook.model;

import lombok.Data;

/**
 * Created by ravindra on 3/10/17.
 */
@Data
public class ErrorResponse {
    private int code;
    private String message;
}
