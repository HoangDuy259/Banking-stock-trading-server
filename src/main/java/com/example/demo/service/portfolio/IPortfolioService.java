package com.example.demo.service.portfolio;

import com.example.demo.dto.request.portfolio.PortfolioCreateRequest;
import com.example.demo.dto.response.stock.PortfolioResponse;

public interface IPortfolioService {
    PortfolioResponse create(PortfolioCreateRequest request);
}
