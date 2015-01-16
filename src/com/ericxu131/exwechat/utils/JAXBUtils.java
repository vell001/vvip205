package com.ericxu131.exwechat.utils;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author eric
 */
public class JAXBUtils {

    public static <T> T parserString(String content, Class c) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(content);
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException ex) {
            throw new ParserStringException(ex);
        }
    }

    public static String objectToXml(Object object, Class c) {
        try {
            JAXBContext jc = JAXBContext.newInstance(c);
            System.out.println(jc.getClass());
            Marshaller m = jc.createMarshaller();
            StringWriter writer = new StringWriter();
            writer.append("");
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);
            m.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                public void escape(char[] ac, int i, int j, boolean flag,
                        Writer writer) throws IOException {
                    writer.write(ac, i, j);
                }
            });

            m.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
