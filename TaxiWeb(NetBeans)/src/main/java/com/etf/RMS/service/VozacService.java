/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.service;

import com.etf.RMS.dao.ResourcesManager;
import com.etf.RMS.dao.VozacDao;
import com.etf.RMS.dao.VoziloDao;
import com.etf.RMS.exception.TaxiException;
import com.etf.bonusRMS.data.Vozac;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class VozacService {

    private static final VozacService instance = new VozacService();

    private VozacService() {
    }

    public static VozacService getInstance() {
        return instance;
    }

    public int addNewVozac(Vozac vozac) throws TaxiException {
        Connection con = null;
        int id = -1;
        try {
            con = ResourcesManager.getConnection();

            //more than one SQL statement to execute, needs to be a single transaction
            // con.setAutoCommit(false);
            Vozac vozac2 = VozacDao.getInstance().find(vozac.getEmail(), vozac.getSifra(), con);
            if (vozac2 == null) {

                id = VozacDao.getInstance().create(vozac, con);
            } else {
                id = vozac2.getVozacId();
            }

            //   con.commit();
        } catch (SQLException ex) {
            //   ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Failed to add new driver " + vozac.toString(), ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
        return id;
    }

    public Vozac findVozac(String email, String sifra) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return VozacDao.getInstance().find(email, sifra, con);

        } catch (SQLException ex) {
            throw new TaxiException("Nije moguće naći klijenta-vozaca sa emailom i sifrom: " + email + sifra, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }

    }

    public void updateVozac(Vozac vozac) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            VozacDao.getInstance().update(vozac, con);
            con.commit();

        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Neuspele izmene vozačevih podataka.", ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }

    }

    public void deleteVozac(String email, String sifra) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Vozac vozac = VozacDao.getInstance().find(email, sifra, con);
            if (vozac != null) {
                VozacDao.getInstance().delete(vozac, con);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Greška pri brisanju klijenta-vozaca :" + email + sifra, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
}
