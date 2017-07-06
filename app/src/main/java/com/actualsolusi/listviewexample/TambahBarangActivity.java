package com.actualsolusi.listviewexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import dataaccess.DatabaseProvider;
import models.Kategori;

public class TambahBarangActivity extends AppCompatActivity {
    private ImageView imgBarang;
    private EditText txtBarangID,txtNamaBarang,txtDeskripsi,txtStok,txtHargaBeli,txtHargaJual;
    private Spinner spKategori;
    private DatabaseProvider db;
    private List<Kategori> listKategori;
    private Kategori kategoriSelected;
    private Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);
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

        db = new DatabaseProvider(this);

        imgBarang = (ImageView)findViewById(R.id.imgBarang);
        txtBarangID = (EditText)findViewById(R.id.txtBarangID);
        txtNamaBarang = (EditText)findViewById(R.id.txtNamaBarang);
        txtDeskripsi = (EditText)findViewById(R.id.txtDeskripsi);
        txtStok = (EditText)findViewById(R.id.txtStok);
        txtHargaBeli = (EditText)findViewById(R.id.txtHargaBeli);
        txtHargaJual = (EditText)findViewById(R.id.txtHargaJual);

        spKategori = (Spinner)findViewById(R.id.spKategori);

        listKategori = db.GetAllKategori();

        SpinnerKategoriAdapter spinnerAdapter = new SpinnerKategoriAdapter(TambahBarangActivity.this,
                android.R.layout.simple_spinner_item,listKategori);
        spKategori.setAdapter(spinnerAdapter);

        spKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategoriSelected = (Kategori)parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        String.valueOf(kategoriSelected.getKategoriID())+
                                " "+kategoriSelected.getNamaKategori(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCapture = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCapture,0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap)extras.get("data");
            imgBarang.setImageBitmap(imageBitmap);
        }
    }
}
