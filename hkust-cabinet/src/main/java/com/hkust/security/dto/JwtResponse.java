package com.hkust.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}
