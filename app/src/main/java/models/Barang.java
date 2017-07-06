package models;

/**
 * Created by erick on 05/07/2017.
 */

public class Barang {
    private String BarangID;
    private int KategoriID;
    private String NamaBarang;
    private String Deskripsi;
    private int Stok;
    private double HargaBeli;
    private double HargaJual;
    private String Gambar;
    private int isSync;

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }

    public Barang(){}

    public Barang(String barangID, int kategoriID, String namaBarang, String deskripsi, int stok, double hargaBeli, double hargaJual, String gambar) {
        BarangID = barangID;
        KategoriID = kategoriID;
        NamaBarang = namaBarang;
        Deskripsi = deskripsi;
        Stok = stok;
        HargaBeli = hargaBeli;
        HargaJual = hargaJual;
        Gambar = gambar;
    }

    public String getBarangID() {
        return BarangID;
    }

    public void setBarangID(String barangID) {
        BarangID = barangID;
    }

    public int getKategoriID() {
        return KategoriID;
    }

    public void setKategoriID(int kategoriID) {
        KategoriID = kategoriID;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public int getStok() {
        return Stok;
    }

    public void setStok(int stok) {
        Stok = stok;
    }

    public double getHargaBeli() {
        return HargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        HargaBeli = hargaBeli;
    }

    public double getHargaJual() {
        return HargaJual;
    }

    public void setHargaJual(double hargaJual) {
        HargaJual = hargaJual;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }
}
