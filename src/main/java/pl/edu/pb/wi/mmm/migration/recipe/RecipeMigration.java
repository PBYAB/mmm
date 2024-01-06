package pl.edu.pb.wi.mmm.migration.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeMigration {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Category")
    private String category;

    @JsonProperty("Total time")
    private String totalTime;

    @JsonProperty("Ingredients")
    private Set<String> ingredients;

    @JsonProperty("Instructions")
    private String instructions;

    @JsonProperty("Yields")
    private String yields;

    @JsonProperty("Image URL")
    private String imageUrl;
}
