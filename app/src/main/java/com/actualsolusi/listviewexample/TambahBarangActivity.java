package com.actualsolusi.listviewexample;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import dataaccess.DatabaseProvider;
import models.Barang;
import models.Kategori;

public class TambahBarangActivity extends AppCompatActivity {
    private ImageView imgBarang;
    private EditText txtBarangID,txtNamaBarang,txtDeskripsi,txtStok,txtHargaBeli,txtHargaJual;
    private Spinner spKategori;
    private Button btnTambah;
    private DatabaseProvider db;
    private List<Kategori> listKategori;
    private Kategori kategoriSelected;
    private Bitmap imageBitmap;
    public static String absolutePath;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
        btnTambah = (Button) findViewById(R.id.btnTambah);

        spKategori = (Spinner)findViewById(R.id.spKategori);

        listKategori = db.GetAllKategori();

        SpinnerKategoriAdapter spinnerAdapter = new SpinnerKategoriAdapter(TambahBarangActivity.this,
                android.R.layout.simple_spinner_item,listKategori);
        spKategori.setAdapter(spinnerAdapter);

        kategoriSelected = listKategori.get(0);

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

                /*Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                startActivityForResult(i, 2);*/
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Barang barang = new Barang();
                barang.setBarangID(txtBarangID.getText().toString().trim());
                barang.setKategoriID(kategoriSelected.getKategoriID());
                barang.setNamaBarang(txtNamaBarang.getText().toString().trim());
                barang.setDeskripsi(txtDeskripsi.getText().toString().trim());
                barang.setStok(Integer.valueOf(txtStok.getText().toString().trim()));
                barang.setHargaBeli(Double.valueOf(txtHargaBeli.getText().toString().trim()));
                barang.setHargaJual(Double.valueOf(txtHargaJual.getText().toString().trim()));
                barang.setGambar(txtBarangID.getText().toString().trim()+".png");
                barang.setIsSync(0);

                long status = db.InsertBarang(barang);
                if(status!=-1){
                    try {
                        String myPath = saveToInternalStorage(imageBitmap);
                        absolutePath = myPath;
                        Intent i = new Intent(TambahBarangActivity.this,
                                BarangListActivity.class);
                        i.putExtra("myPath",absolutePath);
                        startActivity(i);

                        /*Toast.makeText(getApplicationContext(),
                                "Data Barang berhasil ditambah",Toast.LENGTH_LONG).show();
                        Log.v("MyPath",myPath);*/
                    } catch (IOException e) {
                        Log.e("Error : ",e.getLocalizedMessage());
                    }
                }
            }
        });

    }

    private String saveToInternalStorage(Bitmap bitmapImage) throws IOException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir",MODE_PRIVATE);
        File mypath = new File(directory,txtBarangID.getText().toString().trim()+".png");
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG,100,fos);
            Log.v("TambahBarangActivity",directory.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fos.close();
        }

        return directory.getAbsolutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap)extras.get("data");
            imgBarang.setImageBitmap(imageBitmap);
        }

        /*if (requestCode == 2 && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageBitmap = BitmapFactory.decodeFile(picturePath);
            imgBarang.setImageBitmap(imageBitmap);
        }*/
    }
}
