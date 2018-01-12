package net.mhabib.todo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import net.mhabib.todo.common.deserializers.CalendarDeserializer;
import net.mhabib.todo.common.deserializers.DateDeserializer;
import net.mhabib.todo.common.deserializers.LocalDateTimeDeserializer;
import net.mhabib.todo.common.deserializers.ZonedDateTimeDeserializer;
import net.mhabib.todo.common.serializers.CalendarSerializer;
import net.mhabib.todo.common.serializers.DateSerializer;
import net.mhabib.todo.common.serializers.LocalDateTimeSerializer;
import net.mhabib.todo.common.serializers.ZonedDateTimeSerializer;
import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
import static com.fasterxml.jackson.databind.DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;

public class CustomObjectMapper extends ObjectMapper {

    private static final String ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String TIME_ZONE_UTC = "UTC";

    private DateFormat dateFormat;
    private DateTimeFormatter dateTimeFormatter;
    private TimeZone timeZone;

    public CustomObjectMapper() {
        this(ISO_PATTERN, ISO_PATTERN, TIME_ZONE_UTC);
    }

    public CustomObjectMapper(final String dateFormatPattern, final String dateTimeFormatterPattern, final String timeZoneId) {
        this.dateFormat = new SimpleDateFormat(dateFormatPattern);
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatterPattern);
        this.timeZone = TimeZone.getTimeZone(timeZoneId);

        final JavaTimeModule javaTimeModule = getJavaTimeModule();
        initGeneralConfig();
        registerModule(javaTimeModule);
    }

    private JavaTimeModule getJavaTimeModule() {
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        initSerializersConfig(javaTimeModule);
        initDeserializersConfig(javaTimeModule);
        return javaTimeModule;
    }

    private void initGeneralConfig() {

        disable(WRITE_DATES_AS_TIMESTAMPS);
        disable(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        disable(READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        setPropertyNamingStrategy(SNAKE_CASE);
        setTimeZone(timeZone);
        setDateFormat(dateFormat);
    }

    private void initDeserializersConfig(final JavaTimeModule javaTimeModule) {
        javaTimeModule.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        javaTimeModule.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        javaTimeModule.addDeserializer(Date.class, new DateDeserializer(dateFormat));
        javaTimeModule.addDeserializer(Calendar.class, new CalendarDeserializer(dateFormat, timeZone));
    }

    private void initSerializersConfig(final JavaTimeModule javaTimeModule) {
        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        javaTimeModule.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
        javaTimeModule.addSerializer(Date.class, new DateSerializer(dateFormat));
        javaTimeModule.addSerializer(Calendar.class, new CalendarSerializer(dateFormat));
    }

}
