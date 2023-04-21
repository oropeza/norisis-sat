package com.norisoft.sat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import com.norisoft.sat.constantes.TipoFactor;
import com.norisoft.sat.constantes.TipoImpuesto;

import mx.gob.sat.cfd._4.Comprobante;
import mx.gob.sat.cfd._4.Comprobante.Conceptos;
import mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto;
import mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados;
import mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados.Traslado;
import mx.gob.sat.cfd._4.Comprobante.Impuestos.Retenciones;
import mx.gob.sat.cfd._4.Comprobante.Impuestos.Retenciones.Retencion;
import mx.gob.sat.sitio_internet.cfd.catalogos.CTipoFactor;
import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

public class ComprobanteHelper {
	
	
	public static void creaConcepto(Comprobante comprobante,String claveProdServ,String claveUnidad, String unidad, String descripcion, 
			BigDecimal valorUnitario,BigDecimal cantidad,
			String tipoImpuesto, BigDecimal tasaOCuota ) {
		
		creaConcepto( comprobante, claveProdServ, claveUnidad,  unidad,  descripcion, 
				 valorUnitario, cantidad,
				 tipoImpuesto,  tasaOCuota,null,null);
		
	}	
	
	
	public static void creaConcepto(Comprobante comprobante,String claveProdServ,String claveUnidad, String unidad, String descripcion, 
			BigDecimal valorUnitario,BigDecimal cantidad,
			String tipoImpuesto, BigDecimal tasaOCuota,
					BigDecimal retencionIvaTasa, BigDecimal retencionIsrTasa ) {
		
		
		   Conceptos.Concepto c = new  Conceptos.Concepto();
		   c.setClaveProdServ(claveProdServ);	   
		   c.setClaveUnidad(claveUnidad);
		   c.setUnidad(unidad);
		   c.setDescripcion(descripcion);
		   c.setValorUnitario(valorUnitario);
		   c.setCantidad(cantidad);
		   c.setImporte(c.getCantidad().multiply(c.getValorUnitario()).setScale(6, RoundingMode.HALF_UP));
		   c.setObjetoImp("02");
		   
		   
		 
	       
	       
	       if(tipoImpuesto!=null && tipoImpuesto.equals(TipoImpuesto.IMPUESTO_002.clave)) { //IVA
	    	   
	    	   c.setImpuestos(new mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto.Impuestos());
		       c.getImpuestos().setTraslados(new Comprobante.Conceptos.Concepto.Impuestos.Traslados());
		       
		      
		       
	    	   Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado =  new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
	    	   	traslado.setBase(c.getImporte());		        
		        traslado.setImpuesto(tipoImpuesto);
		        traslado.setTasaOCuota(tasaOCuota.setScale(6));
		        traslado.setTipoFactor(CTipoFactor.TASA);		        
		        traslado.setImporte(traslado.getBase().multiply(traslado.getTasaOCuota()).setScale(6, RoundingMode.HALF_UP));	        
		        c.getImpuestos().getTraslados().getTraslado().add(traslado);
	       }
	       
	       
	       if((retencionIvaTasa!=null && retencionIvaTasa.compareTo(BigDecimal.ZERO)!=0) || 
	    		   (retencionIsrTasa!=null && retencionIsrTasa.compareTo(BigDecimal.ZERO)!=0)) {
	       
	    	   	c.getImpuestos().setRetenciones(new Comprobante.Conceptos.Concepto.Impuestos.Retenciones());
	       }
	    
	       
	       if(  retencionIsrTasa!=null && retencionIsrTasa.compareTo(BigDecimal.ZERO)!=0) {
	    	   
	    	   
		       Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencion =  new Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion();
	    	   retencion.setBase(c.getImporte());
	    	   retencion.setImpuesto(TipoImpuesto.IMPUESTO_001.clave);
	    	   retencion.setTipoFactor(CTipoFactor.TASA);	
	    	   retencion.setTasaOCuota(retencionIsrTasa.divide(new BigDecimal(100)).setScale(6,RoundingMode.HALF_UP));
		       retencion.setImporte(c.getImporte().multiply(retencion.getTasaOCuota()).setScale(2,RoundingMode.HALF_UP));
		       c.getImpuestos().getRetenciones().getRetencion().add(retencion);
		       
	       }
	       
	       
	       
	       
	       if(retencionIvaTasa!=null && retencionIvaTasa.compareTo(BigDecimal.ZERO)!=0 ) {
	    	   
	    	    
		       Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencion =  new Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion();
	    	   retencion.setBase(c.getImporte());
	    	   retencion.setImpuesto(TipoImpuesto.IMPUESTO_002.clave);
	    	   retencion.setTipoFactor(CTipoFactor.TASA);	
	    	   retencion.setTasaOCuota(retencionIvaTasa.divide(new BigDecimal(100)).setScale(6,RoundingMode.HALF_UP));
	    	   retencion.setImporte(c.getImporte().multiply(retencionIvaTasa.divide(new BigDecimal(100))).setScale(2,RoundingMode.HALF_UP));
		       c.getImpuestos().getRetenciones().getRetencion().add(retencion);
	       }
	        
	       
	        
	        
	        if(comprobante.getConceptos()==null)comprobante.setConceptos(new Conceptos());
	        
	        comprobante.getConceptos().getConcepto().add(c);
	        
		
	}
	
