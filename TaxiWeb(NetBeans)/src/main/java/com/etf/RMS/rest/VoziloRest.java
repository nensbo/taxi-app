/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.rest;

import com.etf.RMS.exception.TaxiException;
import com.etf.RMS.service.VoziloService;
import com.etf.bonusRMS.data.Vozilo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author HP EliteBook 840 G1
 */
@Path("vozilo")
public class VoziloRest {

    private final VoziloService voziloService = VoziloService.getInstance();

    @GET
    @Path("/{voziloId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vozilo getVozilo(@PathParam("voziloId") int voziloId) throws TaxiException {
        return voziloService.findVozilo(voziloId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVozilo(Vozilo vozilo) throws TaxiException {
        voziloService.addNewVozilo(vozilo);
        return Response.ok().build();
    }

    @DELETE

    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVozilo(Vozilo vozilo) throws TaxiException {
        voziloService.deleteVozilo(vozilo);
        return Response.ok().build();
    }
}
