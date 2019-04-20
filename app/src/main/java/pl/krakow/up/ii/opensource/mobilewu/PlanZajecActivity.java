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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlanZajecActivity extends AppCompatActivity {

    final OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    ArrayList<String> listValue = new ArrayList<>();

    ArrayAdapter<String> adapter = null;


    TextView textView = null;
    List<String> list = new ArrayList<>();
    ListView listView;

    ArrayList<String> listValueData = new ArrayList<>();
    ArrayList<String> listValueOd= new ArrayList<>();
    ArrayList<String> listValueDo = new ArrayList<>();
    ArrayList<String> listValueNazwa = new ArrayList<>();
    ArrayList<String> listValueProwadzacy = new ArrayList<>();
    ArrayList<String> listValueSala = new ArrayList<>();
    ArrayList<String> listValueAdres = new ArrayList<>();
    ArrayList<String> listValueForma = new ArrayList<>();
    ArrayList<String> listValueZaliczenie = new ArrayList<>();
    ArrayList<String> listValueTyp = new ArrayList<>();
    ArrayList<String> napis = new ArrayList<>();
    final String page_url = "https://wu.up.krakow.pl/WU/";


    final OkHttpClient client = new OkHttpClient().newBuilder()
            .cookieJar(cookieHelper.cookieJar())
            .build();

    class GetPlanZajecTask extends AsyncTask<Void, Integer, String> {

        final ProgressBar simpleProgressBar = findViewById(R.id.progressBarPlanZajec);

        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url(page_url + "PodzGodzin.aspx")
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
            Document doc = Jsoup.parse(body);
            String document = doc.toString();
            Element e = doc.getElementById("ctl00_ctl00_ContentPlaceHolder_RightContentPlaceHolder_tab");
            if (e != null) {
                for (Element td : e.getElementsByTag("td")) {
                    Log.e("--td -- ", td.text());
                }
            }
            String szukacz1 = "<table class=\"gridPadding fill\" cellspacing=\"0\" cellpadding=\"0\" rules=\"all\" border=\"1\" id=\"ctl00_ctl00_ContentPlaceHolder_RightContentPlaceHolder_dgDane\">";
            String szukacz2 = "</table>";
            String result = null;

            for (int i = 0; i < doc.toString().length(); i++) {
                System.out.println("\n\n\n\n\n\n\n\n");
                if (document.contains(szukacz1) && document.contains(szukacz2)) {
                    System.out.println("Szukane frazy wystepują na pozycji: ");
                    System.out.println(+(document.indexOf(szukacz1) + 1) + " oraz: " + (document.indexOf(szukacz1) + 1));
                    result=document.substring((document.indexOf(szukacz1)), (document.indexOf(szukacz2)));
                    break;
                } else {
                    System.out.println("Szukana fraza nie wystepuje");
                    break;
                }
            }

            if (result != null){
                result.trim();
                String[] tabBadString = new String[]{"<tr","tbody","\n","/tr","/td","td","table"};
                StringTokenizer stringTokenizer = new StringTokenizer(result);
                int p1=0,p2=0;
                while (stringTokenizer.hasMoreTokens()) {
                    String t=stringTokenizer.nextToken("><");
                    for (String s: tabBadString){
                        if (!t.contains(s)){ p1+=1; }
                    }
                    if (p1==tabBadString.length){ list.add(t);}
                    p1=0;
                }
                System.out.println("\n\n\n");

                if (listValue.size()==0) {
                    for (int j = 0; j < list.size(); j++) {
                        if (j > 0 && j + 10 <= list.size() && list.get(j - 1).equals("tr class=\"gridDane\"")) {
                            listValueData.add(list.get(j));
                            listValueOd.add(list.get(j + 1));
                            listValueDo.add(list.get(j + 2));
                            listValueNazwa.add(list.get(j + 3));
                            listValueProwadzacy.add(list.get(j + 5));
                            listValueSala.add(list.get(j + 7));
                            listValueAdres.add(list.get(j + 8));
                            listValueForma.add(list.get(j + 9));
                            listValueZaliczenie.add(list.get(j + 10));
                            napis.add(list.get(j)+"\n"+list.get(j + 1)+" - "+list.get(j + 2));
                        }
                    }
                }
                for (int j=0;j<11;j++){listValueTyp.add(list.get(j+1));}

                adapter.notifyDataSetInvalidated();
                simpleProgressBar.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Synchronizacja przebiegła pomyślnie", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_plan_zajec);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Plan Zajęć");
        actionBar.hide();

        textView = findViewById(R.id.tvPlanZajec);
        listView = findViewById(R.id.lvPlanZajec);
        listView.setVisibility(View.VISIBLE);
        if (listValue.size()==0) listView.setVisibility(View.INVISIBLE);

        adapter=new ArrayAdapter<String>(this, R.layout.activity_list_view_plan_zajec, R.id.textViewPlanZajec, napis);
        listView.setAdapter(adapter);

       GetPlanZajecTask planZajecTask = new GetPlanZajecTask();
       planZajecTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanZajecActivity.this, PlanZajecSecondActivity.class);
                intent.putExtra("Data",listValueData.get(position));
                intent.putExtra("Od",listValueOd.get(position));
                intent.putExtra("Do",listValueDo.get(position));
                intent.putExtra("Nazwa",listValueNazwa.get(position));
                intent.putExtra("Prowadzacy",listValueProwadzacy.get(position));
                intent.putExtra("Sala",listValueSala.get(position));
                intent.putExtra("Adres",listValueAdres.get(position));
                intent.putExtra("Forma",listValueForma.get(position));
                intent.putExtra("Zaliczenie",listValueZaliczenie.get(position));
                intent.putExtra("Typ",listValueTyp);
                startActivity(intent);
            }
        });
    }
}

