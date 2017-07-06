package com.actualsolusi.listviewexample;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;

import dataaccess.DataProvider;
import models.Barang;

public class DetailBarangActivity extends AppCompatActivity {
    private ImageView imageDetailBarang;
    private EditText txtDetailBarangID,txtDetailKategoriID,txtDetailNamaBarang,txtDetailDeskripsiBarang,txtDetailStok,txtDetailHargaBeli,txtDetailHargaJual;
    private Spinner spKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageDetailBarang = (ImageView)findViewById(R.id.imageDetailBarang);
        txtDetailBarangID = (EditText)findViewById(R.id.txtDetailBarangID);
        txtDetailNamaBarang = (EditText)findViewById(R.id.txtDetailNamaBarang);
        txtDetailDeskripsiBarang = (EditText)findViewById(R.id.txtDetailDeskripsiBarang);

        String BarangID = getIntent().getStringExtra("BarangID");
        Barang barang = DataProvider.barangMap.get(BarangID);
        Bitmap bitmap = getBitmapFromAsset(barang.getBarangID());
        imageDetailBarang.setImageBitmap(bitmap);
        txtDetailBarangID.setText(barang.getBarangID());
        txtDetailNamaBarang.setText(barang.getNamaBarang());
        txtDetailDeskripsiBarang.setText(barang.getDeskripsi());

        spKategori = (Spinner)findViewById(R.id.spKategori);

    }

    private Bitmap getBitmapFromAsset(String barangId){
        AssetManager assetManager = getAssets();
        InputStream stream = null;
        try{
            stream = assetManager.open(barangId+".png");
            return BitmapFactory.decodeStream(stream);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
