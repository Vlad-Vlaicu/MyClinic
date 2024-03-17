package com.pweb.MyClinic.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

import static com.pweb.MyClinic.helper.TimeHelper.DATE_FORMATTER_0;
import static java.time.format.ResolverStyle.STRICT;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        if (!jsonParser.getValueAsString().isBlank()){
            return LocalDate.parse(jsonParser.getValueAsString(), DATE_FORMATTER_0.withResolverStyle(STRICT));
        }
        return null;
    }
}