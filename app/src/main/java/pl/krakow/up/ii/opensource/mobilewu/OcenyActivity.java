package pl.krakow.up.ii.opensource.mobilewu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



import org.riversun.okhttp3.OkHttp3CookieHelper;

public class OcenyActivity extends AppCompatActivity {

    final OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();

    TextView textView = null;
    Button textBtn = null;
    ListView listView;
    String[] listValue;
    final String page_url = "https://wu.up.krakow.pl/WU/";

    final OkHttpClient client = new OkHttpClient().newBuilder()
            //   .followRedirects(false)
            //   .followSslRedirects(false)
            .cookieJar(cookieHelper.cookieJar())
            .build();

    class NetworkTask extends AsyncTask<String, Integer, String> {

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

            //Pattern compiledPattern = Pattern.compile("tbody");
            //Matcher matcher = compiledPattern.matcher(body);
            //textView.setText(matcher.toString());
            //System.out.println("\n To jest print body: "+matcher.toString()+"    : no i tak\n");
            //String[] wynik = body.split("<tbody><tr class=\"gridDaneHead\" valign=\"bottom\">");
            //for(String w : wynik){
            //System.out.println("\n To jest print body: "+w+"    : no i tak\n");
            //textView.setText(w);}
            System.out.println("BODY: "+document);
            String szukacz1 = "<table class=\"gridPadding fill\" cellspacing=\"0\" cellpadding=\"0\" rules=\"all\" border=\"1\" id=\"ctl00_ctl00_ContentPlaceHolder_RightContentPlaceHolder_dgDane\">";
            String szukacz2 = "</table>";
            int p1, p2;
            for (int i = 0; i < doc.toString().length(); i++) {
                p1 = document.indexOf(szukacz1);
                p2 = document.indexOf(szukacz2);
                System.out.println("\n\n\n\n"+p1+" "+p2+"\n\n\n\n");
                if (p1 > 0 && p2 > 0) {
                    System.out.println("Szukane frazy wystepują na pozycji: ");
                    System.out.println(+(p1 + 1) + " oraz: " + (p2 + 1));
                    System.out.println("\n" + document.substring((p1), (p2)));
                    textView.setText(document.substring((p1), (p2)));
                    break;
                } else {
                    System.out.println("Szukana fraza nie wystepuje");
                    break;
                }
            }

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oceny);


        textBtn = findViewById(R.id.btnOceny);
        textView = findViewById(R.id.tvOceny);

        String bodyParams = "ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24txtIdent=" + "login" + "&ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24txtHaslo=" + "password"
                + "&ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24butLoguj=Zaloguj"
                + "&__VIEWSTATE=%2FwEPDwUKMTgxMTA3NTE0Mw8WAh4DaGFzZRYCZg9kFgJmD2QWAgIBD2QWBAICD2QWAgIBD2QWAgIBD2QWAgICDxQrAAIUKwACDxYEHgtfIURhdGFCb3VuZGceF0VuYWJsZUFqYXhTa2luUmVuZGVyaW5naGRkZGQCBA9kFgICAw9kFg4CAQ8WAh4JaW5uZXJodG1sBS1XaXJ0dWFsbmEgVWN6ZWxuaWE8IS0tIHN0YXR1czogNzcyMjA2MTI1IC0tPiBkAg0PDxYCHgRNb2RlCyolU3lzdGVtLldlYi5VSS5XZWJDb250cm9scy5UZXh0Qm94TW9kZQJkZAIVDw8WAh4EVGV4dAUZT2R6eXNraXdhbmllIGhhc8WCYTxiciAvPmRkAhcPDxYCHgdWaXNpYmxlaGQWAgIDDxBkDxYCZgIBFgIFB3N0dWRlbnQFCGR5ZGFrdHlrFgFmZAIZD2QWBAIBDw8WAh8FBTQ8YnIgLz5MdWIgemFsb2d1aiBzacSZIGpha28gc3R1ZGVudCBwcnpleiBPZmZpY2UzNjU6ZGQCAw8PFgIfBQUIUHJ6ZWpkxbpkZAIbDw8WBB8FBRhTZXJ3aXMgQWJzb2x3ZW50w7N3PGJyLz4fBmhkZAIfDw8WAh8GaGRkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBUpjdGwwMCRjdGwwMCRUb3BNZW51UGxhY2VIb2xkZXIkVG9wTWVudUNvbnRlbnRQbGFjZUhvbGRlciRNZW51VG9wMyRtZW51VG9wM71u5cvxo3%2F6OarM3JXDhn%2F9bImN&__VIEWSTATEGENERATOR=7D6A02AE";
        NetworkTask task = new NetworkTask();
        task.execute(page_url + "Logowanie2.aspx?returnUrl=/WU/OcenyP.aspx", bodyParams);


    }
}

