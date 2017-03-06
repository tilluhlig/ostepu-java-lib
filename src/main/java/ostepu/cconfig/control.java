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
package ostepu.cconfig;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 * dieser Befehlsteil liefert und schreibt die Konfiguration der Komponente als
 * OSTEPU-Komponente
 *
 * @author Till
 */
public class control {

    /**
     * ruft die existierende Konfiguration ab und gibt sie aus
     *
     * @param context  der Kontext des Servlet
     * @param request  die eingehende Anfrage
     * @param response das Antwortobjekt
     */
    public static void getControl(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(control.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
            return;
        }
        Path cconfigPath = Paths.get(context.getRealPath("data/CConfig.json"));

        try {
            if (Files.exists(cconfigPath)) {
                List<String> content = Files.readAllLines(cconfigPath);
                JsonElement obj = new JsonParser().parse(String.join("", content));
                JsonObject newObject = obj.getAsJsonObject();

                if (newObject.has("prefix")) {
                    newObject.remove("prefix");
                    newObject.add("prefix", (JsonElement) new JsonPrimitive(context.getAttribute("prefix").toString()));
                } else {
                    newObject.add("prefix", (JsonElement) new JsonPrimitive(context.getAttribute("prefix").toString()));
                }
                out.print(newObject.toString());
                response.setStatus(200);
            } else {
                response.setStatus(404);
                out.print("[]");
            }
        } catch (IOException ex) {
            Logger.getLogger(control.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
        } finally {
            out.close();
        }
    }

    /**
     * diese Methode wird von Außen aufgerufen und wählt die passende
     * Unterfunktion
     *
     * @param context  der Kontext des Servlet
     * @param request  die eingehende Anfrage
     * @param response das Antwortobjekt
     */
    public static void request(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();

        if ("GET".equals(request.getMethod())) {
            getControl(context, request, response);
        } else if ("POST".equals(request.getMethod())) {
            setControl(context, request, response);
        } else {
            try {
                response.sendError(404);
            } catch (IOException ex) {
                Logger.getLogger(control.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
            }
        }
    }

    /**
     * setzt die Konfiguration der Komponente anhand der eingehenden Daten
     *
     * @param context  der Kontext des Servlet
     * @param request  die eingehende Anfrage
     * @param response das Antwortobjekt
     */
    public static void setControl(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(control.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
            return;
        }

        Path cconfigPath = Paths.get(context.getRealPath("data/CConfig.json"));

        try {
            String q = IOUtils.toString(request.getReader());
            JsonElement obj = new JsonParser().parse(q);
            JsonObject newObject = obj.getAsJsonObject();

            if (newObject.has("prefix")) {
                newObject.remove("prefix");
                newObject.add("prefix", (JsonElement) new JsonPrimitive(context.getAttribute("prefix").toString()));
            } else {
                newObject.add("prefix", (JsonElement) new JsonPrimitive(context.getAttribute("prefix").toString()));
            }
            Files.write(cconfigPath, newObject.toString().getBytes());
            cconfig.myConf = null;
            response.setStatus(201);
        } catch (IOException | JsonSyntaxException e) {
            try {
                response.sendError(500);
            } catch (IOException ex) {
                Logger.getLogger(control.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
