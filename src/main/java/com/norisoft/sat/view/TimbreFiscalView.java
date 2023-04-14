package com.norisoft.sat.view;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.norisoft.sat.ComprobanteHelper;
import com.norisoft.sat.constantes.TipoImpuesto;

import lombok.Data;
import mx.gob.sat.cfd._4.Comprobante;
import mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto;
import mx.gob.sat.cfd._4.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado;
import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

@Data
public class TimbreFiscalView {
	
	
	String selloCFD;
	String selloSAT;
	String cadenaOriginal;
	String fechaTimbrado;
	String noCertificadoSAT;
	String rfcProvCertif;
	String UUID;
	String noCertificado;
	String leyenda;
	
	
	
	
	
	public TimbreFiscalView(Comprobante comprobante) {
		TimbreFiscalDigital  timbrado = ComprobanteHelper.obtenerTimbreFiscalDigital(comprobante);
		
		selloCFD  = timbrado.getSelloCFD().replaceAll("(.{90})", "$1<br/>");
		selloSAT  = timbrado.getSelloSAT().replaceAll("(.{90})", "$1<br/>");
		
		this.fechaTimbrado = timbrado.getFechaTimbrado().toString();
		this.noCertificadoSAT = timbrado.getNoCertificadoSAT();
		this.rfcProvCertif = timbrado.getRfcProvCertif();
		this.UUID = timbrado.getUUID();
		this.noCertificado = comprobante.getNoCertificado();
	
		StringBuilder cadenaOriginal = new StringBuilder("||1.1|");
			cadenaOriginal.append(this.UUID);
			cadenaOriginal.append("|");
			cadenaOriginal.append(timbrado.getFechaTimbrado());
			cadenaOriginal.append("|");
			cadenaOriginal.append(timbrado.getRfcProvCertif());
			cadenaOriginal.append("|");
			cadenaOriginal.append(timbrado.getSelloCFD());
			cadenaOriginal.append("|");
			cadenaOriginal.append(timbrado.getNoCertificadoSAT());
			cadenaOriginal.append("||");
		this.cadenaOriginal = cadenaOriginal.toString().replaceAll("(.{90})", "$1<br/>");
		
		
		
	}
	

	

}
