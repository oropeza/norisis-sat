# norisis-sat

Librería de timbrado SAT versón 3.3 integrado con SeFactura. 

Funciones:

- Timbrado
- Lectura de XML



# Maven


mvn clean compile

## Timbrado 

```
InputStream cer = ClassLoader.class.getResourceAsStream("/certificados/CSD01_AAA010101AAA.cer");
InputStream key = ClassLoader.class.getResourceAsStream("/certificados/CSD01_AAA010101AAA.key");
String clave = "12345678a";
	
Comprobante c =  //Comprobante;
ComprobanteFiscal comprobante_fiscal = ComprobanteFiscalFactory.getComprobante(c);
comprobante_fiscal.carga_certificado(cer,key,clave);
comprobante_fiscal.genera_comprobante_timbrado();		
```

## Lectura de XML

```
Path path =  Paths.get(getClass().getClassLoader().getResource("xml/uuid.xml").toURI());
    	
ComprobanteFiscal comprobante_fiscal = ComprobanteFiscalFactory.getComprobante();
comprobante_fiscal.setLLER_XML_EXTERNO(true);;
comprobante_fiscal.leer_archivo_xml(path);
Comprobante c =  comprobante_fiscal.getComprobante();
```

## Libreria Helper para gestionar Comprobante


Funcòn *calculaTotales* carga en el comprobante toda la informaciòn de impuestos y los montos totales. 

 
```
Comprobante comprobante =  //Setup
    	  
        
ComprobanteHelper.creaConcepto(comprobante, 
	"53131600", "H87","pieza", "Duracell Copper & Black Pila Alcalina 9V, 1 pieza",new BigDecimal("107.76"), 
	new BigDecimal("1"), "002", new BigDecimal("0.160000"), CTipoFactor.TASA);
        
ComprobanteHelper.creaConcepto(comprobante, 
	"40142000", "H87","pieza", "Nite Ize S-Biner 3Pk Stainless",new BigDecimal("130.79"), new BigDecimal("1"), 
	"002", new BigDecimal("0.160000"), CTipoFactor.TASA);

ComprobanteHelper.creaConcepto(comprobante, 
	"51171900", "H87","pza", "PANTOPRAZOL FARMACOM 1 PZA",new BigDecimal("37.52"), new BigDecimal("1"));        

ComprobanteHelper.calculaTotales(comprobante);

```

## Todos:

- Configuración de carpetas
- Diseño de PDF
- Cargar datos de compra desde json
- Gestión de Errores
- Activación Modo productivo
- Quitar recursos generados de jaxb o evitar que se generen de forma continua