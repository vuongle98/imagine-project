package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.domain.entities.ShortenUrl;
import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.services.ShortenUrlService;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.StringUtils;

import java.time.Instant;
import java.util.UUID;

public class ShortenUrlServiceImpl implements ShortenUrlService {


  private final BaseRepository<ShortenUrl> shortenUrlRepository;

  public ShortenUrlServiceImpl(BaseRepository<ShortenUrl> shortenUrlRepository) {
    this.shortenUrlRepository = shortenUrlRepository;
  }

  @Override
  public String shortUrl(UUID userId, String originalUrl, String customAlias, Instant expireDate) {

    if (!validateUrl(originalUrl)) {
      throw new RuntimeException("URL not valid.");
    }

    // encode url
    String encoded = encodeUrl(originalUrl);

    // validate in DB

    // save to db
    User user = Context.getUser();
    ShortenUrl shortenUrl = new ShortenUrl();
    shortenUrl.setOriginalUrl(originalUrl);
    shortenUrl.setHashed(encoded);
    shortenUrl.setCreatedDate(Instant.now());
    shortenUrl.setExpiredDate(expireDate);
    shortenUrl.setUser(user);

    shortenUrlRepository.save(shortenUrl);

    return encoded;
  }

  @Override
  public void deleteUrl(UUID userId, String urlKey) {

  }

  private String encodeUrl(String url) {
    String md5Encoded = StringUtils.toMD5(url);
    return StringUtils.toBase64(md5Encoded).substring(0, 8);
  }

  private boolean validateUrl(String url) {
    return true;
  }

  private String sanitizeURL(String url) {
    if (url.startsWith("http://"))
			url = url.substring(7);

		if (url.startsWith("https://"))
			url = url.substring(8);

    if (url.charAt(url.length() - 1) == '/')
			url = url.substring(0, url.length() - 1);

    return url;
  }
}
