package jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class BigDecimalXmlAdapter extends XmlAdapter<String,BigDecimal> {

    @Override
    public String marshal(BigDecimal bigDecimal) throws Exception {
        if (bigDecimal != null){
            return bigDecimal.toPlainString();
        }
        else {
            return null;
        }
    }

    @Override
    public BigDecimal unmarshal(String s) throws Exception {
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}