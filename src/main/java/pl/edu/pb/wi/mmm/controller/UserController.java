package pl.edu.pb.wi.mmm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import pl.edu.pb.wi.mmm.controller.handlers.ValidationHandler;
import pl.edu.pb.wi.mmm.dto.CanUserCreateReviewDTO;
import pl.edu.pb.wi.mmm.dto.RecipeReviewDTO;
import pl.edu.pb.wi.mmm.dto.UserDTO;
import pl.edu.pb.wi.mmm.dto.create.CreateRecipeReviewRequest;
import pl.edu.pb.wi.mmm.dto.mapper.RecipeReviewMapper;
import pl.edu.pb.wi.mmm.dto.mapper.UserMapper;
import pl.edu.pb.wi.mmm.entity.RecipeReview;
import pl.edu.pb.wi.mmm.entity.User;
import pl.edu.pb.wi.mmm.service.RecipeReviewService;
import pl.edu.pb.wi.mmm.service.UserService;

import java.net.URI;


@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping(UserController.API_USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    public static final String API_USERS = "/api/v1/users";

    @GetMapping("/me/profile")
    @Operation(summary = "Get user profile")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMapper.map(user));
    }
}
