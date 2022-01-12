package com.example.taxi.data;

import java.io.Serializable;

public class Cena implements Serializable {
    public Cena() {
    }
    private int cenaPoKm=30;
    private int cenaStart=100;

    public int getCenaPoKm() {
        return cenaPoKm;
    }

    public void setCenaPoKm(int cenaPoKm) {
        this.cenaPoKm = cenaPoKm;
    }

    public int getCenaStart() {
        return cenaStart;
    }

    public void setCenaStart(int cenaStart) {
        this.cenaStart = cenaStart;
    }





}
