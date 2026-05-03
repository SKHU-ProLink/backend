package software_capstone.backend.app.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.document.ShopItem;
import software_capstone.backend.app.store.service.ShopService;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/items")
    public ResponseEntity<List<ShopItem>> getItems(
            @RequestParam(required = false) ItemCategory category) {
        return ResponseEntity.ok(shopService.getItems(category));
    }
}
