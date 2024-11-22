package com.taskflow.demo.controllers;

import com.taskflow.demo.records.CreateResponse;
import com.taskflow.demo.records.SignInRequestRecord;
import com.taskflow.demo.records.SignInResponseRecord;
import com.taskflow.demo.records.SignUpRequestRecord;
import com.taskflow.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public CreateResponse signUp(@RequestBody SignUpRequestRecord signUpRequest) {
        try {
            authService.signUp(signUpRequest);
            return new CreateResponse(null,  "Success", "el usuario se ha registrado correctamente");
        } catch (RuntimeException e) {
            return new CreateResponse(null, "Error", "fallo el registro deol usuario");
        }
    }

    @PostMapping("/signin")
    public CreateResponse signIn(@RequestBody SignInRequestRecord signInRequest) {
        try {
            CreateResponse response = authService.signIn(signInRequest);
            return response;
        } catch (RuntimeException e) {
            return new CreateResponse(null, "Error", "usuario o contraseña incorrectos");
        }
    }
}