/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.process.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ostepu.cconfig.cconfig;
import static ostepu.cconfig.cconfig.loadComponent;
import ostepu.cconfig.control;
import ostepu.process.command;
import ostepu.request.httpRequestResult;
import ostepu.structure.component;
import ostepu.structure.link;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class getCourseExists implements command{

    @Override
    public void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        PrintWriter out = response.getWriter();
        component conf = cconfig.loadConfig(context);
        JsonObject mycomponent = cconfig.loadComponent(context);
        
        link getProcess = cconfig.getLink(conf, "getProcess");
        
        String[] pathInfo = request.getPathInfo().split("/");
        if (pathInfo.length<4){
            response.setStatus(500);
            return;
        }
        
        String courseid = pathInfo[3];
        String componentid = conf.getId();
        Map<String,String> placeholder = new HashMap<String,String>();
        placeholder.put("courseid", courseid);
        placeholder.put("componentid", componentid);
        
        httpRequestResult result = cconfig.callConstLink(mycomponent, getProcess, "", placeholder);

        if (result.getStatus() == 200){
            response.setStatus(200);
            return;
        }
        
        response.setStatus(404);
    }
    
}
