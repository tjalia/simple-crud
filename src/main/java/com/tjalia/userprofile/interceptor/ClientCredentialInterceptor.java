package com.tjalia.userprofile.interceptor;

import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.property.HeadersProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ClientCredentialInterceptor implements HandlerInterceptor {
    private final HeadersProperty headersProperty;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String headerClientId = request.getHeader("x-client-id");
        String headerClientSecret = request.getHeader("x-client-secret");

        if (headerClientId == null || headerClientId.isEmpty()){
            throw new ApiException("Client ID is required.");
        }

        if (headerClientSecret == null || headerClientSecret.isEmpty()) {
            throw new ApiException("Client Secret is required");
        }

        HeadersProperty.HeadersClientProperty headersClientProperty = headersProperty.getClient();

        if (!headerClientId.equals(headersClientProperty.getId())){
            throw new ApiException("Incorrect Client ID");
        }

        if (!headerClientSecret.equals(headersClientProperty.getSecret())) {
            throw new ApiException("Incorrect Client Secret.");
        }

        return true;
    }

}
