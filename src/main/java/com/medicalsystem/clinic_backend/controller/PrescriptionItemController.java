package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.PrescriptionItem;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.PrescriptionItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescription-items")
@RequiredArgsConstructor
public class PrescriptionItemController {

    private final PrescriptionItemService prescriptionItemService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<PrescriptionItem>> getAllPrescriptionItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(prescriptionItemService.getAllPrescriptionItems(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionItem> getPrescriptionItemById(@PathVariable Long id) {
        PrescriptionItem item = prescriptionItemService.getPrescriptionItemById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/prescription/{prescriptionId}")
    public ResponseEntity<List<PrescriptionItem>> getPrescriptionItemsByPrescriptionId(@PathVariable Long prescriptionId) {
        List<PrescriptionItem> result = prescriptionItemService.getPrescriptionItemsByPrescriptionId(prescriptionId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<PrescriptionItem> createPrescriptionItem(@Valid @RequestBody PrescriptionItem prescriptionItem) {
        PrescriptionItem created = prescriptionItemService.createPrescriptionItem(prescriptionItem);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionItem> updatePrescriptionItem(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionItem prescriptionItem) {
        PrescriptionItem updated = prescriptionItemService.updatePrescriptionItem(id, prescriptionItem);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescriptionItem(@PathVariable Long id) {
        prescriptionItemService.deletePrescriptionItem(id);
        return ResponseEntity.ok().build();
    }
}
