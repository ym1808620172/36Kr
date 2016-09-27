package com.sunhongxu.a36kr.model.bean;

import java.util.List;

/**
 * Created by dllo on 16/9/20.
 * 近期活动的实体类
 */
public class RecentAtyBean {



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
        private int page;
        private int totalCount;
        private int pageSize;


        private List<DataBeans> data;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<DataBeans> getData() {
            return data;
        }

        public void setData(List<DataBeans> data) {
            this.data = data;
        }

        public static class DataBeans {
            private String activityCity;
            private String activityDesc;
            private String activityImg;
            private String activityLink;
            private String activityName;
            private String activityStatus;
            private String activityTime;

            public String getActivityCity() {
                return activityCity;
            }

            public void setActivityCity(String activityCity) {
                this.activityCity = activityCity;
            }

            public String getActivityDesc() {
                return activityDesc;
            }

            public void setActivityDesc(String activityDesc) {
                this.activityDesc = activityDesc;
            }

            public String getActivityImg() {
                return activityImg;
            }

            public void setActivityImg(String activityImg) {
                this.activityImg = activityImg;
            }

            public String getActivityLink() {
                return activityLink;
            }

            public void setActivityLink(String activityLink) {
                this.activityLink = activityLink;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public String getActivityStatus() {
                return activityStatus;
            }

            public void setActivityStatus(String activityStatus) {
                this.activityStatus = activityStatus;
            }

            public String getActivityTime() {
                return activityTime;
            }

            public void setActivityTime(String activityTime) {
                this.activityTime = activityTime;
            }
        }
    }
}
