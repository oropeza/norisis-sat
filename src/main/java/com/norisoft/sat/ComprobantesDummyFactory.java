package com.norisoft.sat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import mx.gob.sat.cfd._4.Comprobante;
import mx.gob.sat.cfd._4.ObjectFactory;
import mx.gob.sat.cfd._4.Comprobante.CfdiRelacionados.CfdiRelacionado;
import mx.gob.sat.cfd._4.Comprobante.Complemento;
import mx.gob.sat.cfd._4.Comprobante.Conceptos;
import mx.gob.sat.cfd._4.Comprobante.Emisor;
import mx.gob.sat.cfd._4.Comprobante.Receptor;
import mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto.Impuestos.Traslados;
import mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado;
import mx.gob.sat.pagos.Pagos;
import mx.gob.sat.pagos.Pagos.Pago;
import mx.gob.sat.pagos.Pagos.Pago.DoctoRelacionado;
import mx.gob.sat.sitio_internet.cfd.catalogos.CMetodoPago;
import mx.gob.sat.sitio_internet.cfd.catalogos.CMoneda;
import mx.gob.sat.sitio_internet.cfd.catalogos.CTipoDeComprobante;
import mx.gob.sat.sitio_internet.cfd.catalogos.CTipoFactor;
import mx.gob.sat.sitio_internet.cfd.catalogos.CUsoCFDI;
import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

public class ComprobantesDummyFactory {
	
	
	
	public static Comprobante getComprobanteCompra() {
		return generaComprobante();
	}
	
	public static Comprobante getComprobante() {
		return generaComprobanteSimple();
	}	
	/*

	private static Comprobante generaComplementoPago() {
		try {
	        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        Date date = format.parse("2020-07-18 12:50:21");
	
	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(date);
	
	        XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        xmlGregCal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	        xmlGregCal.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
	        
	        ObjectFactory factory = new ObjectFactory();
	        
	        Comprobante comprobante = factory.createComprobante();
	        
	        comprobante.setLugarExpedicion("03550");
	        comprobante.setMetodoPago(CMetodoPago.PUE);
	        comprobante.setCondicionesDePago("Contado");
	        comprobante.setFormaPago("01");
	        comprobante.setTipoDeComprobante(CTipoDeComprobante.I);	        
	        comprobante.setMoneda(CMoneda.MXN);
	        comprobante.setVersion("3.3");
	        comprobante.setFecha(xmlGregCal);
	
	        comprobante.setTotal(new BigDecimal(23200));
	        comprobante.setSubTotal(new BigDecimal(20000));
	        
	        
	        	        	        
	       
	
	        
	        Emisor emisor = new Emisor();
	        emisor.setRfc("AAA010101AAA");
	        emisor.setNombre("Norisoft Consultores S.A. de C.V.");
	        emisor.setRegimenFiscal("601");
	        
	        Receptor receptor = new Receptor();
	        receptor.setRfc("XAXX010101000");
	        receptor.setNombre("");
	        receptor.setUsoCFDI(CUsoCFDI.G_03);
	        
	        
	        comprobante.setEmisor(emisor);
	        comprobante.setReceptor(receptor);
	        
	        
	        Conceptos conceptos = new Conceptos();        
	        comprobante.setConceptos(conceptos);
	        
	        Conceptos.Concepto c = new  Conceptos.Concepto();
		        c.setClaveProdServ("81112105");	        
		        c.setCantidad(new BigDecimal(1));
		        c.setClaveUnidad("E48");
		        c.setDescripcion("SERVIDOR");
		        c.setValorUnitario(new BigDecimal(20000));
		        c.setImporte(new BigDecimal(20000));
		        
		        c.setImpuestos(new mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto.Impuestos());
		        c.getImpuestos().setTraslados(new Traslados());
		        
		         		        
		        
		        Traslado traslado = new Traslado();
		        traslado.setBase(new BigDecimal(20000));
		        traslado.setImpuesto("002");
		        traslado.setTasaOCuota(new BigDecimal("0.160000"));
		        traslado.setTipoFactor(CTipoFactor.TASA);
		        traslado.setImporte(new BigDecimal("3200"));
		        
		        
		        
		        
		        c.getImpuestos().getTraslados().getTraslado().add(traslado);
		        
		        
		        
	        	        
	        conceptos.getConcepto().add(c);
	        
	        comprobante.setImpuestos(new mx.gob.sat.cfd._4.Comprobante.Impuestos());
	        comprobante.getImpuestos().setTraslados(new mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados() );
	        
	        mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados.Traslado tra = new mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados.Traslado();
	        tra.setImpuesto("002");
	        tra.setTipoFactor(CTipoFactor.TASA);
	        tra.setTasaOCuota(new BigDecimal("0.160000"));
	        tra.setImporte(new BigDecimal("3200"));
	        comprobante.getImpuestos().getTraslados().getTraslado().add(tra);
	        comprobante.getImpuestos().setTotalImpuestosTrasladados(new BigDecimal("3200"));
	        
	        
	        mx.gob.sat.pagos.ObjectFactory pagos_factory = new mx.gob.sat.pagos.ObjectFactory();
	       
	        
	        Complemento complemento =  new Complemento();
	       
	        Pagos pagos = pagos_factory.createPagos();
	        complemento.getAny().add(pagos);
	        
	        
	        comprobante.getComplemento().add(complemento);
	        
	        
	        return comprobante;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}	
	

	
	*/
	
	
	private static Emisor generaEmisor() {
		   
        Emisor emisor = new Emisor();
        emisor.setRfc("CACX7605101P8");
        emisor.setNombre("Norisoft Consultores S.A. de C.V.");
        emisor.setRegimenFiscal("601");
        return emisor;
		
	}
	
