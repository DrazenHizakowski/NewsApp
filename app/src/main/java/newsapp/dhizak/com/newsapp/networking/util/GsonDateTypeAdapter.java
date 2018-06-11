package newsapp.dhizak.com.newsapp.networking.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dražen Hižak on 09/06/2018.
 */

public class GsonDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final DateFormat simpleDateFormat;
    private final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public GsonDateTypeAdapter() {
        simpleDateFormat = new SimpleDateFormat(dateFormat);
    }

    @Override
    public synchronized JsonElement serialize(Date date, Type type,
                                              JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(simpleDateFormat.format(date));
    }

    @Override
    public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                         JsonDeserializationContext jsonDeserializationContext) {
        try {
            String str = jsonElement.getAsString();
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
