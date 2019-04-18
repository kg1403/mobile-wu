package pl.krakow.up.ii.opensource.mobilewu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlanStudiowActivity extends AppCompatActivity {

    final OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();

    TextView textView = null;
    GridView gridView;

    final String page_url = "https://wu.up.krakow.pl/WU/";

    ArrayAdapter<String> gridViewArrayAdapter;
    ArrayList colName = new ArrayList();
    ArrayList nazwyKursow = new ArrayList();
    ArrayList nazwyProwadzacych = new ArrayList();
    ArrayList nazwyFormaZajec = new ArrayList();
    ArrayList nazwyGodziny = new ArrayList();
    ArrayList nazwyECTS = new ArrayList();
    ArrayList nazwyFormaZaliczenia = new ArrayList();
    ArrayList nazwyPobierzDokumenty = new ArrayList();
    ArrayList nazwyNazwaGrupy = new ArrayList();


    final OkHttpClient client = new OkHttpClient().newBuilder()
            //   .followRedirects(false)
            //   .followSslRedirects(false)
            .cookieJar(cookieHelper.cookieJar())
            .build();

    class GetPlanStudiowTask extends AsyncTask<Void, Integer, String> {

        final ProgressBar simpleProgressBar = findViewById(R.id.progressBarPlanStudiow);

        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url(page_url + "PlanStudiow.aspx")
                    .get()
                    .build();

            try (Response response = AppConfiguration.okHttpClient.newCall(request).execute()) {
                Log.e("tag", response.message().toString());
                try {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String body) {
            Document doc;
            doc = Jsoup.parse(body);


            int trList = 0;
            int tdList = 0;

            Element element = doc.getElementById("ctl00_ctl00_ContentPlaceHolder_RightContentPlaceHolder_dgDane");
            Element tbody = element.getElementsByTag("tbody").first();

            Elements tds = null;
            Elements trs = tbody.getElementsByTag("tr");


            for (Element tr : trs){
                if(tr.hasClass("gridDane"))
                    trList++;
                tds = tr.getElementsByTag("td");
            }

            for(Element td : tds){
                tdList++;
            }

            Element cols = tbody.getElementsByTag("tr").get(0);
            for(int i =0; i<tdList; i++){
                Element colElemet = cols.getElementsByTag("td").get(i);
                String colNazwa = colElemet.toString().substring(colElemet.toString().indexOf(">")+1, colElemet.toString().indexOf("<", 2));
                colName.add(colNazwa);
            }

            System.out.println("colNames : " + colName);
            for(int i = 1; i < trList; i++){
                Element tr = tbody.getElementsByTag("tr").get(i);
                Element td = tr.getElementsByTag("td").get(1);
                Element name = td.getElementsByTag("span").first();
                String nazwa = name.toString().substring(name.toString().indexOf(">")+1, name.toString().indexOf("<", 2) );

                Element typ = tr.getElementsByTag("td").get(3);
                String typName = typ.toString().substring(typ.toString().indexOf(">")+1, typ.toString().indexOf("<", 2) );;
                nazwyFormaZajec.add(typName);
                if(typName.contains("W"))
                    nazwyKursow.add(nazwa + '\n' + "WYKŁAD");
                else if (typName.contains("L"))
                    nazwyKursow.add(nazwa + '\n' + "LABORATORIUM");
                else if (typName.contains("E"))
                    nazwyKursow.add(nazwa + '\n' + "EGZAMIN");
                else if (typName.contains("S"))
                    nazwyKursow.add(nazwa + '\n' + "SEMINARIUM");
                else if (typName.contains("K"))
                    nazwyKursow.add(nazwa + '\n' + "KONWERSATORIUM");
                else if (typName.contains("P"))
                    nazwyKursow.add(nazwa + '\n' + "PRAKTYKA");
                else if (typName.contains("Z"))
                    nazwyKursow.add(nazwa + '\n' + "WYKŁAD ZDALNY");
                else if (typName.contains("A"))
                    nazwyKursow.add(nazwa + '\n' + "WYCHOWANIE FIZYCZNE");


                Element prowadzacy = tr.getElementsByTag("td").get(2);
                Element a = prowadzacy.getElementsByTag("a").first();
                String prowadzacyName = a.toString().substring(a.toString().indexOf(">")+1, a.toString().indexOf("<", 2));
                nazwyProwadzacych.add(prowadzacyName);

                Element godziny = tr.getElementsByTag("td").get(4);
                String godzinyName = godziny.toString().substring(godziny.toString().indexOf(">")+1, godziny.toString().indexOf("<", 2));
                nazwyGodziny.add(godzinyName);

                Element ects = tr.getElementsByTag("td").get(5);
                String ectsName = ects.toString().substring(ects.toString().indexOf(">")+1, ects.toString().indexOf("<", 2));
                nazwyECTS.add(ectsName);

                Element formaZal = tr.getElementsByTag("td").get(6);
                String formaZalName = formaZal.toString().substring(formaZal.toString().indexOf(">")+1, formaZal.toString().indexOf("<", 2));
                nazwyFormaZaliczenia.add(formaZalName);

                Element dokumenty = tr.getElementsByTag("td").get(7);
                Element span = dokumenty.getElementsByTag("span").get(0);
                String dokName = span.toString().substring(span.toString().indexOf(">")+1, span.toString().indexOf("<", 2));
                nazwyPobierzDokumenty.add(dokName);

                Element grupy = tr.getElementsByTag("td").get(8);
                String grupName = grupy.toString().substring(grupy.toString().indexOf(">")+1, grupy.toString().indexOf("<", 2));
                nazwyNazwaGrupy.add(grupName);
            }

            gridViewArrayAdapter.notifyDataSetInvalidated();
            simpleProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Synchronizacja przebiegła pomyślnie", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_planstudiow);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new GetPlanStudiowTask().execute();

        textView = findViewById(R.id.tvPlanStudiow);
        gridView = findViewById(R.id.gvPlanStudiow);
        //nazwyKursowList = new ArrayList<String>(Arrays.asList(nazwyKursow));
        gridViewArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.activity_list_view_planstudiow, R.id.textViewPlanStudiow, nazwyKursow);
        gridView.setAdapter(gridViewArrayAdapter);







        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanStudiowActivity.this, PlanStudiowSecondActivity.class);
                intent.putExtra("Nazwa",nazwyKursow.get(position).toString());
                intent.putExtra("Prowadzacy",nazwyProwadzacych.get(position).toString());
                intent.putExtra("FormaZajec",nazwyFormaZajec.get(position).toString());
                intent.putExtra("LiczbaGodzin",nazwyGodziny.get(position).toString());
                intent.putExtra("ECTS",nazwyECTS.get(position).toString());
                intent.putExtra("FormaZaliczenia",nazwyFormaZaliczenia.get(position).toString());
                intent.putExtra("Dokumenty",nazwyPobierzDokumenty.get(position).toString());
                intent.putExtra("Grupa",nazwyNazwaGrupy.get(position).toString());
                intent.putExtra("Typ",colName);
                startActivity(intent);
            }
        });


    }
}

