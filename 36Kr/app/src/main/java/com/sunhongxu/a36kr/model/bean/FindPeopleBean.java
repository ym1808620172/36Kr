package com.sunhongxu.a36kr.model.bean;

import java.util.List;

/**
 * Created by dllo on 16/9/18.
 * 寻找投资人的实体类
 */
public class FindPeopleBean {




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
        private int totalPages;


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

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<DataBeans> getDatas() {
            return data;
        }

        public void setDatas(List<DataBeans> data) {
            this.data = data;
        }

        public static class DataBeans {
            /**
             * id : 252012
             * name : 吕薇
             * avatar : https://krid-assets.b0.upaiyun.com/uploads/user/avatar/405882/a72c9c69-0add-4a61-89f5-1cdaa3e0cf79.jpg!480
             */

            private UserBean user;
            private List<String> investPhases;
            private List<String> focusIndustry;

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public List<String> getInvestPhases() {
                return investPhases;
            }

            public void setInvestPhases(List<String> investPhases) {
                this.investPhases = investPhases;
            }

            public List<String> getFocusIndustry() {
                return focusIndustry;
            }

            public void setFocusIndustry(List<String> focusIndustry) {
                this.focusIndustry = focusIndustry;
            }

            public static class UserBean {
                private int id;
                private String name;
                private String avatar;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }
            }
        }
    }
}
