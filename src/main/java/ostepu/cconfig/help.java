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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;

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
