package pl.edu.pb.wi.mmm.entity.migration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String _id;

    @Field("code")
    private String code;

    @Field("countries_tags")
    private Set<String> countries_tags;

    @Field("brands_tags")
    private Set<String> brands;

    @Field("quantity")
    private String quantity;

    @Field("categories_tags")
    private Set<String> categoriesTags;

    @Field("allergens_tags")
    private Set<String> allergensTags;

    @Field("ingredients")
    private Set<Ingredient> ingredients;

    @Field("ingredients_text")
    private String ingredients_text;

    @Field("product_name")
    private String product_name;

    @Field("product_name_pl")
    private String product_name_pl;

    @Field("_keywords")
    private Set<String> keywords;

    @Field("nova_group")
    private String nova_group;

    @Field("nutriscore_score")
    private String nutriScoreGrade;

    @Field("nutriments")
    private Nutriment nutriments;

    @Field("ingredients_analysis_tags")
    private Set<String> ingredients_analysis_tags;
}
