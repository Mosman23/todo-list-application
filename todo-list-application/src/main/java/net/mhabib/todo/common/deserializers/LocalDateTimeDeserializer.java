package net.mhabib.todo.common.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private DateTimeFormatter dateTimeFormatter;

    public LocalDateTimeDeserializer(final DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public LocalDateTime deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        try {
            return LocalDateTime.parse(p.getText(), dateTimeFormatter);
        } catch (DateTimeParseException ex) {
            throw new IOException(ex.getMessage(), ex);
        }
    }
}
