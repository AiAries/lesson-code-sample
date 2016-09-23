package com.feicui.edu.highpart.bean;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class NewsGroup<T> {

    /**
     * data : [{"gid":1,
     * "subgrp":[{"subid":2,"subgroup":"社会"},
     * {"subid":1,"subgroup":"军事"}],"group":"新闻"},
     * {"gid":2,"subgrp":[{"subid":3,"subgroup":"股票"},
     * {"subid":4,"subgroup":"基金"}],"group":"财经"},
     * {"gid":3,"subgrp":[{"subid":6,"subgroup":"探索"},
     * {"subid":5,"subgroup":"手机"}],"group":"科技"},{"gid":4,"subgrp":[{"subid":8,"subgroup":"NBA"},{"subid":7,"subgroup":"英超"}],"group":"体育"}]
     * message : OK
     * status : 0
     */

    private String message;
    private int status;
    /**
     * gid : 1
     * subgrp : [{"subid":2,"subgroup":"社会"},{"subid":1,"subgroup":"军事"}]
     * group : 新闻
     */

    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public static class DataBean<V> {
        private int gid;
        private String group;
        /**
         * subid : 2
         * subgroup : 社会
         */

        private V subgrp;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public V getSubgrp() {
            return subgrp;
        }


        public static class SubgrpBean {
            private int subid;
            private String subgroup;

            public int getSubid() {
                return subid;
            }

            public void setSubid(int subid) {
                this.subid = subid;
            }

            public String getSubgroup() {
                return subgroup;
            }

            public void setSubgroup(String subgroup) {
                this.subgroup = subgroup;
            }
        }
    }
}
