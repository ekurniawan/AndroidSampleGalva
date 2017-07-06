package com.actualsolusi.listviewexample;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import models.Barang;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by erick on 05/07/2017.
 */

public class BarangListAdapter extends ArrayAdapter<Barang> {
    private List<Barang> listBarang;
    public BarangListAdapter(Context context,int resource,
                             List<Barang> objects) {
        super(context, resource, objects);
        listBarang = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item,parent,false);
        }

        Barang barang = listBarang.get(position);
        TextView tvNamaBarang = (TextView) convertView.findViewById(R.id.tvNamaBarang);
        tvNamaBarang.setText(barang.getNamaBarang());

        TextView tvStok = (TextView) convertView.findViewById(R.id.tvStok);
        tvStok.setText(String.valueOf(barang.getStok()));

        TextView tvHarga = (TextView)convertView.findViewById(R.id.tvHarga);
        tvHarga.setText(String.valueOf(barang.getHargaJual()));

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        try {
            //Log.v("BarangListAdapter",getContext().getFilesDir().getAbsolutePath());
            //Log.v("BarangListAdapter",barang.getGambar());
            String myPath = "";
            /*SharedPreferences myPrefs = getContext().getSharedPreferences("myPrefs",0);
            if(myPrefs.contains("myPath")){
                myPath = myPrefs.getString("myPath","tidak ditemukan");
            }*/
            ContextWrapper cw = new ContextWrapper(getContext());
            File directory = cw.getDir("imageDir",MODE_PRIVATE);

            Bitmap bitmap = loadImageFromStorage(directory.getAbsolutePath(),
                    barang.getGambar().toString().trim());
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    /*private Bitmap getBitmapFromAsset(String barangId){
        AssetManager assetManager = getContext().getAssets();
        InputStream stream = null;
        try{
            stream = assetManager.open(barangId+".png");
            return BitmapFactory.decodeStream(stream);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }*/

    private Bitmap loadImageFromStorage(String path, String filename) throws FileNotFoundException {
        File f = new File(path,filename);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }

}
