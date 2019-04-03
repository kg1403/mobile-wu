package pl.krakow.up.ii.opensource.mobilewu;

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
    public static int anInt = 0;
    TextView textView = null;
    List<String> list = new ArrayList<>();
    ListView listView;
    GridView gridView;
    String[] listValue = new String[]{"1","2","3","4","5","6","7","8","9","10","12","13"};
    final String page_url = "https://wu.up.krakow.pl/WU/";


    final OkHttpClient client = new OkHttpClient().newBuilder()
            //   .followRedirects(false)
            //   .followSslRedirects(false)
            .cookieJar(cookieHelper.cookieJar())
            .build();

    class NetworkTask extends AsyncTask<String, Integer, String> {

        final ProgressBar simpleProgressBar = findViewById(R.id.progressBarOceny);

        @Override
        protected String doInBackground(String... strings) {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, strings[1]);
            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache")
                    .build();

            try (Response response = client.newCall(request).execute()) {
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
            //textView.setText("Response is: "+ body.substring(0,500));
            try {
                Element blad = doc.getElementById("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_lblMessage");
                if (blad != null) {
                    Toast.makeText(OcenyActivity.this, blad.text(), Toast.LENGTH_SHORT)
                            .show();
//                    Log.e("tag", blad.text());
                    return;
                }
            } catch (Exception e) {
                Log.e("tag", "jest blad");
                e.printStackTrace();
            }
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
            /* //Wybiera nazwy kolumn
            if (result != null){
                result.trim();
                String[] tabBadString = new String[]{"<tr","tbody","\n","/tr","/td","td","table"};
                StringTokenizer stringTokenizer = new StringTokenizer(result);
                int p=0;
                while (stringTokenizer.hasMoreTokens()) {
                    String t=stringTokenizer.nextToken("><");
                    for (String s: tabBadString){
                        if (!t.contains(s)){ p+=1; }
                    }
                    if (p==tabBadString.length){ list.add(t);}
                    p=0;
                }
                System.out.println("\n\n\n");
                simpleProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Synchronizacja przebiegła pomyślnie", Toast.LENGTH_SHORT).show();
                for (int j=0;j<list.size();j++){System.out.println(list.get(j));}
                for (int j=0;j<8;j++){listValue[j]=list.get(j+1);}
                //wybranie poszczegolnych elementow listy
                for (String s:listValue){System.out.println(s);}
                //textView.setText(list.get(1));
                textView.invalidate();
                listView.invalidate();

            }
            */
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
                    if (j>0 && j+1<=list.size() && list.get(j-1).equals("tr class=\"gridDane\"")){
                        System.out.println(list.get(j));
                        listValue[p2]=list.get(j) + "\n" + list.get(j+1);
                        p2+=1;
                    }

                }
                for (String s:listValue){System.out.println(s);}
                simpleProgressBar.setVisibility(View.INVISIBLE);
                anInt =1;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_list_view_oceny, R.id.textViewOceny, listValue);
        gridView.setAdapter(adapter);



        if (anInt==1){
            adapter.notifyDataSetChanged();
            System.out.println("\nAdapter wysłany ponownie\n");
            //listView.setAdapter(adapter);
        }



        String bodyParams = "ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24txtIdent=" + "raz" + "&ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24txtHaslo=" + "dewa"
                + "&ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24butLoguj=Zaloguj"
                + "&__VIEWSTATE=%2FwEPDwUKMTgxMTA3NTE0Mw8WAh4DaGFzZRYCZg9kFgJmD2QWAgIBD2QWBAICD2QWAgIBD2QWAgIBD2QWAgICDxQrAAIUKwACDxYEHgtfIURhdGFCb3VuZGceF0VuYWJsZUFqYXhTa2luUmVuZGVyaW5naGRkZGQCBA9kFgICAw9kFg4CAQ8WAh4JaW5uZXJodG1sBS1XaXJ0dWFsbmEgVWN6ZWxuaWE8IS0tIHN0YXR1czogNzcyMjA2MTI1IC0tPiBkAg0PDxYCHgRNb2RlCyolU3lzdGVtLldlYi5VSS5XZWJDb250cm9scy5UZXh0Qm94TW9kZQJkZAIVDw8WAh4EVGV4dAUZT2R6eXNraXdhbmllIGhhc8WCYTxiciAvPmRkAhcPDxYCHgdWaXNpYmxlaGQWAgIDDxBkDxYCZgIBFgIFB3N0dWRlbnQFCGR5ZGFrdHlrFgFmZAIZD2QWBAIBDw8WAh8FBTQ8YnIgLz5MdWIgemFsb2d1aiBzacSZIGpha28gc3R1ZGVudCBwcnpleiBPZmZpY2UzNjU6ZGQCAw8PFgIfBQUIUHJ6ZWpkxbpkZAIbDw8WBB8FBRhTZXJ3aXMgQWJzb2x3ZW50w7N3PGJyLz4fBmhkZAIfDw8WAh8GaGRkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBUpjdGwwMCRjdGwwMCRUb3BNZW51UGxhY2VIb2xkZXIkVG9wTWVudUNvbnRlbnRQbGFjZUhvbGRlciRNZW51VG9wMyRtZW51VG9wM71u5cvxo3%2F6OarM3JXDhn%2F9bImN&__VIEWSTATEGENERATOR=7D6A02AE";
        NetworkTask task = new NetworkTask();
        task.execute(page_url + "Logowanie2.aspx?returnUrl=/WU/OcenyP.aspx", bodyParams);


    }
}

