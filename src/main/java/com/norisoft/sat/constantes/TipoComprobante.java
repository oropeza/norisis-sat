package com.norisoft.sat.constantes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TipoComprobante {
	
	

	I("I","Ingreso"),
	E("E","Egreso"),
	T("T","Traslado"),
	P("P","Pago"),
	N("N","Nómina"),
	;

	public String descripcion;
	public String clave;
	
	
	TipoComprobante(String clave, String descripcion){
		this.clave = clave;
        this.descripcion = descripcion;
        
    }

}





