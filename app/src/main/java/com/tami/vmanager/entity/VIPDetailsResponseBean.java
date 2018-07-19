package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/6
 * VIP 详情 服务器返回实体类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VIPDetailsResponseBean extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 3733601646258769240L;
    /**
     * data : {"curPage":1,"elements":[{"createOn":1528709829000,"id":2,"intro":"马云，男，1964年9月10日生于浙江省杭州市，祖籍浙江省嵊州市（原嵊县）谷来镇， 阿里巴巴集团主要创始人，现担任阿里巴巴集团董事局主席、日本软银董事、大自然保护协会中国理事会主席兼全球董事会成员、华谊兄弟董事、生命科学突破奖基金会董事。 [1]  1988年毕业于杭州师范学院外语系，同年担任杭州电子工业学院英文及国际贸易教师，1995年创办中国第一家互联网商业信息发布网站\u201c中国黄页\u201d，1998年出任中国国际电子商务中心国富通信息技术发展有限公司总经理，1999年创办阿里巴巴，并担任阿里集团CEO、董事局主席。","meetingId":1,"name":"马云","status":0,"systemId":4,"updateOn":1528709829000},{"createOn":1528709754000,"id":1,"intro":"李彦宏，百度公司创始人、董事长兼首席执行官，全面负责百度公司的战略规划和运营管理。 1991年，李彦宏毕业于北京大学信息管理专业，随后前往美国布法罗纽约州立大学完成计算机科学硕士学位，先后担任道·琼斯公司高级顾问、《华尔街日报》网络版实时金融信息系统设计者，以及国际知名互联网企业\u2014\u2014Infoseek公司资深工程师。李彦宏所持有的\u201c超链分析\u201d技术专利，是奠定整个现代搜索引擎发展趋势和方向的基础发明之一。 2000年1月，李彦宏创建了百度。经过十多年的发展，百度已经发展成为全球第二大独立搜索引擎和最大的中文搜索引擎。百度的成功，也使中国成为美国、俄罗斯和韩国之外，全球仅有的4个拥有搜索引擎核心技术的国家之一。2005年，百度在美国纳斯达克成功上市，并成为首家进入纳斯达克成分股的中国公司。百度已经成为中国最具价值的品牌之一。","meetingId":1,"name":"李彦宏","status":0,"systemId":4,"updateOn":1528709754000}],"firstPage":true,"lastPage":true,"lastPageNumber":1,"nextPage":2,"pageSize":10,"previousPage":0,"thisPageFirstElementNumber":1,"thisPageLastElementNumber":2,"totalElements":2}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DataBean implements Serializable{
        private static final long serialVersionUID = -6835812355842007277L;
        /**
         * curPage : 1
         * elements : [{"createOn":1528709829000,"id":2,"intro":"马云，男，1964年9月10日生于浙江省杭州市，祖籍浙江省嵊州市（原嵊县）谷来镇， 阿里巴巴集团主要创始人，现担任阿里巴巴集团董事局主席、日本软银董事、大自然保护协会中国理事会主席兼全球董事会成员、华谊兄弟董事、生命科学突破奖基金会董事。 [1]  1988年毕业于杭州师范学院外语系，同年担任杭州电子工业学院英文及国际贸易教师，1995年创办中国第一家互联网商业信息发布网站\u201c中国黄页\u201d，1998年出任中国国际电子商务中心国富通信息技术发展有限公司总经理，1999年创办阿里巴巴，并担任阿里集团CEO、董事局主席。","meetingId":1,"name":"马云","status":0,"systemId":4,"updateOn":1528709829000},{"createOn":1528709754000,"id":1,"intro":"李彦宏，百度公司创始人、董事长兼首席执行官，全面负责百度公司的战略规划和运营管理。 1991年，李彦宏毕业于北京大学信息管理专业，随后前往美国布法罗纽约州立大学完成计算机科学硕士学位，先后担任道·琼斯公司高级顾问、《华尔街日报》网络版实时金融信息系统设计者，以及国际知名互联网企业\u2014\u2014Infoseek公司资深工程师。李彦宏所持有的\u201c超链分析\u201d技术专利，是奠定整个现代搜索引擎发展趋势和方向的基础发明之一。 2000年1月，李彦宏创建了百度。经过十多年的发展，百度已经发展成为全球第二大独立搜索引擎和最大的中文搜索引擎。百度的成功，也使中国成为美国、俄罗斯和韩国之外，全球仅有的4个拥有搜索引擎核心技术的国家之一。2005年，百度在美国纳斯达克成功上市，并成为首家进入纳斯达克成分股的中国公司。百度已经成为中国最具价值的品牌之一。","meetingId":1,"name":"李彦宏","status":0,"systemId":4,"updateOn":1528709754000}]
         * firstPage : true
         * lastPage : true
         * lastPageNumber : 1
         * nextPage : 2
         * pageSize : 10
         * previousPage : 0
         * thisPageFirstElementNumber : 1
         * thisPageLastElementNumber : 2
         * totalElements : 2
         */

        private int curPage;
        private boolean firstPage;
        private boolean lastPage;
        private int lastPageNumber;
        private int nextPage;
        private int pageSize;
        private int previousPage;
        private int thisPageFirstElementNumber;
        private int thisPageLastElementNumber;
        private int totalElements;
        private List<ElementsBean> elements;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getLastPageNumber() {
            return lastPageNumber;
        }

        public void setLastPageNumber(int lastPageNumber) {
            this.lastPageNumber = lastPageNumber;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPreviousPage() {
            return previousPage;
        }

        public void setPreviousPage(int previousPage) {
            this.previousPage = previousPage;
        }

        public int getThisPageFirstElementNumber() {
            return thisPageFirstElementNumber;
        }

        public void setThisPageFirstElementNumber(int thisPageFirstElementNumber) {
            this.thisPageFirstElementNumber = thisPageFirstElementNumber;
        }

        public int getThisPageLastElementNumber() {
            return thisPageLastElementNumber;
        }

        public void setThisPageLastElementNumber(int thisPageLastElementNumber) {
            this.thisPageLastElementNumber = thisPageLastElementNumber;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public List<ElementsBean> getElements() {
            return elements;
        }

        public void setElements(List<ElementsBean> elements) {
            this.elements = elements;
        }
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ElementsBean implements Serializable{
            private static final long serialVersionUID = 7969906173066207318L;
            /**
             * createOn : 1528709829000
             * id : 2
             * intro : 马云，男，1964年9月10日生于浙江省杭州市，祖籍浙江省嵊州市（原嵊县）谷来镇， 阿里巴巴集团主要创始人，现担任阿里巴巴集团董事局主席、日本软银董事、大自然保护协会中国理事会主席兼全球董事会成员、华谊兄弟董事、生命科学突破奖基金会董事。 [1]  1988年毕业于杭州师范学院外语系，同年担任杭州电子工业学院英文及国际贸易教师，1995年创办中国第一家互联网商业信息发布网站“中国黄页”，1998年出任中国国际电子商务中心国富通信息技术发展有限公司总经理，1999年创办阿里巴巴，并担任阿里集团CEO、董事局主席。
             * meetingId : 1
             * name : 马云
             * status : 0
             * systemId : 4
             * updateOn : 1528709829000
             */

            private long createOn;
            private int id;
            private String intro;
            private int meetingId;
            private String name;
            private int status;
            private int systemId;
            private long updateOn;

            public long getCreateOn() {
                return createOn;
            }

            public void setCreateOn(long createOn) {
                this.createOn = createOn;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public int getMeetingId() {
                return meetingId;
            }

            public void setMeetingId(int meetingId) {
                this.meetingId = meetingId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSystemId() {
                return systemId;
            }

            public void setSystemId(int systemId) {
                this.systemId = systemId;
            }

            public long getUpdateOn() {
                return updateOn;
            }

            public void setUpdateOn(long updateOn) {
                this.updateOn = updateOn;
            }
        }
    }
}
