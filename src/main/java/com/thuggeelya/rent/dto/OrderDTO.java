package com.thuggeelya.rent.dto;

import com.thuggeelya.rent.model.enums.EOrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    private EOrderStatus status;
    private String address;
}
