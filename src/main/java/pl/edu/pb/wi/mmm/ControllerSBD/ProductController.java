package pl.edu.pb.wi.mmm.ControllerSBD;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import pl.edu.pb.wi.mmm.dto.ProductDTO;
import pl.edu.pb.wi.mmm.dto.ProductIngredientDTO;
import pl.edu.pb.wi.mmm.dto.mapper.AllergenMapper;
import pl.edu.pb.wi.mmm.dto.mapper.BrandMapper;
import pl.edu.pb.wi.mmm.dto.mapper.ProductCategoryMapper;
import pl.edu.pb.wi.mmm.dto.mapper.ProductMapper;

import pl.edu.pb.wi.mmm.entity.User;
import pl.edu.pb.wi.mmm.service.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {
    private final ProductCategoryMapper productCategoryMapper;
    private final BrandMapper brandMapper;
    private final AllergenMapper allergenMapper;

    private final ProductService productService;

    private final AllergenService allergenService;

    private final BrandService brandService;

    private final ProductMapper productMapper;

    private final ProductCategoryService productCategoryService;

    private final CountryService countryService;

    @GetMapping
    public String findAll(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(defaultValue = "id") String sortField
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortField));
        var productPage = productService.findAll(pageRequest).map(productMapper::mapToListElement);

        model.addAttribute("page", productPage);

        return "products";
    }

    @GetMapping("/add")
    public String addView(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        model.addAttribute("productForm", new ProductDTO());
        model.addAttribute("allergens", allergenService.findAll().stream().map(allergenMapper::map).collect(Collectors.toList()));
        model.addAttribute("brands", brandService.findAll().stream().map(brandMapper::map).collect(Collectors.toList()));
        model.addAttribute("categories", productCategoryService.findAll().stream().map(productCategoryMapper::map).collect(Collectors.toList()));
        model.addAttribute("countries", countryService.findAll());
        return "add-product";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute("productForm") ProductDTO form,
            @ModelAttribute("customIngredients") ProductIngredientDTO[] customIngredients,
            BindingResult result,
            @AuthenticationPrincipal User user,
            HttpServletResponse response,
            Model model
    ) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            return "add-product";
        }
        form.setIngredients((Set<ProductIngredientDTO>) Arrays.stream(customIngredients).toList());
        productService.save(productMapper.map(form));

        return "redirect:/products";
    }
}
