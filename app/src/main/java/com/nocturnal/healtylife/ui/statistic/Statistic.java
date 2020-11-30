package com.nocturnal.healtylife.ui.statistic;

import com.google.gson.annotations.SerializedName;

public class Statistic {
    @SerializedName("jml_pdp")
    private int jml_pdp;

    @SerializedName("jml_odp")
    private int jml_odp;

    @SerializedName("jml_otg")
    private int jml_otg;

    @SerializedName("jml_sembuh")
    private int jml_sembuh;

    @SerializedName("jml_negatif")
    private int jml_negatif;

    @SerializedName("jml_positif")
    private int jml_positif;

    @SerializedName("jml_meninggal")
    private int jml_meninggal;

    public int getJml_pdp() {
        return jml_pdp;
    }

    public void setJml_pdp(int jml_pdp) {
        this.jml_pdp = jml_pdp;
    }

    public int getJml_odp() {
        return jml_odp;
    }

    public void setJml_odp(int jml_odp) {
        this.jml_odp = jml_odp;
    }

    public int getJml_otg() {
        return jml_otg;
    }

    public void setJml_otg(int jml_otg) {
        this.jml_otg = jml_otg;
    }

    public int getJml_sembuh() {
        return jml_sembuh;
    }

    public void setJml_sembuh(int jml_sembuh) {
        this.jml_sembuh = jml_sembuh;
    }

    public int getJml_negatif() {
        return jml_negatif;
    }

    public void setJml_negatif(int jml_negatif) {
        this.jml_negatif = jml_negatif;
    }

    public int getJml_positif() {
        return jml_positif;
    }

    public void setJml_positif(int jml_positif) {
        this.jml_positif = jml_positif;
    }

    public int getJml_meninggal() {
        return jml_meninggal;
    }

    public void setJml_meninggal(int jml_meninggal) {
        this.jml_meninggal = jml_meninggal;
    }
}
