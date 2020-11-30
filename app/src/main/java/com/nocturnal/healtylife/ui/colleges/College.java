package com.nocturnal.healtylife.ui.colleges;

public class College {
    private String name;
    private String logo;
    private String web;

    public College(String name, String logo, String web) {
        this.name = name;
        this.logo = logo;
        this.web = web;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
