package net.mhabib.todo.common.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    private DateTimeFormatter dateTimeFormatter;

    public ZonedDateTimeSerializer(final DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public void serialize(final ZonedDateTime value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(dateTimeFormatter));
    }
}