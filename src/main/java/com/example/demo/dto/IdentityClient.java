package com.example.demo.dto;


import com.example.demo.dto.identity.ClientTokenExchangeParam;
import com.example.demo.dto.identity.LogoutRequest;
import com.example.demo.dto.identity.TokenExchangeResponse;
import com.example.demo.dto.identity.TokenExchangeParam;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(
            value = "/realms/SuperApp/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);

    // cấp token
    @PostMapping(
            value = "/realms/SuperApp/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeTokenClient(@QueryMap ClientTokenExchangeParam param);

    // 👇 thêm logout
    @PostMapping(
            value = "/realms/SuperApp/protocol/openid-connect/logout",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void logout(@QueryMap LogoutRequest param);
}
