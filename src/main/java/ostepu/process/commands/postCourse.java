/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        
        httpRequestResult result = cconfig.callConstLink(mycomponent, postProcess, "[{\"exercise\":{\"courseId\":\""+courseid+"\"},\"target\":{\"id\":\""+componentid+"\"}}]", null);
        
        if (result.getStatus() != 201){
            response.sendError(500, "sign up has failed");
            return;
        }
        
        response.setStatus(201);
    }

}
