/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.service;

import com.etf.RMS.dao.KorisnikDao;
import com.etf.RMS.dao.ResourcesManager;
import com.etf.RMS.exception.TaxiException;
import com.etf.bonusRMS.data.Korisnik;
import com.etf.bonusRMS.data.Voznja;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class KorisnikService {

    private static final KorisnikService instance = new KorisnikService();

    private KorisnikService() {
    }

    public static KorisnikService getInstance() {
        return instance;
    }

    public int addNewKorisnik(Korisnik korisnik) throws TaxiException {
        Connection con = null;
        int id = -1;
        try {
            con = ResourcesManager.getConnection();

            //more than one SQL statement to execute, needs to be a single transaction
            // con.setAutoCommit(false);
            Korisnik korisnik2 = KorisnikDao.getInstance().find(korisnik.getEmail(), korisnik.getSifra(), con);
            if (korisnik2 == null) {

                id = KorisnikDao.getInstance().create(korisnik, con);
            } else {
                id = korisnik2.getKorisnikId();
            }

            //   con.commit();
        } catch (SQLException ex) {
            //   ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Failed to add new customer " + korisnik.toString(), ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
        return id;
    }

    public Korisnik findKorisnik(String email, String sifra) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            return KorisnikDao.getInstance().find(email, sifra, con);

        } catch (SQLException ex) {
            throw new TaxiException("Nije moguće naći korisnika sa emailom i sifrom: " + email + sifra, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }

    }
    
      

    public void updateKorisnik(Korisnik korisnik) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            KorisnikDao.getInstance().update(korisnik, con);

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Greška pri azuriranju korisnika :" + korisnik.getIme() + " " + korisnik.getPrezime(), ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

    public void deleteKorisnik(String email, String sifra) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Korisnik korisnik = KorisnikDao.getInstance().find(email, sifra, con);
            if (korisnik != null) {
                KorisnikDao.getInstance().delete(korisnik, con);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Greška pri brisanju korisnika :" + email + sifra, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
}
