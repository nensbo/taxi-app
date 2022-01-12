/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.dao;

import com.etf.bonusRMS.data.Voznja;
import com.etf.bonusRMS.data.VoznjaDetalji;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class VoznjaDetaljiDao {

    private static final VoznjaDetaljiDao instance = new VoznjaDetaljiDao();

    private VoznjaDetaljiDao() {
    }

    public static VoznjaDetaljiDao getInstance() {
        return instance;
    }

    public VoznjaDetalji find(int voznjaDetaljiId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        VoznjaDetalji voznjaDetalji = null;
        String vremePocetak = null, vremeKraj = null;
        int trajanjeVoznje = 0;
        try {
            ps = con.prepareStatement("SELECT * FROM voznja_detalji where voznja_detalji_id=?");
            ps.setInt(1, voznjaDetaljiId);

            rs = ps.executeQuery();
            if (rs.next()) {
                if ((rs.getTimestamp("vreme_pocetak")) != null) {
                    vremePocetak = (rs.getTimestamp("vreme_pocetak")).toString();
                }
                if ((rs.getTimestamp("vreme_kraj")) != null) {
                    vremeKraj = (rs.getTimestamp("vreme_kraj")).toString();
                }
                if ((rs.getInt("trajanje_voznje")) != 0) {
                    trajanjeVoznje = rs.getInt("trajanje_voznje");
                }

                voznjaDetalji = new VoznjaDetalji(voznjaDetaljiId, (rs.getTimestamp("vreme_rezervacije")).toString(), vremePocetak, vremeKraj, trajanjeVoznje, rs.getDouble("pocetna_adresa_lat"), rs.getDouble("pocetna_adresa_lon"), rs.getDouble("krajnja_adresa_lat"), rs.getDouble("krajnja_adresa_lon"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return voznjaDetalji;
    }

    public int create(VoznjaDetalji voznjaDetalji, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int mm = voznjaDetalji.getTrajanjeVoznje() * 60 * 1000;
        Timestamp vremeVoznje;
        if (voznjaDetalji.getVremePocetak() != null) {
            vremeVoznje = Timestamp.valueOf(voznjaDetalji.getVremePocetak());

            long tt = vremeVoznje.getTime();
            Time vremePocetak = new Time(tt);

            Time vremeKraj = new Time(tt + mm);
            Timestamp timestampKraj = timestamp;
            timestampKraj.setTime(tt + mm);
        } else {
            vremeVoznje = null;
        }
        double dd = 43.9876543;

        System.out.println("Trajanje voznje: " + voznjaDetalji.getTrajanjeVoznje());
        try {

            ps = con.prepareStatement("INSERT INTO voznja_detalji(vreme_rezervacije, trajanje_voznje, vreme_pocetak, vreme_kraj, pocetna_adresa_lat, pocetna_adresa_lon,krajnja_adresa_lat, krajnja_adresa_lon) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, timestamp);
            ps.setInt(2, voznjaDetalji.getTrajanjeVoznje());
            ps.setTimestamp(3, null);
            ps.setTimestamp(4, null);
            ps.setDouble(5, voznjaDetalji.getPocetnaLokacijaLat());
            ps.setDouble(6, voznjaDetalji.getPocetnaLokacijaLon());
            ps.setDouble(7, voznjaDetalji.getKrajnjaLokacijaLat());
            ps.setDouble(8, voznjaDetalji.getKrajnjaLokacijaLon());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return id;
    }

    public void update(VoznjaDetalji voznjaDetalji, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {

            ps = con.prepareStatement("UPDATE voznja_detalji SET vreme_rezervacije=?, trajanje_voznje=?, vreme_pocetak=?, vreme_kraj=?, pocetna_adresa_lat=?, pocetna_adresa_lon=?,krajnja_adresa_lat=?, krajnja_adresa_lon=?");
            ps.setTimestamp(1, Timestamp.valueOf(voznjaDetalji.getVremeRezervacije()));
            ps.setInt(2, voznjaDetalji.getTrajanjeVoznje());
            ps.setTimestamp(3, Timestamp.valueOf(voznjaDetalji.getVremePocetak()));
            ps.setTimestamp(4, Timestamp.valueOf(voznjaDetalji.getVremeKraj()));
            ps.setDouble(5, voznjaDetalji.getPocetnaLokacijaLat());
            ps.setDouble(6, voznjaDetalji.getPocetnaLokacijaLon());
            ps.setDouble(7, voznjaDetalji.getKrajnjaLokacijaLat());
            ps.setDouble(8, voznjaDetalji.getKrajnjaLokacijaLon());

            ps.executeUpdate();

        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(VoznjaDetalji voznjaDetalji, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {

            //delete customer
            ps = con.prepareStatement("DELETE FROM voznja_detalji WHERE voznja_detalji_id=?");
            ps.setInt(1, voznjaDetalji.getVoznjaDetaljiId());
            ps.executeUpdate();

        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
