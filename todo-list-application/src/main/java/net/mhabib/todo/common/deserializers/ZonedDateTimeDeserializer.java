package net.mhabib.todo.common.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    private DateTimeFormatter dateTimeFormatter;

    public ZonedDateTimeDeserializer(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public ZonedDateTime deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        try {
            return ZonedDateTime.parse(jsonParser.getText(), dateTimeFormatter);
        } catch (DateTimeParseException ex) {
            throw new IOException(ex.getMessage(), ex);
        }
    }
}