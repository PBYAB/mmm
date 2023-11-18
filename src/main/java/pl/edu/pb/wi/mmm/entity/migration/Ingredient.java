package pl.edu.pb.wi.mmm.entity.migration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @Field("id")
    private String tagName;

    @Field("vegan")
    private String vegan;

    @Field("vegetarian")
    private String vegetarian;

    @Field("from_palm_oil")
    private String from_palm_oil;
}
