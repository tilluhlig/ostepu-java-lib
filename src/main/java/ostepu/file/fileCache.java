/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class fileCache {

    /**
     * sichert eine Datei lokal (unter dem Hash ihrer URL)
     *
     * @param context der Kontext des Servlet
     * @param content der Inhalt der Datei
     * @param URL
     */
    public static void cacheFile(ServletContext context, byte[] content, String URL) {
        String fileHash = DigestUtils.sha512Hex(URL);
        String localFile = context.getRealPath("cache/" + fileHash);

        try {
            if (!Files.exists(Paths.get(localFile))) {
                OutputStream outputStream = new FileOutputStream(new File(localFile));
                outputStream.write(content, 0, content.length);
                outputStream.close();
            }
        } catch (IOException e) {
            // do something
        }
    }

    /**
     *
     * @param context
     */
    public static void cleanCache(ServletContext context) {
        // todo: muss implementiert werden
    }

    /**
     * liefert den lokalen Pfad einer Datei
     *
     * @param context der Kontext des Servlet
     * @param URL
     * @return der Pfad (oder null, wenn sie nicht existert)
     */
    public static String getCachedFilePath(ServletContext context, String URL) {
        // liefert einen lokalen Pfad oder null
        String fileHash = DigestUtils.sha512Hex(URL);
        String localFile = context.getRealPath("cache/" + fileHash);

        try {
            if (Files.exists(Paths.get(localFile))) {
                return localFile;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
