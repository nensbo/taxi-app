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
public class VoznjaDetalji implements Serializable {

    private int voznjaDetaljiId;
    private String vremeRezervacije;
    private String vremePocetak;
    private String vremeKraj;
    private int trajanjeVoznje;
    private double pocetnaLokacijaLat;
    private double pocetnaLokacijaLon;
    private double krajnjaLokacijaLat;
    private double krajnjaLokacijaLon;

    public VoznjaDetalji() {
    }

    public VoznjaDetalji(int voznjaDetaljiId, String vremeRezervacije, String vremePocetak, String vremeKraj, int trajanjeVoznje, double pocetnaLokacijaLat, double pocetnaLokacijaLon, double krajnjaLokacijaLat, double krajnjaLokacijaLon) {
        this.voznjaDetaljiId = voznjaDetaljiId;
        this.vremeRezervacije = vremeRezervacije;
        this.vremePocetak = vremePocetak;
        this.vremeKraj = vremeKraj;
        this.trajanjeVoznje = trajanjeVoznje;
        this.pocetnaLokacijaLat = pocetnaLokacijaLat;
        this.pocetnaLokacijaLon = pocetnaLokacijaLon;
        this.krajnjaLokacijaLat = krajnjaLokacijaLat;
        this.krajnjaLokacijaLon = krajnjaLokacijaLon;
    }

    public VoznjaDetalji(String vremeRezervacije, int trajanjeVoznje, double pocetnaLokacijaLat, double pocetnaLokacijaLon, double krajnjaLokacijaLat, double krajnjaLokacijaLon) {
        this.vremeRezervacije = vremeRezervacije;
        this.trajanjeVoznje = trajanjeVoznje;
        this.pocetnaLokacijaLat = pocetnaLokacijaLat;
        this.pocetnaLokacijaLon = pocetnaLokacijaLon;
        this.krajnjaLokacijaLat = krajnjaLokacijaLat;
        this.krajnjaLokacijaLon = krajnjaLokacijaLon;
    }

    public int getVoznjaDetaljiId() {
        return voznjaDetaljiId;
    }

    public void setVoznjaDetaljiId(int voznjaDetaljiId) {
        this.voznjaDetaljiId = voznjaDetaljiId;
    }

    public String getVremeRezervacije() {
        return vremeRezervacije;
    }

    public void setVremeRezervacije(String vremeRezervacije) {
        this.vremeRezervacije = vremeRezervacije;
    }

    public String getVremePocetak() {
        return vremePocetak;
    }

    public void setVremePocetak(String vremePocetak) {
        this.vremePocetak = vremePocetak;
    }

    public String getVremeKraj() {
        return vremeKraj;
    }

    public void setVremeKraj(String vremeKraj) {
        this.vremeKraj = vremeKraj;
    }

    public int getTrajanjeVoznje() {
        return trajanjeVoznje;
    }

    public void setTrajanjeVoznje(int trajanjeVoznje) {
        this.trajanjeVoznje = trajanjeVoznje;
    }

    public double getPocetnaLokacijaLat() {
        return pocetnaLokacijaLat;
    }

    public void setPocetnaLokacijaLat(double pocetnaLokacijaLat) {
        this.pocetnaLokacijaLat = pocetnaLokacijaLat;
    }

    public double getPocetnaLokacijaLon() {
        return pocetnaLokacijaLon;
    }

    public void setPocetnaLokacijaLon(double pocetnaLokacijaLon) {
        this.pocetnaLokacijaLon = pocetnaLokacijaLon;
    }

    public double getKrajnjaLokacijaLat() {
        return krajnjaLokacijaLat;
    }

    public void setKrajnjaLokacijaLat(double krajnjaLokacijaLat) {
        this.krajnjaLokacijaLat = krajnjaLokacijaLat;
    }

    public double getKrajnjaLokacijaLon() {
        return krajnjaLokacijaLon;
    }

    public void setKrajnjaLokacijaLon(double krajnjaLokacijaLon) {
        this.krajnjaLokacijaLon = krajnjaLokacijaLon;
    }

    @Override
    public String toString() {
        return "VoznjaDetalji{" + "voznjaDetaljiId=" + voznjaDetaljiId + ", vremeRezervacije=" + vremeRezervacije + ", vremePocetak=" + vremePocetak + ", vremeKraj=" + vremeKraj + ", trajanjeVoznje=" + trajanjeVoznje + ", pocetnaLokacijaLat=" + pocetnaLokacijaLat + ", pocetnaLokacijaLon=" + pocetnaLokacijaLon + ", krajnjaLokacijaLat=" + krajnjaLokacijaLat + ", krajnjaLokacijaLon=" + krajnjaLokacijaLon + '}';
    }

}
