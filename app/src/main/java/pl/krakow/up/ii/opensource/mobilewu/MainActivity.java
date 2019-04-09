package pl.krakow.up.ii.opensource.mobilewu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    final OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();

    EditText login = null;
    EditText pass = null;
    Button loginBtn = null;

    final String page_url = "https://wu.up.krakow.pl/WU/";

    //klasa służąca do wysyłania zapytań do strony z wykorzystaniem cookies
    class PostLoginTask extends AsyncTask<String, Integer, String> {

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

            try (Response response = AppConfiguration.okHttpClient.newCall(request).execute()) {
                Log.e("tag", response.message().toString());
                try {
                    //System.out.println("\n To jest print: "+response.body().string()+"    : no i tak\n");
                    return response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"Błąd sesji",Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String body) {
            Document doc = Jsoup.parse(body); //

            try {
                Element blad = doc.getElementById("ctl00_ctl001_ContentPlaceHolder_MiddleContentPlaceHolder_lblMessage");
                if (blad != null) {
                    Toast.makeText(MainActivity.this, blad.text(), Toast.LENGTH_SHORT).show();
                    //Log.e("tag", blad.text());
                    return;
                }
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                finish();
                startActivity(intent);
            } catch (Exception e) {
                Log.e("tag", "jest blad");
                e.printStackTrace();
            }
        }
    }
    //klasa służąca do pobierania zawartości strony z wykorzystaniem cookies
    class GetLoginTask extends AsyncTask<Void, Integer, Void>{  //w przyszłości użyty np do pobrania błędów)

        @Override
        protected Void doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url(page_url + "Logowanie2.aspx")
                    .get()
                    .build();
            try (Response response = AppConfiguration.okHttpClient.newCall(request).execute()) {
                Log.e("tag", response.message());
                try {
                    Log.e("tag", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        pass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_button);
        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(v -> {
            String slogin = login.getText().toString();
            String spass = pass.getText().toString();
            simpleProgressBar.setVisibility(View.VISIBLE);
            String bodyParams = "ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24txtIdent=" + slogin + "&ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24txtHaslo=" + spass
                    + "&ctl00%24ctl00%24ContentPlaceHolder%24MiddleContentPlaceHolder%24butLoguj=Zaloguj"
                    + "&__VIEWSTATE=%2FwEPDwUKMTgxMTA3NTE0Mw8WAh4DaGFzZRYCZg9kFgJmD2QWAgIBD2QWBAICD2QWAgIBD2QWAgIBD2QWAgICDxQrAAIUKwACDxYEHgtfIURhdGFCb3VuZGceF0VuYWJsZUFqYXhTa2luUmVuZGVyaW5naGRkZGQCBA9kFgICAw9kFg4CAQ8WAh4JaW5uZXJodG1sBS1XaXJ0dWFsbmEgVWN6ZWxuaWE8IS0tIHN0YXR1czogNzcyMjA2MTI1IC0tPiBkAg0PDxYCHgRNb2RlCyolU3lzdGVtLldlYi5VSS5XZWJDb250cm9scy5UZXh0Qm94TW9kZQJkZAIVDw8WAh4EVGV4dAUZT2R6eXNraXdhbmllIGhhc8WCYTxiciAvPmRkAhcPDxYCHgdWaXNpYmxlaGQWAgIDDxBkDxYCZgIBFgIFB3N0dWRlbnQFCGR5ZGFrdHlrFgFmZAIZD2QWBAIBDw8WAh8FBTQ8YnIgLz5MdWIgemFsb2d1aiBzacSZIGpha28gc3R1ZGVudCBwcnpleiBPZmZpY2UzNjU6ZGQCAw8PFgIfBQUIUHJ6ZWpkxbpkZAIbDw8WBB8FBRhTZXJ3aXMgQWJzb2x3ZW50w7N3PGJyLz4fBmhkZAIfDw8WAh8GaGRkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBUpjdGwwMCRjdGwwMCRUb3BNZW51UGxhY2VIb2xkZXIkVG9wTWVudUNvbnRlbnRQbGFjZUhvbGRlciRNZW51VG9wMyRtZW51VG9wM71u5cvxo3%2F6OarM3JXDhn%2F9bImN&__VIEWSTATEGENERATOR=7D6A02AE";
            PostLoginTask postLoginTask = new PostLoginTask();
            postLoginTask.execute(page_url + "Logowanie2.aspx?returnUrl=/WU/OcenyP.aspx", bodyParams);


        });
    }
}