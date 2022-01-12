/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.bonusRMS.data;

import java.io.Serializable;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class Cena implements Serializable{
      private int cenaPoKm=30;
    private int cenaStart=100;

    public int getCenaPoKm() {
        return cenaPoKm;
    }

    public void setCenaPoKm(int cenaPoKm) {
        this.cenaPoKm = cenaPoKm;
    }

    public int getCenaStart() {
        return cenaStart;
    }

    public void setCenaStart(int cenaStart) {
        this.cenaStart = cenaStart;
    }

    public Cena() {
    }

  
    
}
