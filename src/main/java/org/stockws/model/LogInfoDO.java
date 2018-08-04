package org.stockws.model;

import java.io.Serializable;
import java.util.Date;

public class LogInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date date;
    private String dateStr;
    private String originText;
    private String logLevel;
    private String detailMsg;
    private String threadId;
    private String classSimpleName;
    private String fullClassName;
    private String classStatus;
    private String invokeMethod;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getOriginText() {
        return originText;
    }

    public void setOriginText(String originText) {
        this.originText = originText;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(String invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    @Override
    public String toString() {
        return "LogInfoDO [date=" + date + ", originText=" + originText + ", logLevel=" + logLevel + ", detailMsg=" + detailMsg
            + ", threadId=" + threadId + ", classSimpleName=" + classSimpleName + ", fullClassName=" + fullClassName
            + ", classStatus=" + classStatus + ", invokeMethod=" + invokeMethod + "]";
    }


}
