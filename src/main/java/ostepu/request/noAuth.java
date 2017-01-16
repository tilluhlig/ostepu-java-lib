/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.request;

import java.net.HttpURLConnection;
import java.util.Base64;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class noAuth extends authentication {

    /**
     *
     * @param connection
     */
    @Override
    public void performAuth(HttpURLConnection connection) {
        // wir müssen hier nichts mehr machen
        return;
    }

}
