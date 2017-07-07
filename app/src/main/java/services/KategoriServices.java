package services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import models.Kategori;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by erick on 07/07/2017.
 */

public final class KategoriServices {
    private static final String SERVICES_URL = "http://10.0.2.2:8081/";
    private static final OkHttpClient client;

    static {
        client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS).build();
    }

    public static List<Kategori> GetAll() throws IOException {
        List<Kategori> listKategori = new ArrayList<>();
        Request request = new Request.Builder()
                .url(SERVICES_URL+"api/Kategori").build();
        Response response = client.newCall(request).execute();

        String results = response.body().string();

        try {
            JSONArray jsonArray = new JSONArray(results);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Kategori kategori = new Kategori();
                kategori.setKategoriID(jsonObject.getInt("KategoriID"));
                kategori.setNamaKategori(jsonObject.getString("NamaKategori"));
                listKategori.add(kategori);
            }
        }catch(JSONException jEx){
            Log.d("MyException",jEx.getLocalizedMessage());
        }
        return listKategori;
    }
}
