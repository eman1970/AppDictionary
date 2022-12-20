package com.example.appdictionary;

import java.io.Serializable;

public class WordModel implements Serializable {

    private final String ordSvenska;
    private final String ordRyska;
    private final String imgURL;
    private final String svenskaKort;
    private final String ryskaKort;
    private final String linkSV;
    private final String linkRY;

    public WordModel(String ordSvenska, String ordRyska, String imgURL, String svenskaKort, String ryskaKort, String linkSV, String linkRY) {
        this.ordSvenska = ordSvenska;
        this.ordRyska = ordRyska;
        this.imgURL = imgURL;
        this.svenskaKort = svenskaKort;
        this.ryskaKort = ryskaKort;
        this.linkSV = linkSV;
        this.linkRY = linkRY;
    }

    public String getOrdSvenska() {
        return ordSvenska;
    }

    public String getOrdRyska() {
        return ordRyska;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getSvenskaKort() {
        return svenskaKort;
    }

    public String getRyskaKort() {
        return ryskaKort;
    }

    public String getLinkSV() {
        return linkSV;
    }

    public String getLinkRY() {
        return linkRY;
    }


}
