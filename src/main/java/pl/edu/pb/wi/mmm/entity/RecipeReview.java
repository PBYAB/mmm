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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "comment")
    private String comment;
}
