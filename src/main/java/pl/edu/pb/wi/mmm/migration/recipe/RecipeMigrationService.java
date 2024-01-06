package pl.edu.pb.wi.mmm.migration.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.mmm.dto.RecipeIngredientForm;
import pl.edu.pb.wi.mmm.dto.create.CreateIngredientRequest;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeRequest;
import pl.edu.pb.wi.mmm.entity.Ingredient;
import pl.edu.pb.wi.mmm.enumeration.Unit;
import pl.edu.pb.wi.mmm.service.IngredientService;
import pl.edu.pb.wi.mmm.service.RecipeService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class RecipeMigrationService {
    private ObjectMapper objectMapper;

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    public void migrate(String jsonFilePath) throws IOException {
        // Read JSON file
        long startTime = System.currentTimeMillis();


        InputStream in = getClass().getResourceAsStream("/" + jsonFilePath);
        byte[] jsonData = in.readAllBytes();

        objectMapper = new ObjectMapper();
        // Convert JSON to Java objects
        List<RecipeMigration> recipeMigrations = Arrays.asList(objectMapper.readValue(jsonData, RecipeMigration[].class));


        // Save each recipe to the database
        for (RecipeMigration recipeMigration : recipeMigrations) {

            ArrayList<RecipeIngredientForm> recipeIngredientForms = new ArrayList<>();
            for (String ingredient : recipeMigration.getIngredients()) {
                String name = null;
                String amount = null;
                Unit unit = null;

                if (ingredient.endsWith(" null")) {
                    ingredient = ingredient.replaceAll(" null", " ");
                }

                if (ingredient.endsWith(" ")) {
                    ingredient = ingredient + "sztuka";
                }

                Matcher matcher = INGREDIENT_PATTERN.matcher(ingredient);


                if (matcher.matches()) {
                    name = matcher.group(1).trim();
                    amount = matcher.group(2).split("\\s")[1];
                    String inputUnit = matcher.group(2).split("\\s")[2];

                    try {
                    unit = switch (inputUnit) {
                        case "gramów", "gramy", "gram", "g" -> Unit.G;
                        case "kilogramów", "kilogramy", "kilogram", "kilograma", "kg" -> {
                            amount = String.valueOf(Double.parseDouble(amount) * 1000);
                            yield Unit.G;
                        }
                        case "miligramów", "miligramy", "miligram", "mg" -> {
                            amount = String.valueOf(Double.parseDouble(amount) / 1000);
                            yield Unit.G;
                        }
                        case "dekagramów", "dekagramy", "dekagram", "dekagrama", "dag" -> {
                            amount = String.valueOf(Double.parseDouble(amount) * 10);
                            yield Unit.G;
                        }
                        case "mililitrów", "mililitry", "mililitr", "ml", "mL" -> Unit.ML;
                        case "litry", "litr","litra", "l" -> {
                            amount = String.valueOf(Double.parseDouble(amount) * 1000);
                            yield Unit.ML;
                        }
                        case "słoik", "słoika", "słoiki", "słoików" -> Unit.JAR;
                        case "pęczek", "pęczka", "pęczki", "pęczków" -> Unit.BUNCH;
                        case "łyżeczka", "łyżeczki", "łyżeczek" -> Unit.TSP;
                        case "łyżka", "łyżki", "łyżek" -> Unit.TBSP;
                        case "szklanki", "szklanka", "szklankę", "szklanką", "szklankach", "szklanek" -> Unit.CUP;
                        case "puszka", "puszek", "puszki" -> Unit.CAN;
                        case "sztuk", "sztuka", "sztuki" -> Unit.PIECE;
                        case "szczypta", "szczypty" -> Unit.PINCH;
                        case "opakowanie", "opakowania" -> Unit.PACKAGE;
                        case "garść", "garście" -> Unit.HANDFUL;
                        case "plaster", "plastry", "plastrów" -> Unit.SLICE;
                        case "null" -> Unit.OTHER;
                        default -> throw new IllegalStateException("Unexpected value: " + inputUnit);
                    };
                    } catch (IllegalStateException e) {
                        // If the unit does not match any of the available units, set it to Unit.PIECE and add the input unit to the name
                        unit = Unit.OTHER;
                        name = name + " " + inputUnit;
                    }
                }   else {
                    // If the ingredient does not match the pattern, assume it has no unit
                    name = ingredient;
                    amount = "1";
                    unit = Unit.PIECE;
                }

                Ingredient ingredient1 = ingredientService.findByName(name);
                if (ingredient1 == null) {
                    CreateIngredientRequest createIngredientRequest = CreateIngredientRequest.builder()
                            .name(name)
                            .build();

                    ingredient1 = ingredientService.createAndSave(createIngredientRequest);
                }

                recipeIngredientForms.add(RecipeIngredientForm.builder()
                        .ingredientId(ingredient1.getId())
                        .amount(Double.parseDouble(amount))
                        .unit(unit)
                        .build());
            }

            String yield = null;
            Matcher matcher = YIELD_PATTERN.matcher(recipeMigration.getYields());
            if (matcher.matches()) {
                yield = matcher.group(1);
            }
            if (recipeService.existsByNameAndUrl(recipeMigration.getTitle(), recipeMigration.getImageUrl())) {
                continue;
            }
            if(recipeMigration.getTotalTime() == null || recipeMigration.getTotalTime().equals("null")) {
                recipeMigration.setTotalTime("0");
            }

            CreateRecipeRequest createRecipeRequest = CreateRecipeRequest.builder()
                    .name(recipeMigration.getTitle())
                    .coverImageUrl(recipeMigration.getImageUrl())
                    .instructions(recipeMigration.getInstructions())
                    .servings(yield != null ? Integer.parseInt(yield) : null)
                    .ingredients(recipeIngredientForms)
                    .totalTime(Integer.valueOf(recipeMigration.getTotalTime()))
                    .build();

            System.out.println(createRecipeRequest.toString());
            System.out.println();
            recipeService.createRecipe(createRecipeRequest, true);

        }
        long endTime = System.currentTimeMillis();


        long executionTime = endTime - startTime;
        System.out.println("Czas wykonania migracji: " + executionTime/1000/60 + " minut");

    }
    private static final Pattern INGREDIENT_PATTERN = Pattern.compile("(.*)(\\s\\d+\\.?\\d*\\s.*)");

    private static final Pattern YIELD_PATTERN = Pattern.compile("(\\d+)\\s.*");

}
