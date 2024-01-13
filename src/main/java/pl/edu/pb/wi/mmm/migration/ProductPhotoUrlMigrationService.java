package pl.edu.pb.wi.mmm.migration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pb.wi.mmm.entity.ProductImage;
import pl.edu.pb.wi.mmm.enumeration.PhotoSize;
import pl.edu.pb.wi.mmm.repository.ProductImageRepository;
import pl.edu.pb.wi.mmm.service.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPhotoUrlMigrationService {

    private final ProductService productService;

    private final ProductImageRepository productImageRepository;

    private static final String AWS_BASE_PATH = "https://openfoodfacts-images.s3.eu-west-3.amazonaws.com";

    //@Transactional
    public void migrate() {
        try (var urlz = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data_keys.txt"))).lines()) {

            var urls = urlz.collect(Collectors.toList());
            log.info("Loading urls file");

            log.info("Loaded {} urls", urls.size());

            var products = productService.findAll(PageRequest.of(0, 100000));
            log.info("Loaded {} products", products.getTotalElements());

            Map<String, List<String>> urlGroups = urls.stream()
                    .collect(Collectors.groupingBy(url -> url.split("/")[1]));

            products.forEach(product -> {
                var productBarcode = product.getBarcode();
                var isEan8 = productBarcode.length() == 8;
                var isEan13 = productBarcode.length() == 13;

                if (!isEan8 && !isEan13) {
                    log.error("Product {} has invalid barcode {}", product.getId(), productBarcode);
                }

                String bucketKey = "";
                try {
                    bucketKey = isEan8 ? productBarcode : productBarcode.substring(0, 3) + "/" + productBarcode.substring(3, 6) + "/" + productBarcode.substring(6, 9) + "/" + productBarcode.substring(9, 13);
                } catch (Exception e) {
                    log.error("Product {} has invalid barcode {}", product.getId(), productBarcode);
                }

                var optionBucketKey = Optional.of(bucketKey);
                // Get the URLs for this bucketKey
                var bucketUrls = urlGroups.getOrDefault(optionBucketKey.orElse(""), Collections.emptyList());

                var images = bucketUrls.stream()
                        .map(url -> {
                            var size = url.contains(".400.jpg") ? PhotoSize.SMALL : PhotoSize.BIG;
                            return ProductImage.builder()
                                    .product(product)
                                    .url(AWS_BASE_PATH + "/" + url)
                                    .size(size)
                                    .build();
                        })
                        .toList();

                productImageRepository.saveAll(images);
            });
        }
    }
}
