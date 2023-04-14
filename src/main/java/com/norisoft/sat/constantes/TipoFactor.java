package com.norisoft.sat.constantes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TipoFactor {
	


	TASA("Tasa"),
	CUOTA("Cuota"),
	EXENTO("Exento");

	public String descripcion;

	
	
	TipoFactor( String descripcion){
		
        this.descripcion = descripcion;
        
    }

}





