package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class measure extends AppCompatActivity {

    private static final String TAG="measure=<<";

    // Message types sent from the BluetoothReadService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_DEVICE_NAME = 4;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";

    Handler bluetoothIn;

    private Toolbar toolbar1;
    private ImageButton readhb;
    private ImageView icrotate;
    private LinearLayout recon;

    int handlerstate=0;
    public BluetoothAdapter btAdapter=null;
    public BluetoothSocket btSocket=null;
    public BluetoothDevice device;
    public StringBuilder recDataString = new StringBuilder();
//
    public ConnectedThread mConnectedThread;
    public ConnectThread mConnectThread;

    public String address;
    public static Boolean STATE;
    public static UUID BTMODULEUUID= UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //change this according to the network htdocs
    private static final String url = "http://192.168.137.1/TA/coba2.php";
    private StringRequest request;
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing!!!!!!!!
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    private ProgressDialog progress;

    public String sensor;



    //firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    FireHelp helper;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        toolbar1= (Toolbar) findViewById(R.id.app_bar2);
        readhb= (ImageButton) findViewById(R.id.btnreadhb);
        icrotate= (ImageView) findViewById(R.id.rotateicon);
        recon= (LinearLayout) findViewById(R.id.recon);

        recon.setClickable(true);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Measure Hb");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myRef=FirebaseDatabase.getInstance().getReference();
        helper=new FireHelp(myRef);

        final Animation animAlpha = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
        final Animation animRotate = AnimationUtils.loadAnimation(this,R.anim.anim_rotate);




        bluetoothIn = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==handlerstate){
                    byte[] rBuff= (byte[]) msg.obj;
                    String readMessage= new String(rBuff,0,msg.arg1);

                    recDataString.append(readMessage);

                    int endOfLineIndex= recDataString.indexOf("~");//determine end ofline

                    if (endOfLineIndex>0){
                        String dataInPrint = recDataString.substring(0,endOfLineIndex);
                        Log.d(TAG, "handleMessage: "+dataInPrint.length());
//                        tanggaldanwaktu();
//                        FireModel f=new FireModel();
//                        f.setDatee(date);
//                        f.setTimee(time);
                        // code to push firebase
//                         date time hbvalue
                        if (dataInPrint.length()<=6){
                            Toast.makeText(measure.this,"Try Again",Toast.LENGTH_SHORT).show();
                        }else {
                            sensor = dataInPrint.substring(1, 6);
//                            f.setHblevels(sensor);
//                            if (helper.save(f)){
//                                Toast.makeText(measure.this,"berhasil disimpan",Toast.LENGTH_SHORT).show();
//
//                            }else{
//                                Log.e(TAG, "handleMessage: cek Fire Help di save");
//                            }
                        }
                        Log.d(TAG, "handleMessage: "+sensor);



                        recDataString.delete(0,recDataString.length());
                        dataInPrint=" ";
                    }

                }

            }
        };
        btAdapter=BluetoothAdapter.getDefaultAdapter();
        checkBTState();
        checkBTPermissions();
        Intent intent = getIntent();
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        if (address==null) {
            checkadd();
        }

        readhb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mConnectedThread.write("1");

                if (sensor!=null) {
                    request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
//                        dissmissFlipprogressdialog();
//                        flip.dismiss();
//                        digpro.dismiss();
                        dismissLoadingDialog();
                            if (response.isEmpty()) {
                                ShowMessage("gagal nerima");
                            } else {
                                ShowMessage(response);
                                tanggaldanwaktu();
                                // code to push firebase
                                FireModel f=new FireModel();
                                f.setDatee(date);
                                f.setTimee(time);
                                f.setHblevels(response);
                                if (helper.save(f)){
                                    Toast.makeText(measure.this,"berhasil disimpan",Toast.LENGTH_SHORT).show();

                                }else{
                                    Log.e(TAG, "handleMessage: cek Fire Help di save");
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        dismissLoadingDialog();
//                        dissmissFlipprogressdialog();
//                        flip.dismiss();
//                        digpro.dismiss();

                            if (error instanceof com.android.volley.TimeoutError) {
                                ShowMessage("Time Out Error");
                            } else if (error instanceof com.android.volley.NoConnectionError) {
                                ShowMessage("No Connection Error");
                            } else if (error instanceof com.android.volley.AuthFailureError) {
                                ShowMessage("AuthFailureError");
                            } else if (error instanceof com.android.volley.NetworkError) {
                                ShowMessage("Network Error");
                            } else if (error instanceof com.android.volley.ServerError) {
                                ShowMessage("Server Error");
                            } else if (error instanceof com.android.volley.ParseError) {
                                ShowMessage("JSON Parse Error");
                            } else if (error instanceof com.android.volley.VolleyError) {
                                ShowMessage("Volley Error");
                            }
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> hashMap = new HashMap<>();
                            hashMap.put("HB",sensor);
                            Log.d(TAG, "getParams: "+sensor);
                            return hashMap;
                        }

                    };
                    request.setRetryPolicy(new DefaultRetryPolicy(
                            20000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                    view.startAnimation(animAlpha);
                    showLoadingDialog();

                }






            }
        });
        recon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icrotate.startAnimation(animRotate);
                mConnectedThread.cancel();
                btAdapter=BluetoothAdapter.getDefaultAdapter();
                checkBTState();
                checkBTPermissions();
                Intent intent = getIntent();
                address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                address=null;
                if (address==null) {
                    checkadd();
                }

            }
        });



        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(measure.this,MainActivity.class);
                startActivity(intent);finish();
            }
        });
    }

    private void checkadd() {
        if (address==null){
            Intent deviact = new Intent(measure.this,DeviceListActivity.class);
            startActivity(deviact);
        }
    }



   //masi ada yang salah
