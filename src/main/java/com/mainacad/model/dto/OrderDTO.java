package com.mainacad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

	private Integer id;
	private Integer itemId;
    private Integer cartId;
    private String itemName;
    private Integer itemPrice;
    private Integer amount;
}
