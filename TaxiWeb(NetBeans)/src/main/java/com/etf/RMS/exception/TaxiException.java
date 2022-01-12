/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.exception;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class TaxiException extends Exception {
   public TaxiException(String message) {
        super(message);
    } 
    public TaxiException(String message, Throwable cause) {
        super(message, cause);
    }
}


