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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class postCourse implements command {

    @Override
    public void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        PrintWriter out = response.getWriter();
        component conf = cconfig.loadConfig(context);
        JsonObject mycomponent = cconfig.loadComponent(context);
        
        link postProcess = cconfig.getLink(conf, "postProcess");
        
        String incomingCourseString = IOUtils.toString(request.getInputStream());
        course courseObject = (course) course.decode(incomingCourseString);
        
        if (courseObject.getId() == null){
            response.sendError(500, "missing courseid");
            return;
        }
        
        String courseid = courseObject.getId();
        String componentid = conf.getId();
        
        httpRequestResult result = cconfig.callConstLink(mycomponent, postProcess, "[{\"exercise\":{\"courseId\":\""+courseid+"\"},\"target\":{\"id\":\""+componentid+"\"}}]", null, new httpAuth());
        
        if (result.getStatus() != 201){
            response.sendError(500, "sign up has failed");
            return;
        }
        
        response.setStatus(201);
    }

}
