package pl.edu.pb.wi.mmm.ControllerSBD;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.wi.mmm.entity.Product;
import pl.edu.pb.wi.mmm.entity.Recipe;
import pl.edu.pb.wi.mmm.entity.User;
import pl.edu.pb.wi.mmm.service.ProductService;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

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
        Page<Product> taskPage = productService.findAll(user, pageRequest);

        model.addAttribute("page", taskPage);
        return "products";
    }
}
