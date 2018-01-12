package net.mhabib.todo.common.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

public class CalendarDeserializer extends JsonDeserializer<Calendar> {

    private DateFormat dateFormat;
    private TimeZone timeZone;

    public CalendarDeserializer(DateFormat dateFormat, TimeZone timeZone) {
        this.dateFormat = dateFormat;
        this.timeZone = timeZone;
    }

    @Override
    public Calendar deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        try {
            final Calendar instance = Calendar.getInstance(timeZone);
            instance.setTimeInMillis(dateFormat.parse(jsonParser.getText()).getTime());
            return instance;
        } catch (ParseException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
