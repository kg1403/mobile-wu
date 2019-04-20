package pl.krakow.up.ii.opensource.mobilewu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class PlanZajecSecondActivity extends AppCompatActivity {

    TextView tvNazwa;
    TextView tvNazwaEdit;

    TextView tvProwadzacy;
    TextView tvProwadzacyEdit;

    TextView tvSala;
    TextView tvSalaEdit;

    TextView tvAdres;
    TextView tvAdresEdit;

    TextView tvForma;
    TextView tvFormaEdit;

    TextView tvZaliczenie;
    TextView tvZaliczenieEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_plan_zajec_second);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tvNazwa = findViewById(R.id.tvNazwa);
        tvProwadzacy = findViewById(R.id.tvProwadzacy);
        tvSala = findViewById(R.id.tvSala);
        tvAdres = findViewById(R.id.tvAdres);
        tvForma = findViewById(R.id.tvForma);
        tvZaliczenie = findViewById(R.id.tvZaliczenie);

        tvProwadzacyEdit = findViewById(R.id.tvProwadzacyEdit);
        tvSalaEdit = findViewById(R.id.tvSalaEdit);
        tvAdresEdit = findViewById(R.id.tvAdresEdit);
        tvFormaEdit = findViewById(R.id.tvFormaEdit);
        tvZaliczenieEdit = findViewById(R.id.tvZaliczenieEdit);


        Intent intent = getIntent();
        int a = intent.getIntExtra("IdNazwa",0);
        ArrayList<String> Typ = intent.getStringArrayListExtra("Typ");
        tvNazwa.setText(intent.getStringExtra("Nazwa"));
        for (int i=4;i<Typ.size();i++){
            if (i==4){tvProwadzacy.setText(Typ.get(i)+": ");}
            if (i==5){tvSala.setText(Typ.get(i)+": ");}
            if (i==6){tvAdres.setText(Typ.get(i)+": ");}
            if (i==7){tvForma.setText(Typ.get(i)+": ");}
            if (i==8){tvZaliczenie.setText(Typ.get(i)+": ");}
        }
        tvProwadzacyEdit.setText(intent.getStringExtra("Prowadzacy"));
        tvSalaEdit.setText(intent.getStringExtra("Sala"));
        tvAdresEdit.setText(intent.getStringExtra("Adres"));
        tvFormaEdit.setText(intent.getStringExtra("Forma"));
        tvZaliczenieEdit.setText(intent.getStringExtra("Zaliczenie"));
    }
}
