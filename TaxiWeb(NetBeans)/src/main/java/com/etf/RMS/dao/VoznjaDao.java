/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.dao;

import com.etf.RMS.rest.FCMSender;
import com.etf.bonusRMS.data.Cena;
import com.etf.bonusRMS.data.Korisnik;
import com.etf.bonusRMS.data.Vozac;
import com.etf.bonusRMS.data.Vozilo;
import com.etf.bonusRMS.data.Voznja;
import com.etf.bonusRMS.data.VoznjaDetalji;
import com.etf.bonusRMS.data.VoznjaDodaci;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class VoznjaDao {

    private static final VoznjaDao instance = new VoznjaDao();

    private VoznjaDao() {
    }

    public static VoznjaDao getInstance() {
        return instance;
    }

    public ArrayList<Voznja> findVoznjaKorisnik(int korisnikId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Voznja> listVoznja = new ArrayList<>();
        Voznja voznja = null;
        try {
            ps = con.prepareStatement("SELECT * FROM voznja where korisnik_id=?");
            ps.setInt(1, korisnikId);

            rs = ps.executeQuery();
            while (rs.next()) {
                Vozac vozac = VozacDao.getInstance().find(rs.getInt("vozac_id"), con);
                Korisnik korisnik = KorisnikDao.getInstance().find(rs.getInt("korisnik_id"), con);
                VoznjaDodaci voznjaDodaci = VoznjaDodaciDao.getInstance().find(rs.getInt("voznja_dodaci_id"), con);
                VoznjaDetalji voznjaDetalji = VoznjaDetaljiDao.getInstance().find(rs.getInt("voznja_detalji_id"), con);
                voznja = new Voznja(rs.getInt("voznja_id"), korisnik, rs.getString("status"), voznjaDetalji, voznjaDodaci, rs.getInt("ocena"), rs.getString("komentar"));
                listVoznja.add(voznja);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return listVoznja;
    }

    public ArrayList<Voznja> findVoznjaVozac(int vozacId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Voznja> listVoznja = new ArrayList<>();
        Voznja voznja = null;
        try {
            ps = con.prepareStatement("SELECT * FROM voznja where vozac_id=?");
            ps.setInt(1, vozacId);

            rs = ps.executeQuery();
            while (rs.next()) {
                Vozac vozac = VozacDao.getInstance().find(rs.getInt("vozac_id"), con);
                Korisnik korisnik = KorisnikDao.getInstance().find(rs.getInt("korisnik_id"), con);
                VoznjaDodaci voznjaDodaci = VoznjaDodaciDao.getInstance().find(rs.getInt("voznja_dodaci_id"), con);
                VoznjaDetalji voznjaDetalji = VoznjaDetaljiDao.getInstance().find(rs.getInt("voznja_detalji_id"), con);
                voznja = new Voznja(rs.getInt("voznja_id"), korisnik, rs.getString("status"), voznjaDetalji, voznjaDodaci, vozac, rs.getInt("ocena"), rs.getString("komentar"), rs.getInt("cena_po_km"), rs.getInt("cena_start"));
                listVoznja.add(voznja);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return listVoznja;
    }

    public Voznja find(int voznjaId, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Voznja voznja = null;
        try {
            ps = con.prepareStatement("SELECT * FROM voznja where voznja_id=?");
            ps.setInt(1, voznjaId);

            rs = ps.executeQuery();
            if (rs.next()) {
                Vozac vozac = VozacDao.getInstance().find(rs.getInt("vozac_id"), con);
                Korisnik korisnik = KorisnikDao.getInstance().find(rs.getInt("korisnik_id"), con);
                VoznjaDetalji voznjaDetalji = VoznjaDetaljiDao.getInstance().find(rs.getInt("voznja_detalji_id"), con);
                VoznjaDodaci voznjaDodaci = VoznjaDodaciDao.getInstance().find(rs.getInt("voznja_id"), con);
                voznja = new Voznja(voznjaId, korisnik, rs.getString("status"), voznjaDetalji, voznjaDodaci, vozac, rs.getInt("ocena"), rs.getString("komentar"), rs.getInt("cena_po_km"),rs.getInt("cena_start"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return voznja;
    }

    public ArrayList<Voznja> findVoznja(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Voznja> listVoznja = new ArrayList<>();
        Voznja voznja = null;
        try {
            ps = con.prepareStatement("SELECT * FROM voznja where status=?");
            ps.setString(1, "cekanje");

            rs = ps.executeQuery();
            while (rs.next()) {
                //Vozac vozac= VozacDao.getInstance().find(rs.getInt("vozac_id"), con);         
                Korisnik korisnik = KorisnikDao.getInstance().find(rs.getInt("korisnik_id"), con);
                VoznjaDodaci voznjaDodaci = VoznjaDodaciDao.getInstance().find(rs.getInt("voznja_dodaci_id"), con);
                VoznjaDetalji voznjaDetalji = VoznjaDetaljiDao.getInstance().find(rs.getInt("voznja_detalji_id"), con);
                //         System.out.println(vozac.toString()+korisnik.toString()+voznjaDodaci.toString()+voznjaDetalji.toString());
                voznja = new Voznja(rs.getInt("voznja_id"), korisnik, rs.getString("status"), voznjaDetalji, voznjaDodaci, rs.getInt("ocena"), rs.getString("komentar"));
                listVoznja.add(voznja);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return listVoznja;
    }
   public int findVoznjaCena(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Voznja voznja = null;
        int[] niz= new int[2];
        int cena=-1;
        try {
            ps = con.prepareStatement("SELECT * FROM voznja where voznja_id=?");
            ps.setInt(1, 1);

            rs = ps.executeQuery();
            if (rs.next()) {
     //  niz[0]=rs.getInt("cena_po_km");
        //      niz[1]=rs.getInt("cena_start");
     cena=rs.getInt("cena_po_km");
System.out.println("cena: "+ cena);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return cena;
    }
    public int create(Voznja voznja, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int voznjaId = -1;
        Cena cena=new Cena();
        try {

            Integer fkVoznjaDetalji = null;
            if (voznja.getVoznjaDetalji() != null) {
                //insert address and receive the value of id
                fkVoznjaDetalji = VoznjaDetaljiDao.getInstance().create(voznja.getVoznjaDetalji(), con);
            }
            Integer fkVoznjaDodaci = null;
            if (voznja.getVoznjaDodaci() != null) {
                //insert address and receive the value of id
                System.out.println("Voznja dodaci nije null");
                fkVoznjaDodaci = VoznjaDodaciDao.getInstance().create(voznja.getVoznjaDodaci(), con);
            }

            Integer fkKorisnik = null;
            if (voznja.getKorisnik() != null) {
                //insert address and receive the value of id
                Korisnik korisnik2 = KorisnikDao.getInstance().find(voznja.getKorisnik().getEmail(), voznja.getKorisnik().getSifra(), con);
                if (korisnik2 == null) {
                    fkKorisnik = KorisnikDao.getInstance().create(voznja.getKorisnik(), con);
                } else {
                    fkKorisnik = korisnik2.getKorisnikId();
                }
            }

            ps = con.prepareStatement("INSERT INTO voznja(korisnik_id,voznja_dodaci_id,voznja_detalji_id, cena_po_km,cena_start) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, fkKorisnik);
            ps.setInt(2, fkVoznjaDodaci);
            ps.setInt(3, fkVoznjaDetalji);
            ps.setInt(4, cena.getCenaPoKm());
            ps.setInt(5, cena.getCenaStart());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            voznjaId = rs.getInt(1);
            String token = KorisnikDao.getInstance().findKorisnikToken(fkKorisnik, con);
            if (voznjaId != 0) {
                if (voznja.getStatus() == "otkazana") {
                    FCMSender sender = new FCMSender("otkazana", token);
                    sender.slanjeNotifikacije(0);
                } else {
                    FCMSender sender = new FCMSender("0", token);
                    sender.slanjeNotifikacije(0);
                }
               // System.out.println(token);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return voznjaId;
    }

    /*   public void update(Voznja voznja, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {

            ps = con.prepareStatement("UPDATE voznja SET korisnik_id=?,voznja_dodaci_id=?,voznja_detalji_id=?,vozac_id=? WHERE voznja_id=?");
            ps.setInt(1, voznja.getKorisnik().getKorisnikId());
            ps.setInt(2, voznja.getVoznjaDodaci().getVoznjaDodaciId());
            ps.setInt(3, voznja.getVoznjaDetalji().getVoznjaDetaljiId());
             ps.setInt(4, voznja.getVozac().getVozacId());
             ps.setInt(5,voznja.getVoznjaId());
            ps.executeUpdate();
              
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }   */

    public void updateVoznja(int voznjaId, int vozacId, int status, Connection con) throws SQLException {
        PreparedStatement ps = null;
        String stringStatus = "cekanje";
        Voznja voznja = null;

        try {
            // cekanje-0;prihvacena-1;aktivna-2,zavrsena=3;odbijena-4
            switch (status) {
                case 0:
                    stringStatus = "cekanje";

                    break;
                case 1:
                    stringStatus = "prihvacena";
                    break;
                case 2:
                    stringStatus = "aktivna";
                    break;
                case 3:
                    stringStatus = "zavrsena";
                    break;
                case 4:
                    stringStatus = "cekanje";
                    voznja = find(voznjaId, con);
                    delete(voznja, con);
                    voznja.setVozac(null);
                    voznja.setStatus("otkazana");
                    create(voznja, con);
                    break;
            }
            if (status != 4) {
                ps = con.prepareStatement("UPDATE voznja SET vozac_id=?, status=? WHERE voznja_id=?");
                ps.setInt(1, vozacId);
                ps.setString(2, stringStatus);
                ps.setInt(3, voznjaId);
                ps.executeUpdate();
                voznja = find(voznjaId, con);
                String token = voznja.getKorisnik().getToken();
                FCMSender sender = new FCMSender(stringStatus, token);
                if (stringStatus == "prihvacena") {
                    sender.slanjeNotifikacije(voznjaId);
                } else {
                    sender.slanjeNotifikacije(0);
                }

            }

        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void update(int voznjaId, int ocena, String komentar, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE voznja SET ocena=?,komentar=? WHERE voznja_id=?");
            ps.setInt(1, ocena);
            ps.setString(2, komentar);
            ps.setInt(3, voznjaId);
            ps.executeUpdate();

        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(Voznja voznja, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM voznja WHERE voznja_id=?");
            ps.setInt(1, voznja.getVoznjaId());

            ps.executeUpdate();
            if (voznja.getVoznjaDetalji() != null) {
                VoznjaDetaljiDao.getInstance().delete(voznja.getVoznjaDetalji(), con);
            }
            if (voznja.getVoznjaDodaci() != null) {
                VoznjaDodaciDao.getInstance().delete(voznja.getVoznjaDodaci(), con);
            }
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(Korisnik korisnik, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM voznja WHERE korisnik_id=?");
            ps.setInt(1, korisnik.getKorisnikId());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(Vozilo vozilo, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM voznja WHERE vozilo_id=?");
            ps.setInt(1, vozilo.getVoziloId());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(VoznjaDetalji voznjaDetalji, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM voznja WHERE voznja_detalji_id=?");
            ps.setInt(1, voznjaDetalji.getVoznjaDetaljiId());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    public void delete(VoznjaDodaci voznjaDodaci, Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM voznja WHERE voznja_dodaci_id=?");
            ps.setInt(1, voznjaDodaci.getVoznjaDodaciId());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
}
