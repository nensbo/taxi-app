package com.example.taxi.data;

import android.os.Parcelable;

import java.io.Serializable;

public class Vozac implements Serializable {
    private int vozacId;
    private Vozilo vozilo;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private String sifra;
    private String adresa;
    private String slika;
    private int godineStarosti;
    private int godineIskustva;
    private String straniJezik;
    private String status="neaktivan";
    private String token;

    public Vozac() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Vozac(int vozacId, Vozilo vozilo, String ime, String prezime, String email, String telefon, String sifra, String adresa, String slika, int godineStarosti, int godineIskustva, String straniJezik, String status, String token) {
        this.vozacId = vozacId;
        this.vozilo = vozilo;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.sifra = sifra;
        this.adresa = adresa;
        this.slika = slika;
        this.godineStarosti = godineStarosti;
        this.godineIskustva = godineIskustva;
        this.straniJezik = straniJezik;
        this.status=status;
        this.token=token;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vozac(Vozilo vozilo, String ime, String prezime, String email, String telefon, String sifra, String adresa, String slika, int godineStarosti, int godineIskustva, String straniJezik, String status,String token) {
        this.vozilo = vozilo;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.sifra = sifra;
        this.adresa = adresa;
        this.slika = slika;
        this.godineStarosti = godineStarosti;
        this.godineIskustva = godineIskustva;
        this.straniJezik = straniJezik;
        this.status=status;
        this.token=token;
    }


    public int getVozacId() {
        return vozacId;
    }

    public void setVozacId(int vozacId) {
        this.vozacId = vozacId;
    }

    public Vozilo getVozilo() {
        return vozilo;
    }

    public void setVozilo(Vozilo vozilo) {
        this.vozilo = vozilo;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public int getGodineStarosti() {
        return godineStarosti;
    }

    public void setGodineStarosti(int godineStarosti) {
        this.godineStarosti = godineStarosti;
    }

    public int getGodineIskustva() {
        return godineIskustva;
    }

    public void setGodineIskustva(int godineIskustva) {
        this.godineIskustva = godineIskustva;
    }

    public String getStraniJezik() {
        return straniJezik;
    }

    public void setStraniJezik(String straniJezik) {
        this.straniJezik = straniJezik;
    }
}
