package org.stockws.model;

import java.io.Serializable;

public class ChatCommentDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;

    public String getText() {
        return text;
    }

    private String color;
    private Integer size;
    private Integer position;
    private Integer time;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChatCommentDO [text=" + text + ", color=" + color + ", size=" + size + ", position=" + position + ", time=" + time
            + "]";
    }

}
