package com.actualsolusi.listviewexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import dataaccess.DatabaseProvider;
import models.Kategori;
import services.KategoriServices;

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
               SyncWithServer();
            }
        });

        kategoriListView = (ListView)findViewById(R.id.kategoriListView);

        //dari sqlite
        dbProvider = new DatabaseProvider(KategoriListActivity.this);
        //dbProvider.SeedKategori();
        //dbProvider.DeleteAllKategori();


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLocalData();
        //loadContent();
    }

    private void loadLocalData(){
        listKategori = dbProvider.GetAllKategori();

        ArrayAdapter<Kategori> adapterKategori = new ArrayAdapter<Kategori>(getApplicationContext(),
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

    private void SyncWithServer(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    dbProvider.DeleteAllKategori();
                    listKategori = KategoriServices.GetAll();

                } catch (IOException e) {
                    Log.d("MyServices",e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                for(Kategori kat : listKategori){
                    dbProvider.TambahKategori(kat);
                }
                Toast.makeText(KategoriListActivity.this,"Sync data kategori berhasil !",Toast.LENGTH_LONG).show();
                loadLocalData();
            }
        }.execute();
    }

    private void loadContent(){
        new AsyncTask<Void,Void,Void>(){
            ProgressDialog progress;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(KategoriListActivity.this);
                progress.setMessage("loading...");
                progress.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    listKategori = KategoriServices.GetAll();

                } catch (IOException e) {
                    Log.d("MyServices",e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                ArrayAdapter<Kategori> adapterKategori = new ArrayAdapter<Kategori>(getApplicationContext(),
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
                if(progress!=null){
                    progress.dismiss();
                }
            }
        }.execute();
    }

}
