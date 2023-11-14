package com.abdullahturhan.recipe.repository;

import com.abdullahturhan.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,String> {

    Recipe findRecipeByRequestFoodName(String name);
}
