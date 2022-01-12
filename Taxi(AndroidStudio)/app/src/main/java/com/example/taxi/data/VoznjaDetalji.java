package com.example.taxi.data;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;


public class VoznjaDetalji implements Serializable {
    private int voznjaDetaljiId;
    private String vremeRezervacije;
    private int trajanjeVoznje;
    private String vremeKraj;
    private String vremePocetak;
    private double pocetnaLokacijaLat;
    private double pocetnaLokacijaLon;
    private double krajnjaLokacijaLat;
    private double krajnjaLokacijaLon;


    public VoznjaDetalji() {
    }

    public VoznjaDetalji(int voznjaDetaljiId, String vremeRezervacije, int trajanjeVoznje, String vremeKraj, String vremePocetak, double pocetnaLokacijaLat, double pocetnaLokacijaLon, double krajnjaLokacijaLat, double krajnjaLokacijaLon) {
        this.voznjaDetaljiId = voznjaDetaljiId;
        this.vremeRezervacije = vremeRezervacije;
        this.trajanjeVoznje = trajanjeVoznje;
        this.vremeKraj = vremeKraj;
        this.vremePocetak = vremePocetak;
        this.pocetnaLokacijaLat = pocetnaLokacijaLat;
        this.pocetnaLokacijaLon = pocetnaLokacijaLon;
        this.krajnjaLokacijaLat = krajnjaLokacijaLat;
        this.krajnjaLokacijaLon = krajnjaLokacijaLon;
    }

    public VoznjaDetalji(String vremeRezervacije, int trajanjeVoznje, String vremeKraj, String vremePocetak, double pocetnaLokacijaLat, double pocetnaLokacijaLon, double krajnjaLokacijaLat, double krajnjaLokacijaLon) {
        this.vremeRezervacije = vremeRezervacije;
        this.trajanjeVoznje = trajanjeVoznje;
        this.vremeKraj = vremeKraj;
        this.vremePocetak = vremePocetak;
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

    public int getTrajanjeVoznje() {
        return trajanjeVoznje;
    }

    public void setTrajanjeVoznje(int trajanjeVoznje) {
        this.trajanjeVoznje = trajanjeVoznje;
    }

    public String getVremeKraj() {
        return vremeKraj;
    }

    public void setVremeKraj(String vremeKraj) {
        this.vremeKraj = vremeKraj;
    }

    public String getVremePocetak() {
        return vremePocetak;
    }

    public void setVremePocetak(String vremePocetak) {
        this.vremePocetak = vremePocetak;
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

    public VoznjaDetalji(String vremeRezervacije, int trajanjeVoznje, double pocetnaLokacijaLat, double pocetnaLokacijaLon, double krajnjaLokacijaLat, double krajnjaLokacijaLon) {
        this.vremeRezervacije = vremeRezervacije;
        this.trajanjeVoznje = trajanjeVoznje;
        this.pocetnaLokacijaLat = pocetnaLokacijaLat;
        this.pocetnaLokacijaLon = pocetnaLokacijaLon;
        this.krajnjaLokacijaLat = krajnjaLokacijaLat;
        this.krajnjaLokacijaLon = krajnjaLokacijaLon;
    }
}