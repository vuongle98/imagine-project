package com.vuongle.imaginepg.domain.services.impl;

import com.vuongle.imaginepg.application.commands.CreatePostCommand;
import com.vuongle.imaginepg.application.dto.FileDto;
import com.vuongle.imaginepg.domain.services.CrawlService;
import com.vuongle.imaginepg.domain.services.FileService;
import com.vuongle.imaginepg.domain.services.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class CrawlServiceImpl implements CrawlService {

    private final PostService postService;
    private final FileService fileService;

    public CrawlServiceImpl(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @Override
    public void process(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        Elements news = document.getElementsByClass("item-news");

        for (Element item : news) {

            CreatePostCommand command = new CreatePostCommand();

            // title
            Elements titles = item.select("h3");

            if (Objects.nonNull(titles.first())) {
                Element title = Objects.requireNonNull(titles.first()).getElementsByTag("a").first();

                if (Objects.nonNull(title)) {
                    System.out.println(title.text());
                    System.out.println(title.absUrl("href"));

                    crawlDetail(title.absUrl("href"), command);

                    command.setTitle(title.text());
                }
            }

            // description
            Elements descriptions = item.select("p");
            if (Objects.nonNull(descriptions.first())) {
                Element description = Objects.requireNonNull(descriptions.first()).getElementsByTag("a").first();
                if (Objects.nonNull(description)) {
                    System.out.println(description.text());
                    command.setDescription(description.text());
                }
            }

            if (Objects.nonNull(titles.first()) || Objects.nonNull(descriptions.first())) {
                command.setCategoryId(UUID.fromString("35cd5b6d-616c-4a25-8392-38cdaa7984cf"));

                postService.create(command);
            }
        }


    }

    public void crawlDetail(String url, CreatePostCommand command) throws IOException {
        Document document = Jsoup.connect(url).get();

        Element news = document.getElementsByClass("fck_detail").first();

        if (Objects.nonNull(news)) {
            Element img = news.getElementsByTag("img").first();

            if (Objects.nonNull(img)) {
                System.out.println(img.attr("src"));
                System.out.println(img.attr("data-src"));

                String fileUrl = img.attr("data-src");
                System.out.println(fileUrl);

                FileDto fileInfo = fileService.createFileInfo(fileUrl);

                if (Objects.nonNull(fileInfo)) command.setFileId(fileInfo.getId());
            }

            command.setContent(news.children().toString());
        }
    }

}
