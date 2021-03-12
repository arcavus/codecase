package com.sahibinden.common.dto.ad;

import java.util.List;

public class AdCreateRequest {

   private ClientType clientType;

   private List<Category> categoryList;

   private Long bidPrice;

   private Long totalBudget;

   private Long frequencyCapping;

   private List<Long> locations;

   private String title;

   private String description;

   private String link;


   public ClientType getClientType() {
      return clientType;
   }

   public void setClientType(ClientType clientType) {
      this.clientType = clientType;
   }

   public List<Category> getCategoryList() {
      return categoryList;
   }

   public void setCategoryList(List<Category> categoryList) {
      this.categoryList = categoryList;
   }

   public Long getBidPrice() {
      return bidPrice;
   }

   public void setBidPrice(Long bidPrice) {
      this.bidPrice = bidPrice;
   }

   public Long getTotalBudget() {
      return totalBudget;
   }

   public void setTotalBudget(Long totalBudget) {
      this.totalBudget = totalBudget;
   }

   public Long getFrequencyCapping() {
      return frequencyCapping;
   }

   public void setFrequencyCapping(Long frequencyCapping) {
      this.frequencyCapping = frequencyCapping;
   }

   public List<Long> getLocations() {
      return locations;
   }

   public void setLocations(List<Long> locations) {
      this.locations = locations;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }


   @Override
   public String toString() {
      return "AdCreateRequest{" +
              "clientType=" + clientType +
              ", categoryList=" + categoryList +
              ", bidPrice=" + bidPrice +
              ", totalBudget=" + totalBudget +
              ", frequencyCapping=" + frequencyCapping +
              ", locations=" + locations +
              ", title='" + title + '\'' +
              ", description='" + description + '\'' +
              ", link='" + link + '\'' +
              '}';
   }

   public static boolean fieldControl(AdCreateRequest adCreateRequest) {
      if (adCreateRequest.getTitle().length() > 30 || adCreateRequest.getTitle().length() < 10)
         return true;
      if (adCreateRequest.getDescription().length() < 30 || adCreateRequest.getDescription().length() > 100)
         return true;
      if (adCreateRequest.getBidPrice() < 50L || adCreateRequest.getBidPrice() > 300L)
         return true;
      if (adCreateRequest.getFrequencyCapping() < 6L || adCreateRequest.getFrequencyCapping() > 24L)
         return true;
      if (adCreateRequest.getLocations().stream().anyMatch(a -> a < 1 || a > 81))
         return true;
      if (adCreateRequest.getClientType()==null)
         return true;
      if (adCreateRequest.getCategoryList().size()<1)
         return true;
      if (adCreateRequest.getTotalBudget() < adCreateRequest.getBidPrice()*10)
         return true;
      return false;
   }

}
