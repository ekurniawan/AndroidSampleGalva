package com.actualsolusi.listviewexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import dataaccess.DatabaseProvider;

public class TambahBarangActivity extends AppCompatActivity {
    private ImageView imgBarang;
    private EditText txtBarangID,txtNamaBarang,txtDeskripsi,txtStok,txtHargaBeli,txtHargaJual;
    private Spinner spKategori;
    private DatabaseProvider db;

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
    }

}
