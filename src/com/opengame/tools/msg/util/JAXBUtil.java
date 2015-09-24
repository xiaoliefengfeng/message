package com.opengame.tools.msg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class JAXBUtil {
	public static void write(Object xmlObject, Writer writer) {
		JAXBContext _context = null;
		try {
			_context = JAXBContext.newInstance(new Class[] { xmlObject
					.getClass() });
			Marshaller _marshaller = _context.createMarshaller();
			_marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			_marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			_marshaller.marshal(xmlObject, writer);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T read(Class<T> xmlObjectClass, Reader reader) {
		JAXBContext _context = null;
		try {
			_context = JAXBContext.newInstance(xmlObjectClass);
			Unmarshaller _unmarshaller = _context.createUnmarshaller();
			return (T) _unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T read(Class<T> xmlObjectClass, String fileXsd,
			String message) {
		JAXBContext _context = null;
		Reader reader = null;
		try {
			Source source = validator(fileXsd, message);
			reader = new InputStreamReader(new FileInputStream(message),
					Charset.forName("UTF-8"));
			_context = JAXBContext.newInstance(xmlObjectClass);
			Unmarshaller _unmarshaller = _context.createUnmarshaller();
			return (T) _unmarshaller.unmarshal(source);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static Source validator(String fileXsd, String message) {
		Source source = null;
		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			File schemaLocation = new File(fileXsd);
			if (!schemaLocation.exists()) {
				throw new RuntimeException(fileXsd + " no exist");
			}
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			source = new StreamSource(message);
			validator.validate(source);
			System.out.println("load message xml " + message);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return source;
	}

}
