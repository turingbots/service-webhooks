package com.turingbots.webhook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ravindra on 3/10/17.
 */
@RestController
public class ApiController {
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public ModelAndView getApiInfo() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }
}
