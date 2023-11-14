package com.abdullahturhan.recipe.service;

import com.abdullahturhan.recipe.dto.RecipeDto;
import com.abdullahturhan.recipe.model.Recipe;
import com.abdullahturhan.recipe.repository.RecipeRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@CacheConfig(cacheNames = {"recipes"})
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.api-ninjas.com/v1/recipe?X-Api-Key={YOUR_API_KEY}&offset=0&query=";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RecipeService(RecipeRepository recipeRepository, RestTemplate restTemplate) {
        this.recipeRepository = recipeRepository;
        this.restTemplate = restTemplate;
    }
    //@Cacheable(key = "#recipes",unless = "#result == null")
    @Cacheable(value = "recipes", key = "#name", unless = "#result == null")
    public RecipeDto getRecipeByName(String name) throws IOException {
        log.info("Request from cache: " + name);
        Recipe recipeFromDb = recipeRepository.findRecipeByRequestFoodName(name);
        if (recipeFromDb == null){
            log.info("Request from cache: " + name);
            return RecipeDto.converter((Recipe) getRecipeFromApi(name));
        }
        return RecipeDto.converter(recipeFromDb);

    }

    private Recipe getRecipeFromApi(String name) throws IOException {
        ResponseEntity<String> recipe = restTemplate.getForEntity(API_URL+name,String.class);
        log.info(String.format("Recipe from: %s",recipe));
        List<RecipeDto> recipeDto = objectMapper.readValue(recipe.getBody(), new TypeReference<List<RecipeDto>>() {});
        log.info(String.format("Recipe list: %s",recipeDto.size()));
        if (!recipeDto.isEmpty()) {
            RecipeDto firstRecipeDto = recipeDto.get(0);
            return saveRecipe(name, firstRecipeDto);
        }
        throw new RuntimeException("Recipe list is empty");

    }



    public Recipe saveRecipe(String name, RecipeDto recipeDto){
        Recipe newRecipe = Recipe.builder()
                .title(recipeDto.title())
                .requestFoodName(name)
                .ingredients(recipeDto.ingredients())
                .instructions(recipeDto.instructions())
                .servings(recipeDto.servings())
                .build();
        log.info(String.format("Recipe saved successfully = %s",newRecipe));
        return recipeRepository.save(newRecipe);

    }
}
