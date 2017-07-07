package dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import models.Barang;
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

    public void DeleteAllKategori(){
        SQLiteDatabase db = this.getWritableDatabase();
        String strSql = "delete from "+TableKategori;
        db.execSQL(strSql);
        db.close();
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

    public void SeedKategori(){
        Kategori kategori1 = new Kategori();
        kategori1.setKategoriID(1);
        kategori1.setNamaKategori("Shirt");
        TambahKategori(kategori1);

        Kategori kategori2 = new Kategori();
        kategori2.setKategoriID(2);
        kategori2.setNamaKategori("Jacket");
        TambahKategori(kategori2);

        Kategori kategori3 = new Kategori();
        kategori3.setKategoriID(3);
        kategori3.setNamaKategori("Pants");
        TambahKategori(kategori3);

        Kategori kategori4 = new Kategori();
        kategori4.setKategoriID(4);
        kategori4.setNamaKategori("Vest");
        TambahKategori(kategori4);

        Kategori kategori5 = new Kategori();
        kategori5.setKategoriID(5);
        kategori5.setNamaKategori("Blouse");
        TambahKategori(kategori5);
    }

    //endregion

    //region Table Barang

    public long InsertBarang(Barang barang){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BarangID,barang.getBarangID());
        values.put(KategoriID,barang.getKategoriID());
        values.put(NamaBarang,barang.getNamaBarang());
        values.put(Deskripsi,barang.getDeskripsi());
        values.put(Stok,barang.getStok());
        values.put(HargaBeli,barang.getHargaBeli());
        values.put(HargaJual,barang.getHargaJual());
        values.put(Gambar,barang.getGambar());
        values.put(isSync,barang.getIsSync());

        long status = db.insert(TableBarang,null,values);
        db.close();
        return status;
    }

    public List<Barang> GetAllBarang(){
        List<Barang> listBarang = new ArrayList<>();
        String strSql = "select Barang.BarangID,Barang.KategoriID,Kategori.NamaKategori," +
                "Barang.NamaBarang,Barang.Deskripsi,Barang.Stok,Barang.HargaBeli,Barang.HargaJual," +
                "Barang.Gambar,Barang.isSync " +
                "from Barang inner join Kategori " +
                "on Barang.KategoriID=Kategori.KategoriID";

        //Log.e(LOG,strSql);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(strSql,null);
        if(c.moveToFirst()){
            do{
                Barang barang = new Barang();
                barang.setBarangID(c.getString(c.getColumnIndex(BarangID)));
                barang.setKategoriID(c.getInt(c.getColumnIndex(KategoriID)));
                barang.setNamaBarang(c.getString(c.getColumnIndex(NamaBarang)));
                barang.setDeskripsi(c.getString(c.getColumnIndex(Deskripsi)));
                barang.setStok(c.getInt(c.getColumnIndex(Stok)));
                barang.setHargaBeli(c.getDouble(c.getColumnIndex(HargaBeli)));
                barang.setHargaJual(c.getDouble(c.getColumnIndex(HargaJual)));
                barang.setGambar(c.getString(c.getColumnIndex(Gambar)));
                barang.setIsSync(c.getInt(c.getColumnIndex(isSync)));

                Kategori kategori = new Kategori();
                kategori.setKategoriID(c.getInt(c.getColumnIndex(KategoriID)));
                kategori.setNamaKategori(c.getString(c.getColumnIndex(NamaKategori)));
                barang.setKategori(kategori);

                listBarang.add(barang);
            }while (c.moveToNext());
        }
        db.close();

        return listBarang;
    }

    public Barang GetBarangById(String barangID){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSql = "select * from "+TableBarang+" where "+BarangID+"=?";
        Log.e(LOG,strSql);

        Cursor c = db.rawQuery(strSql,new String[]{barangID});
        if(c!=null)
            c.moveToFirst();
        Barang barang = new Barang();
        barang.setBarangID(c.getString(c.getColumnIndex(BarangID)));
        barang.setKategoriID(c.getInt(c.getColumnIndex(KategoriID)));
        barang.setNamaBarang(c.getString(c.getColumnIndex(NamaBarang)));
        barang.setDeskripsi(c.getString(c.getColumnIndex(Deskripsi)));
        barang.setStok(c.getInt(c.getColumnIndex(Stok)));
        barang.setHargaBeli(c.getDouble(c.getColumnIndex(HargaBeli)));
        barang.setHargaJual(c.getDouble(c.getColumnIndex(HargaJual)));
        barang.setGambar(c.getString(c.getColumnIndex(Gambar)));
        barang.setIsSync(c.getInt(c.getColumnIndex(isSync)));

        db.close();

        return barang;
    }


    //endregion
}