	public static void creaConcepto(Comprobante comprobante,String claveProdServ,String claveUnidad, String unidad, String descripcion, 
			BigDecimal valorUnitario,BigDecimal cantidad) {
		
		
		   Conceptos.Concepto c = new  Conceptos.Concepto();
		   c.setClaveProdServ(claveProdServ);	   
		   c.setClaveUnidad(claveUnidad);
		   c.setUnidad(unidad);
		   c.setDescripcion(descripcion);
		   c.setValorUnitario(valorUnitario);
		   c.setCantidad(cantidad);
		   c.setImporte(c.getCantidad().multiply(c.getValorUnitario()).setScale(6, RoundingMode.HALF_UP));
		   
		   c.setImpuestos(new mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto.Impuestos());
	
	        
	        if(comprobante.getConceptos()==null)comprobante.setConceptos(new Conceptos());
	        
	        comprobante.getConceptos().getConcepto().add(c);
	        
		
	}

	
	
	public static TimbreFiscalDigital obtenerTimbreFiscalDigital(Comprobante comprobante) {

		List<Object> complementos = comprobante.getComplemento().getAny(); //.get(0).getAny();		
		for (Object object : complementos) {
			if(object.getClass().getSimpleName().equals("TimbreFiscalDigital")) {
				return (TimbreFiscalDigital) object;
			}
		}						
		return null;
	}
	
	

	public static Traslado obtenerImpuestoTraslado(Comprobante comprobante,String impuesto,BigDecimal tasa_cuota ) {
		
		if(comprobante.getImpuestos()==null ||  comprobante.getImpuestos().getTraslados()==null)return null;		
		Traslados traslados = comprobante.getImpuestos().getTraslados();		
		for(Traslado traslado : traslados.getTraslado()) {						
			if(traslado.getImpuesto().equals(impuesto) && tasa_cuota==null) {
				return traslado;
			}else if(traslado.getImpuesto().equals(impuesto) && traslado.getTasaOCuota().compareTo(tasa_cuota)==0) {
				return traslado;
			}			
		}
		return null;
		
	}
	
	public static Retencion  obtenerImpuestoRetencion(Comprobante comprobante,String impuesto ) {
		
		if(comprobante.getImpuestos()==null ||  comprobante.getImpuestos().getRetenciones()==null)return null;		
		Retenciones retenciones = comprobante.getImpuestos().getRetenciones();		
		for(Retencion retencion: retenciones.getRetencion()) {						
			if(retencion.getImpuesto().equals(impuesto)) {
				return retencion;
			}			
		}
		return null;
		
	}	



