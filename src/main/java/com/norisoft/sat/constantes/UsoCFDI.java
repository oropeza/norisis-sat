package com.norisoft.sat.constantes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum UsoCFDI {
	
	
	G_01("G01","G_01","Adquisición de mercancias"),
	G_02("G02","G_02","Devoluciones, descuentos o bonificaciones"),
	G_03("G03","G_03","Gastos en general"),
	I_01("I01","I_01","Construcciones"),
	I_02("I02","I_02","Mobilario y equipo de oficina por inversiones"),
	I_03("I03","I_03","Equipo de transporte"),
	I_04("I04","I_04","Equipo de computo y accesorios"),
	I_05("I05","I_05","Dados, troqueles, moldes, matrices y herramental"),
	I_06("I06","I_06","Comunicaciones telefónicas"),
	I_07("I07","I_07","Comunicaciones satelitales"),
	I_08("I08","I_08","Otra maquinaria y equipo"),
	D_01("D01","D_01","Honorarios médicos, dentales y gastos hospitalarios."),
	D_02("D02","D_02","Gastos médicos por incapacidad o discapacidad"),
	D_03("D03","D_03","Gastos funerales."),
	D_04("D04","D_04","Donativos."),
	D_05("D05","D_05","Intereses reales efectivamente pagados por créditos hipotecarios (casa habitación)."),
	D_06("D06","D_06","Aportaciones voluntarias al SAR."),
	D_07("D07","D_07","Primas por seguros de gastos médicos."),
	D_08("D08","D_08","Gastos de transportación escolar obligatoria."),
	D_09("D09","D_09","Depósitos en cuentas para el ahorro, primas que tengan como base planes de pensiones."),
	D_10("D10","D_10","Pagos por servicios educativos (colegiaturas)"),
	P_01("P01","P_01","Por definir");
	 
	public String descripcion;
	public String clave;
	public String claveSat;
	
	
	UsoCFDI(String clave,String clave_sat,String descripcion){ 
        this.descripcion = descripcion;
        this.clave = clave;
        this.claveSat = clave_sat; 
    }

}







