package com.sahibinden.common.service;

import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.dto.ad.AdStatistic;
import com.sahibinden.common.dto.ad.DeliveryResult;
import com.sahibinden.common.dto.ad.MatchCriteria;

import java.io.IOException;
import java.net.MalformedURLException;

public interface AdService {


   AdResponse createAd(AdCreateRequest adCreateRequest) throws IOException;

   AdStatistic getAdStatistic(String adId);

   DeliveryResult getWinner(MatchCriteria matchCriteria);

   void processImpression(String deliveryId);

   void processClick(String deliveryId);

   void deleteAll();

}
