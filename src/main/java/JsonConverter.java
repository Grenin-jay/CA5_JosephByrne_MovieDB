import com.google.gson.Gson;

import java.util.List;

public class JsonConverter
{
    public static final Gson gsonParser = new Gson();

    public static String moviesListToJson (List<Movie> list) {
        return gsonParser.toJson(list);
    }
}