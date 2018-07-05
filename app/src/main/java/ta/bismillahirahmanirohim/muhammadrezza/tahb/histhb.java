package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static ta.bismillahirahmanirohim.muhammadrezza.tahb.R.attr.layoutManager;

public class histhb extends AppCompatActivity {
    private static final String TAG="histhb";
    private Toolbar toolbar1;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<FireModel> list;
    RecyclerView recycle;
    Button view;
    ImageButton locbtn;
    MenuItem reitem;
    private boolean isRefreshing;

    @Override
    protected void onStart() {
        super.onStart();
        RecyclerAdapter recyleradapter= new RecyclerAdapter(list,histhb.this);
//                RecyclerView.LayoutManager recyce= new GridLayoutManager(histhb.this,1);
        RecyclerView.LayoutManager recyce=new LinearLayoutManager(histhb.this);
        recycle.setLayoutManager(recyce);
//                recycle.addItemDecoration(new GridSpacingItemDecoration());
        recycle.setItemAnimator(new DefaultItemAnimator());
        recycle.setAdapter(recyleradapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histhb);

        view= (Button) findViewById(R.id.view);
        recycle= (RecyclerView) findViewById(R.id.recycle);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Data");

        toolbar1= (Toolbar) findViewById(R.id.app_bar2);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("History Hb");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list=new ArrayList<FireModel>();

                Log.d(TAG, "onDataChange: test");
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    FireModel value=dataSnapshot1.getValue(FireModel.class);
                    FireModel fire =new FireModel();
                    String date= value.getDatee();
                    String time= value.getTimee();
                    String Hblevels= value.getHblevels();
                    Log.d(TAG, "onDataChange: "+time+" "+date+" "+Hblevels);
                    fire.setDatee(date);
                    fire.setTimee(time);
                    fire.setHblevels(Hblevels);
                    list.add(fire);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w("Hello","Failed to read value", databaseError.toException() );

            }
        });


       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ini analisi ulang
                RecyclerAdapter recyleradapter= new RecyclerAdapter(list,histhb.this);
//                RecyclerView.LayoutManager recyce= new GridLayoutManager(histhb.this,1);
                RecyclerView.LayoutManager recyce=new LinearLayoutManager(histhb.this);
                recycle.setLayoutManager(recyce);
//                recycle.addItemDecoration(new GridSpacingItemDecoration());
                recycle.setItemAnimator(new DefaultItemAnimator());
                recycle.setAdapter(recyleradapter);
            }
        });*/




        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(histhb.this,healthinfo.class);
                startActivity(intent);finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menuhistory, menu);





        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.icrf) {

            RecyclerAdapter recyleradapter= new RecyclerAdapter(list,histhb.this);

            RecyclerView.LayoutManager recyce=new LinearLayoutManager(histhb.this);
            recycle.setLayoutManager(recyce);

            recycle.setItemAnimator(new DefaultItemAnimator());
            recycle.setAdapter(recyleradapter);

            return false;
        }

        return super.onOptionsItemSelected(item);
    }


}
