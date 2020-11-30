package com.nocturnal.healtylife.ui.statistic;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attributes {
    @SerializedName("attributes")
    List<Statistic> attributes;

    public List<Statistic> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Statistic> attributes) {
        this.attributes = attributes;
    }
}
