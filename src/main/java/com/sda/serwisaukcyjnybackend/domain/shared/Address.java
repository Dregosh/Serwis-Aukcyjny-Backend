package com.sda.serwisaukcyjnybackend.domain.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address implements Serializable {
    private String city;
    private String state;
    private String street;
    private String number;
    private String postal;
}
