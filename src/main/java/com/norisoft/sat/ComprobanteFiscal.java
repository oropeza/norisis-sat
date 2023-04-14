package com.norisoft.sat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.ssl.PKCS8Key;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.lowagie.text.DocumentException;
import com.norisoft.sat.constantes.FormaPago;
import com.norisoft.sat.constantes.MetodoPago;
import com.norisoft.sat.constantes.Moneda;
import com.norisoft.sat.constantes.RegimenFiscal;
import com.norisoft.sat.constantes.TipoComprobante;
import com.norisoft.sat.constantes.UsoCFDI;
import com.norisoft.sat.view.ConceptoView;
import com.norisoft.sat.view.ImpuestoView;
import com.norisoft.sat.view.TimbreFiscalView;
import com.sefactura.pac.client.RespuestaTimbrado;
import com.sefactura.pac.client.Sefactura;

import mx.gob.sat.cfd._4.Comprobante;

public class ComprobanteFiscal {
	
	 private static final Logger logger = LogManager.getLogger(ComprobanteFiscal.class);
	  
	private String UUID;
	private Comprobante comprobante;
	private X509Certificate certificado;
	private PrivateKey private_key;
	private Path comprobante_timbrado_xml;
	private Path comprobante_timbrado_pdf;	
	private Path comprobante_timbrado_qr;
	
	private boolean BANDER_VALIDAR_XSD = false;
	private boolean LLER_XML_EXTERNO = false;
	private boolean GUARDAR_EN_DISCO = true;
	private boolean LEER_CFDI_DE_MEMORIA = false;
	
	private boolean conComplementoPago = false; 
	
	private String SEFACTURA_URL = null;
	private String SEFACTURA_USUARIO = null;
	private String SEFACTURA_CLAVE = null;
	
	private String DIRECTORIO_DOCUMENTOS = "dev";
	
	private byte CFDITimbradoQR[];
	private byte CFDITimbradoPDF[];
	private String CFDITimbradoXML;
	
	private String customTemplate =null;

	private Map informacion_pdf;
	
	private String mensajeError = null;
	
	
	
	public boolean isConComplementoPago() {
		return conComplementoPago;
	}

	public void setConComplementoPago(boolean conComplementoPago) {
		this.conComplementoPago = conComplementoPago;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensaje_error) {
		this.mensajeError = mensaje_error;
	}

	public boolean isLEER_CFDI_DE_MEMORIA() {
		return LEER_CFDI_DE_MEMORIA;
	}

	public void setLEER_CFDI_DE_MEMORIA(boolean lEER_CFDI_DE_MEMORIA) {
		LEER_CFDI_DE_MEMORIA = lEER_CFDI_DE_MEMORIA;
	}


	
	
	
	

	
	public Map getInformacion_pdf() {
		return informacion_pdf;
	}

	public void setInformacion_pdf(Map informacion_pdf) {
		this.informacion_pdf = informacion_pdf;
	}

	public byte[] getCFDITimbradoPDF() {
		return CFDITimbradoPDF;
	}

	public void setCFDITimbradoPDF(byte[] cFDITimbradoPDF) {
		CFDITimbradoPDF = cFDITimbradoPDF;
	}

	public boolean isGUARDAR_EN_DISCO() {
		return GUARDAR_EN_DISCO;
	}

	public void setGUARDAR_EN_DISCO(boolean gUARDAR_EN_DISCO) {
		GUARDAR_EN_DISCO = gUARDAR_EN_DISCO;
	}

	public byte[] getCFDITimbradoQR() {
		return CFDITimbradoQR;
	}

	public void setCFDITimbradoQR(byte[] cFDITimbradoQR) {
		CFDITimbradoQR = cFDITimbradoQR;
	}

	public String getCFDITimbradoXML() {
		return CFDITimbradoXML;
	}

	public void setCFDITimbradoXML(String cFDITimbradoXML) {
		CFDITimbradoXML = cFDITimbradoXML;
	}

	public boolean isLLER_XML_EXTERNO() {
		return LLER_XML_EXTERNO;
	}

