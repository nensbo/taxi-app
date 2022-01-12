package com.example.taxi.api;




import com.example.taxi.data.Cena;
import com.example.taxi.data.Korisnik;
import com.example.taxi.data.Vozac;
import com.example.taxi.data.Vozilo;
import com.example.taxi.data.Voznja;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("korisnik")
    Call<Integer> addKorisnik(@Body Korisnik korisnik);

    @GET("korisnik/{email}/{sifra}")
    Call<Korisnik> getKorisnik(@Path("email") String email, @Path("sifra") String sifra);

    @PUT("korisnik")
    Call<Void> updateKorisnik(@Body Korisnik korisnik);

    @GET("vozac/{email}/{sifra}")
    Call<Vozac> getVozac(@Path("email") String email, @Path("sifra") String sifra);

    @POST("vozac")
    Call<Integer> addVozac(@Body Vozac vozac);
    @PUT("vozac")
    Call<Void> updateVozac(@Body Vozac vozac);

    @GET("vozilo")
    Call<Vozilo> getVozilo(@Path("id") int id);
    @POST("voznja")
    Call<Integer> makeVoznja(@Body Voznja voznja);

    @GET("voznja/{id}/{tip}")
    Call<ArrayList<Voznja>> getVoznja(@Path("id") int id,@Path("tip") String tip);
    @GET("voznja")
    Call<ArrayList<Voznja>> getVoznja();

    @GET("voznja/{zahtev}")
    Call<Cena> getVoznjaCena(@Path("zahtev") String zahtev);

    @PUT("voznja/{voznja_id}/{vozac_id}/{status}/{potvrda}")
    Call<Void> updateVoznjaStatus(@Path("voznja_id") int voznjaId, @Path("vozac_id") int vozacId, @Path("status") int status,@Path("potvrda") int potvrda);

    @PUT("voznja/{voznja_id}/{ocena}/{komentar}")
    Call<Void> updateVoznja(@Path("voznja_id") int voznjaId,@Path("ocena") int ocena,@Path("komentar") String komentar);

}