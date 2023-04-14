package com.norisoft.sat.constantes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum FormaPago {
	

	
	FORMA_PAGO_01("01","Efectivo"),
	FORMA_PAGO_02("02","Cheque nominativo"),
	FORMA_PAGO_03("03","Transferencia electrónica de fondos"),
	FORMA_PAGO_04("04","Tarjeta de crédito"),
	FORMA_PAGO_05("05","Monedero electrónico"),
	FORMA_PAGO_06("06","Dinero electrónico"),
	FORMA_PAGO_08("08","Vales de despensa"),
	FORMA_PAGO_12("12","Dación en pago"),
	FORMA_PAGO_13("13","Pago por subrogación"),
	FORMA_PAGO_14("14","Pago por consignación"),
	FORMA_PAGO_15("15","Condonación"),
	FORMA_PAGO_17("17","Compensación"),
	FORMA_PAGO_23("23","Novación"),
	FORMA_PAGO_24("24","Confusión"),
	FORMA_PAGO_25("25","Remisión de deuda"),
	FORMA_PAGO_26("26","Prescripción o caducidad"),
	FORMA_PAGO_27("27","A satisfacción del acreedor"),
	FORMA_PAGO_28("28","Tarjeta de débito"),
	FORMA_PAGO_29("29","Tarjeta de servicios"),
	FORMA_PAGO_30("30","Aplicación de anticipos"),
	FORMA_PAGO_31("31","Intermediario pagos"),
	FORMA_PAGO_99("99","Por definir");
	 
		
	
	public String descripcion;
	public String clave;
	
	
	FormaPago(String clave,String descripcion){ 
        this.descripcion = descripcion;
        this.clave = clave; 
    }
	
	

}







