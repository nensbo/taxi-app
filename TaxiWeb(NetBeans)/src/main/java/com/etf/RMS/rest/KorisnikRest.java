/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.rest;

import com.etf.RMS.exception.TaxiException;
import com.etf.RMS.service.KorisnikService;
import com.etf.bonusRMS.data.Korisnik;
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
@Path("korisnik")
public class KorisnikRest {

    private final KorisnikService korisnikService = KorisnikService.getInstance();

    @GET
    @Path("/{email}/{sifra}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik getKorisnik(@PathParam("email") String email, @PathParam("sifra") String sifra) throws TaxiException {
        return korisnikService.findKorisnik(email, sifra);
    }
    
   

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Integer addKorisnik(Korisnik korisnik) throws TaxiException {
        return korisnikService.addNewKorisnik(korisnik);

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateKorisnik(Korisnik korisnik) throws TaxiException {
        korisnikService.updateKorisnik(korisnik);
        return Response.ok().build();

    }

    @DELETE
    @Path("/{email}/{sifra}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKorisnik(@PathParam("email") String email, @PathParam("sifra") String sifra) throws TaxiException {
        korisnikService.deleteKorisnik(email, sifra);
        return Response.ok().build();
    }
}
