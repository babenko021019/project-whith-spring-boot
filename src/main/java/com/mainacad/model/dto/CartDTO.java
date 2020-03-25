package com.mainacad.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Integer id;

    @JsonProperty("user")
    private Integer userId;

    @JsonProperty("time")
    private Long creationTime;

    private String status;

}
