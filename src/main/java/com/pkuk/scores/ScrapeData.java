package com.pkuk.scores;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ScrapeData {

    public List<WebsiteDto> retrieveData() {

        ArrayList<WebsiteDto> dataList = new ArrayList<>();

        WebsiteDto websiteDto = null;
        try {
            Document webPage = Jsoup.connect("http://www.90minut.pl/liga/1/liga12602.html").get();

            String firstTableOnSite = webPage
                    .getElementsByTag("tbody").get(0)
                    .getElementsByAttributeValue("align", "left").text();

            websiteDto = new WebsiteDto(firstTableOnSite);

            dataList.add(websiteDto);
        } catch (IOException ioException) {
            log.error(String.valueOf(ioException));
        }

        log.info(String.valueOf(websiteDto));

        return dataList;
    }
}
