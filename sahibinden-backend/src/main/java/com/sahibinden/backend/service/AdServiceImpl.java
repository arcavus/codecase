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
import java.util.UUID;

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
        if (AdCreateRequest.fieldControl(adCreateRequest))
            return null;
        if (isAliveLink(adCreateRequest))
            return null;
        if (titleAndDexcriptionBadWordsControl(adCreateRequest))
            return null;
        AdDocument adDocument = adMongoRepository.save(adDocumentCreator(adCreateRequest));
        return new AdResponse(adDocument.getId(), adCreateRequest.getTitle(), adCreateRequest.getDescription(), adCreateRequest.getLink());
    }

    @Override
    public AdStatistic getAdStatistic(String adId) {
        return mongoTemplate.findById(adId, AdStatistic.class);
    }

    @Override
    public DeliveryResult getWinner(MatchCriteria matchCriteria) {
        AdDocument adDocument = adMongoRepository.findByCategoryListEqualsAndClientTypeEqualsAndLocationsEquals(matchCriteria.getCategory(), matchCriteria.getClientType(), matchCriteria.getLocation());
        if (adDocument == null)
            return new DeliveryResult(null, null);
        boolean isAdDocumentSuit = (adDocument.getTotalBudget() < -2 * adDocument.getBidPrice()) || (adDocument.getFrequencyCapping() < -1l);
        if(isAdDocumentSuit)
            return new DeliveryResult(null, null);
        AdResponse adResponse = new AdResponse(adDocument.getId(), adDocument.getTitle(), adDocument.getDescription(), adDocument.getLink());
        UUID uuid = UUID.randomUUID();
        DeliveryResult deliveryResult = new DeliveryResult(adResponse, uuid.toString());
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add(uuid.toString(), deliveryResult)
                .get();
        mongoTemplate.save(objectToSave, "collection");
        return deliveryResult;
    }

    @Override
    public void processImpression(String deliveryId) {
        impressionOperation(deliveryId);
    }

    @Override
    public void processClick(String deliveryId) {
        impressionOperation(deliveryId);
    }


    @Override
    public void deleteAll() {
        adMongoRepository.deleteAll();
    }

    private AdDocument adDocumentCreator(AdCreateRequest adCreateRequest)
    {
        AdDocument adDocument = new AdDocument();
        adDocument.setFrequencyCapping(adCreateRequest.getFrequencyCapping());
        adDocument.setBidPrice(adCreateRequest.getBidPrice());
        adDocument.setTotalBudget(adCreateRequest.getTotalBudget());
        adDocument.setCategoryList(adCreateRequest.getCategoryList());
        adDocument.setClientType(adCreateRequest.getClientType());
        adDocument.setDescription(adCreateRequest.getDescription());
        adDocument.setLink(adCreateRequest.getLink());
        adDocument.setTitle(adCreateRequest.getTitle());
        adDocument.setLocations(adCreateRequest.getLocations());
        adDocument.setId(UUID.randomUUID().toString());
        return adDocument;
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
            if (adCreateRequest.getDescription().contains(line) || adCreateRequest.getTitle().contains(line)) {
                return true;
            }
        }
        return false;
    }

    private void impressionOperation(String deliveryId)
    {
        DeliveryResult deliveryResult = mongoTemplate.findById(deliveryId, DeliveryResult.class);
        AdDocument adDocument = adMongoRepository.findById(deliveryResult.getAdResponse().getId()).get();
        long frequencyCapping = adDocument.getFrequencyCapping() - 1l;
        adDocument.setFrequencyCapping(frequencyCapping);
        Long totalBudget = adDocument.getTotalBudget() - adDocument.getBidPrice();
        adDocument.setTotalBudget(totalBudget);
        adMongoRepository.save(adDocument);

        adStatisticControl(adDocument);
    }

    private void adStatisticControl(AdDocument adDocument)
    {
        AdStatistic adStatistic = mongoTemplate.findById(adDocument.getId(), AdStatistic.class);
        if (adStatistic != null)
        {
            adStatistic.setImpressionCount(adStatistic.getImpressionCount() + 1l);
            adStatistic.setClickCount(adStatistic.getClickCount() + 1l);
        }
        else
        {
            adStatistic.setAdId(adDocument.getId());
            adStatistic.setImpressionCount(1l);
            adStatistic.setClickCount(1l);
        }

        DBObject objectToSave = BasicDBObjectBuilder.start().add(adStatistic.getAdId(), adStatistic).get();
        mongoTemplate.save(objectToSave, "collection");
    }
}