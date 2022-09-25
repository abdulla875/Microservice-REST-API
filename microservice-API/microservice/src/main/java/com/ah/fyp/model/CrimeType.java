package com.ah.fyp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrimeType {

    private String cType;

    private int amount;
}