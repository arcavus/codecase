package com.sahibinden.backend.service;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sahibinden.backend.model.AdDocument;
import com.sahibinden.backend.repo.mongo.AdMongoRepository;
import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.dto.ad.AdStatistic;
import com.sahibinden.common.dto.ad.DeliveryResult;
import com.sahibinden.common.dto.ad.MatchCriteria;
import com.sahibinden.common.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Service
public class AdServiceImpl implements AdService {
    private AdMongoRepository adMongoRepository;
    private ResourceLoader resourceLoader;
    private MongoTemplate mongoTemplate;

    @Autowired
    public AdServiceImpl(AdMongoRepository adMongoRepository, ResourceLoader resourceLoader, MongoTemplate mongoTemplate) {
        this.adMongoRepository = adMongoRepository;
        this.resourceLoader = resourceLoader;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public AdResponse createAd(AdCreateRequest adCreateRequest) throws IOException {
        if (AdCreateRequest.fieldControl(adCreateRequest)) return null;
        if (isAliveLink(adCreateRequest)) return null;
        if (titleAndDexcriptionBadWordsControl(adCreateRequest)) return null;
        AdDocument adDocument = adMongoRepository.save(new AdDocument());
        return new AdResponse(adDocument.getId(), adCreateRequest.getTitle(), adCreateRequest.getDescription(), adCreateRequest.getLink());
    }

    @Override
    public AdStatistic getAdStatistic(String adId) {
        return null;
    }


    @Override
    public DeliveryResult getWinner(MatchCriteria matchCriteria) {
        AdDocument adDocument = adMongoRepository.findByCategoryListEqualsAndClientTypeEqualsAndLocationsEquals(matchCriteria.getCategory(), matchCriteria.getClientType(), matchCriteria.getLocation());
        if (adDocument == null)
            return null;
        AdResponse adResponse = new AdResponse(adDocument.getId(), adDocument.getTitle(), adDocument.getDescription(), adDocument.getLink());
        Long time = new Date().getTime();
        DeliveryResult deliveryResult = new DeliveryResult(adResponse, time.toString());
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add(time.toString(), deliveryResult)
                .get();
        mongoTemplate.save(objectToSave, "collection");
        return deliveryResult;
    }

    @Override
    public void processImpression(String deliveryId) {
    }

    @Override
    public void processClick(String deliveryId) {

    }


    @Override
    public void deleteAll() {
        adMongoRepository.deleteAll();
    }

    private boolean isAliveLink(AdCreateRequest adCreateRequest) throws IOException {
        URL url = new URL(adCreateRequest.getLink());
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        int responseCode = huc.getResponseCode();
        if (HttpURLConnection.HTTP_OK != responseCode) {
            return true;
        }
        return false;
    }

    private boolean titleAndDexcriptionBadWordsControl(AdCreateRequest adCreateRequest) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/badWords.txt");
        Scanner scanner = new Scanner(resource.getInputStream());
        int lineNum = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineNum++;
            if (line.equals(adCreateRequest.getDescription()) || line.equals(adCreateRequest.getTitle())) {
                return true;
            }
        }
        return false;
    }
}
