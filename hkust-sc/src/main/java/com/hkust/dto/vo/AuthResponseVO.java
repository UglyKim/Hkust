package com.hkust.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseVO implements Serializable {

    private static final long serialVersionUID = 1908397456003330109L;

    @Schema(required = true, description = "token")
    private String token;

    @Schema(required = true, description = "roles")
    private Collection<String> roles;

    @Override
    public String toString() {
        return "AuthResponseVO{" +
                "token='" + token + '\'' +
                ", roles=" + roles +
                '}';
    }
}
