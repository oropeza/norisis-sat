package com.norisoft.sat.constantes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TipoImpuesto {
	
	
	IMPUESTO_001("001","ISR"),
	IMPUESTO_002("002","IVA"),
	IMPUESTO_003("003","IEPS");
	
	public String descripcion;
	public String clave;
	
	
	TipoImpuesto(String clave, String descripcion){
		this.clave = clave;
        this.descripcion = descripcion;
        
    }

}







