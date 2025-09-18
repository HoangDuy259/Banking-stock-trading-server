package com.example.demo.service.portfolio;


import com.example.demo.dto.request.portfolio.PortfolioCreateRequest;
import com.example.demo.dto.response.stock.PortfolioResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.portfolio.Portfolio;
import com.example.demo.mapper.PortfolioMapper;
import com.example.demo.repository.PortfolioRespository;
import com.example.demo.utils.UserContextUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PortfolioService implements IPortfolioService {
    PortfolioRespository portfolioRespository;
    UserContextUtils userContextUtils;
    PortfolioMapper portfolioMapper;


    @Override
    public PortfolioResponse create(PortfolioCreateRequest request) {
        if(portfolioRespository.findByName(request.getPortfolioName()) != null){
            throw new RuntimeException("Portfolio already exists");
        }
        User user = userContextUtils.getAuthenticationUsername();
        Portfolio portfolio = new Portfolio();
        portfolio.setName(request.getPortfolioName());
        portfolio.setUser(user);
        return portfolioMapper.toPortfolioResponse(portfolioRespository.save(portfolio));
    }
}
