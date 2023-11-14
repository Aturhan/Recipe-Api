package com.abdullahturhan.recipe.dto;

import com.abdullahturhan.recipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Optional;


@Builder
public record RecipeDto(
        String title,
        String ingredients,
        String servings,
        String instructions

) {
    public static RecipeDto converter(Recipe from){
        return RecipeDto.builder()
                .instructions(from.getInstructions())
                .title(from.getTitle())
                .servings(from.getServings())
                .ingredients(from.getIngredients())
                .build();

    }
}
