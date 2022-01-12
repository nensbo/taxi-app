package com.example.taxi.data;

import java.io.Serializable;

public class Vozilo implements Serializable{
    private int voziloId;
    private String modelITipVozila;
    private String registarskiBroj;
    private int ekoloskoVozilo;
    private int sedisteZaBebe;
    private int brojSedista;

    public Vozilo(){}

    public Vozilo(String modelITipVozila, String registarskiBroj, int ekoloskoVozilo, int sedisteZaBebe, int brojSedista) {
        this.modelITipVozila = modelITipVozila;
        this.registarskiBroj = registarskiBroj;
        this.ekoloskoVozilo = ekoloskoVozilo;
        this.sedisteZaBebe = sedisteZaBebe;
        this.brojSedista = brojSedista;
    }

    public Vozilo(int voziloId, String modelITipVozila, String registarskiBroj, int ekoloskoVozilo, int sedisteZaBebe, int brojSedista) {
        this.voziloId = voziloId;
        this.modelITipVozila = modelITipVozila;
        this.registarskiBroj = registarskiBroj;
        this.ekoloskoVozilo = ekoloskoVozilo;
        this.sedisteZaBebe = sedisteZaBebe;
        this.brojSedista = brojSedista;
    }

    public int getVoziloId() {
        return voziloId;
    }

    public void setVoziloId(int voziloId) {
        this.voziloId = voziloId;
    }

    public String getModelITipVozila() {
        return modelITipVozila;
    }

    public void setModelITipVozila(String modelITipVozila) {
        this.modelITipVozila = modelITipVozila;
    }

    public String getRegistarskiBroj() {
        return registarskiBroj;
    }

    public void setRegistarskiBroj(String registarskiBroj) {
        this.registarskiBroj = registarskiBroj;
    }

    public int getEkoloskoVozilo() {
        return ekoloskoVozilo;
    }

    public void setEkoloskoVozilo(int ekoloskoVozilo) {
        this.ekoloskoVozilo = ekoloskoVozilo;
    }

    public int getSedisteZaBebe() {
        return sedisteZaBebe;
    }

    public void setSedisteZaBebe(int sedisteZaBebe) {
        this.sedisteZaBebe = sedisteZaBebe;
    }

    public int getBrojSedista() {
        return brojSedista;
    }

    public void setBrojSedista(int brojSedista) {
        this.brojSedista = brojSedista;
    }
}
