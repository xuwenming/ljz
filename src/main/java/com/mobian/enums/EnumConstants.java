package com.mobian.enums;

/**
 * Created by wenming on 2016/8/19.
 */
public class EnumConstants {

    public static enum WEEK {
        WEEK_1(1, "周一"),
        WEEK_2(2, "周二"),
        WEEK_3(3, "周三"),
        WEEK_4(4, "周四"),
        WEEK_5(5, "周五"),
        WEEK_6(6, "周六"),
        WEEK_7(7, "周日");

        private int num;
        private String cnName;

        WEEK(int num, String cnName) {
            this.num = num;
            this.cnName = cnName;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public static String getCnName(int num) {
            for(WEEK item : WEEK.values()){
                if(item.getNum() == num) {
                    return item.getCnName();
                }
            }
            return num + "";
        }
    }

    public static enum TIME {
        AM(1, "上午"),
        PM(2, "下午"),
        NIGHT(3, "夜班");

        private int num;
        private String cnName;

        TIME(int num, String cnName) {
            this.num = num;
            this.cnName = cnName;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public static String getCnName(int num) {
            for(TIME item : TIME.values()){
                if(item.getNum() == num) {
                    return item.getCnName();
                }
            }
            return num + "";
        }
    }

    public static enum VISIT_TYPE {
        SC(1, "专家门诊"),
        VC(2, "特需门诊");

        private int num;
        private String cnName;

        VISIT_TYPE(int num, String cnName) {
            this.num = num;
            this.cnName = cnName;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public static String getCnName(int num) {
            for(VISIT_TYPE item : VISIT_TYPE.values()){
                if(item.getNum() == num) {
                    return item.getCnName();
                }
            }
            return num + "";
        }
    }

}
