/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.dao;

import com.etf.bonusRMS.data.Korisnik;
import com.etf.bonusRMS.data.Voznja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class KorisnikDao {

    private static final KorisnikDao instance = new KorisnikDao();

    private KorisnikDao() {
    }

    public static KorisnikDao getInstance() {
        return instance;
    }

    public Korisnik find(String email, String sifra, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Korisnik korisnik = null;
        try {
            System.out.println(sifra + " " + email);
            ps = con.prepareStatement("SELECT * FROM korisnik where email=? AND sifra=? ");
            ps.setString(1, email);
            ps.setString(2, sifra);
            rs = ps.executeQuery();

            if (rs.next()) {
                korisnik = new Korisnik(rs.getInt("korisnik_id"), rs.getString("ime"), rs.getString("prezime"), rs.getString("email"), rs.getString("telefon"), rs.getString("sifra"), rs.getString("adresa"), rs.getString("token"));

            }

        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return korisnik;
    }

    public Korisnik find(int korisnikId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Korisnik korisnik = null;
        try {
            ps = con.prepareStatement("SELECT * FROM korisnik where korisnik_id=?");
            ps.setInt(1, korisnikId);

            rs = ps.executeQuery();
            if (rs.next()) {

                korisnik = new Korisnik(rs.getInt("korisnik_id"), rs.getString("ime"), rs.getString("prezime"), rs.getString("email"), rs.getString("telefon"), rs.getString("sifra"), rs.getString("adresa"), rs.getString("token"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return korisnik;
    }

    public String findKorisnikToken(int korisnikId, Connection con) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String token = null;
        try {
            ps = con.prepareStatement("SELECT token FROM korisnik where korisnik_id=?");
            ps.setInt(1, korisnikId);

            rs = ps.executeQuery();
            if (rs.next()) {
                token = rs.getString("token");
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return token;
    }

    public int create(Korisnik korisnik, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {

            ps = con.prepareStatement("INSERT INTO korisnik(ime, prezime,email,telefon,sifra,adresa,token) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, korisnik.getIme());
            ps.setString(2, korisnik.getPrezime());
            ps.setString(3, korisnik.getEmail());
            ps.setString(4, korisnik.getTelefon());
            ps.setString(5, korisnik.getSifra());
            ps.setString(6, korisnik.getAdresa());
            ps.setString(7, korisnik.getToken());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return id;
    }

    public void update(Korisnik korisnik, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {

            ps = con.prepareStatement("UPDATE korisnik SET ime=?, prezime=?,email=?,telefon=?,sifra=?,adresa=? WHERE korisnik_id=?");
            ps.setString(1, korisnik.getIme());
            ps.setString(2, korisnik.getPrezime());
            ps.setString(3, korisnik.getEmail());
            ps.setString(4, korisnik.getTelefon());
            ps.setString(5, korisnik.getSifra());
            ps.setString(6, korisnik.getAdresa());
            ps.setInt(7, korisnik.getKorisnikId());

            ps.executeUpdate();

        } finally {
            ResourcesManager.closeResources(rs, ps);
        }

    }

    public void delete(Korisnik korisnik, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM korisnik WHERE email=? AND sifra=?");
            ps.setString(1, korisnik.getEmail());
            ps.setString(2, korisnik.getSifra());

            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
