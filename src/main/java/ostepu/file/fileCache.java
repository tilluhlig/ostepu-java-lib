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
package ostepu.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

/**
 * diese Klasse speichert und liefert Dateien
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class fileCache {

    /**
     * sichert eine Datei lokal (unter dem Hash ihrer URL)
     *
     * @param context der Kontext des Servlet
     * @param content der Inhalt der Datei
     * @param URL     der Pfad/URL der Datei, für den Namen
     */
    public static void cacheFile(ServletContext context, byte[] content, String URL) {
        String fileHash = DigestUtils.sha512Hex(URL);
        String localFile = context.getRealPath("cache/" + fileHash);

        File folder = new File(context.getRealPath("cache"));

        if (!folder.exists()) {
            // wenn der Ordner nicht existert, versuchen wir ihn anzulegen
            boolean succeeded = folder.mkdir();
            if (!succeeded) {
                // wenn wir den Ordner nicht anlegen konnten, bringt das cachen nichts
                return;
            }
        }

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
     * entfernt alle gespeicherten Dateien
     *
     * @param context der Kontext des Servlet
     */
    public static void cleanCache(ServletContext context) {
        File folder = new File(context.getRealPath("cache"));
        if (folder.exists() && folder.isDirectory()) {
            try {
                FileUtils.deleteDirectory(folder);
            } catch (IOException ex) {
                Logger.getLogger(fileCache.class.getName()).log(Level.SEVERE, null, ex);
                // wenn es nicht gelöscht werden kann, dann kann ich es auch
                // nicht ändern
                return;
            }
        }
    }

    /**
     * liefert den lokalen Pfad einer Datei
     *
     * @param context der Kontext des Servlet
     * @param URL     der Pfad/URL der Datei, für den Namen
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
            // Datei konnte nicht gefunden werden
            return null;
        }
        // Datei konnte nicht gefunden werden
        return null;
    }

}
