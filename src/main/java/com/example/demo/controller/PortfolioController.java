package com.example.demo.controller;


import com.example.demo.dto.request.portfolio.PortfolioCreateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.stock.PortfolioResponse;
import com.example.demo.service.portfolio.IPortfolioService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/portfolios")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PortfolioController {
    IPortfolioService iPortfolioService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PortfolioResponse>> createPortfolio(@RequestBody @Valid PortfolioCreateRequest request) {
        try {
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<>("Thành công", iPortfolioService.create(request)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
