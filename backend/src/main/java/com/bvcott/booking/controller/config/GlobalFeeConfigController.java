package com.bvcott.booking.controller.config;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bvcott.booking.dto.config.GlobalFeeConfigDTO;
import com.bvcott.booking.service.config.GlobalFeeConfigService;

import lombok.AllArgsConstructor;

@RestController @RequestMapping("api/v1/config/fee")
@AllArgsConstructor
public class GlobalFeeConfigController {
    private final GlobalFeeConfigService feeService;

    @GetMapping
    public ResponseEntity<GlobalFeeConfigDTO> getCurrentFees(@RequestParam UUID adminId) {
        return ResponseEntity.ok(feeService.getCurrentFees(adminId));
    }

    @PutMapping
    public ResponseEntity<GlobalFeeConfigDTO> updateFees(
        @RequestParam UUID adminId,
        @RequestBody GlobalFeeConfigDTO dto) {
            return ResponseEntity.ok(feeService.updateFees(adminId, dto));
        }
}
