package com.vuongle.imaginepg.domain.services;

import java.io.IOException;

public interface CrawlService {

    void process(String url) throws IOException;
}
