package pl.krakow.up.ii.opensource.mobilewu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    String[] listValue = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};

    ArrayAdapter<String> adapter = null;


    TextView textView = null;
    List<String> list = new ArrayList<>();
    ListView listView;
    GridView gridView;

    String[] listValueWystawil = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};
    String[] listValueGodziny= new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};
    String[] listValueTerminI = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};
    String[] listValuePoprawkowy = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};
    String[] listValueKomisyjny = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};
    String[] listValueEtcs = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};
    String[] listValueTyp = new String[]{"1","2","3","4","5","6","7","8"};
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
                //wybranie poszczegolnych elementow listy
                for (int j=0;j<list.size();j++){
                    if (j>0 && j+7<=list.size() && list.get(j-1).equals("tr class=\"gridDane\"")){
                        System.out.println(list.get(j));
                        listValue[p2]=list.get(j) + "\n" + list.get(j+1);
                        listValueWystawil[p2]=list.get(j+2);
                        listValueGodziny[p2]=list.get(j+3);
                        listValueTerminI[p2]=list.get(j+4);
                        listValuePoprawkowy[p2]=list.get(j+5);
                        listValueKomisyjny[p2]=list.get(j+6);
                        listValueEtcs[p2]=list.get(j+7);
                        p2+=1;
                    }
                }
                for (int j=0;j<8;j++){listValueTyp[j]=list.get(j+1);}
                //wybranie poszczegolnych elementow listy
                for (String s:listValueWystawil){System.out.println(s);}
                for (String s:listValueGodziny){System.out.println(s);}
                for (String s:listValueTerminI){System.out.println(s);}
                for (String s:listValuePoprawkowy){System.out.println(s);}
                for (String s:listValueKomisyjny){System.out.println(s);}
                for (String s:listValue){System.out.println(s);}
                adapter.notifyDataSetInvalidated();
                //adapter.notifyDataSetChanged();
                simpleProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Synchronizacja przebiegła pomyślnie", Toast.LENGTH_SHORT).show();
                //textView.setText(list.get(1));

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oceny);
        textView = findViewById(R.id.tvOceny);
        gridView = findViewById(R.id.gvOceny);
        //listView = findViewById(R.id.lvMenuOceny);
        adapter=new ArrayAdapter<String>(this,R.layout.activity_list_view_oceny, R.id.textViewOceny, listValue);
        gridView.setAdapter(adapter);

       GetOcenyTask ocenyTask = new GetOcenyTask();
       ocenyTask.execute();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OcenyActivity.this, OcenySecondActivity.class);
                intent.putExtra("IdNazwa",position);
                intent.putExtra("Nazwa",listValue[position]);
                intent.putExtra("Wystawil",listValueWystawil[position]);
                intent.putExtra("Godziny",listValueGodziny[position]);
                intent.putExtra("TerminI",listValueTerminI[position]);
                intent.putExtra("Poprawkowy",listValuePoprawkowy[position]);
                intent.putExtra("Komisyjny",listValueKomisyjny[position]);
                intent.putExtra("ETCS",listValueEtcs[position]);
                intent.putExtra("Typ",listValueTyp);
                startActivity(intent);
            }
        });


    }
}

