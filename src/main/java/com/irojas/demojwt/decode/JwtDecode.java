package com.irojas.demojwt.decode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irojas.demojwt.Jwt.JwtService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/v1/")
public class JwtDecode {
    @Autowired
    private JwtService jwtService;

    @PostMapping("/gettoken")
    public ResponseEntity<?> createComment(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            Claims claims = jwtService.extractAllClaims(jwtToken);
            
            return ResponseEntity.ok(claims); // Devolver las claims en el cuerpo de la respuesta
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    

}

