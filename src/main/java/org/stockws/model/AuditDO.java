package org.stockws.model;

import java.io.Serializable;

public class AuditDO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    private String auditer;

    public String getAuditer() {
        return auditer;
    }

    public void setAuditer(String auditer) {
        this.auditer = auditer;
    }


    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    private String flow;

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
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


    @Override
    public String toString() {
        return "AuditDO [" + "id=" + id + ", " + "auditer=" + auditer + ", " + "city=" + city + ", " + "time=" + time + ", "
            + "flow=" + flow + ", " + "comment=" + comment + ", " + "operation=" + operation + "]";
    }

}
