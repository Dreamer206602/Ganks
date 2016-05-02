package com.mx.ganks.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by boobooL on 2016/4/29 0029
 * Created 邮箱 ：boobooMX@163.com
 */
public class CommonDate implements Serializable{

    public static final long serialVersionUID=-3717349166478282625L;
    private boolean error;
    private List<ResultsEntity>mResults;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsEntity> getResults() {
        return mResults;
    }

    public void setResults(List<ResultsEntity> results) {
        mResults = results;
    }

    public static class ResultsEntity implements Serializable{
        private static final long serialVersionUID = -5285218081391954727L;
        private String _id;
        private String  _ns;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String get_ns() {
            return _ns;
        }

        public void set_ns(String _ns) {
            this._ns = _ns;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        @Override
        public String toString() {
            return "ResultsEntity{" +
                    "_id='" + _id + '\'' +
                    ", _ns='" + _ns + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return  "PublicData{"+"error="+error+",results="+mResults+'}';
    }
}
