package DatabaseInteractors;

import java.util.Hashtable;

public interface DataSetterInterface {
    void set_data(int id, Hashtable<String, String> data);

    void add_data(Hashtable<String, String> data);
}
