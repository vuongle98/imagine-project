package com.vuongle.imaginepg.domain.services;

import java.time.Instant;
import java.util.UUID;

public interface ShortenUrlService {

    String shortUrl(UUID userId, String originalUrl, String customAlias, Instant expireDate);

    void deleteUrl(UUID userId, String urlKey);
}
