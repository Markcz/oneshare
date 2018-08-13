package com.xinxiu.oneshare.model;

/**
 * Created by chenzhen on 2018/1/8.
 */

public class VideoModel {


        private int id;
        private String img_name;
        private String img_data;
        private long img_size;
        private long img_date_modify;
        private long duration;

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg_name() {
            return img_name;
        }

        public void setImg_name(String img_name) {
            this.img_name = img_name;
        }

        public String getImg_data() {
            return img_data;
        }

        public void setImg_data(String img_data) {
            this.img_data = img_data;
        }

        public long getImg_size() {
            return img_size;
        }

        public void setImg_size(long img_size) {
            this.img_size = img_size;
        }

        public long getImg_date_modify() {
            return img_date_modify;
        }

        public void setImg_modify(Long img_date_modify) {
            this.img_date_modify = img_date_modify;
        }
    }

