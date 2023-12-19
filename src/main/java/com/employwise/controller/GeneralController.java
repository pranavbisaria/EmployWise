package com.employwise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {
    @GetMapping("/")
    public ResponseEntity<?> status() {
        return new ResponseEntity<>("<h1>Welcome!!</h1><a href = \"/swagger-ui/index.html\">API Documentation</a><br><a href = \"https://github.com/pranavbisaria/employ-wise\">GitHub Repository</a><p id=\"bottom-section\">ThankYou!!!<Br><b>Regards,<br>Pranav Bisaria<br>Phone Number: +918287027446<br>Email: <a href=\"mailto: pranavbisariya29@gmail.com\">pranavbisariya29@gmail.com</a></b></p>", HttpStatus.OK);
    }
}
