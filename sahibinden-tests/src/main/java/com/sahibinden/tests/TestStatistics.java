package com.sahibinden.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sahibinden.common.dto.ad.AdCreateRequest;
import com.sahibinden.common.dto.ad.AdResponse;
import com.sahibinden.common.dto.ad.AdStatistic;
import com.sahibinden.common.dto.ad.DeliveryResult;
import com.sahibinden.common.dto.ad.MatchCriteria;
import com.sahibinden.common.service.AdService;
import com.sahibinden.service.AdServiceImpl;
import com.sahibinden.util.AdUtil;
import com.sahibinden.util.AdWithActualStat;


public class TestStatistics {

   private AdService adService;


   @Before
   public void init() {
      adService = new AdServiceImpl("http://localhost:9494","http://localhost:9393");
      adService.deleteAll();
   }


   @Test
   public void testUseSameDeliveryTwice() throws IOException {
      ConcurrentLinkedQueue<String> visitors = AdUtil.generateRandomVisitors(1);
      AdCreateRequest adCreateRequest = AdUtil.getRandomAdCreateRequest();
      AdResponse adResponse = adService.createAd(adCreateRequest);
      AdWithActualStat ad = new AdWithActualStat(adResponse, adCreateRequest, adService);
      MatchCriteria matchCriteria = ad.getMatchableCriteria(visitors);
      DeliveryResult deliveryResult = adService.getWinner(matchCriteria);

      adService.processClick(deliveryResult.getDeliveryId());
      try {
         Thread.sleep(1100);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      adService.processClick(deliveryResult.getDeliveryId());


      AdStatistic adStatistic = adService.getAdStatistic(ad.getId());

      Assert.assertEquals("You can not use same delivery id twice", 1, adStatistic.getClickCount().intValue());

   }


   @Test
   public void testClickAndImpressionStatistics() throws InterruptedException, IOException {
      ConcurrentLinkedQueue<String> visitors = AdUtil.generateRandomVisitors(500);
      List<AdWithActualStat> adWithActualStats = AdUtil.generateRandomAds(30, adService);

      List<Thread> threads = new ArrayList<>();
      IntStream.range(0, 40).forEach(i ->
      {
         AdRunner adRunner = new AdRunner(visitors, adWithActualStats);
         threads.add(new Thread(adRunner));
      });

      threads.forEach(Thread::start);


      for (Thread thread : threads) {
         thread.join();
      }

      adWithActualStats.forEach(x ->
      {
         AdStatistic adStatistic = adService.getAdStatistic(x.getId());
         Assert.assertFalse(
                 String.format("Ad with id : %s exceeded its budget limit", x.getId()),
                 x.isBudgetExceeded(3));
         Assert.assertFalse(
                 String.format("Ad with id : %s exceeded its frequency capping limit ", x.getId()),
                 x.isFrequencyCappingExceeded(3));
         Assert.assertEquals(
                 String.format("Adstatistics for ad with id : %s shows %s click but actual is %s ", x.getId(), adStatistic.getClickCount(), x.getClickCount())
                 , adStatistic.getClickCount().intValue(), x.getClickCount());
         Assert.assertEquals(
                 String.format("Adstatistics for ad with id : %s shows %s impression but actual is %s ", x.getId(), adStatistic.getImpressionCount(), x.getImpressionCount())
                 , adStatistic.getImpressionCount().intValue(), x.getImpressionCount());
      });

   }


   class AdRunner implements Runnable {
      private ConcurrentLinkedQueue<String> visitors;
      private List<AdWithActualStat> adWithActualStats;

      public AdRunner(ConcurrentLinkedQueue<String> visitors, List<AdWithActualStat> adWithActualStats) {
         this.visitors = visitors;
         this.adWithActualStats = adWithActualStats;
      }

      @Override
      public void run() {
         long startTime = System.currentTimeMillis();
         while (System.currentTimeMillis() - 15000 < startTime) {
            AdWithActualStat adWithActualStat = AdUtil.getRandomAd(adWithActualStats);
            if (adWithActualStat == null) {
               break;
            }
            MatchCriteria matchCriteria = adWithActualStat.getMatchableCriteria(visitors);
            try {
               DeliveryResult deliveryResult = adService.getWinner(matchCriteria);
               if (deliveryResult.getDeliveryId() != null) {
                  String adId = deliveryResult.getAdResponse().getId();
                  Predicate<AdWithActualStat> filter = x -> x.getId().equals(adId);

                  Optional<AdWithActualStat> ad = adWithActualStats.stream().filter(filter).findAny();

                  Assert.assertTrue(ad.isPresent());

                  AdWithActualStat x = ad.get();

                  if (AdUtil.withPercentage(70)) {
                     x.processImpression(deliveryResult.getDeliveryId(), matchCriteria.getVisitorId());
                  } else if (AdUtil.withPercentage(90)) {
                     x.processClick(deliveryResult.getDeliveryId(), matchCriteria.getVisitorId());
                  }
               }
            } finally {
               visitors.add(matchCriteria.getVisitorId());
            }


         }

      }
   }
}
