package dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import models.Kategori;

/**
 * Created by erick on 06/07/2017.
 */

public class DatabaseProvider extends SQLiteOpenHelper {
    private static final String LOG = DatabaseProvider.class.getName();

    //versi database
    private static final int DATABASE_VERSION = 1;

    //database name
    private static  final String DATABASE_NAME = "BackendLocalDb";

    private static final String TableKategori = "Kategori";
    private static final String TableBarang = "Barang";

    //table Kategori
    private static final String KategoriID = "KategoriID";
    private static final String NamaKategori = "NamaKategori";

    //table Barang
    private static final String BarangID = "BarangID";
    private static final String NamaBarang = "NamaBarang";
    private static final String Deskripsi = "Deskripsi";
    private static final String Stok = "Stok";
    private static final String HargaBeli = "HargaBeli";
    private static final String HargaJual = "HargaJual";
    private static final String Gambar = "Gambar";
    private static final String isSync = "isSync";

    //create table
    private static final String Create_Table_Kategori = "create table "+TableKategori+
            "("+KategoriID+" integer primary key,"+NamaKategori+" text);";

    private static final String Create_Table_Barang = "create table "+TableBarang+
            "("+BarangID+" text primary key,"+KategoriID+" integer,"+NamaBarang+" text,"
            +Deskripsi+" text,"+Stok+" integer,"+HargaBeli+" real,"
            +HargaJual+" real,"+Gambar+" text, "+isSync+" integer);";

    public DatabaseProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Kategori);
        db.execSQL(Create_Table_Barang);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TableKategori);
        db.execSQL("drop table if exists "+TableBarang);
        onCreate(db);
    }

    //region Table Kategori

    public long TambahKategori(Kategori kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KategoriID,kategori.getKategoriID());
        values.put(NamaKategori,kategori.getNamaKategori());
        long status = db.insert(TableKategori,null,values);
        db.close();
        return status;
    }

    public int UpdateKategori(Kategori kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NamaKategori,kategori.getNamaKategori());

        int status = db.update(TableKategori,values,KategoriID+"=?",
                new String[]{String.valueOf(kategori.getKategoriID())});
        db.close();
        return status;
    }

    public int DeleteKategori(int kategoriID){
        SQLiteDatabase db = this.getWritableDatabase();
        int status = db.delete(TableKategori,KategoriID+"=?",
                new String[]{String.valueOf(kategoriID)});
        db.close();
        return status;
    }

    public List<Kategori> GetAllKategori(){
        List<Kategori> listKategori = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String strSql = "select * from "+TableKategori+" order by "+
                NamaKategori+" asc";
        Cursor c = db.rawQuery(strSql,null);
        if(c.moveToFirst()){
            do{
                Kategori kategori = new Kategori();
                kategori.setKategoriID(c.getInt(c.getColumnIndex(KategoriID)));
                kategori.setNamaKategori(c.getString(c.getColumnIndex(NamaKategori)));
                listKategori.add(kategori);
            }while(c.moveToNext());
        }
        db.close();

        return listKategori;
    }

    public Kategori GetKategoriById(int kategoriID){
        Kategori kategori = new Kategori();
        SQLiteDatabase db = this.getReadableDatabase();
        String strSql = "select * from "+TableKategori+" where "+
                KategoriID+"=? order by "+NamaKategori+" asc";
        Cursor c = db.rawQuery(strSql,new String[]{String.valueOf(kategoriID)});
        if(c!=null)
            c.moveToFirst();
        kategori.setKategoriID(c.getInt(c.getColumnIndex(KategoriID)));
        kategori.setNamaKategori(c.getString(c.getColumnIndex(NamaKategori)));

        db.close();
        return kategori;
    }

    //endregion
}
