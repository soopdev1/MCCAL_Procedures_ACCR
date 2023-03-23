/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.so.accreditamento;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;

/**
 *
 * @author Administrator
 */
public class MainSelector {

    public static final ResourceBundle rb = ResourceBundle.getBundle("conf.conf");
    public static final Logger log = createLog(rb.getString("name.log"), rb.getString("path.log"), rb.getString("date.log"));

    public static void main(String[] args) {

        String dbname;

        try {
            dbname = args[0];
        } catch (Exception e) {
            dbname = "bandoi9";
        }

        log.info("ACCREDITAMENTO START");
        try {
            Db_Bando db = new Db_Bando(dbname);
            ArrayList<String> al = db.getUsername();
            for (int i = 0; i < al.size(); i++) {
                DomandeComplete dc = db.domandeComplete("BA0F6", al.get(i));
                log.log(Level.WARNING, "{0} - {1}", new Object[]{dc.getUsername(), db.insertBandoH8(dc)});
            }
            db.expexcel_completo();
            db.closeDB();
        } catch (Exception ex) {
            log.severe(estraiEccezione(ex));
        }
        log.info("ACCREDITAMENTO END");

    }

    public static String formatAL(String cod, ArrayList<String[]> array, int index) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((String[]) array.get(i))[0])) {
                return ((String[]) array.get(i))[index];
            }
        }
        return "-";
    }

    public static String estraiEccezione(Exception ec1) {
        try {
            String stack_nam = ec1.getStackTrace()[0].getMethodName();
            String stack_msg = ExceptionUtils.getStackTrace(ec1);
            return stack_nam + " - " + stack_msg;
        } catch (Exception e) {
        }
        return ec1.getMessage();

    }

    private static Logger createLog(String appname, String folderini, String patterndatefolder) {
        Logger LOGGER = Logger.getLogger(appname);
        try {
            DateTime dt = new DateTime();
            String filename = appname + "-" + dt.toString("HHmmssSSS") + ".log";
            File dirING = new File(folderini);
            dirING.mkdirs();
            if (patterndatefolder != null) {
                File dirINGNew = new File(dirING.getPath() + File.separator + dt.toString(patterndatefolder));
                dirINGNew.mkdirs();
                filename = dirINGNew.getPath() + File.separator + filename;
            } else {
                filename = dirING.getPath() + File.separator + filename;
            }
            Handler fileHandler = new FileHandler(filename);
            LOGGER.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);
        } catch (Exception localIOException) {
        }

        return LOGGER;
    }

}