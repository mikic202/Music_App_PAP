package server.DatabaseInteractors;

import java.util.Hashtable;

public interface DataSetterInterface {
    static void setData(int id, Hashtable<String, String> data) {
    }

    static int addData(Hashtable<String, String> data) {
        return 0;
    }
}
