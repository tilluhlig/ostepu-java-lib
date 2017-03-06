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
package ostepu.process.commands;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ostepu.cconfig.cconfig;
import ostepu.process.command;
import ostepu.request.httpAuth;
import ostepu.request.httpRequestResult;
import ostepu.structure.component;
import ostepu.structure.link;

/**
 * dieser Befehl prüft, ob die Komponente korrekt als Verarbeitung eingetragen
 * ist
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class getCourseExists implements command {

    @Override
    public void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(getCourseExists.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
            return;
        }
        component conf = cconfig.loadConfig(context);
        JsonObject mycomponent = cconfig.loadComponent(context);

        link getProcess = cconfig.getLink(conf, "getProcess");

        String[] pathInfo = request.getPathInfo().split("/");
        if (pathInfo.length < 4) {
            // der eingehende Aufruf muss die korrekt Form haben
            response.setStatus(500);
            return;
        }

        String courseid = pathInfo[3];
        String componentid = conf.getId();

        // hier werden die Platzhalter vorbereitet
        Map<String, String> placeholder = new HashMap<String, String>();
        placeholder.put("courseid", courseid);
        placeholder.put("componentid", componentid);

        // hier wird OSTEPU aufgerufen und gefragt
        httpRequestResult result = cconfig.callConstLink(mycomponent, getProcess, "", placeholder, new httpAuth());

        if (result.getStatus() == 200) {
            // wird wurden korrekt gefunden
            response.setStatus(200);
            return;
        }

        // wir wurden nicht gefunden
        response.setStatus(404);
    }

}
