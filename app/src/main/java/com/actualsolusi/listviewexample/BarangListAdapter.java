package com.actualsolusi.listviewexample;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import models.Barang;

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
        Bitmap bitmap = getBitmapFromAsset(barang.getBarangID());
        imageView.setImageBitmap(bitmap);

        return convertView;
    }

    private Bitmap getBitmapFromAsset(String barangId){
        AssetManager assetManager = getContext().getAssets();
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
