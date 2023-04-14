package com.norisoft.sat;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import mx.gob.sat.cfd._4.Comprobante;
import mx.gob.sat.cfd._4.Comprobante.Impuestos.Traslados.Traslado;

public class LeerFacturasXML {
	

	public static void main(String[] args) throws IOException {
		
		Paths.get("dev/out.txt").toFile().delete();
		
		
		FileWriter fw2 = new FileWriter("dev/out.txt",true);
		fw2.write("UUID|Tipo|RFC|Nombre|iva|ieps|Subtotal|Desceunto|Total\n");
		fw2.close();
		;
		
		try (Stream<Path> paths = Files.walk(Paths.get("/home/eclipse-workspace/NorisisDescargaSAT/dev/f4280987-a285-4135-8d46-1a578cd71185"))) {
										
			paths.filter(Files::isRegularFile).forEach(filePath ->{
					//System.out.println(filePath);
					try {
					    		
					FileWriter fw = new FileWriter("dev/out.txt",true);
		    		 	    	    		
				
				
		    		
		    		
		    		ComprobanteFiscal comprobante_fiscal = ComprobanteFiscalFactory.getComprobante();
		    		comprobante_fiscal.setLLER_XML_EXTERNO(true);;
		    		comprobante_fiscal.leer_archivo_xml(filePath);
		    		
		    		Comprobante c =  comprobante_fiscal.getComprobante();
		    		
		    		String a = ComprobanteHelper.obtenerTimbreFiscalDigital(c).getUUID()+"|"+c.getTipoDeComprobante()+"|";
		    		
		    		a+=c.getEmisor().getRfc()+"|"+c.getEmisor().getNombre()+"|";
		    		
		    		
		    		Traslado impuesto = ComprobanteHelper.obtenerImpuestoTraslado(c, "002", new BigDecimal("0.16"));
		    		Traslado impuesto_isr = ComprobanteHelper.obtenerImpuestoTraslado(c, "003",null);
		    		
		    		a+=( impuesto!=null ? impuesto.getImporte() : "0"  )+"|";
		    		a+=( impuesto_isr!=null ? impuesto_isr.getImporte() : "0"  );
		    		a+="|"+c.getSubTotal()+"|"+( c.getDescuento()!=null ? c.getDescuento() : "0"  )+"|"+c.getTotal()+"|";
		    		fw.write(a+"\n");
		
		    		
		    		fw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
		    		
		    		
			}
		        		
		        		);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
