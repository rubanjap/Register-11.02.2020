package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jennifer - AIT on 04-03-2020PM 05:12.
 */
public class ActiveCompProject {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }


    public class ActiveProjectDetail {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("projectId")
        @Expose
        private Integer projectId;
        @SerializedName("productname")
        @Expose
        private String productname;
        @SerializedName("productnumber")
        @Expose
        private String productnumber;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("registrationexpert")
        @Expose
        private String registrationexpert;
        @SerializedName("bidamount")
        @Expose
        private String bidamount;
        @SerializedName("paidamount")
        @Expose
        private String paidamount;
        @SerializedName("balanceamount")
        @Expose
        private String balanceamount;
        @SerializedName("nextdueamount")
        @Expose
        private String nextdueamount;
        @SerializedName("completiondate")
        @Expose
        private String completiondate;
        @SerializedName("projectstatus")
        @Expose
        private Object projectstatus;

        public Integer getProductid() {
            return productid;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getProductnumber() {
            return productnumber;
        }

        public void setProductnumber(String productnumber) {
            this.productnumber = productnumber;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getRegistrationexpert() {
            return registrationexpert;
        }

        public void setRegistrationexpert(String registrationexpert) {
            this.registrationexpert = registrationexpert;
        }

        public String getBidamount() {
            return bidamount;
        }

        public void setBidamount(String bidamount) {
            this.bidamount = bidamount;
        }

        public String getPaidamount() {
            return paidamount;
        }

        public void setPaidamount(String paidamount) {
            this.paidamount = paidamount;
        }

        public String getBalanceamount() {
            return balanceamount;
        }

        public void setBalanceamount(String balanceamount) {
            this.balanceamount = balanceamount;
        }

        public String getNextdueamount() {
            return nextdueamount;
        }

        public void setNextdueamount(String nextdueamount) {
            this.nextdueamount = nextdueamount;
        }

        public String getCompletiondate() {
            return completiondate;
        }

        public void setCompletiondate(String completiondate) {
            this.completiondate = completiondate;
        }

        public Object getProjectstatus() {
            return projectstatus;
        }

        public void setProjectstatus(Object projectstatus) {
            this.projectstatus = projectstatus;
        }

    }


    public class Data {

        @SerializedName("activeProjectDetails")
        @Expose
        private List<ActiveProjectDetail> activeProjectDetails = null;
        @SerializedName("completedProjectDetails")
        @Expose
        private List<CompletedProjectDetail> completedProjectDetails = null;

        public List<ActiveProjectDetail> getActiveProjectDetails() {
            return activeProjectDetails;
        }

        public void setActiveProjectDetails(List<ActiveProjectDetail> activeProjectDetails) {
            this.activeProjectDetails = activeProjectDetails;
        }

        public List<CompletedProjectDetail> getCompletedProjectDetails() {
            return completedProjectDetails;
        }

        public void setCompletedProjectDetails(List<CompletedProjectDetail> completedProjectDetails) {
            this.completedProjectDetails = completedProjectDetails;
        }

    }

    public class CompletedProjectDetail {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("projectId")
        @Expose
        private Integer projectId;
        @SerializedName("productname")
        @Expose
        private String productname;
        @SerializedName("productnumber")
        @Expose
        private String productnumber;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("registrationexpert")
        @Expose
        private String registrationexpert;
        @SerializedName("completiondate")
        @Expose
        private String completiondate;
        @SerializedName("projectcomments")
        @Expose
        private String projectcomments;

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getProductnumber() {
            return productnumber;
        }

        public void setProductnumber(String productnumber) {
            this.productnumber = productnumber;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getRegistrationexpert() {
            return registrationexpert;
        }

        public void setRegistrationexpert(String registrationexpert) {
            this.registrationexpert = registrationexpert;
        }

        public String getCompletiondate() {
            return completiondate;
        }

        public void setCompletiondate(String completiondate) {
            this.completiondate = completiondate;
        }

        public String getProjectcomments() {
            return projectcomments;
        }

        public void setProjectcomments(String projectcomments) {
            this.projectcomments = projectcomments;
        }

    }
}
