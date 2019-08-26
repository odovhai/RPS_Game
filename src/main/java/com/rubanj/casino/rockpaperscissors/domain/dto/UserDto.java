package com.rubanj.casino.rockpaperscissors.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @JsonProperty(access = Access.WRITE_ONLY)
    @ToStringExclude
    private String password;
    private Date createdAt;
    private Date updatedAt;
}
