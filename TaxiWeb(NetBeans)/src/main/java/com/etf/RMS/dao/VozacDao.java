/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.dao;

import com.etf.bonusRMS.data.Vozac;
import com.etf.bonusRMS.data.Vozilo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class VozacDao {

    private static final VozacDao instance = new VozacDao();

    private VozacDao() {
    }

    public static VozacDao getInstance() {
        return instance;
    }

    public Vozac find(String email, String sifra, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vozac vozac = null;
        try {
            System.out.println(sifra + " " + email);
            ps = con.prepareStatement("SELECT * FROM vozac where email=? AND sifra=? ");
            ps.setString(1, email);
            ps.setString(2, sifra);
            rs = ps.executeQuery();

            if (rs.next()) {
                Vozilo vozilo = VoziloDao.getInstance().find(rs.getInt("vozilo_id"), con);

                vozac = new Vozac(rs.getInt("vozac_id"), vozilo, rs.getString("ime"), rs.getString("prezime"), rs.getString("email"), rs.getString("telefon"), rs.getString("sifra"), rs.getString("adresa"), rs.getString("slika"), rs.getInt("godine_starosti"), rs.getInt("godine_iskustva"), rs.getString("strani_jezik"), rs.getString("status"), rs.getString("token"));

            }

        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return vozac;
    }

    public Vozac find(int vozacId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vozac vozac = null;
        try {
            ps = con.prepareStatement("SELECT * FROM vozac where vozac_id=?");
            ps.setInt(1, vozacId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Vozilo vozilo = VoziloDao.getInstance().find(rs.getInt("vozilo_id"), con);

                vozac = new Vozac(rs.getInt("vozac_id"), vozilo, rs.getString("ime"), rs.getString("prezime"), rs.getString("email"), rs.getString("telefon"), rs.getString("sifra"), rs.getString("adresa"), rs.getString("slika"), rs.getInt("godine_starosti"), rs.getInt("godine_iskustva"), rs.getString("strani_jezik"), rs.getString("status"), rs.getString("token"));
           //     System.out.println(vozac.toString());

            }

        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return vozac;
    }

    public int create(Vozac vozac, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int fkVozilo;
        int id = -1;
        try {

            fkVozilo = VoziloDao.getInstance().create(vozac.getVozilo(), con);

            ps = con.prepareStatement("INSERT INTO vozac(vozilo_id,ime, prezime,email,telefon,sifra,adresa,slika,godine_starosti,godine_iskustva,strani_jezik,status,token) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, fkVozilo);
            ps.setString(2, vozac.getIme());
            ps.setString(3, vozac.getPrezime());
            ps.setString(4, vozac.getEmail());
            ps.setString(5, vozac.getTelefon());
            ps.setString(6, vozac.getSifra());
            ps.setString(7, vozac.getAdresa());
            ps.setString(8, vozac.getSlika());
            ps.setInt(9, vozac.getGodineStarosti());
            ps.setInt(10, vozac.getGodineIskustva());
            ps.setString(11, vozac.getStraniJezik());
            ps.setString(12, vozac.getStatus());
            ps.setString(13, vozac.getToken());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return id;
    }

    public void update(Vozac vozac, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int fkVozilo;
        int id = -1;
        try {

            VoziloDao.getInstance().update(vozac.getVozilo(), con);

            ps = con.prepareStatement("UPDATE vozac SET ime=?, prezime=?,email=?,telefon=?,sifra=?,adresa=?,slika=?,godine_starosti=?,godine_iskustva=?,strani_jezik=?,status=? WHERE vozac_id=?");
            ps.setString(1, vozac.getIme());
            ps.setString(2, vozac.getPrezime());
            ps.setString(3, vozac.getEmail());
            ps.setString(4, vozac.getTelefon());
            ps.setString(5, vozac.getSifra());
            ps.setString(6, vozac.getAdresa());
            ps.setString(7, vozac.getSlika());
            ps.setInt(8, vozac.getGodineStarosti());
            ps.setInt(9, vozac.getGodineIskustva());
            ps.setString(10, vozac.getStraniJezik());
            ps.setString(11, vozac.getStatus());
            ps.setInt(12, vozac.getVozacId());

            ps.executeUpdate();
            /*   rs=ps.getGeneratedKeys();
            rs.next();
            id=rs.getInt(1); */
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
    }

    public void delete(Vozac vozac, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM vozac WHERE email=? AND sifra=?");
            ps.setString(1, vozac.getEmail());
            ps.setString(2, vozac.getSifra());

            ps.executeUpdate();
            if (vozac.getVozilo() != null) {
                VoziloDao.getInstance().delete(vozac.getVozilo(), con);
            }
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
