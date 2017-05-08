/* 
 * Copyright (C) 2017 Till Uhlig <till.uhlig@student.uni-halle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ostepu.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletContext;
import java.util.Base64;
import java.util.Base64.Encoder;

/**
 * führt eine simple HTTP-Authentifizierung durch
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
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
     * liest die lokalen Authentifizierungsdaten aus der data/AuthConfig.json
     *
     * @param context der Kontext des Servlet
     */
    public static void loadLocalAuthData(ServletContext context) {
        if (authData == null) {
            Path confPath;
            try{
                String localPath = context.getRealPath("/data/AuthConfig.json");
                confPath = Paths.get(localPath);
            }catch (Exception e){
                return;
            }

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
     * führt eine simple HTTP-Authentifizierung durch (setzt "Authorization")
     *
     * @param connection die HTTP-Verbindung
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
