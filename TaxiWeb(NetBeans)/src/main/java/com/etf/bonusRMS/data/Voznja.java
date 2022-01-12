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
public class Voznja implements Serializable {

    private int voznjaId;
    private Korisnik korisnik;
    private String status;
    private VoznjaDetalji voznjaDetalji;
    private VoznjaDodaci voznjaDodaci;
    private Vozac vozac;
    private int ocena;
    private String komentar;
    private static int cenaPoKm=30;
    private static int cenaStart=100;


    public Voznja() {
    }

    public Voznja(int voznjaId, Korisnik korisnik, String status, VoznjaDetalji voznjaDetalji, VoznjaDodaci voznjaDodaci, Vozac vozac, int ocena, String komentar, int cenaPoKm, int cenaStart) {
        this.voznjaId = voznjaId;
        this.korisnik = korisnik;
        this.status = status;
        this.voznjaDetalji = voznjaDetalji;
        this.voznjaDodaci = voznjaDodaci;
        this.vozac = vozac;
        this.ocena = ocena;
        this.komentar = komentar;
        this.cenaPoKm = cenaPoKm;
        this.cenaStart=cenaStart;
    }

    public int getCenaStart() {
        return cenaStart;
    }

    public void setCenaStart(int cenaStart) {
        this.cenaStart = cenaStart;
    }

    public Voznja(int voznjaId, Korisnik korisnik, String status, VoznjaDetalji voznjaDetalji, VoznjaDodaci voznjaDodaci, int ocena, String komentar) {
        this.voznjaId = voznjaId;
        this.korisnik = korisnik;
        this.status = status;
        this.voznjaDetalji = voznjaDetalji;
        this.voznjaDodaci = voznjaDodaci;
        this.ocena = ocena;
        this.komentar = komentar;
    }

    public Voznja(Korisnik korisnik, String status, VoznjaDetalji voznjaDetalji, VoznjaDodaci voznjaDodaci) {
        this.korisnik = korisnik;
        this.status = status;
        this.voznjaDetalji = voznjaDetalji;
        this.voznjaDodaci = voznjaDodaci;

    }

    public Voznja(Korisnik korisnik, String status, VoznjaDetalji voznjaDetalji, VoznjaDodaci voznjaDodaci, Vozac vozac, int ocena, String komentar, int cenaPoKm) {
        this.korisnik = korisnik;

        this.status = status;
        this.voznjaDetalji = voznjaDetalji;
        this.voznjaDodaci = voznjaDodaci;
        this.vozac = vozac;
        this.ocena = ocena;
        this.komentar = komentar;
        this.cenaPoKm = cenaPoKm;
    }

    public int getCenaPoKm() {
        return cenaPoKm;
    }

    public void setCenaPoKm(int cenaPoKm) {
        this.cenaPoKm = cenaPoKm;
    }

    public int getVoznjaId() {
        return voznjaId;
    }

    public void setVoznjaId(int voznjaId) {
        this.voznjaId = voznjaId;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VoznjaDetalji getVoznjaDetalji() {
        return voznjaDetalji;
    }

    public void setVoznjaDetalji(VoznjaDetalji voznjaDetalji) {
        this.voznjaDetalji = voznjaDetalji;
    }

    public VoznjaDodaci getVoznjaDodaci() {
        return voznjaDodaci;
    }

    public void setVoznjaDodaci(VoznjaDodaci voznjaDodaci) {
        this.voznjaDodaci = voznjaDodaci;
    }

    public Vozac getVozac() {
        return vozac;
    }

    public void setVozac(Vozac vozac) {
        this.vozac = vozac;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
