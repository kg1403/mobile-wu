package pl.krakow.up.ii.opensource.mobilewu;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class OcenySecondActivity extends AppCompatActivity {

    TextView tvNazwa;
    TextView tvNazwaEdit;

    TextView tvWystawil;
    TextView tvWystawilEdit;

    TextView tvLiczbaGodzin;
    TextView tvLiczbaGodzinEdit;

    TextView tvTermin;
    TextView tvTerminEdit;

    TextView tvPoprawkowy;
    TextView tvPoprawkowyEdit;

    TextView tvKomisyjny;
    TextView tvKomisyjnyEdit;

    TextView tvEtcs;
    TextView tvEtcsEdit;

    List<String> list = new ArrayList<>();
    String[] listValue = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oceny_second);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //tabView lewej kolumny
        tvNazwa = findViewById(R.id.tvNazwa);
        tvWystawil = findViewById(R.id.tvWystawil);
        tvLiczbaGodzin = findViewById(R.id.tvLiczbaGodzin);
        tvTermin = findViewById(R.id.tvTermin);
        tvPoprawkowy = findViewById(R.id.tvPoprawkowy);
        tvKomisyjny = findViewById(R.id.tvKomisyjny);
        tvEtcs = findViewById(R.id.tvEtcs);
        //tabView prawej kolumny
        tvWystawilEdit = findViewById(R.id.tvWystawilEdit);
        tvLiczbaGodzinEdit = findViewById(R.id.tvLiczbaGodzinEdit);
        tvTerminEdit = findViewById(R.id.tvTerminEdit);
        tvPoprawkowyEdit = findViewById(R.id.tvPoprawkowyEdit);
        tvKomisyjnyEdit = findViewById(R.id.tvKomisyjnyEdit);
        tvEtcsEdit = findViewById(R.id.tvEtcsEdit);


        Intent intent = getIntent();
        int a = intent.getIntExtra("IdNazwa",0);
        ArrayList<String> Typ = intent.getStringArrayListExtra("Typ");
        tvNazwa.setText(intent.getStringExtra("Nazwa"));
        for (int i=0;i<Typ.size();i++){
            if (i==2){tvWystawil.setText(Typ.get(i));}
            if (i==3){tvLiczbaGodzin.setText(Typ.get(i));}
            if (i==4){tvTermin.setText(Typ.get(i));}
            if (i==5){tvPoprawkowy.setText(Typ.get(i));}
            if (i==6){tvKomisyjny.setText(Typ.get(i));}
            if (i==7){tvEtcs.setText(Typ.get(i));}
        }
        tvWystawilEdit.setText(intent.getStringExtra("Wystawil"));
        tvLiczbaGodzinEdit.setText(intent.getStringExtra("Godziny"));
        tvTerminEdit.setText(intent.getStringExtra("TerminI"));
        tvPoprawkowyEdit.setText(intent.getStringExtra("Poprawkowy"));
        tvKomisyjnyEdit.setText(intent.getStringExtra("Komisyjny"));
        tvEtcsEdit.setText(intent.getStringExtra("ETCS"));


    }
}
