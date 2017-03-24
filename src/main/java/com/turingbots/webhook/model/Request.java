package com.turingbots.webhook.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ravindra on 3/23/17.
 */
@Data
public class Request {
    @ApiModelProperty
    String input;
    String accountNumber;

}
