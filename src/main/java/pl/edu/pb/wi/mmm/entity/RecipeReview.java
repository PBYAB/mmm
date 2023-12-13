package pl.edu.pb.wi.mmm.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipe_review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RecipeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "comment")
    private String comment;
}
