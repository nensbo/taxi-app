package com.example.taxi.data;

import java.io.Serializable;

public class VoznjaDodaci implements Serializable {
    private int ekoloskoVozilo;
    private int sedisteBebe;
    private int brojSedista;
    private String straniJezik;

    public VoznjaDodaci(int ekoloskoVozilo, int sedisteBebe, int brojSedista, String straniJezik) {
        this.ekoloskoVozilo = ekoloskoVozilo;
        this.sedisteBebe = sedisteBebe;
        this.brojSedista = brojSedista;
        this.straniJezik=straniJezik;
    }

    public VoznjaDodaci() {

    }

    public int getEkoloskoVozilo() {
        return ekoloskoVozilo;
    }

    public void setEkoloskoVozilo(int ekoloskoVozilo) {
        this.ekoloskoVozilo = ekoloskoVozilo;
    }

    public int getSedisteBebe() {
        return sedisteBebe;
    }

    public String getStraniJezik() {
        return straniJezik;
    }

    public void setStraniJezik(String straniJezik) {
        this.straniJezik = straniJezik;
    }

    public void setSedisteBebe(int sedisteBebe) {
        this.sedisteBebe = sedisteBebe;
    }

    public int getBrojSedista() {
        return brojSedista;
    }

    public void setBrojSedista(int brojSedista) {
        this.brojSedista = brojSedista;
    }
}
