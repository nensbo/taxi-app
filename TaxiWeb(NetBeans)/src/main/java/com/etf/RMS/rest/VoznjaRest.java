/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.rest;

import com.etf.RMS.exception.TaxiException;
import com.etf.RMS.service.VoznjaService;
import com.etf.bonusRMS.data.Cena;
import com.etf.bonusRMS.data.Vozac;
import com.etf.bonusRMS.data.Voznja;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author HP EliteBook 840 G1
 */
@Path("voznja")
public class VoznjaRest {

    private final VoznjaService voznjaService = VoznjaService.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)

    public Integer makeVoznja(Voznja voznja) throws TaxiException {
        return voznjaService.makeVoznja(voznja.getKorisnik(), voznja.getStatus(), voznja.getVoznjaDodaci(), voznja.getVoznjaDetalji());

    }

    @GET
    @Path("/{id}/{tip}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Voznja> getVoznja(@PathParam("id") int id, @PathParam("tip") String tip) throws TaxiException {
        return voznjaService.findVoznja(id, tip);
    }
    
     @GET
      @Path("/{zahtev}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cena getVoznjaCena(@PathParam("zahtev") String zahtev) throws TaxiException {
       return voznjaService.findVoznjaCena();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Voznja> getVoznja() throws TaxiException {
        return voznjaService.findVoznja();
    }
    
   
    @PUT
    @Path("/{voznja_id}/{ocena}/{komentar}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateVoznja(@PathParam("voznja_id") int voznjaId, @PathParam("ocena") int ocena, @PathParam("komentar") String komentar) throws TaxiException {
        voznjaService.updateVoznja(voznjaId, ocena, komentar);
        return Response.ok().build();

    }

    @PUT
    @Path("/{voznja_id}/{vozac_id}/{status}/{potvrda}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateVoznja(@PathParam("voznja_id") int voznjaId, @PathParam("vozac_id") int vozacId, @PathParam("status") int status, @PathParam("potvrda") int potvrda) throws TaxiException {
        voznjaService.updateVoznja(voznjaId, vozacId, status);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{voznjaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVozac(@PathParam("voznja_id") int voznjaId) throws TaxiException {
        voznjaService.deleteVoznja(voznjaId);
        return Response.ok().build();
    }
}
