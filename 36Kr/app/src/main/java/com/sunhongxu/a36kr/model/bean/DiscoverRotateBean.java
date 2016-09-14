package com.sunhongxu.a36kr.model.bean;

import java.util.List;

/**
 * Created by dllo on 16/9/14.
 * 发现界面轮播图实体类
 */
public class DiscoverRotateBean {

    /**
     * code : 0
     * data : {"pics":[{"action":"web","id":0,"imgUrl":"https://krplus-pic.b0.upaiyun.com/201605/16/8bc33bd479980a7e70d6791756932e5c.jpg","location":"http://huodong.36kr.com/h5/star/index-m.html?ktm_src=xsdappbanner","title":"创业星生代"},{"action":"web","id":0,"imgUrl":"https://krplus-pic.b0.upaiyun.com/201607/29/d2eb3d3b485932c5fb278d737e93a4a1.png","location":"http://36kr.com/p/5050033.html","title":"行家说"},{"action":"web","id":0,"imgUrl":"https://krplus-pic.b0.upaiyun.com/201607/29/d65bece22f0ac710ac8b363a4223a112.jpg","location":"http://h5.welian.com/event/i/MTU1NDQ=","title":"BAT千人峰会"},{"action":"web","id":0,"imgUrl":"https://krplus-pic.b0.upaiyun.com/201608/04/83bcf3ebdbd3b59025dc6b3a858456e5.jpg","location":"http://www.huodongxing.com/event/5346528719200","title":"华兴 36氪"}]}
     * msg : 操作成功！
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * action : web
         * id : 0
         * imgUrl : https://krplus-pic.b0.upaiyun.com/201605/16/8bc33bd479980a7e70d6791756932e5c.jpg
         * location : http://huodong.36kr.com/h5/star/index-m.html?ktm_src=xsdappbanner
         * title : 创业星生代
         */

        private List<PicsBean> pics;

        public List<PicsBean> getPics() {
            return pics;
        }

        public void setPics(List<PicsBean> pics) {
            this.pics = pics;
        }

        public static class PicsBean {
            private String action;
            private int id;
            private String imgUrl;
            private String location;
            private String title;

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
