package com.actualsolusi.listviewexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import dataaccess.DatabaseProvider;
import models.Kategori;

public class KategoriListActivity extends AppCompatActivity {
    private ListView kategoriListView;
    private DatabaseProvider dbProvider;
    private List<Kategori> listKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_list);
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

        dbProvider = new DatabaseProvider(KategoriListActivity.this);
        //dbProvider.SeedKategori();

        listKategori = dbProvider.GetAllKategori();

        kategoriListView = (ListView)findViewById(R.id.kategoriListView);
        ArrayAdapter<Kategori> adapterKategori = new ArrayAdapter<Kategori>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1,listKategori){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView==null){
                    convertView = LayoutInflater.from(getContext())
                            .inflate(android.R.layout.simple_list_item_2,parent,false);
                }
                TextView text1 = (TextView)convertView.findViewById(android.R.id.text1);
                TextView text2 = (TextView)convertView.findViewById(android.R.id.text2);

                Kategori kategori = listKategori.get(position);
                text1.setText(String.valueOf(kategori.getKategoriID()));
                text2.setText(kategori.getNamaKategori());
                return convertView;
            }
        };

        kategoriListView.setAdapter(adapterKategori);

    }

}
