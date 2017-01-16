/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.process;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public interface command {

    /**
     *
     * @param context
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception;

}