//    public Boolean save(FireModel fire){
//        Boolean saved;
//        if (fire==null){
//            saved=false;
//        }else{
//            try {
//                myRef.child("Data").push().setValue(fire);
//                saved=true;
//            }catch (DatabaseException e){
//                Log.e(TAG, "save: "+e );
//                saved=false;
//            }
//
//        }
//
//        return saved;
//    }

    int year,month,day,hour,minute;
    String date,time;
    public void tanggaldanwaktu(){
        Calendar cc= Calendar.getInstance();
        year=cc.get(Calendar.YEAR);
        month=cc.get(Calendar.MONTH)+1;
        day=cc.get(Calendar.DAY_OF_MONTH);
        hour=cc.get(Calendar.HOUR_OF_DAY);
        minute=cc.get(Calendar.MINUTE);
        date=day+"/"+month+"/"+year;
        time=hour+":"+minute;
    }
    private void ShowMessage(String msg) {
        Toast.makeText(measure.this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            boolean isSuccess = false;
            if(Build.VERSION.SDK_INT >= 17) {
                try {
//                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { int.class });
                    final Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                    tmp = (BluetoothSocket) m.invoke(device, 1);
                    isSuccess = true;
                } catch (Exception e) {
                    Log.i(TAG, "Could not create Insecure RFComm Connection", e);
                }
            }
            if (!isSuccess){
                try {
                    tmp = device.createRfcommSocketToServiceRecord(BTMODULEUUID);
                } catch (Exception e) {
                    Log.i(TAG, "Could not create Insecure RFComm Connection",e);
                }
            }
            mmSocket = tmp;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");

            // Always cancel discovery because it will slow down a connection
            btAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
                Log.i(TAG,"SOCKET CONNECTED");
            } catch (IOException e) {
                //connectionFailed();
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }
                // Start the service over to restart listening mode
                //BluetoothSerialService.this.start();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (measure.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private static final boolean D = true;
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (D) Log.d(TAG, "connected");

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

// Send the name of the connected device back to the UI Activity
        Message msg = bluetoothIn.obtainMessage(MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, device.getName());
        msg.setData(bundle);
        bluetoothIn.sendMessage(msg);

        setState(STATE_CONNECTED);
    }
    private synchronized void setState(int state) {
        if (D) Log.d(TAG, "setState() " + handlerstate + " -> " + state);
        handlerstate = state;

        // Give the new state to the Handler so the UI Activity can update
        bluetoothIn.obtainMessage(MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConnectedThread.cancel();
    }

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(measure.this);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setMessage("Predicting HB");
            progress.setCancelable(false);

        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
//        Log.e(TAG, "onResume: "+address );
//        Intent intent = getIntent();
//
//
//            get MAC add from devicelist act
//
//            get mac via extra
//
//
//            create device and set MAC
        if (address!=null) {
            device = btAdapter.getRemoteDevice(address);
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        }else{
            Log.e(TAG, "onResume: "+address );
        }
    }

    public class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private BluetoothSocket temp;
        public ConnectedThread(BluetoothSocket socket){
            InputStream tmpIn=null;
            OutputStream tmpOut=null;

            try {//create I/O stream for connection
                tmpIn=socket.getInputStream();
                tmpOut=socket.getOutputStream();

            }catch (IOException e){
                Log.i(TAG, "ConnectedThread: error get input="+e );
            }
            mmInStream=tmpIn;
            mmOutStream=tmpOut;
            temp=socket;
        }

        @Override
        public void run() {
            super.run();
            byte[] buffer = new byte[256];
            int bytes;
            btAdapter.cancelDiscovery();

            //keep looping to listen for received message

            while (STATE=true){
                try {
                    bytes =mmInStream.read(buffer); //read bytes from input buffer
                    String readMessage= new String(buffer,0,bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerstate,bytes,-1,buffer).sendToTarget();
                }catch (IOException e){
                    Log.e(TAG, "run: error loop to listen" );
                    break;
                }
            }
        }
        public void write(String input){
            byte[] msgBuffer = input.getBytes();//convert String into bytes
            try {
                mmOutStream.write(msgBuffer);////write bytes over BT connection via outstream
            }catch (IOException e){
                Log.i(TAG, "write: cant to send" );
            }
        }

        public void cancel() {
            try {
                STATE=false;
                temp.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    private void checkBTState() {
        if (btAdapter==null){
            Log.e(TAG, "checkBTState: no adpater" );
        }else{
            if (btAdapter.isEnabled()){
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,1);
            }
        }
    }
}
