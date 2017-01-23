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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import static ostepu.cconfig.info.getCommands;
import static ostepu.cconfig.info.getInfo;
import static ostepu.cconfig.info.getLinks;
import org.apache.commons.io.IOUtils;
import ostepu.structure.component;

/**
 *
 * @author Till
 */
public class control {

    /**
     *
     * @param context
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void getControl(ServletContext context, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
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
        } finally {
            out.close();
        }
    }

    /**
     *
     * @param context
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void request(ServletContext context, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if ("GET".equals(request.getMethod())) {
            getControl(context, request, response);
        } else if ("POST".equals(request.getMethod())) {
            setControl(context, request, response);
        } else {
            response.sendError(404);
        }
    }

    /**
     *
     * @param context
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void setControl(ServletContext context, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
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
        } catch (IOException e) {
            response.sendError(500);
        } catch (JsonSyntaxException e) {
            response.sendError(500);
        } finally {
            out.close();
        }
    }

}
