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
public class Korisnik implements Serializable {

    private int korisnikId;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private String sifra;
    private String adresa;
    private String token;

    public Korisnik() {
    }

    public Korisnik(int korisnikId, String ime, String prezime, String email, String telefon, String sifra, String adresa, String token) {
        this.korisnikId = korisnikId;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.sifra = sifra;
        this.adresa = adresa;
        this.token = token;
    }

    public Korisnik(String ime, String prezime, String email, String telefon, String sifra, String adresa, String token) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.sifra = sifra;
        this.adresa = adresa;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
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

    @Override
    public String toString() {
        return "Korisnik{" + "ime=" + ime + ", prezime=" + prezime + ", email=" + email + ", telefon=" + telefon + ", sifra=" + sifra + ", adresa=" + adresa + '}';
    }

}
