package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("TAHB");
        getSupportActionBar().setIcon(R.drawable.ico_logo);

        final Animation animAlpha = AnimationUtils.loadAnimation(this,
                R.anim.anim_alpha);

        Button btnmenu1 = (Button) findViewById(R.id.menu1);
        Button btnmenu2 = (Button) findViewById(R.id.menu2);
        Button btnmenu3 = (Button) findViewById(R.id.menu3);
        Button btnmenu4 = (Button) findViewById(R.id.menu4);
        btnmenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                Intent intent= new Intent(MainActivity.this,advice.class);
                startActivity(intent);
                finish();
            }
        });
        btnmenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                Intent intent= new Intent(MainActivity.this,healthinfo.class);
                startActivity(intent);
                finish();


            }
        });
        btnmenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                Intent intent= new Intent(MainActivity.this,measure.class);
                startActivity(intent);
                finish();
            }
        });
        btnmenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                Intent intent= new Intent(MainActivity.this,touse.class);
                startActivity(intent);
                finish();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.toolabout) {
            Intent intent = new Intent(MainActivity.this,aboutactivity.class);
            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
