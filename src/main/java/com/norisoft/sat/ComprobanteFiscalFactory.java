package com.norisoft.sat;

import java.nio.file.Path;

import mx.gob.sat.cfd._4.Comprobante;

public class ComprobanteFiscalFactory {
	
	
	public static ComprobanteFiscal getComprobante() {
		return new ComprobanteFiscal();
	}
	
	public static ComprobanteFiscal getComprobante(Comprobante c) {
		return new ComprobanteFiscal(c);
	}

	public static ComprobanteFiscal getComprobante(Path path) {
		return new ComprobanteFiscal(path);
	}
		

}
