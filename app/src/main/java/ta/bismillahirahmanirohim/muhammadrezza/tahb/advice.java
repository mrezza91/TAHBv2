package ta.bismillahirahmanirohim.muhammadrezza.tahb;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.ms.square.android.expandabletextview.ExpandableTextView;

public class advice extends AppCompatActivity {

    private Toolbar toolbar1;
    TextView text;
    String longtext="Hemoglobin is the protein molecule in red blood cells that carries oxygen from the lungs to the body's tissues and returns carbon dioxide from the tissues back to the lungs.\n" +
            "\n" +
            "Hemoglobin is made up of four protein molecules (globulin chains) that are connected together. The normal adult hemoglobin (abbreviated Hgb or Hb) molecule contains two alpha-globulin chains and two beta-globulin chains. In fetuses and infants, beta chains are not common and the hemoglobin molecule is made up of two alpha chains and two gamma chains. As the infant grows, the gamma chains are gradually replaced by beta chains, forming the adult hemoglobin structure.\n" +
            "\n" +
            "Each globulin chain contains an important iron-containing porphyrin compound termed heme. Embedded within the heme compound is an iron atom that is vital in transporting oxygen and carbon dioxide in our blood. The iron contained in hemoglobin is also responsible for the red color of blood.\n" +
            "\n" +
            "Hemoglobin also plays an important role in maintaining the shape of the red blood cells. In their natural shape, red blood cells are round with narrow centers resembling a donut without a hole in the middle. Abnormal hemoglobin structure can, therefore, disrupt the shape of red blood cells and impede their function and flow through blood vessels.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);



        toolbar1= (Toolbar) findViewById(R.id.app_bar2);
        text= (TextView) findViewById(R.id.txt);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Health Advice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        text.setText(longtext);



        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(advice.this,MainActivity.class);
                startActivity(intent);finish();
            }
        });


    }
}
