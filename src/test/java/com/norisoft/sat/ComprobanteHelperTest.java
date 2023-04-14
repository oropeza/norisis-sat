package com.norisoft.sat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mx.gob.sat.cfd._4.Comprobante;

class ComprobanteHelperTest {

    

    @Test
    @DisplayName("Compra Amazon")
    void verifica_calculo_totales_00() {
    	Comprobante comprobante =  ComprobantesDummyFactory.getComprobante();
    	  
        
        ComprobanteHelper.creaConcepto(comprobante, 
        		"53131600", "H87","pieza", "Duracell Copper & Black Pila Alcalina 9V, 1 pieza",
        		new BigDecimal("107.76"), new BigDecimal("1"), "002", new BigDecimal("0.160000"));
        
     
        ComprobanteHelper.creaConcepto(comprobante, 
        		"40142000", "H87","pieza", "Nite Ize S-Biner 3Pk Stainless",
        		new BigDecimal("130.79"), new BigDecimal("1"), "002", new BigDecimal("0.160000"));
        
        
        ComprobanteHelper.calculaTotales(comprobante);
        
        assertEquals(0,comprobante.getSubTotal().compareTo(new  BigDecimal("238.55")));
        assertEquals(0,comprobante.getTotal().compareTo(new  BigDecimal("276.72")));
        assertEquals(0,comprobante.getImpuestos().getTotalImpuestosTrasladados().compareTo(new  BigDecimal("38.17")));
        
    }
    
    @Test
    @DisplayName("Compra Farmacia")
    void verifica_calculo_totales_01() {
    	Comprobante comprobante =  ComprobantesDummyFactory.getComprobante();
    	  
        
        ComprobanteHelper.creaConcepto(comprobante, 
        		"51171900", "H87","pza", "PANTOPRAZOL FARMACOM 1 PZA",new BigDecimal("37.52"), new BigDecimal("1"));        
     
        ComprobanteHelper.creaConcepto(comprobante, 
        		"01010101", "H87","pieza", "OPTIFIBRE NESTLE 1 PZA",
        		new BigDecimal("206.896552"), new BigDecimal("1"), "002", new BigDecimal("0.160000"));
        

        ComprobanteHelper.creaConcepto(comprobante, 
        		"50161815", "H87","pieza", "GOMA TRIDENT 16.32 GRS",
        		new BigDecimal("8.189655"), new BigDecimal("1"), "002", new BigDecimal("0.160000"));

        ComprobanteHelper.creaConcepto(comprobante, 
        		"51171500", "H87","pieza", "PEPTO PROCTER 236 ML", new BigDecimal("132.50"), new BigDecimal("1"));
        
        ComprobanteHelper.creaConcepto(comprobante, 
        		"51171500", "H87","pieza", "RIOPAN TAKEDA PHARMACEUTICALS",new BigDecimal("205.00"), new BigDecimal("1"));        
        
        ComprobanteHelper.calculaTotales(comprobante);
        
        assertEquals(0,comprobante.getSubTotal().compareTo(new  BigDecimal("590.11")));
        assertEquals(0,comprobante.getTotal().compareTo(new  BigDecimal("624.52")));
        assertEquals(0,comprobante.getImpuestos().getTotalImpuestosTrasladados().compareTo(new  BigDecimal("34.41")));
        
    }
        



}