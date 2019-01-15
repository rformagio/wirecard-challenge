package br.com.wirecard.payment.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateWithTimezoneSerializer extends JsonSerializer<Date> {

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    final SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }

}
