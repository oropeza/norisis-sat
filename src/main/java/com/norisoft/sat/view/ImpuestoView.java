package com.norisoft.sat.view;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.norisoft.sat.constantes.TipoImpuesto;

import lombok.Data;
import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto;
import mx.gob.sat.cfd._3.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado;

@Data
public class ImpuestoView {
	
	
	String iva;
	String ieps;
	String retencionIVA = null;
	String retencionISR = null;
	
	
	
	
	
	public ImpuestoView(mx.gob.sat.cfd._3.Comprobante.Impuestos  impuestos ) {
		
		
		
		
		BigDecimal iva = BigDecimal.ZERO;
		BigDecimal ieps = BigDecimal.ZERO;

		
		for (mx.gob.sat.cfd._3.Comprobante.Impuestos.Traslados.Traslado traslado : impuestos.getTraslados().getTraslado()) {
			
			if(traslado.getImpuesto().equals("002"))iva = iva.add(traslado.getImporte());
			else if(traslado.getImpuesto().equals("003"))ieps = ieps.add(traslado.getImporte());
		}
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		this.iva = currency.format(iva);
		this.ieps = (ieps.compareTo(BigDecimal.ZERO)==0 ? "" : currency.format(ieps));
		
		
		BigDecimal ret_iva = null;
		BigDecimal ret_isr = null;
		
		if(impuestos.getRetenciones()!=null) {
			
			for (mx.gob.sat.cfd._3.Comprobante.Impuestos.Retenciones.Retencion retencion : impuestos.getRetenciones().getRetencion()) {
				
				if(retencion.getImpuesto().equals("002")) {
					ret_iva = ( ret_iva==null ? retencion.getImporte() : ret_iva.add(retencion.getImporte()) );
					
				}
				else if(retencion.getImpuesto().equals("001")) {
					ret_isr = ( ret_isr==null ? retencion.getImporte() : ret_isr.add(retencion.getImporte()) );
				}
			}
			
			
		}
		
		this.retencionIVA = ret_iva==null ? "" : currency.format(ret_iva);
		this.retencionISR = ret_isr==null ? "" : currency.format(ret_isr);
		
		
		
		
	}
	
	

	

}
