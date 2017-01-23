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
import java.io.PrintWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.*;

import ostepu.cconfig.info;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import static java.lang.System.console;
import java.net.ProtocolException;

import java.util.List;
import java.util.ArrayList;
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
 *
 * @author Till
 */
public class cconfig extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

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
            response.sendError(404);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            doRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(cconfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        return "Short description";
    }// </editor-fold>

    /**
     *
     */
    public static JsonObject myComponent = null;

    /**
     *
     * @param context
     * @return
     * @throws IOException
     */
    public static JsonObject loadComponent(ServletContext context) throws IOException {
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
     *
     */
    public static component myConf = null;

    /**
     *
     * @param context
     * @return
     * @throws IOException
     */
    public static component loadConfig(ServletContext context) throws IOException {
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
     *
     * @param content
     * @param linkName
     * @return
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
     *
     * @param mycomponent
     * @param linkName
     * @param URI
     * @param method
     * @param content
     * @return
     * @throws Exception
     */
    public static httpRequestResult callLinkByName(component mycomponent, String linkName, String URI, String method, String content) throws Exception {
        return callLinkByName(mycomponent, linkName, URI, method, content, new noAuth());
    }

    /**
     *
     * @param mycomponent
     * @param linkName
     * @param URI
     * @param method
     * @param content
     * @param auth
     * @return
     * @throws Exception
     */
    public static httpRequestResult callLinkByName(component mycomponent, String linkName, String URI, String method, String content, authentication auth) throws Exception {
        link linkObject = getLink(mycomponent, linkName);
        if (linkObject.getAddress() == null) {
            return null;
        }

        //System.out.println(linkObject.getAddress() + URI);
        return httpRequest.custom(linkObject.getAddress() + URI, method, content, auth);
    }

    /**
     *
     * @param mycomponent
     * @param linkObject
     * @param content
     * @param placeholder
     * @return
     * @throws Exception
     */
    public static httpRequestResult callConstLink(JsonObject mycomponent, link linkObject, String content, Map<String, String> placeholder) throws Exception {
        return callConstLink(mycomponent, linkObject, content, placeholder, new noAuth());
    }

    /**
     *
     * @param mycomponent
     * @param linkObject
     * @param content
     * @param placeholder
     * @param auth
     * @return
     * @throws Exception
     */
    public static httpRequestResult callConstLink(JsonObject mycomponent, link linkObject, String content, Map<String, String> placeholder, authentication auth) throws Exception {
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
