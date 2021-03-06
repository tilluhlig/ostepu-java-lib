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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
 */
public interface command {

    /**
     * das ist ein REST-Aufruf, der entsprechend einer definierten
     * Zusammenstellung aus METHODE und PFAD von jemandem aufgerufen werden kann
     *
     * @param context  der Serverkontent
     * @param request  der eingehende Aufruf
     * @param response das Rückgabeobjekt
     */
    void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response);

}
