package com.pkuk.scores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/klasaA")
public class WebsiteController {

    ScrapeData scrapeData = new ScrapeData();

    @GetMapping("lodz4")
    public ResponseEntity<List<WebsiteDto>> getWebsiteData() {
        return new ResponseEntity(scrapeData.retrieveData(), HttpStatus.OK);
    }

}
