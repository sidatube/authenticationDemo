package com.example.demo.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
}
