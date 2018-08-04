package org.stockws.model;

import java.io.Serializable;

public class ApproveDO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    private String approver;

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }


    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    private String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }


    private String flow;

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }


    @Override
    public String toString() {
        return "ApproveDO [" + "id=" + id + ", " + "time=" + time + ", " + "approver=" + approver + ", " + "city=" + city + ", "
            + "comment=" + comment + ", " + "operation=" + operation + ", " + "flow=" + flow + "]";
    }

}
