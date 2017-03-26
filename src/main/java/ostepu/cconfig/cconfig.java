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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import java.util.Map;
import com.google.gson.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ostepu.request.authentication;
import ostepu.request.httpAuth;
import ostepu.request.httpRequest;
import ostepu.request.httpRequestResult;
import ostepu.request.noAuth;
import ostepu.structure.component;
import ostepu.structure.link;

/**
 * Dieses Servlet behandelt die Konfiguration dieses Webservices als Komponente
 * von OSTEPU
 *
 * @author Till
 */
public class cconfig extends HttpServlet {

    /**
     * bei einem eingehenden Aufruf behandelt diese Methode den Aufruf und wählt
     * die entsprechende Unterfunktion aus
     *
     * @param request  der eingehende Aufruf
     * @param response das Rückgabeobjekt
     */
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) {

        // lädt die Anmeldedaten (eventuell für eine httpAuth)
        httpAuth.loadLocalAuthData(getServletContext());

        String servletPath = request.getServletPath();
        getServletContext().setAttribute("prefix", "process,course,link");
        if ("/info".equals(servletPath)) {
            info.request(getServletContext(), request, response);
        } else if ("/help".equals(servletPath)) {
            help.request(getServletContext(), request, response);
        } else if ("/control".equals(servletPath)) {
            control.request(getServletContext(), request, response);
        } else {
            try {
                response.sendError(404, "unbekannter Befehl");
            } catch (IOException ex) {
                Logger.getLogger(cconfig.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
            }
        }
    }

    /**
     * behandelt einen eingehenden GET-Aufruf
     *
     * @param request  der eingehende Aufruf
     * @param response das Rückgabeobjekt
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            doRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(cconfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * behandelt einen eingehenden POST-Aufruf
     *
     * @param request  der eingehende Aufruf
     * @param response das Rückgabeobjekt
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            doRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(cconfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Dieses Servlet behandelt die Konfiguration dieses Webservices als Komponente von OSTEPU";
    }

    /**
     * wenn die Komponentendefinition geladen wurde, dann wir sie hier abgelegt,
     * sodass wir sie nicht immer erneut laden müssen
     */
    public static JsonObject myComponent = null;

    /**
     * lädt die Komponentendefinition (component.json)
     *
     * @param context der kontext des Servlet
     * @return die Konfiguration oder null (Fehler)
     */
    public static JsonObject loadComponent(ServletContext context) {
        if (myComponent != null) {
            return myComponent;
        }

        Path cconfigPath = Paths.get(context.getRealPath("data/Component.json"));
        try {
            if (Files.exists(cconfigPath)) {
                List<String> content = Files.readAllLines(cconfigPath);
                JsonElement obj = new JsonParser().parse(String.join("", content));
                myComponent = obj.getAsJsonObject();
                return myComponent;
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * hier wird die Konfiguration der Komponente nach dem einmaligen Laden
     * hinterlegt
     */
    public static component myConf = null;

    /**
     * lädt die Konfiguration der Komponente
     *
     * @param context der Servlet-Kontext
     * @return die Konfiguration oder null (Fehler)
     */
    public static component loadConfig(ServletContext context) {
        if (myConf != null) {
            return myConf;
        }

        Path cconfigPath = Paths.get(context.getRealPath("data/CConfig.json"));
        try {
            if (Files.exists(cconfigPath)) {
                List<String> content = Files.readAllLines(cconfigPath);
                JsonElement obj = new JsonParser().parse(String.join("", content));
                component newComponent = new component(obj.getAsJsonObject());
                myConf = newComponent;
                return newComponent;
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * ermittelt eine Verbinung linkName aus einer Komponentendefinition content
     *
     * @param content  die Definition einer Komponente
     * @param linkName der Linkname
     * @return der Link oder null (Fehler oder unbekannt)
     */
    public static link getLink(component content, String linkName) {
        if (content == null) {
            return null;
        }

        for (link mylink : content.getLinks()) {
            if (mylink.getName().equals(linkName)) {
                return mylink;
            }
        }
        return null;
    }

    /**
     * ermittelt eine Verbinung linkName aus der globalen Komponentendefinition
     *
     * @param linkName der Linkname
     * @return der Link oder null (Fehler oder unbekannt)
     */
    public static link getLink(String linkName) {
        if (myConf == null) {
            return null;
        }

        for (link mylink : myConf.getLinks()) {
            if (mylink.getName().equals(linkName)) {
                return mylink;
            }
        }
        return null;
    }

    /**
     * ruft einen Link anhand seines Namens auf
     *
     * @param mycomponent die Komponentendefinition
     * @param linkName    der Name der Verbindung
     * @param URI         der Befehl
     * @param method      die Methode (GET, POST, DELETE, ...)
     * @param content     der Inhalt
     * @return das Ergebnis der Anfrage
     */
    public static httpRequestResult callLinkByName(component mycomponent, String linkName, String URI, String method, String content) {
        return callLinkByName(mycomponent, linkName, URI, method, content, new noAuth());
    }

    /**
     * ruft einen Link anhand seines Namens auf
     *
     * @param mycomponent die Komponentendefinition
     * @param linkName    der Name der Verbindung
     * @param URI         der Befehl
     * @param method      die Methode (GET, POST, DELETE, ...)
     * @param content     der Inhalt
     * @param auth        eine Authentifizierungsmethode
     * @return das Ergebnis der Anfrage
     */
    public static httpRequestResult callLinkByName(component mycomponent, String linkName, String URI, String method, String content, authentication auth) {
        link linkObject = getLink(mycomponent, linkName);
        if (linkObject.getAddress() == null) {
            return null;
        }

        // System.out.println(linkObject.getAddress() + URI);
        return httpRequest.custom(linkObject.getAddress() + URI, method, content, auth);
    }

    /**
     * ruft eine Verbindung auf und verwendet dabei die Befehlsinformationen aus
     * der Linkdefinition
     *
     * @param mycomponent die Komponentendefinition
     * @param linkObject  die Verbindung
     * @param content     der Inhalt
     * @param placeholder Platzhalter, welche im Aufrufbefehl ersetzt werden
     *                    soll (PLATZHALTER => TEXT)
     * @return das Ergebnis
     */
    public static httpRequestResult callConstLink(JsonObject mycomponent, link linkObject, String content, Map<String, String> placeholder) {
        return callConstLink(mycomponent, linkObject, content, placeholder, new noAuth());
    }

    /**
     * ruft eine Verbindung auf und verwendet dabei die Befehlsinformationen aus
     * der Linkdefinition
     *
     * @param mycomponent die Komponentendefinition
     * @param linkObject  die Verbindung
     * @param content     der Inhalt
     * @param placeholder placeholder Platzhalter, welche im Aufrufbefehl
     *                    ersetzt werden soll (PLATZHALTER => TEXT)
     * @param auth        eine Authentifizierungsmethode
     * @return das Ergebnis
     */
    public static httpRequestResult callConstLink(JsonObject mycomponent, link linkObject, String content, Map<String, String> placeholder, authentication auth) {
        if (mycomponent.has("links")) {
            JsonArray linkList = mycomponent.get("links").getAsJsonArray();
            for (JsonElement mylink : linkList) {
                JsonObject mylinkObject = mylink.getAsJsonObject();
                if (mylinkObject.has("name")) {
                    String myName = mylinkObject.get("name").getAsString();
                    if (linkObject.getName().equals(myName)) {
                        if (mylinkObject.has("links")) {
                            JsonArray aa = mylinkObject.get("links").getAsJsonArray();
                            if (aa.size() > 0) {
                                JsonObject request = aa.get(0).getAsJsonObject();
                                if (request.has("method") && request.has("path")) {
                                    // nun können wir den Aufruf ausführen
                                    String method = request.get("method").getAsString();
                                    String path = request.get("path").getAsString();

                                    // jetzt muss path noch mit den Platzhaltern versorgt werden
                                    if (placeholder != null) {
                                        for (Map.Entry<String, String> entry : placeholder.entrySet()) {
                                            path = path.replaceAll(":" + entry.getKey(), entry.getValue());
                                        }
                                    }

                                    ///System.out.println(linkObject.getAddress() + path);
                                    return httpRequest.custom(linkObject.getAddress() + path, method, content, auth);
                                }
                            }
                        }
                        return null;
                    }
                }
            }
        }
        return null;
    }

}
