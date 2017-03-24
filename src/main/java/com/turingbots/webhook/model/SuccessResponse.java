package com.turingbots.webhook.model;

import lombok.Data;

/**
 * Created by ravindra on 3/10/17.
 */
@Data
public class SuccessResponse<T> {
    private T data;
    private String message;
}
