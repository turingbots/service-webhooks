package com.turingbots.webhook.controller;

import com.turingbots.webhook.model.AimlInput;
import com.turingbots.webhook.model.Request;
import com.turingbots.webhook.model.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.OutputKeys;

/**
 * Created by ravindra on 3/10/17.
 */
@RestController
public class ServiceController {

    private final String RESOURCE_NAME = "/service/v1/webhook";

    private Logger logger = LoggerFactory.getLogger(ServiceController.class.getName());

    private final String nlpServiceUrl = "https://service-citi-api.cfapps.io/service/v1/nlpservice";
    private final String aimlServiceUrl = "https://service-aiml.cfapps.io/v1/message";

    @ApiOperation(value = "Take actions based on the user input",
            nickname = "delegater", response = SuccessResponse.class)
    @RequestMapping(value = RESOURCE_NAME, method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = SuccessResponse.class)})
    public ResponseEntity<?> delegate(@RequestBody Request input) {

        // SuccessResponse response = getOutputFromNlpService(input);
        // Object data = response.getData();
        String accountNumber = input.getAccountNumber();
//        String outPutFromNlpService = "Accounts";
        String outPutFromNlpService = input.getInput();
        SuccessResponse outputFromAimlService = getOutputFromAimlService(accountNumber, outPutFromNlpService);
        // assume out put from nlpservice is success
        return new ResponseEntity<>(outputFromAimlService, HttpStatus.OK);
    }

    private SuccessResponse getOutputFromNlpService(String input) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(input);
        ResponseEntity<?> response = null;

        try {
            response = restTemplate.exchange(nlpServiceUrl, HttpMethod.POST, request, SuccessResponse.class);
        } catch (HttpClientErrorException e) {
            logger.error("error getting data from NLP service");
            e.printStackTrace();
        }

        SuccessResponse successResponse = (SuccessResponse) response.getBody();
        return successResponse;
    }

    private SuccessResponse getOutputFromAimlService(String accountNumber, String input) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = null;

        AimlInput aimlInput = new AimlInput();
        aimlInput.setText(input);
        aimlInput.setUserId(accountNumber);
        HttpEntity<AimlInput> request = new HttpEntity<>(aimlInput);

        try {
            response = restTemplate.exchange(aimlServiceUrl, HttpMethod.POST, request, SuccessResponse.class);
        } catch (HttpClientErrorException e) {
            logger.error("error getting data from AIML service");
            e.printStackTrace();
        }

        SuccessResponse successResponse = (SuccessResponse) response.getBody();
        return successResponse;
    }
}
