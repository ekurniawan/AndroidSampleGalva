package services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import models.Barang;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by erick on 07/07/2017.
 */

public final class BarangServices {
    private static final String SERVICES_URL = "http://actualserver.southeastasia.cloudapp.azure.com/";
    private static final OkHttpClient client;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    static {
        client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS).build();
    }

    public static int PostBarang(Barang barang) throws JSONException, IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVICES_URL + "api/Barang").newBuilder();
        String url = urlBuilder.build().toString();

        JSONObject newBarang = new JSONObject();
        newBarang.put("BarangID",barang.getBarangID());
        newBarang.put("KategoriID",barang.getKategoriID());
        newBarang.put("NamaBarang",barang.getNamaBarang());
        newBarang.put("Deskripsi",barang.getDeskripsi());
        newBarang.put("Stok",barang.getStok());
        newBarang.put("HargaBeli",barang.getHargaBeli());
        newBarang.put("HargaJual",barang.getHargaJual());
        newBarang.put("Gambar",barang.getGambar());

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                newBarang.toString());
        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = client.newCall(request).execute();

        return response.code();
        //Log.d("PostKategori",String.valueOf(response.code()));
    }

    public static String UploadPic(File image) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",image.getName(),RequestBody.create(MEDIA_TYPE_PNG,image))
                .build();
        Request request = new Request.Builder().url(SERVICES_URL+"api/Barang/PostUserImage")
                .post(requestBody).build();

        Response response = client.newCall(request).execute();

        if(!response.isSuccessful()){
            throw new IOException("Unexpected code " + String.valueOf(response.code()));
        }else{
            return response.body().string().replace("\"","");
        }
    }


}
