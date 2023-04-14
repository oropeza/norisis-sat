package com.norisoft.sat.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.norisoft.sat.constantes.TipoImpuesto;

import lombok.Data;
import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto;
import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion;
import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado;

@Data
public class ConceptoView {
	
	private String claveProdServ;
	private String unidad;
	private String descripcion;
	private String valorUnitario;
	private String cantidad;
	private String importe;
	private List<ImpuestoView> impuestos;
	private List<RetencionView> retenciones;
	
	
	public static List<ConceptoView> getConceptoView(List<Concepto> conceptos) {
		
		List<ConceptoView> lista = new ArrayList<ConceptoView>();
		
		for (Concepto concepto : conceptos) {
			lista.add(new ConceptoView(concepto));
		}
		
		return lista;
		
	}
	
	
	public ConceptoView(Concepto concepto) {
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		
		this.claveProdServ = concepto.getClaveProdServ();
		this.unidad = concepto.getClaveUnidad()+" "+concepto.getUnidad();
		this.descripcion = concepto.getDescripcion();
		this.valorUnitario = currency.format(concepto.getValorUnitario());
		this.cantidad = concepto.getCantidad().toString();
		this.importe = currency.format(concepto.getImporte());
		
		
		impuestos = new ArrayList<ConceptoView.ImpuestoView>();
		
		
		if(concepto.getImpuestos()!=null &&  concepto.getImpuestos().getTraslados() !=null) {
		
			for (Traslado traslado : concepto.getImpuestos().getTraslados().getTraslado()) {
				
				ImpuestoView iv = new ImpuestoView();
				iv.setImpuesto(TipoImpuesto.valueOf("IMPUESTO_"+traslado.getImpuesto()).descripcion+" "+traslado.getTipoFactor()+" "+traslado.getTasaOCuota().multiply(new BigDecimal(100)).setScale(4, RoundingMode.HALF_UP));
				iv.setImporte( currency.format(traslado.getImporte()));
				impuestos.add(iv);
			}
		}
		
		retenciones = new ArrayList<ConceptoView.RetencionView>();
		
		if(concepto.getImpuestos()!=null &&  concepto.getImpuestos().getRetenciones() !=null) {
		
			for (Retencion retencion : concepto.getImpuestos().getRetenciones().getRetencion()) {
				
				RetencionView iv = new RetencionView();
				iv.setImpuesto(TipoImpuesto.valueOf("IMPUESTO_"+retencion.getImpuesto()).descripcion+" "+retencion.getTipoFactor()+" "+retencion.getTasaOCuota().multiply(new BigDecimal(100)).setScale(4, RoundingMode.HALF_UP));
				iv.setImporte( currency.format(retencion.getImporte()));
				
				retenciones.add(iv);
			}
		}
		
		
	
	}
	
	
	@Data
	protected class ImpuestoView {
		String impuesto;
		String importe;
		
	}
	
	@Data
	protected class RetencionView {
		String impuesto;
		String importe;
	}
	
	

}
