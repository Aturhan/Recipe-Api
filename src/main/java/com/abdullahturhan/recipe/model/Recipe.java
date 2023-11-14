package com.abdullahturhan.recipe.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String requestFoodName;
    private String title;
    @Column(length = 500)
    private String ingredients;
    private String servings;
    @Column(length = 500)
    private String instructions;
}
