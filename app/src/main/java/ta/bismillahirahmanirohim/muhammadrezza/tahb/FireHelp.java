package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.util.Log;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by lenovo Z410p on 18/09/2017.
 */

public class FireHelp {
    DatabaseReference db;
    Boolean saved=null;
    private static final String TAG="FireHelp<<:";

    public FireHelp(DatabaseReference db){
        this.db=db;
    }

    public Boolean save(FireModel fire) {
        if (fire==null){
            saved=false;
        }else {
            try {
                db.child("Data").push().setValue(fire);
                saved=true;
            }catch (DatabaseException e){
                Log.e(TAG, "save: fail to save to Firebase" );
                saved=false;
            }
        }
        return saved;
    }
}
