package software_capstone.backend.app.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software_capstone.backend.app.inventory.dto.InventoryResponse;
import software_capstone.backend.app.inventory.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController implements InventoryControllerDocs {
    private final InventoryService inventoryService;

    @Override
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getInventory(
            @AuthenticationPrincipal String userId
    ) {
        return ResponseEntity.ok(inventoryService.getInventory(userId));
    }
}
