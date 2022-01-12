/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.bonusRMS.data;

import java.io.Serializable;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class VoznjaDodaci implements Serializable {

    private int voznjaDodaciId;
    private int ekoloskoVozilo;
    private int sedisteBebe;
    private int brojSedista;
    private String straniJezik;

    public VoznjaDodaci() {
    }

    public VoznjaDodaci(int ekoloskoVozilo, int sedisteBebe, int brojSedista, String straniJezik) {
        this.ekoloskoVozilo = ekoloskoVozilo;
        this.sedisteBebe = sedisteBebe;
        this.brojSedista = brojSedista;
        this.straniJezik = straniJezik;
    }

    public VoznjaDodaci(int voznjaDodaciId, int ekoloskoVozilo, int sedisteBebe, int brojSedista, String straniJezik) {
        this.voznjaDodaciId = voznjaDodaciId;
        this.ekoloskoVozilo = ekoloskoVozilo;
        this.sedisteBebe = sedisteBebe;
        this.brojSedista = brojSedista;
        this.straniJezik = straniJezik;
    }

    public void setVoznjaDodaciId(int voznjaDodaciId) {
        this.voznjaDodaciId = voznjaDodaciId;
    }

    public int getVoznjaDodaciId() {
        return voznjaDodaciId;
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

    public void setSedisteBebe(int sedisteBebe) {
        this.sedisteBebe = sedisteBebe;
    }

    public int getBrojSedista() {
        return brojSedista;
    }

    public void setBrojSedista(int brojSedista) {
        this.brojSedista = brojSedista;
    }

    public String getStraniJezik() {
        return straniJezik;
    }

    public void setStraniJezik(String straniJezik) {
        this.straniJezik = straniJezik;
    }

    @Override
    public String toString() {
        return "VoznjaDodaci{" + "voznjaDodaciId=" + voznjaDodaciId + ", ekoloskoVozilo=" + ekoloskoVozilo + ", sedisteBebe=" + sedisteBebe + ", brojSedista=" + brojSedista + ", straniJezik=" + straniJezik + '}';
    }

}
