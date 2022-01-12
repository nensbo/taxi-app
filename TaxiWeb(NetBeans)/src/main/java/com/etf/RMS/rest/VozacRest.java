/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.rest;

import com.etf.RMS.exception.TaxiException;
import com.etf.RMS.service.VozacService;
import com.etf.bonusRMS.data.Korisnik;
import com.etf.bonusRMS.data.Vozac;
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
@Path("vozac")
public class VozacRest {

    private final VozacService vozacService = VozacService.getInstance();

    @GET
    @Path("/{email}/{sifra}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vozac getVozac(@PathParam("email") String email, @PathParam("sifra") String sifra) throws TaxiException {
        return vozacService.findVozac(email, sifra);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)

    public Integer addVozac(Vozac vozac) throws TaxiException {
        return vozacService.addNewVozac(vozac);

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateVozac(Vozac vozac) throws TaxiException {
        vozacService.updateVozac(vozac);
        return Response.ok().build();

    }

    @DELETE
    @Path("/{email}/{sifra}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVozac(@PathParam("email") String email, @PathParam("sifra") String sifra) throws TaxiException {
        vozacService.deleteVozac(email, sifra);
        return Response.ok().build();
    }
}
