package com.norisoft.sat.constantes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Moneda {
	
	

	MXN("MXN","Peso Mexicano"),
	USD("USD","Dolar americano"),
	EUR("EUR","Euro"),
	XXX("XXX","XXX");

	public String descripcion;
	public String clave;
	
	
	Moneda(String clave, String descripcion){
		this.clave = clave;
        this.descripcion = descripcion;
        
    }

}





