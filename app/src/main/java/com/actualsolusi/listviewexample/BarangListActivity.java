package com.actualsolusi.listviewexample;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import dataaccess.DatabaseProvider;
import models.Barang;
import services.BarangServices;

public class BarangListActivity extends AppCompatActivity {
    private ListView barangListView;
    private List<Barang>  listBarang;
    private DatabaseProvider db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SyncToServer();
            }
        });

        db = new DatabaseProvider(this);

        loadData();

        barangListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(BarangListActivity.this,
                        DetailBarangActivity.class);
                Barang currBarang = listBarang.get(position);
                i.putExtra("BarangID",currBarang.getBarangID());
                startActivity(i);
            }
        });
    }

    private void loadData(){
        listBarang = db.GetAllBarang();
        BarangListAdapter adapter = new BarangListAdapter(this,
                R.layout.list_item,listBarang);
        barangListView = (ListView)findViewById(R.id.barangListView);
        barangListView.setAdapter(adapter);
    }

    private void SyncToServer(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                listBarang = db.GetAllBarang();
                for(Barang barang : listBarang){
                    try{
                        ContextWrapper cw = new ContextWrapper(BarangListActivity.this);
                        File directory = cw.getDir("imageDir",MODE_PRIVATE);
                        Log.v("Path",directory.getAbsolutePath()+"/"+barang.getGambar());

                        /*Bitmap myBitmap = loadImageFromStorage(directory.getAbsolutePath(),
                                barang.getGambar());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        myBitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
                        byte[] bitmapData = stream.toByteArray();

                        File f = new File(getApplicationContext().getCacheDir(),barang.getGambar());
                        FileOutputStream fos;
                        fos = new FileOutputStream(f);
                        fos.write(bitmapData);
                        fos.flush();
                        fos.close();*/
                        File file = new File(directory.getAbsolutePath(),barang.getGambar());
                        //Log.d("File",String.valueOf(file.getTotalSpace()));

                        String strImg = BarangServices.UploadPic(file);
                        barang.setGambar(strImg);
                        int status = BarangServices.PostBarang(barang);

                    }
                    catch (IOException iEx){
                        Log.d("IOException",iEx.getLocalizedMessage());
                    }
                    catch (JSONException jEx){
                        Log.d("JSONException",jEx.getLocalizedMessage());
                    }
                }
                //db.DeleteAllBarang();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(BarangListActivity.this,"Berhasil sync barang ke server",Toast.LENGTH_LONG).show();
                loadData();
            }
        }.execute();
    }

    private Bitmap loadImageFromStorage(String path, String filename) throws FileNotFoundException {
        File f = new File(path,filename);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }


}
