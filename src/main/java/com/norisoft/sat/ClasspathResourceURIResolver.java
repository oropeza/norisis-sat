package com.norisoft.sat;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

class ClasspathResourceURIResolver implements URIResolver {
	  @Override
	  public Source resolve(String href, String base) throws TransformerException {	
	    return new StreamSource(ClasspathResourceURIResolver.class.getResourceAsStream(href));
	  }
}
