package com.norisoft.sat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mx.gob.sat.cfd._4.Comprobante;
import mx.gob.sat.sitio_internet.cfd.catalogos.CTipoFactor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

class ComprobanteFiscalITest {
	
	static Properties prop = new Properties();
	
	@BeforeAll
	public static void setup() {
		try {
			prop.load(ComprobanteFiscalITest.class.getResourceAsStream("/facturacion.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getCertificadoCerFile() {
		return "/certificados/"+prop.getProperty("sat.certificado.cer");
	}
	private static String getCertificadoKeyFile() {
		return "/certificados/"+prop.getProperty("sat.certificado.key");
	}
    	
	@Test
	@DisplayName("Valida Certificados CSD")
	void valida_certificado() {
			 
		InputStream cer = ComprobanteFiscalITest.class.getResourceAsStream(getCertificadoCerFile());
		InputStream key = ComprobanteFiscalITest.class.getResourceAsStream(getCertificadoKeyFile());
		String clave = prop.getProperty("sat.certificado.clave");
		
		ComprobanteFiscal comprobante_fiscal = ComprobanteFiscalFactory.getComprobante();
		assertTrue(comprobante_fiscal.valida_certificado(cer,key,clave));
		
		
		comprobante_fiscal = ComprobanteFiscalFactory.getComprobante();
		 cer = ComprobanteFiscalITest.class.getResourceAsStream(getCertificadoCerFile());
		 key = ComprobanteFiscalITest.class.getResourceAsStream(getCertificadoKeyFile());
		 clave = prop.getProperty("sat.certificado.clave");
		assertFalse(comprobante_fiscal.valida_certificado(cer,key,new StringBuilder(clave).reverse().toString()));
	}
	
    @Test    
    @DisplayName("Timbrado")
    void prueba_de_timbrado() {
    	
		InputStream cer = ComprobanteFiscalITest.class.getResourceAsStream(getCertificadoCerFile());
		InputStream key = ComprobanteFiscalITest.class.getResourceAsStream(getCertificadoKeyFile());
		String clave = prop.getProperty("sat.certificado.clave");
		
		assertNotNull(cer);
		assertNotNull(key);
		
		Comprobante c =  ComprobantesDummyFactory.getComprobanteCompra();
		
		
		ComprobanteFiscal comprobante_fiscal = ComprobanteFiscalFactory.getComprobante(c);
		comprobante_fiscal.validar_xsd(true);		
		comprobante_fiscal.carga_certificado(cer,key,clave);
		comprobante_fiscal.genera_comprobante_timbrado();
		
		
		assertNotNull(comprobante_fiscal.getComprobante_timbrado_xml());
		assertNotNull(comprobante_fiscal.getComprobante_timbrado_pdf());

		assertNotNull(comprobante_fiscal.getComprobante_timbrado_xml().toFile().exists());
		assertNotNull(comprobante_fiscal.getComprobante_timbrado_pdf().toFile().exists());

        
    }
    
    
    @Test    
    @DisplayName("Timbrado Retencion")
    void prueba_de_timbrado_retencion() {
    	
		InputStream cer = ComprobanteFiscalITest.class.getResourceAsStream(getCertificadoCerFile());
		InputStream key = ComprobanteFiscalITest.class.getResourceAsStream(getCertificadoKeyFile());
		String clave = prop.getProperty("sat.certificado.clave");
		
		assertNotNull(cer);
		assertNotNull(key);
		
		Comprobante c =  ComprobantesDummyFactory.getComprobanteRetencion();
		
		
		ComprobanteFiscal comprobante_fiscal = ComprobanteFiscalFactory.getComprobante(c);
		comprobante_fiscal.validar_xsd(true);		
		comprobante_fiscal.carga_certificado(cer,key,clave);
		comprobante_fiscal.genera_comprobante_timbrado();
		
		
		assertNotNull(comprobante_fiscal.getComprobante_timbrado_xml());
		assertNotNull(comprobante_fiscal.getComprobante_timbrado_pdf());

		assertNotNull(comprobante_fiscal.getComprobante_timbrado_xml().toFile().exists());
		assertNotNull(comprobante_fiscal.getComprobante_timbrado_pdf().toFile().exists());

        
    }
    @Disabled
    void lectura_de_archivo_xml() throws URISyntaxException {
    	
    	
    	Path path =  Paths.get(getClass().getClassLoader().getResource("xml/cf85cd23-7ce7-4cb3-9dc7-96feb633cd78.xml").toURI());
    	
		ComprobanteFiscal comprobante_fiscal = ComprobanteFiscalFactory.getComprobante();
		comprobante_fiscal.setLLER_XML_EXTERNO(true);;
		comprobante_fiscal.leer_archivo_xml(path);
		
		assertEquals(0,comprobante_fiscal.getComprobante().getTotal().compareTo(new  BigDecimal("1589.81")));
		
    	path =  Paths.get(getClass().getClassLoader().getResource("xml/0b9c201b-cff5-48bd-8acb-fd855a2ff5b7.xml").toURI());
    	
		comprobante_fiscal = ComprobanteFiscalFactory.getComprobante();
		comprobante_fiscal.setLLER_XML_EXTERNO(true);;
		comprobante_fiscal.leer_archivo_xml(path);
		
		
		
		assertEquals("0b9c201b-cff5-48bd-8acb-fd855a2ff5b7",ComprobanteHelper.obtenerTimbreFiscalDigital(comprobante_fiscal.getComprobante()).getUUID().toLowerCase());
    }
    
  
        



}