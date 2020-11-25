package eu.nimble.utility.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateSerializer extends StdSerializer<Date> {
    public DateSerializer() {
        this(null);
    }

    public DateSerializer(Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        try {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(value);
            XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
            gen.writeString(date.toString());
        } catch (DatatypeConfigurationException e) {
            throw new IOException(e);
        }
    }
}
