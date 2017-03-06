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
import ostepu.request.httpAuth;
import ostepu.request.httpRequestResult;
import ostepu.structure.component;
import ostepu.structure.course;
import ostepu.structure.link;

/**
 * dieser Befehl trägt unseren Webservice als Verarbeitung bei OSTEPU ein
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class postCourse implements command {

    @Override
    public void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(postCourse.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
            return;
        }
        component conf = cconfig.loadConfig(context);
        JsonObject mycomponent = cconfig.loadComponent(context);

        link postProcess = cconfig.getLink(conf, "postProcess");

        String incomingCourseString;
        try {
            incomingCourseString = IOUtils.toString(request.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(postCourse.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
            return;
        }
        course courseObject = (course) course.decode(incomingCourseString);

        if (courseObject.getId() == null) {
            try {
                response.sendError(500, "missing courseid");
            } catch (IOException ex) {
                Logger.getLogger(postCourse.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
            }
            return;
        }

        String courseid = courseObject.getId();
        String componentid = conf.getId();

        httpRequestResult result = cconfig.callConstLink(mycomponent, postProcess, "[{\"exercise\":{\"courseId\":\"" + courseid + "\"},\"target\":{\"id\":\"" + componentid + "\"}}]", null, new httpAuth());

        if (result.getStatus() != 201) {
            try {
                response.sendError(500, "sign up has failed");
            } catch (IOException ex) {
                Logger.getLogger(postCourse.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
            }
            return;
        }

        response.setStatus(201);
    }

}
