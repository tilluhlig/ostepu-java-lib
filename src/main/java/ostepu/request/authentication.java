/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.request;

import java.net.HttpURLConnection;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public abstract class authentication {

    /**
     *
     * @param connection
     */
    public abstract void performAuth(HttpURLConnection connection);

}
