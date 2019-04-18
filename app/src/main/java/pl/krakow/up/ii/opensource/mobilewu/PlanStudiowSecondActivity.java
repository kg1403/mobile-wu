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

    TextView tvDokumenty;
    TextView tvDokumentyEdit;

    TextView tvGrupa;
    TextView tvGrupaEdit;


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
        tvDokumenty = findViewById(R.id.tvDokumenty);
        tvGrupa = findViewById(R.id.tvGrupa);
        //tabView prawej kolumny
        tvProwadzacyEdit = findViewById(R.id.tvProwadzacyEdit);
        tvFormaZajecEdit = findViewById(R.id.tvFormaZajecEdit);
        tvGodzinyEdit = findViewById(R.id.tvGodzinyEdit);
        tvECTSEdit = findViewById(R.id.tvECTSEdit);
        tvFormaZalEdit = findViewById(R.id.tvFormaZalEdit);
        tvDokumentyEdit = findViewById(R.id.tvDokumentyEdit);
        tvGrupaEdit = findViewById(R.id.tvGrupaEdit);


        Intent intent = getIntent();
        int a = intent.getIntExtra("IdNazwa",0);
        ArrayList Typ = intent.getStringArrayListExtra("Typ");
        tvNazwaKursu.setText(intent.getStringExtra("Nazwa"));
        for (int i=2;i<Typ.size();i++){
            if (i==2){
                tvProwadzacy.setText(Typ.get(i).toString()+": ");
                tvProwadzacyEdit.setText(intent.getStringExtra("Prowadzacy"));
            }
            if (i==3){
                tvFormaZajec.setText(Typ.get(i).toString()+": ");
                tvFormaZajecEdit.setText(intent.getStringExtra("FormaZajec"));
            }
            if (i==4){
                tvGodziny.setText(Typ.get(i).toString()+": ");
                tvGodzinyEdit.setText(intent.getStringExtra("LiczbaGodzin"));
            }
            if (i==5){
                tvECTS.setText(Typ.get(i).toString()+": ");
                tvECTSEdit.setText(intent.getStringExtra("ECTS"));
            }
            if (i==6){
                tvFormaZal.setText(Typ.get(i).toString()+": ");
                tvFormaZalEdit.setText(intent.getStringExtra("FormaZaliczenia"));
            }
            if (i==7){
                tvDokumenty.setText(Typ.get(i).toString()+": ");
                tvDokumentyEdit.setText(intent.getStringExtra("Dokumenty"));
            }
            if (i==8){
                tvGrupa.setText(Typ.get(i).toString()+": ");
                tvGrupaEdit.setText(intent.getStringExtra("Grupa"));
            }

        }

    }
}
