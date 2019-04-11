package pl.krakow.up.ii.opensource.mobilewu;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OcenyActivity extends AppCompatActivity {

    final OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    ArrayList<String> listValue = new ArrayList<>();

    ArrayAdapter<String> adapter = null;


    TextView textView = null;
    List<String> list = new ArrayList<>();
    GridView gridView;
    ListView listView;

    ArrayList<String> listValueWystawil = new ArrayList<>();
    ArrayList<String> listValueGodziny= new ArrayList<>();
    ArrayList<String> listValueTerminI = new ArrayList<>();
    ArrayList<String> listValuePoprawkowy = new ArrayList<>();
    ArrayList<String> listValueKomisyjny = new ArrayList<>();
    ArrayList<String> listValueEtcs = new ArrayList<>();
    ArrayList<String> listValueTyp = new ArrayList<>();
    final String page_url = "https://wu.up.krakow.pl/WU/";


    final OkHttpClient client = new OkHttpClient().newBuilder()
            //   .followRedirects(false)
            //   .followSslRedirects(false)
            .cookieJar(cookieHelper.cookieJar())
            .build();

    class GetOcenyTask extends AsyncTask<Void, Integer, String> {

        final ProgressBar simpleProgressBar = findViewById(R.id.progressBarOceny);

        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url(page_url + "OcenyP.aspx")
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
            Document doc = Jsoup.parse(body); //
            String document = doc.toString();
            Element e = doc.getElementById("ctl00_ctl00_ContentPlaceHolder_RightContentPlaceHolder_tab");
            if (e != null) {
                for (Element td : e.getElementsByTag("td")) {
                    Log.e("--td -- ", td.text());
                }
            }
            //System.out.println("BODY: "+document);
            String szukacz1 = "<table class=\"gridPadding fill\" cellspacing=\"0\" cellpadding=\"0\" rules=\"all\" border=\"1\" id=\"ctl00_ctl00_ContentPlaceHolder_RightContentPlaceHolder_dgDane\">";
            String szukacz2 = "</table>";
            String result = null;

            for (int i = 0; i < doc.toString().length(); i++) {
                System.out.println("\n\n\n\n\n\n\n\n");
                if (document.contains(szukacz1) && document.contains(szukacz2)) {
                    System.out.println("Szukane frazy wystepują na pozycji: ");
                    System.out.println(+(document.indexOf(szukacz1) + 1) + " oraz: " + (document.indexOf(szukacz1) + 1));
                    //System.out.println("\n" + document.substring((document.indexOf(szukacz1)), (document.indexOf(szukacz1))));
                    result=document.substring((document.indexOf(szukacz1)), (document.indexOf(szukacz2)));
                    break;
                } else {
                    System.out.println("Szukana fraza nie wystepuje");
                    break;
                }
            }

            // wybiera nazwy przedmiotow
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
                //for (int j=0;j<list.size();j++){System.out.println(list.get(j));}

                //wybranie poszczegolnych elementow listy i przypisanie do list
                if (listValue.size()==0) {
                    for (int j = 0; j < list.size(); j++) {
                        if (j > 0 && j + 7 <= list.size() && list.get(j - 1).equals("tr class=\"gridDane\"")) {
                            System.out.println(list.get(j));
                            listValue.add(list.get(j) + "\n" + list.get(j + 1));
                            System.out.println(listValue.get(p2));
                            listValueWystawil.add(list.get(j + 2));
                            listValueGodziny.add(list.get(j + 3));
                            listValueTerminI.add(list.get(j + 4));
                            listValuePoprawkowy.add(list.get(j + 5));
                            listValueKomisyjny.add(list.get(j + 6));
                            listValueEtcs.add(list.get(j + 7));

                        }
                    }
                }
                //przypisanie nazw szegółów przedmiotu
                for (int j=0;j<8;j++){listValueTyp.add(list.get(j+1));}

                adapter.notifyDataSetInvalidated();
                //adapter.notifyDataSetChanged();
                simpleProgressBar.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Synchronizacja przebiegła pomyślnie", Toast.LENGTH_SHORT).show();
                //textView.setText(list.get(1));

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_oceny);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Przedmioty");
        actionBar.hide();


        textView = findViewById(R.id.tvOceny);
        listView = findViewById(R.id.lvOceny);
        listView.setVisibility(View.VISIBLE);
        if (listValue.size()==0) listView.setVisibility(View.INVISIBLE);
        adapter=new ArrayAdapter<String>(this,R.layout.activity_list_view_oceny, R.id.textViewOceny, listValue);
        listView.setAdapter(adapter);

       GetOcenyTask ocenyTask = new GetOcenyTask();
       ocenyTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OcenyActivity.this, OcenySecondActivity.class);
                intent.putExtra("IdNazwa",position);
                intent.putExtra("Nazwa",listValue.get(position));
                intent.putExtra("Wystawil",listValueWystawil.get(position));
                intent.putExtra("Godziny",listValueGodziny.get(position));
                intent.putExtra("TerminI",listValueTerminI.get(position));
                intent.putExtra("Poprawkowy",listValuePoprawkowy.get(position));
                intent.putExtra("Komisyjny",listValueKomisyjny.get(position));
                intent.putExtra("ETCS",listValueEtcs.get(position));
                intent.putExtra("Typ",listValueTyp);
                startActivity(intent);
            }
        });


    }
}

