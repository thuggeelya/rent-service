package com.thuggeelya.rent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {
    private String name;
    private Long cost;
    private String brand;
    private String description;
    private String condition;
    private String serialNumber;
}
