package by.test2rest.internet;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestServiceText {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static RestServiceText instanse;
    private RestApiText restApiText;
    public static final String LOG = "text";

    public RestServiceText() {
        Log.e(LOG, "Konstructor");
        init();
    }

    public static RestServiceText getInstanse(){
        Log.e(LOG, "getInstance RestServiceText");
        if(instanse == null){
            instanse = new RestServiceText();
        }
        return instanse;
    }

    public RestApiText getRestApiText(){
        Log.e(LOG, "getrESTapi");
        return restApiText;
    }

    private void init(){
        Log.e(LOG, "init 1");
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Log.e(LOG, "init 2");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Log.e(LOG, "init 3");
        Gson gson = new GsonBuilder().create();

        Log.e(LOG, "init 4");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        Log.e(LOG, "init 5");
        restApiText = retrofit.create(RestApiText.class);

        Log.e(LOG, "init 6");
    }
}
