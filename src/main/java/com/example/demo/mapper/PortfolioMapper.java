package com.example.demo.mapper;


import com.example.demo.dto.response.stock.PortfolioResponse;
import com.example.demo.entity.portfolio.Portfolio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
    PortfolioResponse toPortfolioResponse(Portfolio portfolio);
}
