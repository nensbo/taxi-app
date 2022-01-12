package com.example.taxi.data;

import java.io.Serializable;

public class Voznja implements Serializable {
    private int voznjaId;
    private Korisnik korisnik;
    private String status;
    private VoznjaDetalji voznjaDetalji;
    private VoznjaDodaci voznjaDodaci;
    private Vozac vozac;
    private int ocena;
    private String komentar;
    private int cenaPoKm;
    private int cenaStart;
    public Voznja(){}

    public Voznja(int voznjaId, Korisnik korisnik, String status, VoznjaDetalji voznjaDetalji, VoznjaDodaci voznjaDodaci, Vozac vozac, int ocena, String komentar,int cenaPoKm, int cenaStart) {
        this.voznjaId = voznjaId;
        this.korisnik = korisnik;
        this.status = status;
        this.voznjaDetalji = voznjaDetalji;
        this.voznjaDodaci = voznjaDodaci;
        this.vozac = vozac;
        this.ocena = ocena;
        this.komentar = komentar;
        this.cenaPoKm=cenaPoKm;
        this.cenaStart=cenaStart;
    }

    public Voznja(Korisnik korisnik, String status, VoznjaDetalji voznjaDetalji, VoznjaDodaci voznjaDodaci, Vozac vozac, int ocena, String komentar,int cenaPoKm, int cenaStart) {
        this.korisnik = korisnik;
        this.status = status;
        this.voznjaDetalji = voznjaDetalji;
        this.voznjaDodaci = voznjaDodaci;
        this.vozac = vozac;
        this.ocena = ocena;
        this.komentar = komentar;
        this.cenaPoKm=cenaPoKm;
        this.cenaStart=cenaStart;

    }
    public Voznja(Korisnik korisnik, String status, VoznjaDetalji voznjaDetalji, VoznjaDodaci voznjaDodaci) {
        this.korisnik = korisnik;
        this.status = status;
        this.voznjaDetalji = voznjaDetalji;
        this.voznjaDodaci = voznjaDodaci;

    }

    public int getCenaStart() {
        return cenaStart;
    }

    public void setCenaStart(int cenaStart) {
        this.cenaStart = cenaStart;
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
