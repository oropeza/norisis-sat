package com.norisoft.sat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mx.gob.sat.cfd._4.Comprobante;
import mx.gob.sat.sitio_internet.cfd.catalogos.CTipoFactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

class ComprobanteFiscalTest {

    

    @Test
    @DisplayName("El metodo genera_comprobante_timbrado y genera_xml_sin_timbrar requieren tener el comprobante cargado")
    void verifica_calculo_totales_00() {
    	
    	
    	ComprobanteFiscal comprobante = new ComprobanteFiscal();
            
    	 assertThrows(IllegalArgumentException.class, () -> {
    		 comprobante.genera_xml_sin_timbrar("");
    		  });
    	
	   	 assertThrows(IllegalArgumentException.class, () -> {
			 comprobante.genera_comprobante_timbrado();
			  });    	
        
    }
    
  
        



}