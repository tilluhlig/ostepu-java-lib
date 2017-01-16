/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.cconfig;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import static ostepu.cconfig.info.getCommands;
import static ostepu.cconfig.info.getInfo;
import static ostepu.cconfig.info.getLinks;

/**
 *
 * @author Till
 */
public class help {

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

        if (pathInfo == null) {
            System.out.println("ff");
            response.sendError(404);
            return;
        }

        if (!"GET".equals(request.getMethod())) {
            System.out.println("bb");
            response.sendError(404);
            return;
        }

        PrintWriter out = response.getWriter();
        String[] helpPath = pathInfo.split("/");
        String extension = FilenameUtils.getExtension(pathInfo);
        String language = helpPath[1];
        helpPath[helpPath.length - 1] = helpPath[helpPath.length - 1].substring(0, helpPath[helpPath.length - 1].length() - extension.length() - 1);
        helpPath = Arrays.copyOfRange(helpPath, 2, helpPath.length);

        Path helpFile = Paths.get(context.getRealPath("data/help/" + String.join("_", helpPath) + "_" + language + "." + extension));

        try {
            if (Files.exists(helpFile)) {
                out.write(String.join("\n", Files.readAllLines(helpFile)));
                response.setStatus(200);
            } else {
                response.sendError(404);
            }
        } finally {
            out.close();
        }
    }
}
