package software_capstone.backend.app.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.store.document.category.ItemCategory;
import software_capstone.backend.app.store.dto.request.PurchaseRequest;
import software_capstone.backend.app.store.dto.response.InventoryResponse;
import software_capstone.backend.app.store.dto.response.PurchaseResponse;
import software_capstone.backend.app.store.dto.response.ShopItemResponse;
import software_capstone.backend.app.store.service.InventoryService;
import software_capstone.backend.app.store.service.ShopService;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController implements ShopControllerDocs {

    private final ShopService shopService;
    private final InventoryService inventoryService;

    @Override
    @GetMapping("/items")
    public ResponseEntity<List<ShopItemResponse>> getItems(
            @AuthenticationPrincipal String userId,
            @RequestParam(required = false) ItemCategory category
    ) {
        return ResponseEntity.ok(shopService.getItems(userId, category));
    }

    @Override
    @PostMapping("/items/purchase")
    public ResponseEntity<PurchaseResponse> purchaseItem(
            @AuthenticationPrincipal String userId,
            @RequestBody PurchaseRequest request
    ) {
        return ResponseEntity.ok(shopService.purchaseItem(userId, request));
    }

    @Override
    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryResponse>> getInventory(
            @AuthenticationPrincipal String userId
    ) {
        return ResponseEntity.ok(inventoryService.getInventory(userId));
    }
}
