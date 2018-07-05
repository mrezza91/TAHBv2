package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;

public class DeviceListActivity extends AppCompatActivity {

    private static final String TAG="devicelistact<<";

    ListView pairedListView;
    public static String EXTRA_DEVICE_ADDRESS="device_address";

    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width= dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.8));


        mBtAdapter=BluetoothAdapter.getDefaultAdapter();
        // Initialize array adapter for paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // Find and set up the ListView for paired devices
        pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);


    }
    @Override
    protected void onResume() {
        super.onResume();
        checkBTState();

        mPairedDevicesArrayAdapter=new ArrayAdapter<String>(this,R.layout.device_name);

        // Get a set of currently paired devices and append to pairedDevices list
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();



        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        if (pairedDevices.size()>0){
//            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device: pairedDevices){
                mPairedDevicesArrayAdapter.add(device.getName()+"\n"+device.getAddress());
            }
        }else {
            mPairedDevicesArrayAdapter.add("NO PAIR DEVICE (PAIR DEVICE IN SETTINGS)");
        }

        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

    }

    private AdapterView.OnItemClickListener mDeviceClickListener= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemClick:Connecting.....");
            //get MAC add
            String info = ((TextView)view).getText().toString();
            String address= info.substring(info.length()-17);
            // make intent to start activity while takrong an extra whhich is mac add
            Intent x = new Intent(DeviceListActivity.this,measure.class);
            x.putExtra(EXTRA_DEVICE_ADDRESS,address);
            startActivity(x);
            finish();
        }
    };

    private void checkBTState() {
        if(mBtAdapter==null) {
            Log.e(TAG, "checkBTState: BT null" );
        } else {
            if (mBtAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);

            }
        }
    }
}


