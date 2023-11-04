package pl.edu.pb.wi.mmm.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class CreateArticleCategoryRequest {
    private String name;
}
