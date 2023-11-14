package com.abdullahturhan.recipe.controller;

import com.abdullahturhan.recipe.controller.validations.FoodNameConstraint;
import com.abdullahturhan.recipe.dto.RecipeDto;
import com.abdullahturhan.recipe.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/recipe/")
@Validated
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping(path = "/{name}")
    public ResponseEntity<RecipeDto> getRecipeByName(@PathVariable("name") @NotBlank @FoodNameConstraint String name) throws IOException {
        return ResponseEntity.status(HttpStatus.FOUND).body(recipeService.getRecipeByName(name));
    }
}
