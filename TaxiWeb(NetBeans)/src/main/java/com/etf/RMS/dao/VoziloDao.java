/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.dao;

import com.etf.bonusRMS.data.Vozilo;
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
public class VoziloDao {

    private static final VoziloDao instance = new VoziloDao();

    private VoziloDao() {
    }

    public static VoziloDao getInstance() {
        return instance;
    }

    public int create(Vozilo vozilo, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {
            ps = con.prepareStatement("INSERT INTO vozilo(model_i_tip_vozila, registarski_broj,ekolosko_vozilo,sediste_za_bebe,broj_sedista) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vozilo.getModelITipVozila());
            ps.setString(2, vozilo.getRegistarskiBroj());
            ps.setInt(3, vozilo.getEkoloskoVozilo());
            ps.setInt(4, vozilo.getSedisteZaBebe());
            ps.setInt(5, vozilo.getBrojSedista());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return id;
    }

    public void update(Vozilo vozilo, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("UPDATE vozilo SET model_i_tip_vozila=?, registarski_broj=?,ekolosko_vozilo=?,sediste_za_bebe=?,broj_sedista=? WHERE vozilo_id=?");
            ps.setString(1, vozilo.getModelITipVozila());
            ps.setString(2, vozilo.getRegistarskiBroj());
            ps.setInt(3, vozilo.getEkoloskoVozilo());
            ps.setInt(4, vozilo.getSedisteZaBebe());
            ps.setInt(5, vozilo.getBrojSedista());
            ps.setInt(6, vozilo.getVoziloId());

            ps.executeUpdate();
            /* rs=ps.getGeneratedKeys();
            rs.next();
            id=rs.getInt(1); */
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
    }

    public Vozilo find(int voziloId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vozilo vozilo = null;
        try {
            ps = con.prepareStatement("SELECT * FROM vozilo where vozilo_id=?");
            ps.setInt(1, voziloId);
            rs = ps.executeQuery();
            if (rs.next()) {
                vozilo = new Vozilo(rs.getInt("vozilo_id"), rs.getString("model_i_tip_vozila"), rs.getString("registarski_broj"), rs.getInt("ekolosko_vozilo"), rs.getInt("sediste_za_bebe"), rs.getInt("broj_sedista"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return vozilo;
    }

    public void delete(Vozilo vozilo, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM vozilo WHERE vozilo_id=?");
            ps.setInt(1, vozilo.getVoziloId());

            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

}
