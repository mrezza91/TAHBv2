package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by lenovo Z410p on 25/08/2017.
 */

public class gethbinfo extends AsyncTask<Void, Void, Void> {

    private Context mCon;
    public gethbinfo(Context con){
        mCon=con;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{Thread.sleep(4000);
            return null;

        }catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(mCon, "Finished complex background function!",
                Toast.LENGTH_LONG).show();
    }
}
