package com.base.service;

import io.jsonwebtoken.Claims;

public interface AuthenticationFacade {
    Claims getUserClaims();
}