	public void setLLER_XML_EXTERNO(boolean lLER_XML_EXTERNO) {
		LLER_XML_EXTERNO = lLER_XML_EXTERNO;
	}

	public void validar_xsd(boolean bANDER_VALIDAR_XSD) {
		BANDER_VALIDAR_XSD = bANDER_VALIDAR_XSD;
	}

	public Path getComprobante_timbrado_qr() {
		return comprobante_timbrado_qr;
	}

	public void setComprobante_timbrado_qr(Path comprobante_timbrado_qr) {
		this.comprobante_timbrado_qr = comprobante_timbrado_qr;
	}

	public Path getComprobante_timbrado_xml() {
		return comprobante_timbrado_xml;
	}

	public void setComprobante_timbrado_xml(Path comprobante_timbrado_xml) {
		this.comprobante_timbrado_xml = comprobante_timbrado_xml;
	}

	public Path getComprobante_timbrado_pdf() {
		return comprobante_timbrado_pdf;
	}

	public void setComprobante_timbrado_pdf(Path comprobante_timbrado_pdf) {
		this.comprobante_timbrado_pdf = comprobante_timbrado_pdf;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}
	
	
	public void loadProperties() {
		 Properties prop = new Properties();
				
			try {
				prop.load(ComprobanteFiscal.class.getResourceAsStream("/facturacion.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			 this.SEFACTURA_URL = prop.getProperty("sefactura.url");
			 this.SEFACTURA_USUARIO = prop.getProperty("sefactura.usuario");;
			 this.SEFACTURA_CLAVE =prop.getProperty("sefactura.clave");;
					
	}
	
	public ComprobanteFiscal(Comprobante comprobante) {
		loadProperties();
		this.comprobante = comprobante;
	}
	public ComprobanteFiscal() {
		loadProperties();
	}	

	
	public ComprobanteFiscal(Path xml_timbrado) {
		loadProperties();
		this.comprobante_timbrado_xml = xml_timbrado;
		
		leer_archivo_xml();
	}


	/**
	 * Carga los datos del certificado y valida que se encuentren correctos.
	 * 
	 * @param cer
	 * @param private_key
	 * @param clave
	 * @return
	 */
	public boolean carga_certificado(InputStream cer, InputStream key, String clave) {
		
		try {
			CertificateFactory factory2 = CertificateFactory.getInstance("X.509");
			certificado = (X509Certificate) factory2.generateCertificate(cer);        	
			private_key = ComprobanteFiscal.loadPKCS8PrivateKey(key,clave);        			
			certificado.checkValidity();
			logger.info("Certificado cargado correctamente "+certificado.getSerialNumber());
			return true;
		} catch (CertificateException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return false;
	}
	
	/**
	 * Valida El certificado
	 * 
	 * @param cer
	 * @param private_key
	 * @param clave
	 * @return
	 */
	public boolean valida_certificado(InputStream cer, InputStream key, String clave) {
		
		try {
			CertificateFactory factory2 = CertificateFactory.getInstance("X.509");
			X509Certificate certificado = (X509Certificate) factory2.generateCertificate(cer);        	
			logger.info(certificado.getSerialNumber());
			PrivateKey private_key = ComprobanteFiscal.loadPKCS8PrivateKey(key,clave);        			
			certificado.checkValidity();				
			return true;
		} catch (CertificateException e) {
		} catch (Exception e) {
		}
		return false;
	}	
	
	
	/**
	 * Sella el comprovante mediante la cadena original y carga la información del certificado. Regresa la 
	 * 
	 * @param comprobante
	 * @throws CertificateEncodingException
	 */
	private boolean genera_xml_timbrable(Writer writer )  {
		
		try {
			
			logger.info("Generando XML para timbrar");
		
			if(certificado==null)throw new IllegalStateException("No esta cargada la información del certificado");
			if(comprobante==null)throw new IllegalStateException("No esta cargada la información del comprobante");
			if(private_key==null)throw new IllegalStateException("No esta cargada la información de la llave privada");
			
			//Realiza la Cerrtificación del comprobante
			
			comprobante.setCertificado(Base64.getEncoder().encodeToString(certificado.getEncoded()));				   
	        comprobante.setNoCertificado( new String(certificado.getSerialNumber().toByteArray()));
	        
	        //Realiza el sellado del comprovante, es decir due genera la cadena original, la codifica y la integra al comprobante. 
	        
	        JAXBContext jaxbContext_comprobante = JAXBContext.newInstance(Comprobante.class);
			JAXBSource source = new JAXBSource(jaxbContext_comprobante, comprobante);
			TransformerFactory factory =TransformerFactory.newInstance();
			factory.setURIResolver(new ClasspathResourceURIResolver());
			
					
	        
	        Source  xslt_cadena_original= new StreamSource(ComprobanteFiscal.class.getResourceAsStream("/cadenaoriginal_4_0.xslt"));
	        Transformer transformer_cadena_original = factory.newTransformer(xslt_cadena_original);
	        		
			StringWriter outWriter = new StringWriter();					            
			transformer_cadena_original.transform(source, new StreamResult(outWriter));
			
			
	        //Añade la cadena sellada                	
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initSign(private_key);	
	        sig.update(outWriter.toString().getBytes());						       
		    comprobante.setSello(Base64.getEncoder().encodeToString(sig.sign()));
			
		    
		    
		    String schema_pagos = "";
		    
		    if(isConComplementoPago()) {
		    	schema_pagos = " http://www.sat.gob.mx/Pagos http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos20.xsd";
		    }
		    				    		
		    //Genera el XML                                       
    		Marshaller jaxbMarshaller = jaxbContext_comprobante.createMarshaller();
    		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);    		
    		jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd"+schema_pagos);
    		
    		if(BANDER_VALIDAR_XSD) {
    			SchemaFactory factorySchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    			Schema schema =  factorySchema.newSchema(new Source[] {    					
    					new StreamSource(ComprobanteFiscal.class.getResource("/cfdv40.xsd").toExternalForm()),
    					new StreamSource(ComprobanteFiscal.class.getResource("/Pagos20.xsd").toExternalForm())    					    					  
    					});    			
    			jaxbMarshaller.setSchema(schema);
    		}
    		
    		
    		jaxbMarshaller.marshal(comprobante, System.out);    		
    		//jaxbMarshaller.marshal(comprobante, new File("file.xml"));        		       	
        	jaxbMarshaller.marshal(comprobante, writer);		
        	logger.info("XML Listo para timbrar");
		    return true;
		    
		} catch (CertificateEncodingException e) {
			logger.error(e,e);
		} catch (JAXBException e) {
			logger.error(e,e);
		} catch (TransformerConfigurationException e) {
			logger.error(e,e);
		} catch (TransformerException e) {
			logger.error(e,e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e,e);
		} catch (InvalidKeyException e) {
			logger.error(e,e);
		} catch (SignatureException e) {
			logger.error(e,e);
		} catch (SAXException e) {
			logger.error(e,e);	

		}
		return false;
        
	}
	
	
	 private static PrivateKey loadPKCS8PrivateKey(InputStream in, String passwd) throws Exception {
		    byte[] decrypted = new PKCS8Key(in, passwd.toCharArray()).getDecryptedBytes();
		    PKCS8EncodedKeySpec keysp = new PKCS8EncodedKeySpec(decrypted);
		    KeyFactory kf = KeyFactory.getInstance("RSA");
		    return kf.generatePrivate(keysp);
		  }

	  
	
	 public void configura(String url, String usuario, String clave) {
		 this.SEFACTURA_URL = url;
		 this.SEFACTURA_USUARIO = usuario;
		 this.SEFACTURA_CLAVE =clave;
	 }
	 
	private boolean timbra_comprobante(String xml) {
		
		comprobante_timbrado_xml = null;
		Instant start = Instant.now();
 			
		
		if(xml==null)throw new IllegalArgumentException("No se cargo XML para timbrar");
		//TODO: Validar url usuario clave
		
		logger.info("Timbrando XML... {} - {} ",SEFACTURA_URL,SEFACTURA_USUARIO);
		
    	Sefactura sf = new Sefactura(SEFACTURA_URL,SEFACTURA_USUARIO,SEFACTURA_CLAVE);
    	RespuestaTimbrado rt;
		try {
			rt = sf.timbrado(xml);
		} catch (Exception e) {
			logger.error(e,e);
			mensajeError = " Error: "+SEFACTURA_URL+":"+SEFACTURA_USUARIO + " - " + e.getMessage();
			
			return false;
		}
		
		if (rt.getResultado() != null && rt.getResultado().length() > 0) {
			mensajeError = rt.getResultado()  ;
			logger.error("Error al timbrar: "+rt.getResultado());			
            return false; // Capturar error
        }
    		 
		
    	//Genera QR
		try {
	    	
	    	InputSource inputSource = new InputSource( new StringReader( rt.getXml() ) );
	    	
	    	
			XPath xpath = XPathFactory.newInstance().newXPath();			
			this.UUID = xpath.evaluate ("//*[1]/@UUID",inputSource);			
			logger.info("Documento timbrado correctamente con UUID {}",this.UUID);
	   	
			this.CFDITimbradoQR = Base64.getDecoder().decode(rt.getCadenaCodigo().getBytes(StandardCharsets.UTF_8));
	    	this.CFDITimbradoXML = rt.getXml();
	    	
	    	if(GUARDAR_EN_DISCO) {
		    	comprobante_timbrado_qr = Paths.get(DIRECTORIO_DOCUMENTOS,this.UUID+".png");	    	
		    	Files.write (comprobante_timbrado_qr, this.CFDITimbradoQR);	    	
		    	logger.info("QR guardado en {} ",comprobante_timbrado_qr);
		    	
		    	comprobante_timbrado_xml = Paths.get(DIRECTORIO_DOCUMENTOS,this.UUID+".xml");
		    	Files.write(comprobante_timbrado_xml,rt.getXml().getBytes());
		    	logger.info("XML guardado en {} ",comprobante_timbrado_xml);
	    	}else {
	    		logger.info("QR y XML guardados en memoria ");
	    	}
	    	

	    	
	    	
	    	logger.debug("Tiempo Proceso Timbrado: {}s",Duration.between(start, Instant.now()).getSeconds());
	    	
	    	
		}catch (IOException e) {
			logger.error(e, e);
		} catch (XPathExpressionException e) {
			logger.error(e, e);
		}
		

    	return true;
    	
		
	}
	
	public String cancela_comprobante(String uuid,InputStream cer, InputStream key, String passwd) {
		
		  
		//if(xml==null)throw new IllegalArgumentException("No se cargo XML para timbrar");
		//TODO: Validar url usuario clave
		
		logger.info("Cancelando XML... {} - {} ",SEFACTURA_URL,SEFACTURA_USUARIO);
		
    	Sefactura sf = new Sefactura(SEFACTURA_URL,SEFACTURA_USUARIO,SEFACTURA_CLAVE);
    	
    	
    	RespuestaTimbrado rt;
		try {
			return sf.cancela(uuid, cer.readAllBytes(), key.readAllBytes(), passwd);
		
		} catch (Exception e) {
			logger.error(e,e);
			mensajeError = " Error: "+SEFACTURA_URL+":"+SEFACTURA_USUARIO + " - " + e.getMessage();
			
			return null;
		}
		 
		/*
		if (rt.getResultado() != null && rt.getResultado().length() > 0) {
			mensajeError = rt.getResultado()  ;
			logger.error("Error al timbrar: "+rt.getResultado());			
            return false; // Capturar error
        }*/
    		 
		
    	
    	
		
	}
	

	public void genera_xml_sin_timbrar(String file) {
		if(this.comprobante==null)throw new IllegalArgumentException("No se cargo comprobante");
		if(file==null)throw new IllegalArgumentException("No se indico el archivo");
				
		 
		try {
			FileWriter xml_writer = new FileWriter(file);
			genera_xml_timbrable(xml_writer);		
		} catch (IOException e) {
			logger.error(e, e);
		}    		
		
		
		
		
		

	}
	
	
	public boolean genera_comprobante_timbrado() {
		if(this.comprobante==null)throw new IllegalArgumentException("No se cargo comprobante");
		
		Instant start = Instant.now();
		
		StringWriter xml_writer = new StringWriter();    		
		
		if(!genera_xml_timbrable(xml_writer))return false; 					
		if(!timbra_comprobante(xml_writer.toString()))return false;		
		if(!leer_archivo_xml())return false;
		
		try {
			genera_pdf();
		}catch (Exception e) {
			logger.error(e,e);
		}
		
		logger.info("Tiempo ejeucción timbrado completo: {}s",Duration.between(start, Instant.now()).getSeconds());
		
		return true;
		
		

	}
	
	public boolean leer_archivo_xml(Path xml_timbrado ) {
		
		this.comprobante_timbrado_xml = xml_timbrado;
		return leer_archivo_xml();
	}
	
	public boolean leer_archivo_xml(String xml) {
		this.CFDITimbradoXML = xml;
		return leer_archivo_xml();
	}

	private boolean leer_archivo_xml() {
		this.UUID=null;
		
		if(!LEER_CFDI_DE_MEMORIA && !comprobante_timbrado_xml.toFile().exists())throw new IllegalArgumentException("No se cargo archivo XML");
		
		try {
			
			InputSource inputSource_xpath = null;
			InputSource inputSource_unmarshall = null; 
			if(!LEER_CFDI_DE_MEMORIA) {
				logger.info("Cargando XML "+comprobante_timbrado_xml.toFile());
				inputSource_xpath = new InputSource( new FileReader( comprobante_timbrado_xml.toFile() ) );
				inputSource_unmarshall = new InputSource( new FileReader( comprobante_timbrado_xml.toFile() ) );
			}else {
				inputSource_xpath = new InputSource(new StringReader(CFDITimbradoXML));
				inputSource_unmarshall = new InputSource(new StringReader(CFDITimbradoXML));
			}

			
			
			
			JAXBContext jaxbContext_comprobante = JAXBContext.newInstance(Comprobante.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext_comprobante.createUnmarshaller();					 
			comprobante = (Comprobante) jaxbUnmarshaller.unmarshal( inputSource_unmarshall);//comprobante_timbrado_xml.toFile() 
			
			
			
			XPath xpath = XPathFactory.newInstance().newXPath();			
			
			if(!LLER_XML_EXTERNO) {
			this.UUID = xpath.evaluate ("//*[1]/@UUID",inputSource_xpath);
			logger.info("UUID Cargado "+this.UUID);
			}

			
			return true;
		} catch (JAXBException e) {
			logger.error(e, e);
		} catch (XPathExpressionException e) {
			logger.error(e, e);
		} catch (FileNotFoundException e) {
			logger.error(e, e);
		} 
		return false;
		
	}

	
	public boolean genera_pdf(Map mapa) {
		this.informacion_pdf = mapa;
		return genera_pdf();
		
	}
	
	public boolean genera_pdf(String mustache,Map mapa) {
		this.informacion_pdf = mapa;
		this.customTemplate = mustache;
		
		
		return genera_pdf();
		
	}

	public boolean genera_pdf() {
		comprobante_timbrado_pdf=null;
		Instant start = Instant.now();
		
		if(this.UUID==null)throw new IllegalArgumentException("No se tiene registrado UUID");
		if(this.comprobante==null)throw new IllegalArgumentException("No se tiene registrado comprobante");
		//if(this.comprobante_timbrado_xml.)throw new IllegalArgumentException("No se tiene registrado comprobante");
		
		try {	
								
		
			HashMap<String, Object> scopes = new HashMap<String, Object>();
			
			if(informacion_pdf!=null)scopes.putAll(informacion_pdf);
						
			scopes.put("comprobante", comprobante);
			scopes.put("receptor", comprobante.getReceptor());
			scopes.put("conceptos", ConceptoView.getConceptoView(comprobante.getConceptos().getConcepto()));
			
			if(comprobante.getImpuestos()!=null)scopes.put("impuesto_view", new ImpuestoView(comprobante.getImpuestos()));			
		
			
			//scopes.put("timbre_fiscal", ComprobanteHelper.obtenerTimbreFiscalDigital(comprobante));
			scopes.put("timbre_fiscal", new TimbreFiscalView(comprobante));
			
			scopes.put("moneda",Moneda.valueOf(comprobante.getMoneda().toString()));
			if(comprobante.getMetodoPago()!=null)scopes.put("metodo_pago", MetodoPago.valueOf(comprobante.getMetodoPago().toString()));
			if(comprobante.getFormaPago()!=null)scopes.put("forma_pago", FormaPago.valueOf("FORMA_PAGO_"+comprobante.getFormaPago()));			
			scopes.put("regimen_fiscal", RegimenFiscal.valueOf("REGIMEN_FISCAL_"+comprobante.getEmisor().getRegimenFiscal()));
			
			scopes.put("uso_cfdi", UsoCFDI.valueOf(""+comprobante.getReceptor().getUsoCFDI()));
			
			scopes.put("tipo_comprobante", TipoComprobante.valueOf(comprobante.getTipoDeComprobante().toString()));
			
		
			NumberFormat currency = NumberFormat.getCurrencyInstance();
			scopes.put("subtotal", currency.format(comprobante.getSubTotal()));
			scopes.put("total", currency.format(comprobante.getTotal()));
			
			
			//retenciones
			
			
			
			
					
			String encoded_qr = null;
			if(comprobante_timbrado_qr!=null) {
				byte[] datos = Files.readAllBytes(comprobante_timbrado_qr);
				 encoded_qr = Base64.getEncoder().encodeToString(datos);
				
			}else if(CFDITimbradoQR!=null){
				
				 encoded_qr = Base64.getEncoder().encodeToString(CFDITimbradoQR);
			}
			
			scopes.put("qr_image", "data:image/png;base64,"+encoded_qr);
			
			 
			StringWriter writer =  new StringWriter();
			
			 MustacheFactory mf = new DefaultMustacheFactory();
			 
			 String template = (customTemplate == null ) ? "comprobante_fiscal.mustache" : customTemplate;
			 
			 Mustache mustache = mf.compile(template);
			 //mustache.execute(new PrintWriter(System.out),scopes).flush();
			 //mustache.execute(new FileWriter("FILE.XHTML"),scopes).flush();
			 mustache.execute(writer,scopes).flush();
			 
			 OutputStream os =  null;
			if(!LEER_CFDI_DE_MEMORIA) {
				comprobante_timbrado_pdf = Paths.get(comprobante_timbrado_xml.getParent().toString(),this.UUID+".pdf");
				logger.info("Generando PDF "+comprobante_timbrado_pdf);
				 os = new FileOutputStream(comprobante_timbrado_pdf.toFile());
			}else {
				os = new ByteArrayOutputStream();
			}
	        
	        
	        
	        ITextRenderer renderer = new ITextRenderer();                        	       	     
	        renderer.setDocumentFromString(writer.toString());
	        renderer.layout();	    	        
	        renderer.createPDF(os);
	        os.close();
	        
	        
	        if(comprobante_timbrado_pdf!=null)logger.info("PDF guardado {}",comprobante_timbrado_pdf);
	        else CFDITimbradoPDF = ((ByteArrayOutputStream)os).toByteArray();
	        
	        logger.info("Tiempo PDF: {}s",Duration.between(start, Instant.now()).getSeconds());
	        
	        return true;
		} catch (FileNotFoundException e) {
			logger.error(e,e);
		} catch (IOException e) {
			logger.error(e,e);
		} catch (DocumentException e) {
			logger.error(e,e);
		} catch (Exception e) {
			logger.error(e,e);
		}      
		return false;
				
	}
	
	

}
