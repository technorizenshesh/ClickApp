package com.my.clickapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class serviceDetailsModel
{
    @SerializedName("result")
    @Expose
    public Result result;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public String status;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
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
        public String id;
        @SerializedName("provider_id")
        @Expose
        public String providerId;
        @SerializedName("shop_name")
        @Expose
        public String shopName;
        @SerializedName("shop_address")
        @Expose
        public String shopAddress;
        @SerializedName("shop_lat")
        @Expose
        public String shopLat;
        @SerializedName("shop_lon")
        @Expose
        public String shopLon;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("shop_image")
        @Expose
        public String shopImage;
        @SerializedName("video")
        @Expose
        public String video;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("date_time")
        @Expose
        public String dateTime;
        @SerializedName("avg_rating")
        @Expose
        public String avgRating;
        @SerializedName("total_review")
        @Expose
        public String totalReview;
        @SerializedName("service_details")
        @Expose
        public List<ServiceDetail> serviceDetails = null;
        @SerializedName("shop_video")
        @Expose
        public List<ShopVideo> shopVideo = null;

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

        public String getAvgRating() {
            return avgRating;
        }

        public void setAvgRating(String avgRating) {
            this.avgRating = avgRating;
        }

        public String getTotalReview() {
            return totalReview;
        }

        public void setTotalReview(String totalReview) {
            this.totalReview = totalReview;
        }

        public List<ServiceDetail> getServiceDetails() {
            return serviceDetails;
        }

        public void setServiceDetails(List<ServiceDetail> serviceDetails) {
            this.serviceDetails = serviceDetails;
        }

        public List<ShopVideo> getShopVideo() {
            return shopVideo;
        }

        public void setShopVideo(List<ShopVideo> shopVideo) {
            this.shopVideo = shopVideo;
        }

        public class ServiceDetail {

            @SerializedName("id")
            @Expose
            public String id;
            @SerializedName("provider_id")
            @Expose
            public String providerId;
            @SerializedName("service_name")
            @Expose
            public String serviceName;
            @SerializedName("description")
            @Expose
            public String description;
            @SerializedName("sevice_type")
            @Expose
            public String seviceType;
            @SerializedName("price")
            @Expose
            public String price;
            @SerializedName("status")
            @Expose
            public String status;
            @SerializedName("date_time")
            @Expose
            public String dateTime;
            @SerializedName("servicename")
            @Expose
            public String servicename;
            @SerializedName("serviceimage")
            @Expose
            public String serviceimage;

            boolean Selected=false;

            public boolean isSelected() {
                return Selected;
            }

            public void setSelected(boolean selected) {
                Selected = selected;
            }

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

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getSeviceType() {
                return seviceType;
            }

            public void setSeviceType(String seviceType) {
                this.seviceType = seviceType;
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

            public String getServicename() {
                return servicename;
            }

            public void setServicename(String servicename) {
                this.servicename = servicename;
            }

            public String getServiceimage() {
                return serviceimage;
            }

            public void setServiceimage(String serviceimage) {
                this.serviceimage = serviceimage;
            }

        }

        public class ShopVideo {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("provider_id")
            @Expose
            private String providerId;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("video")
            @Expose
            private String video;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("duration")
            @Expose
            private String duration;
            @SerializedName("total_view")
            @Expose
            private String totalView;
            @SerializedName("date_time")
            @Expose
            private String dateTime;
            @SerializedName("liked")
            @Expose
            private String liked;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getTotalView() {
                return totalView;
            }

            public void setTotalView(String totalView) {
                this.totalView = totalView;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getLiked() {
                return liked;
            }

            public void setLiked(String liked) {
                this.liked = liked;
            }
        }

    }
}

