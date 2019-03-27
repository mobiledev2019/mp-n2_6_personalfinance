package com.example.personal.ThongKe;

/**
 * Created by trongnhan on 10/20/16.
 */

public class ThongKe {
    private String khoanthukhoanchi;
    private String sotien;

    public ThongKe(String khoanthukhoanchi, String sotien, String phanloai) {
        this.khoanthukhoanchi = khoanthukhoanchi;
        this.sotien = sotien;
    }
    public ThongKe() {
    }

    public String getKhoanthukhoanchi() {
        return khoanthukhoanchi;
    }

    public void setKhoanthukhoanchi(String khoanthukhoanchi) {
        this.khoanthukhoanchi = khoanthukhoanchi;
    }

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
    }

}
