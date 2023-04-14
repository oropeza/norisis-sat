package com.norisoft.sat.constantes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum MetodoPago {
	
	
	PUE("PUE","Pago en una sola exhibición"),
	PPD("PPD","Pago en parcialidades o diferido");
	
		 
		
	
	public String descripcion;
	public String clave;
	
	
	MetodoPago(String clave,String descripcion){ 
        this.descripcion = descripcion;
        this.clave = clave; 
    }

}







