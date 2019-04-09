package pl.krakow.up.ii.opensource.mobilewu;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class AppConfiguration {
    static class MyCookieJar implements CookieJar{
        private ArrayList<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if(this.cookies != null) {
                ArrayList<Cookie> newCookies = new ArrayList<>(cookies);
                newCookies.addAll(this.cookies);
                this.cookies = newCookies;
            } else {
                this.cookies = new ArrayList<>(cookies);
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (cookies != null)
                return cookies;
            return new ArrayList<Cookie>();
        }


    }

    public static MyCookieJar cookieJar = new MyCookieJar();
    public static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .followRedirects(true)
            .followSslRedirects(true)
            .cookieJar(AppConfiguration.cookieJar)
            .build();
}