	private static Receptor generaReceptor() {
        
        Receptor receptor = new Receptor();
        receptor.setRfc("XAXX010101000");
        receptor.setNombre("JUAN JORGE SANCHEZ PEREZ");
        receptor.setUsoCFDI(CUsoCFDI.G_03);
        
        return receptor;
	}
	private static Comprobante generaComprobanteSimple() {
		try {
	        
	        	
	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(new Date());
	
	        XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        xmlGregCal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	        xmlGregCal.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
	        
	        ObjectFactory factory = new ObjectFactory();
	        
	        Comprobante comprobante = factory.createComprobante();
	        
	        comprobante.setLugarExpedicion("03550");
	        comprobante.setMetodoPago(CMetodoPago.PUE);
	        comprobante.setCondicionesDePago("Contado");
	        comprobante.setFormaPago("01");
	             
	        comprobante.setMoneda(CMoneda.MXN);
	        comprobante.setVersion("3.3");
	        comprobante.setFecha(xmlGregCal);
	
	        
	        comprobante.setEmisor(generaEmisor());
	        comprobante.setReceptor(generaReceptor());
	        
	      	        
	        
	        return comprobante;
		
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	
	private static Comprobante generaComprobante() {
		try {
	        
	        	
	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(new Date());
	
	        XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        xmlGregCal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	        xmlGregCal.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
	        
	        ObjectFactory factory = new ObjectFactory();
	        
	        Comprobante comprobante = factory.createComprobante();
	        
	        
	        comprobante.setLugarExpedicion("03550");
	        comprobante.setMetodoPago(CMetodoPago.PUE);
	        comprobante.setCondicionesDePago("Contado");
	        comprobante.setFormaPago("01");
	        comprobante.setTipoDeComprobante(CTipoDeComprobante.I);	        
	        comprobante.setMoneda(CMoneda.MXN);
	        comprobante.setVersion("3.3");
	        comprobante.setFecha(xmlGregCal);
	
	        
	        comprobante.setEmisor(generaEmisor());
	        comprobante.setReceptor(generaReceptor());
	        

	        
	        
	        ComprobanteHelper.creaConcepto(comprobante, 
	        		"53131600", "H87","pieza", "Duracell Copper & Black Pila Alcalina 9V, 1 pieza",
	        		new BigDecimal("107.76"), new BigDecimal("1"), "002", new BigDecimal("0.160000"));
	        
	     
	        ComprobanteHelper.creaConcepto(comprobante, 
	        		"40142000", "H87","pieza", "Nite Ize S-Biner 3Pk Stainless",
	        		new BigDecimal("130.79"), new BigDecimal("1"), "002", new BigDecimal("0.160000"));
	        
	        
	        ComprobanteHelper.calculaTotales(comprobante);
	        
	        
	        return comprobante;
		
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	
	public static Comprobante generaComplementoPago() {
		try {
	        
	        	
	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(new Date());
	
	        XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        xmlGregCal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	        xmlGregCal.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
	        
	        ObjectFactory factory = new ObjectFactory();
	        mx.gob.sat.pagos.ObjectFactory pagosFactory = new mx.gob.sat.pagos.ObjectFactory();
	        
	        
	        
	        
	        Comprobante comprobante = factory.createComprobante();
	        
	        
	        comprobante.setLugarExpedicion("03550");	        
	        comprobante.setTipoDeComprobante(CTipoDeComprobante.P);	        
	        comprobante.setMoneda(CMoneda.XXX);
	        comprobante.setVersion("3.3");
	        comprobante.setFecha(xmlGregCal);
	
	        
	        comprobante.setEmisor(generaEmisor());
	        comprobante.setReceptor(generaReceptor());
	        
	        comprobante.setSubTotal(BigDecimal.ZERO);
	        comprobante.setTotal(BigDecimal.ZERO);
	        
	        
	        CfdiRelacionado cfdi =  factory.createComprobanteCfdiRelacionadosCfdiRelacionado();
	        cfdi.setUUID("29D2BC9D-57B2-4E76-B5A2-41587525B521");
	        
	        //comprobante.setCfdiRelacionados(factory.createComprobanteCfdiRelacionados());
	        //comprobante.getCfdiRelacionados() .getCfdiRelacionado().add(cfdi);
	        //comprobante.getCfdiRelacionados().setTipoRelacion("04");
	        

	        Conceptos.Concepto conceptoPago = new  Conceptos.Concepto();
	        conceptoPago.setClaveProdServ("84111506");	   
	        conceptoPago.setClaveUnidad("ACT");
			   
	        conceptoPago.setDescripcion("Pago");
	        conceptoPago.setValorUnitario(BigDecimal.ZERO);
	        conceptoPago.setCantidad(BigDecimal.ONE);
	        conceptoPago.setImporte(BigDecimal.ZERO);
	        
	        if(comprobante.getConceptos()==null)comprobante.setConceptos(new Conceptos());	        
	        comprobante.getConceptos().getConcepto().add(conceptoPago);
	        
	       //***
	
	        
	        GregorianCalendar calPago = new GregorianCalendar();
	        calPago.setTime(new Date());
	
	        XMLGregorianCalendar xmlGregCalPago =  DatatypeFactory.newInstance().newXMLGregorianCalendar(calPago);
	        xmlGregCalPago.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
	        xmlGregCalPago.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
	        
	        Pago pago = pagosFactory.createPagosPago();
	        
	        pago.setFechaPago(xmlGregCalPago);
	        pago.setFormaDePagoP("03");
	        pago.setMonedaP(CMoneda.MXN);
	        pago.setMonto(new BigDecimal("23200.0"));
	        
	        
	        DoctoRelacionado relacionado = pagosFactory.createPagosPagoDoctoRelacionado();
	        relacionado.setIdDocumento("29D2BC9D-57B2-4E76-B5A2-41587525B521");
	        relacionado.setMonedaDR(CMoneda.MXN);
	      //  relacionado.setMetodoDePagoDR(CMetodoPago.PPD);
	        relacionado.setNumParcialidad(BigInteger.ONE);
	        relacionado.setImpSaldoAnt(new BigDecimal("23200"));
	        relacionado.setImpPagado(new BigDecimal("23200"));
	        relacionado.setImpSaldoInsoluto(new BigDecimal("0"));
	        
	       
	        pago.getDoctoRelacionado().add(relacionado);
	         
	        Pagos pagos =  pagosFactory.createPagos();
	        
	        pagos.setVersion("1.0");
	        pagos.getPago().add(pago);
	        	       	       
	        Complemento complemento =  new Complemento();	
	        
	        complemento.getAny().add(pagos);
	    //    comprobante.getComplemento().add(complemento);
	         

	        
	        return comprobante;
		
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
