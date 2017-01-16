/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletContext;
import java.util.Base64;
import java.util.Base64.Encoder;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class httpAuth extends authentication {

    private static JsonObject authData = null;

    /**
     * @return the authData
     */
    public static JsonObject getAuthData() {
        return authData;
    }

    /**
     *
     * @param context
     */
    public static void loadLocalAuthData(ServletContext context) {
        if (authData == null) {
            Path confPath = Paths.get(context.getRealPath("data/AuthConfig.json"));

            try {
                if (Files.exists(confPath)) {
                    List<String> content = Files.readAllLines(confPath);
                    JsonElement obj = new JsonParser().parse(String.join("", content));
                    if (!obj.isJsonNull()) {
                        authData = obj.getAsJsonObject();
                    }
                }
            } catch (IOException e) {

            }
        }
    }

    /**
     *
     * @param connection
     */
    @Override
    public void performAuth(HttpURLConnection connection) {
        if (authData == null || authData.isJsonNull()) {
            // die Authentifizierungsdaten wurden nicht geladen
            return;
        }

        if (authData.has("auth") && authData.has("authLogin") && authData.has("authPasswd")) {
            String q = authData.get("auth").getAsString();
            String q2 = "httpAuth";
            if (!q2.equals(q)) {
                // wir können hier nur httpAuth behandeln
                return;
            }

            Encoder enc = Base64.getEncoder();
            String name = authData.get("authLogin").getAsString();
            String passwd = authData.get("authPasswd").getAsString();

            String rawAuthString = name + ":" + passwd;
            String authString = enc.encodeToString(rawAuthString.getBytes());

            // jetzt können wir die Anmeldeinformationen eintragen
            connection.setRequestProperty("Authorization", "Basic " + authString);
        }
    }

}
