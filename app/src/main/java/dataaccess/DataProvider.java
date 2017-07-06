package dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Barang;

/**
 * Created by erick on 05/07/2017.
 */

public final class DataProvider {
    public static List<Barang> barangList = new ArrayList<>();
    public static Map<String,Barang> barangMap = new HashMap<>();

    public static void addBarang(String BarangID,int KategoriID,
                        String NamaBarang,String Deskripsi,int Stok,
                        double HargaBeli,double HargaJual,String Gambar) {
        Barang barang = new Barang(BarangID,KategoriID,NamaBarang,Deskripsi,Stok,HargaBeli,HargaJual,Gambar);
        barangList.add(barang);
        barangMap.put(BarangID,barang);
    }

    public static List<String> getBarangName(){
        List<String> list = new ArrayList<>();
        for(Barang barang : barangList){
            list.add(barang.getNamaBarang());
        }
        return list;
    }

    public static List<Barang> getFilteredList(String searchString){
        List<Barang> filterList = new ArrayList<>();
        for(Barang barang : barangList){
            if(barang.getBarangID().contains(searchString)){
                filterList.add(barang);
            }
        }
        return filterList;
    }

    static {
        addBarang("shirt101",1,
                "Cross-back training tank",
                "Our cross-back training tank is made from organic cotton with 10% Lycra for form and support, and a flattering feminine cut.",
                35,200000,220000,"shirt101.png");

        addBarang("jacket101",1,
                "Bamboo thermal ski coat",
                "You’ll be the most environmentally conscious skier on the slopes - and the most stylish - wearing our fitted bamboo thermal ski coat, made from organic bamboo with recycled plastic down filling.",
                128,250000,280000,"jacket101.png");

        addBarang("pants101",1,
                "Stretchy dance pants",
                "Whether dancing the samba, mastering a yoga pose, or scaling the climbing wall, our stretchy dance pants, made from 80% organic cotton and 20% Lycra, are the most versatile and comfortable workout pants you’ll ever have the pleasure of wearing.",
                85,120000,140000,"pants101.png");

        addBarang("shirt102",1,
                "Ultra-soft tank top",
                "This versatile tank can be worn in the gym, under a blazer, or for a day out in the sun. Made from our patented organic bamboo and cotton mix, our ultra-soft tank never stops feeling soft, even when you forget the fabric softener.",
                23,120000,150000,"shirt102.png");

        addBarang("shirt103",1,
                "V-neck t-shirt",
                "Our pre-shrunk organic cotton t-shirt, with its slightly fitted waist and elegant V-neck is designed to flatter. You’ll want one in every color!",
                26,260000,300000,"shirt103.png");
    }
}
