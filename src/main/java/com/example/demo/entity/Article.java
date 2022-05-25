package com.example.demo.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Article {
    private int id;
    private String title;
    private String description;
}
