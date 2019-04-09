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

public class PlanStudiowSecondActivity extends AppCompatActivity {

    TextView tvNazwaKursu;
    TextView tvNazwaKursuEdit;

    TextView tvProwadzacy;
    TextView tvProwadzacyEdit;

    TextView tvFormaZajec;
    TextView tvFormaZajecEdit;

    TextView tvGodziny;
    TextView tvGodzinyEdit;

    TextView tvECTS;
    TextView tvECTSEdit;

    TextView tvFormaZal;
    TextView tvFormaZalEdit;

    TextView tvGrupa;
    TextView tvGrupaEdit;

    List<String> list = new ArrayList<>();
    String[] listValue = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_planstudiow_second);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //tabView lewej kolumny
        tvNazwaKursu = findViewById(R.id.tvNazwaKursu);
        tvProwadzacy = findViewById(R.id.tvProwadzacy);
        tvFormaZajec = findViewById(R.id.tvFormaZajec);
        tvGodziny = findViewById(R.id.tvGodziny);
        tvECTS = findViewById(R.id.tvECTS);
        tvFormaZal = findViewById(R.id.tvFormaZal);
        tvGrupa = findViewById(R.id.tvGrupa);
        //tabView prawej kolumny
        tvProwadzacyEdit = findViewById(R.id.tvProwadzacyEdit);
        tvFormaZajecEdit = findViewById(R.id.tvFormaZajecEdit);
        tvGodzinyEdit = findViewById(R.id.tvGodzinyEdit);
        tvECTSEdit = findViewById(R.id.tvECTSEdit);
        tvFormaZalEdit = findViewById(R.id.tvFormaZalEdit);
        tvGrupaEdit = findViewById(R.id.tvGrupaEdit);


        Intent intent = getIntent();
        int a = intent.getIntExtra("IdNazwa",0);
        String[] Typ = intent.getStringArrayExtra("Typ");
        tvNazwaKursu.setText(intent.getStringExtra("Nazwa"));
        for (int i=0;i<Typ.length;i++){
            if (i==2){tvProwadzacy.setText(Typ[i]);}
            if (i==3){tvFormaZajec.setText(Typ[i]);}
            if (i==4){tvGodziny.setText(Typ[i]);}
            if (i==5){tvECTS.setText(Typ[i]);}
            if (i==6){tvFormaZal.setText(Typ[i]);}
            if (i==7){tvGrupa.setText(Typ[i]);}
        }
        tvProwadzacyEdit.setText(intent.getStringExtra("Prowadzący"));
        tvFormaZajecEdit.setText(intent.getStringExtra("Forma zajęć"));
        tvGodzinyEdit.setText(intent.getStringExtra("Liczba godzin"));
        tvECTSEdit.setText(intent.getStringExtra("Pkt. ECTS"));
        tvFormaZalEdit.setText(intent.getStringExtra("Forma zaliczenia"));
        tvGrupaEdit.setText(intent.getStringExtra("Nazwa grupy"));


    }
}
