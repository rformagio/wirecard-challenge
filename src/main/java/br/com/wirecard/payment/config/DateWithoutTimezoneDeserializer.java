package br.com.wirecard.payment.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateWithoutTimezoneDeserializer extends JsonDeserializer<Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private final DateFormat dateFormat = new java.text.SimpleDateFormat(DATE_FORMAT);

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        Date converted = null;
        dateFormat.setLenient(false);
        try {
            if(StringUtils.hasText(p.getText())){
                if(JsonToken.VALUE_STRING.equals(p.getCurrentToken())){
                    converted = dateFormat.parse(p.getText());
                }else if(JsonToken.VALUE_NUMBER_INT.equals(p.getCurrentToken())){
                    converted = new Date(p.getValueAsLong());
                }
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }

        return converted;

    }
}
