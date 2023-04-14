package com.norisoft.sat;


import mx.gob.sat.cfd._3.Comprobante;
import mx.gob.sat.cfd._3.Comprobante.Addenda;
import mx.gob.sat.sitio_internet.cfd.catalogos.CTipoFactor;



import java.io.*;
import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


/**
 * Hello world!
 *
 */
public class App 
{
	
	
	
	

	
    public static void main( String[] args ){
    	
    	 
    	Comprobante comprobante =  ComprobantesDummyFactory.generaComplementoPago();
    	
		InputStream cer = App.class.getResourceAsStream("/certificados/.cer");
		InputStream key = App.class.getResourceAsStream("/certificados/.key");
		String clave = "";

		
		
		ComprobanteFiscal comprobante_fiscal = ComprobanteFiscalFactory.getComprobante(comprobante);
		comprobante_fiscal.validar_xsd(true);		
		comprobante_fiscal.carga_certificado(cer,key,clave);
		comprobante_fiscal.genera_xml_sin_timbrar("dev/complemento_pago.xml");
		    	
    	 
   
    	
    	
    		
    
    	}
    

}


