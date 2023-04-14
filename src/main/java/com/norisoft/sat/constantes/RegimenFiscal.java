package com.norisoft.sat.constantes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum RegimenFiscal {
	
	
	REGIMEN_FISCAL_601("601","General de Ley Personas Morales"),
	REGIMEN_FISCAL_603("603","Personas Morales con Fines no Lucrativos"),
	REGIMEN_FISCAL_605("605","Sueldos y Salarios e Ingresos Asimilados a Salarios"),
	REGIMEN_FISCAL_606("606","Arrendamiento"),
	REGIMEN_FISCAL_608("608","Demás ingresos"),
	REGIMEN_FISCAL_609("609","Consolidación"),
	REGIMEN_FISCAL_610("610","Residentes en el Extranjero sin Establecimiento Permanente en México"),
	REGIMEN_FISCAL_611("611","Ingresos por Dividendos (socios y accionistas)"),
	REGIMEN_FISCAL_612("612","Personas Físicas con Actividades Empresariales y Profesionales"),
	REGIMEN_FISCAL_614("614","Ingresos por intereses"),
	REGIMEN_FISCAL_616("616","Sin obligaciones fiscales"),
	REGIMEN_FISCAL_620("620","Sociedades Cooperativas de Producción que optan por diferir sus ingresos"),
	REGIMEN_FISCAL_621("621","Incorporación Fiscal"),
	REGIMEN_FISCAL_622("622","Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras"),
	REGIMEN_FISCAL_623("623","Opcional para Grupos de Sociedades"),
	REGIMEN_FISCAL_624("624","Coordinados"),
	REGIMEN_FISCAL_628("628","Hidrocarburos"),
	REGIMEN_FISCAL_607("607","Régimen de Enajenación o Adquisición de Bienes"),
	REGIMEN_FISCAL_629("629","De los Regímenes Fiscales Preferentes y de las Empresas Multinacionales"),
	REGIMEN_FISCAL_630("630","Enajenación de acciones en bolsa de valores"),
	REGIMEN_FISCAL_615("615","Régimen de los ingresos por obtención de premios"),
	REGIMEN_FISCAL_625("625","Régimen de las Actividades Empresariales con ingresos a través de Plataformas Tecnológicas");
	
		 
		
	
	public String descripcion;
	public String clave;
	
	
	RegimenFiscal(String clave,String descripcion){ 
        this.descripcion = descripcion;
        this.clave = clave; 
    }

}







