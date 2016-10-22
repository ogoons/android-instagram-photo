package com.zzixx.instagram.model;

/**
 * Created by ogoons on 2016-09-22.
 */
public class InstagramMedia {
    public Meta         meta;
    public String       type;
    public Data[]       data;
    public Pagination   pagination;

    public class Meta {
        public String code;
    }
    public class Pagination {
        public String next_url;
        public String next_max_id;
    }
    public class Data {
        public Images images;
        public class Images {
            public StandardResolution standard_resolution;
            public class StandardResolution {
                public String url;
                public String width;
                public String height;
            }
        }
    }
}
