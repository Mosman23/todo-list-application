package net.mhabib.todo.common.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {

    private DateFormat dateFormatter;

    public DateDeserializer(DateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public Date deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        try {
            return dateFormatter.parse(jsonParser.getText());
        } catch (ParseException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
