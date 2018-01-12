package net.mhabib.todo.common.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class DateSerializer extends JsonSerializer<Date> {

    private DateFormat dateFormat;

    public DateSerializer(final DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void serialize(final Date value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        gen.writeString(dateFormat.format(value));
    }
}
