/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.so.accreditamento;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class MainSelector {

    public static void main(String[] args) {

        String dbname;

        try {
            dbname = args[0];
        } catch (Exception e) {
            dbname = "bandoi9";
        }

        System.out.println("ACCREDITAMENTO START");
        try {
            Db_Bando db = new Db_Bando(dbname);
            ArrayList<String> al = db.getUsername();
            for (int i = 0; i < al.size(); i++) {
                DomandeComplete dc = db.domandeComplete("BA0F6", al.get(i));
                System.out.println(dc.getUsername() + " - " + db.insertBandoH8(dc));
            }
            db.expexcel_completo();
            db.closeDB();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("ACCREDITAMENTO END");

    }

    public static String formatAL(String cod, ArrayList<String[]> array, int index) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((String[]) array.get(i))[0])) {
                return ((String[]) array.get(i))[index];
            }
        }
        return "-";
    }

}
