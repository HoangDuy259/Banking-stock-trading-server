package com.example.demo.controller;


import com.example.demo.dto.request.stock.StockRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.stock.StockResponse;
import com.example.demo.entity.stock.Stock;
import com.example.demo.service.stock.IStockService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockController {
    IStockService stockService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<StockResponse>> addStock(@RequestBody StockRequest request){
        try{
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<>("Thành công", stockService.create(request)));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }

    };

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<StockResponse>>> getStock(){
        try{
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<>("Thành công", stockService.getAllStock()));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }

    };
}
