/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.service;

import com.etf.RMS.dao.ResourcesManager;
import com.etf.RMS.dao.VoznjaDao;
import com.etf.RMS.exception.TaxiException;
import com.etf.bonusRMS.data.Cena;
import com.etf.bonusRMS.data.Korisnik;
import com.etf.bonusRMS.data.Vozac;
import com.etf.bonusRMS.data.Vozilo;
import com.etf.bonusRMS.data.Voznja;
import com.etf.bonusRMS.data.VoznjaDetalji;
import com.etf.bonusRMS.data.VoznjaDodaci;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class VoznjaService {

    private static final VoznjaService instance = new VoznjaService();

    private VoznjaService() {
    }

    public static VoznjaService getInstance() {
        return instance;
    }

    public int makeVoznja(Korisnik korisnik, String status, VoznjaDodaci voznjaDodaci, VoznjaDetalji voznjaDetalji) throws TaxiException {
        Connection con = null;
        int voznjaId = -1;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Voznja voznja = new Voznja(korisnik, status, voznjaDetalji, voznjaDodaci);
            voznjaId = VoznjaDao.getInstance().create(voznja, con);
            con.commit();

            // System.out.println("Vozilo " + voznjaId+ " "+vozilo.getModelITipVozila() +" preuzima korisnika: "+korisnik.getIme()+" "+korisnik.getPrezime()+ " na adresi " + voznjaDetalji.getAdresaOpstina()+" ulica: "+voznjaDetalji.getAdresa()+" u vreme: "+ voznjaDetalji.getVremeVoznje());
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Neuspelo kreiranje vožnje.", ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
        return voznjaId;
    }
    
      public Cena findVoznjaCena() throws TaxiException{
          
          Cena cena=new Cena();
          System.out.println("Cena po km je "+ cena.getCenaPoKm()+" "+ "Cena starta je "+cena.getCenaStart());
          return cena;
          
    /*        Connection con = null;
        try {
            con = ResourcesManager.getConnection();

            return VoznjaDao.getInstance().findVoznjaCena(con);

        } catch (SQLException ex) {
            throw new TaxiException("Nije moguće pronaći slobodnu voznju", ex);
        } finally {
            ResourcesManager.closeConnection(con);
        } */
    /*    Voznja voznja=new Voznja();
       // Integer[] niz=new Integer[2];
       int[] niz=new int[2];
       niz[0]=voznja.getCenaStart();
        niz[1]=voznja.getCenaPoKm(); 
        System.out.println("Cene: "+ niz[0]+" "+niz[1]);
             return niz;

     
     */
        

        }
    
    public ArrayList<Voznja> findVoznja(int id, String tip) throws TaxiException {
        Connection con = null;
        Voznja voznja;
        ArrayList<Voznja> voznje = new ArrayList<Voznja>();
        try {
            con = ResourcesManager.getConnection();
            if (tip.equals("korisnik")) {
                return VoznjaDao.getInstance().findVoznjaKorisnik(id, con);
            }
            if (tip.equals("voznja")) {
                voznja = VoznjaDao.getInstance().find(id, con);
                voznje.add(voznja);
                return voznje;
            }
            if (tip.equals("vozac")) {
                return VoznjaDao.getInstance().findVoznjaVozac(id, con);
            }

        } catch (SQLException ex) {
            throw new TaxiException("Nije moguće pronaći voznju sa datim id  " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
        return voznje;
    }

    public ArrayList<Voznja> findVoznja() throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();

            return VoznjaDao.getInstance().findVoznja(con);

        } catch (SQLException ex) {
            throw new TaxiException("Nije moguće pronaći slobodnu voznju", ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }

    }

    public void updateVoznja(int voznjaId, int vozacId, int status) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            //  Voznja voznja=VoznjaDao.getInstance().find(voznjaId, con);
            VoznjaDao.getInstance().updateVoznja(voznjaId, vozacId, status, con);
            con.commit();

        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Neuspele izmene vožnje.", ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }

    }

    public void updateVoznja(int voznjaId, int ocena, String komentar) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Voznja voznja = VoznjaDao.getInstance().find(voznjaId, con);
            VoznjaDao.getInstance().update(voznjaId, ocena, komentar, con);
            con.commit();

        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Neuspele izmene vožnje.", ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }

    }

    public void deleteVoznja(int voznjaId) throws TaxiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Voznja voznja = VoznjaDao.getInstance().find(voznjaId, con);
            if (voznja != null) {
                VoznjaDao.getInstance().delete(voznja, con);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new TaxiException("Greška pri brisanju voznje :" + voznjaId, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }

}
