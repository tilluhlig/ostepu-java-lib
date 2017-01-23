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
package ostepu.process;

import ostepu.process.commands.postCourse;
import ostepu.process.commands.deleteCourse;
import ostepu.process.commands.getCourseExists;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import ostepu.process.commands.postProcess;
import ostepu.request.httpAuth;

/**
 *
 * @author Till
 */
public class process extends HttpServlet {

    /**
     *
     */
    public String[][] restPattern = {
        {"POST", "/process"},
        {"POST", "/course"},
        {"DELETE", "/course/([0-9_]*)"},
        {"GET", "/link/exists/course/([0-9_]*)"}};

    /**
     *
     */
    public command[] restCommands = {new postProcess(),new postCourse(), new deleteCourse(), new getCourseExists()};

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pathInfo = StringUtils.substring(request.getRequestURI(), request.getContextPath().length());
        
        // lädt die Anmeldedaten (eventuell für eine httpAuth)
        httpAuth.loadLocalAuthData(getServletContext());

        String method = request.getMethod();
        try {
            Matcher matcher;
            for (int i = 0; i < restPattern.length; i++) {
                String[] pattern = restPattern[i];
                if (method == null ? pattern[0] != null : !method.equals(pattern[0])) {
                    continue;
                }
                Pattern regExGetCommands = Pattern.compile(pattern[1]);
                matcher = regExGetCommands.matcher(pathInfo);
                if (matcher.find()) {
                    if (restCommands[i] != null) {
                        restCommands[i].execute(getServletContext(), request, response);
                    }
                    return;
                }
            }

        } finally {
            out.close();
        }
        response.sendError(409);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
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

}
