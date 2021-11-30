package com.my.clickapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyUserBookingModel {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("provider_id")
        @Expose
        private String providerId;
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("date_time")
        @Expose
        private String dateTime;
        @SerializedName("services_details")
        @Expose
        private List<String> servicesDetails = null;
        @SerializedName("shop_details")
        @Expose
        private ShopDetails shopDetails;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public List<String> getServicesDetails() {
            return servicesDetails;
        }

        public void setServicesDetails(List<String> servicesDetails) {
            this.servicesDetails = servicesDetails;
        }

        public ShopDetails getShopDetails() {
            return shopDetails;
        }

        public void setShopDetails(ShopDetails shopDetails) {
            this.shopDetails = shopDetails;
        }

        public class ShopDetails {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("provider_id")
            @Expose
            private String providerId;
            @SerializedName("shop_name")
            @Expose
            private String shopName;
            @SerializedName("shop_address")
            @Expose
            private String shopAddress;
            @SerializedName("shop_lat")
            @Expose
            private String shopLat;
            @SerializedName("shop_lon")
            @Expose
            private String shopLon;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("shop_image")
            @Expose
            private String shopImage;
            @SerializedName("video")
            @Expose
            private String video;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("date_time")
            @Expose
            private String dateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProviderId() {
                return providerId;
            }

            public void setProviderId(String providerId) {
                this.providerId = providerId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopAddress() {
                return shopAddress;
            }

            public void setShopAddress(String shopAddress) {
                this.shopAddress = shopAddress;
            }

            public String getShopLat() {
                return shopLat;
            }

            public void setShopLat(String shopLat) {
                this.shopLat = shopLat;
            }

            public String getShopLon() {
                return shopLon;
            }

            public void setShopLon(String shopLon) {
                this.shopLon = shopLon;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getShopImage() {
                return shopImage;
            }

            public void setShopImage(String shopImage) {
                this.shopImage = shopImage;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

        }

    }
}



