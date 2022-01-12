/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.dao;

import com.etf.bonusRMS.data.VoznjaDodaci;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class VoznjaDodaciDao {

    private static final VoznjaDodaciDao instance = new VoznjaDodaciDao();

    private VoznjaDodaciDao() {
    }

    public static VoznjaDodaciDao getInstance() {
        return instance;
    }

    public VoznjaDodaci find(int voznjaDodaciId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        VoznjaDodaci voznjaDodaci = null;
        try {
            ps = con.prepareStatement("SELECT * FROM voznja_dodaci where voznja_dodaci_id=?");
            ps.setInt(1, voznjaDodaciId);

            rs = ps.executeQuery();
            if (rs.next()) {

                voznjaDodaci = new VoznjaDodaci(voznjaDodaciId, rs.getInt("ekolosko_vozilo"), rs.getInt("sediste_za_bebe"), rs.getInt("broj_sedista"), rs.getString("strani_jezik"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return voznjaDodaci;
    }

    public int create(VoznjaDodaci voznjaDodaci, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        System.out.println("Pokrenuta create nmetoda");

        try {
            ps = con.prepareStatement("INSERT INTO voznja_dodaci(sediste_za_bebe,broj_sedista,ekolosko_vozilo,strani_jezik) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, voznjaDodaci.getSedisteBebe());
            ps.setInt(2, voznjaDodaci.getBrojSedista());
            ps.setInt(3, voznjaDodaci.getEkoloskoVozilo());
            ps.setString(4, voznjaDodaci.getStraniJezik());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            System.out.println("Pokrenuta create nmetoda");

            rs.next();
            id = rs.getInt(1);
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return id;
    }

    public void update(VoznjaDodaci voznjaDodaci, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {

            ps = con.prepareStatement("UPDATE voznja_dodaci SET sediste_za_bebe=?,broj_sedista=?,ekolosko_vozilo=?,strani_jezik=?");
            ps.setInt(1, voznjaDodaci.getSedisteBebe());
            ps.setInt(2, voznjaDodaci.getBrojSedista());
            ps.setInt(3, voznjaDodaci.getEkoloskoVozilo());
            ps.setString(4, voznjaDodaci.getStraniJezik());

            ps.executeUpdate();

        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(VoznjaDodaci voznjaDodaci, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {

            //delete customer
            ps = con.prepareStatement("DELETE FROM voznja_dodaci WHERE voznja_dodaci_id=?");
            ps.setInt(1, voznjaDodaci.getVoznjaDodaciId());
            ps.executeUpdate();

        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
