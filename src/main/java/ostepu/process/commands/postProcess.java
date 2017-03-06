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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import ostepu.cconfig.cconfig;
import ostepu.process.command;
import ostepu.structure.component;
import ostepu.structure.process;

/**
 * Dieser Befehl bearbeitet eingehende Korrekturanfragen (wenn ein Student also
 * etwas einsendet) Dabei handelt es sich hierbei allerdings nur um ein DUMMY,
 * falls keine konkrete Umsetzung anderweitig implementiert wurde
 *
 * @author Till
 */
public class postProcess implements command {

    @Override
    public void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(postProcess.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
            return;
        }

        component conf = cconfig.loadConfig(context);
        JsonObject mycomponent = cconfig.loadComponent(context);

        String incomingProcessString;
        try {
            incomingProcessString = IOUtils.toString(request.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(postProcess.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
            return;
        }

        // die Eingabe wird gelesen und zu einem process-Objekt umgewandelt,
        // um den Status zu setzen
        process processObject = (process) process.decode(incomingProcessString);
        processObject.setStatus("201");

        // einfach wieder zurück senden
        out.write(processObject.encode());
        response.setStatus(201);
    }

}
