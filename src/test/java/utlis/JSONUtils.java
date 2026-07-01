package utlis;

import com.google.gson.Gson;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JSONUtils {

    public static Gson gson = new Gson();

    public static String convertMapToJSONString(Map<String, Object> map) {

        return gson.toJson(map);
    }
    public static JSONObject readRequestFile(String filePath) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));
            jsonObject = (JSONObject) obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jsonObject;
    }

    public static JSONObject readRequestFileFromClasspath(String classpathResourcePath) {
        try (InputStream inputStream = JSONUtils.class.getClassLoader().getResourceAsStream(classpathResourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Request file not found on classpath: " + classpathResourcePath);
            }
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            return (JSONObject) obj;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read request file: " + classpathResourcePath, e);
        }
    }
    public static void saveResponseToFile(Response response, String fileName) {
        try (FileWriter writer = new FileWriter("./src/test/resources/response/response_JSON" + fileName)){
            writer.write(response.asPrettyString());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}