package com.eyougo.common.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Created by mei on 23/03/2017.
 */
public abstract class BaseModel implements Serializable{

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime updatedAt;

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}