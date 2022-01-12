/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.service;

import com.etf.RMS.dao.KorisnikDao;
import com.etf.RMS.dao.ResourcesManager;
import com.etf.RMS.dao.VoziloDao;
import com.etf.RMS.exception.TaxiException;
import com.etf.bonusRMS.data.Korisnik;
import com.etf.bonusRMS.data.Vozilo;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class VoziloService {

    private static final VoziloService instance = new VoziloService();

    private VoziloService() {
    }

    public static VoziloService getInstance() {
        return instance;
    }

    public void addNewVozilo(Vozilo vozilo) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();

            //more than one SQL statement to execute, needs to be a single transaction
            // con.setAutoCommit(false);
            VoziloDao.getInstance().create(vozilo, con);

            //   con.commit();
        } catch (SQLException ex) {
            //   ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Neuspešno dodavanje novog vozila " + vozilo, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public Vozilo findVozilo(int voziloId) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();

            return VoziloDao.getInstance().find(voziloId, con);

        } catch (SQLException ex) {
            throw new TaxiException("Nije moguće naći vozilo sa id: " + voziloId, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }

    }

    public void deleteVozilo(Vozilo vozilo) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            VoziloDao.getInstance().delete(vozilo, con);

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Greška pri brisanju vozila :" + vozilo.getVoziloId(), ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
}
