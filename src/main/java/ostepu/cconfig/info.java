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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.google.gson.*;

import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

/**
 *
 * @author Till
 */
public class info {

    private static Pattern regExGetCommands = Pattern.compile("/commands");
    private static Pattern regExGetComponent = Pattern.compile("/component");
    private static Pattern regExGetInfo = Pattern.compile("/([a-z]*)");

    private static Pattern regExGetLinks = Pattern.compile("/links");

    /**
     *
     * @param context
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void getCommands(ServletContext context, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Path commandsPath = Paths.get(context.getRealPath("data/commands.json"));

        try {
            if (Files.exists(commandsPath)) {
                List<String> commands = Files.readAllLines(commandsPath);
                JsonElement obj = new JsonParser().parse(String.join("", commands));
                JsonArray aa = obj.getAsJsonArray();

                aa.add(new JsonParser().parse("{\"method\":\"GET\", \"path\":\"/info/commands\"}"));
                aa.add(new JsonParser().parse("{\"method\":\"GET\", \"path\":\"/info/links\"}"));
                aa.add(new JsonParser().parse("{\"method\":\"GET\", \"path\":\"/info/:language\"}"));
                aa.add(new JsonParser().parse("{\"method\":\"POST\", \"path\":\"/control\"}"));
                aa.add(new JsonParser().parse("{\"method\":\"GET\", \"path\":\"/help/:language/path+\"}"));

                out.print(aa.toString());
                response.setStatus(200);
            } else {
                response.setStatus(200);
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
    public static void getComponent(ServletContext context, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Path componentPath = Paths.get(context.getRealPath("data/component.json"));

        try {
            if (Files.exists(componentPath)) {
                out.print(String.join("", Files.readAllLines(componentPath)));
            } else {
                response.setStatus(200);
                out.print("[]");
            }
        } finally {
            out.close();
        }
    }

    /**
     *
     * @param context
     * @param language
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void getInfo(ServletContext context, String language, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String infoFile = context.getRealPath("data/help/" + language + ".md");
        String defaultInfoFile = context.getRealPath("data/help/" + language + ".md");
        PrintWriter out = response.getWriter();

        try {
            if (Files.exists(Paths.get(infoFile))) {
                response.setStatus(200);
                out.print(String.join("", Files.readAllLines(Paths.get(infoFile))));
            } else {
                if (Files.exists(Paths.get(defaultInfoFile))) {
                    response.setStatus(200);
                    out.print(String.join("", Files.readAllLines(Paths.get(defaultInfoFile))));
                } else {
                    response.setStatus(404);
                }
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
    public static void getLinks(ServletContext context, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        ServletOutputStream out = response.getOutputStream();
        Path componentPath = Paths.get(context.getRealPath("data/component.json"));

        try {
            if (Files.exists(componentPath)) {
                List<String> content = Files.readAllLines(componentPath);
                JsonElement obj = new JsonParser().parse(String.join("", content));
                JsonObject aa = obj.getAsJsonObject();

                JsonElement newObject = aa.get("links");
                if (newObject == null) {
                    out.print("[]");
                    response.setStatus(200);
                } else {
                    out.print(newObject.toString());
                    response.setStatus(200);
                }
            } else {
                response.setStatus(200);
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
            throws ServletException, IOException, Exception {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            response.sendError(404);
            return;
        }

        if (!"GET".equals(request.getMethod())) {
            response.sendError(404);
            return;
        }

        // regex parse pathInfo
        Matcher matcher;
        matcher = regExGetCommands.matcher(pathInfo);
        if (matcher.find()) {
            getCommands(context, request, response);
            return;
        }

        matcher = regExGetComponent.matcher(pathInfo);
        if (matcher.find()) {
            getComponent(context, request, response);
            return;
        }

        matcher = regExGetLinks.matcher(pathInfo);
        if (matcher.find()) {
            getLinks(context, request, response);
            return;
        }

        matcher = regExGetInfo.matcher(pathInfo);
        if (matcher.find()) {
            String language = matcher.group(1);
            getInfo(context, language, request, response);
            return;
        }

        response.sendError(404);
    }

}
