package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class touse extends AppCompatActivity {

    private Toolbar toolbar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touse);

        toolbar1= (Toolbar) findViewById(R.id.app_bar2);

        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("How To Use");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(touse.this,MainActivity.class);
                startActivity(intent);finish();
            }
        });
    }
}