	public static void calculaTotales(Comprobante comprobante) {
		
		comprobante.setImpuestos(new mx.gob.sat.cfd._4.Comprobante.Impuestos());
        comprobante.getImpuestos().setTraslados(new mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados() );
        
        
        Conceptos c = comprobante.getConceptos();
        
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalImpuestosTrasladados = BigDecimal.ZERO;
        BigDecimal totalImpuestosRetenidos = BigDecimal.ZERO;
        
        BigDecimal retencionIVA = BigDecimal.ZERO;
        BigDecimal retencionISR= BigDecimal.ZERO;
        

        
        
        HashMap<String, BigDecimal> traslado_iva = new HashMap<String, BigDecimal>(); //002
        HashMap<String, BigDecimal> traslado_ieps = new HashMap<String, BigDecimal>(); //003
        
        HashMap<String, BigDecimal> traslado_base_iva = new HashMap<String, BigDecimal>(); //002
        HashMap<String, BigDecimal> traslado_base_ieps = new HashMap<String, BigDecimal>(); //003        
        
        for (Concepto concepto : c.getConcepto()) {        	
        	subtotal = subtotal.add(concepto.getImporte());			
        	
        	if(concepto.getImpuestos()==null || concepto.getImpuestos().getTraslados()==null)continue;
        	for(mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado :  concepto.getImpuestos().getTraslados().getTraslado()) {
        		
        		String tasaOCuota = traslado.getTasaOCuota().stripTrailingZeros().toPlainString();
        		
        		
        		if(traslado.getImpuesto().equals("002")) {
        			if(!traslado_iva.containsKey(tasaOCuota)) traslado_iva.put(tasaOCuota, traslado.getImporte());
        			else  traslado_iva.put(tasaOCuota, traslado_iva.get(tasaOCuota).add(traslado.getImporte()));
        			
        			if(!traslado_base_iva.containsKey(tasaOCuota)) traslado_base_iva.put(tasaOCuota, traslado.getBase());
        			else  traslado_base_iva.put(tasaOCuota, traslado_base_iva.get(tasaOCuota).add(traslado.getBase()));
        			
        		}else  if(traslado.getImpuesto().equals("003")) {
        			if(!traslado_ieps.containsKey(tasaOCuota)) traslado_ieps.put(tasaOCuota, traslado.getImporte());
        			else  traslado_ieps.put(tasaOCuota, traslado_ieps.get(tasaOCuota).add(traslado.getImporte()));
        			
        			if(!traslado_base_ieps.containsKey(tasaOCuota)) traslado_base_ieps.put(tasaOCuota, traslado.getBase());
        			else  traslado_base_ieps.put(tasaOCuota, traslado_base_ieps.get(tasaOCuota).add(traslado.getBase()));        			

        		}
        		
        	}
        	
        	if(concepto.getImpuestos()==null || concepto.getImpuestos().getRetenciones()==null)continue;
        	for(mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retencion :  concepto.getImpuestos().getRetenciones().getRetencion()) {
        		
        		
        		
        		
        		
        		
        		if(retencion.getImpuesto().equals("001")) {        			        			
        			retencionISR = retencionISR.add(retencion.getImporte());
        		}else  if(retencion.getImpuesto().equals("002")) {
        			retencionIVA = retencionIVA.add(retencion.getImporte());
        		}
        		
        	}
        	
        	
		}
        
        
        for(String key : traslado_iva.keySet() ) {
            mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados.Traslado tra = new mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados.Traslado();
            tra.setImpuesto("002");
            tra.setTipoFactor(CTipoFactor.TASA);
            tra.setTasaOCuota(new BigDecimal(key).setScale(6,RoundingMode.HALF_UP));
            tra.setImporte(traslado_iva.get(key).setScale(2,RoundingMode.HALF_UP));
            tra.setBase(traslado_base_iva.get(key).setScale(2,RoundingMode.HALF_UP));        
            comprobante.getImpuestos().getTraslados().getTraslado().add(tra);
            totalImpuestosTrasladados = totalImpuestosTrasladados.add(tra.getImporte());
            
        }
        for(String key : traslado_ieps.keySet() ) {
            mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados.Traslado tra = new mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados.Traslado();
            tra.setImpuesto("003");
            tra.setTipoFactor(CTipoFactor.TASA);
            tra.setTasaOCuota(new BigDecimal(key).setScale(6));
            tra.setImporte(traslado_ieps.get(key).setScale(2,RoundingMode.HALF_UP));    
            tra.setBase(traslado_base_ieps.get(key).setScale(2,RoundingMode.HALF_UP));    
            comprobante.getImpuestos().getTraslados().getTraslado().add(tra);
            totalImpuestosTrasladados = totalImpuestosTrasladados.add(tra.getImporte());
        }             
        
        
        if(retencionISR.compareTo(BigDecimal.ZERO)!=0 || retencionIVA.compareTo(BigDecimal.ZERO)!=0) {
        	comprobante.getImpuestos().setRetenciones(new mx.gob.sat.cfd._4.Comprobante.Impuestos.Retenciones() );
        }
        
        if(retencionISR.compareTo(BigDecimal.ZERO)!=0) {
        	mx.gob.sat.cfd._4.Comprobante.Impuestos.Retenciones.Retencion ret = new mx.gob.sat.cfd._4.Comprobante.Impuestos.Retenciones.Retencion();
        	ret.setImpuesto("001");
        	ret.setImporte(retencionISR);        
            comprobante.getImpuestos().getRetenciones().getRetencion().add(ret);
            totalImpuestosRetenidos = totalImpuestosRetenidos.add(ret.getImporte());
        	
        }
        if(retencionIVA.compareTo(BigDecimal.ZERO)!=0) {
        	mx.gob.sat.cfd._4.Comprobante.Impuestos.Retenciones.Retencion ret = new mx.gob.sat.cfd._4.Comprobante.Impuestos.Retenciones.Retencion();
        	ret.setImpuesto("002");
        	ret.setImporte(retencionIVA);        
            comprobante.getImpuestos().getRetenciones().getRetencion().add(ret);
            totalImpuestosRetenidos = totalImpuestosRetenidos.add(ret.getImporte());
        	
        }
        
        
        
        comprobante.getImpuestos().setTotalImpuestosTrasladados(totalImpuestosTrasladados.setScale(2, RoundingMode.HALF_UP));
        
        if(totalImpuestosRetenidos.compareTo(BigDecimal.ZERO)!=0)
        	comprobante.getImpuestos().setTotalImpuestosRetenidos(totalImpuestosRetenidos.setScale(2, RoundingMode.HALF_UP));
        comprobante.setSubTotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        comprobante.setTotal(subtotal.add(totalImpuestosTrasladados).subtract(totalImpuestosRetenidos).setScale(2, RoundingMode.HALF_UP));

		
	}
	

}
