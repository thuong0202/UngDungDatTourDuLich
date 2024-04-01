package huflit.phn.dulichvietnam.Admin_layout.Admin_element.AdTravel;

import androidx.annotation.NonNull;

public class AdTravel {
    private String HovaTen, Sdt;
    private String GioiTinh;
    private String ngsinh;
    public AdTravel() {
    }

    public AdTravel(String hovaTen, String sdt, String gioiTinh, String ngsinh) {
        HovaTen = hovaTen;
        Sdt = sdt;
        GioiTinh = gioiTinh;
        this.ngsinh = ngsinh;
    }

    public String getHovaTen() {
        return HovaTen;
    }

    public void setHovaTen(String hovaTen) {
        HovaTen = hovaTen;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getNgsinh() {
        return ngsinh;
    }

    public void setNgsinh(String ngsinh) {
        this.ngsinh = ngsinh;
    }

    @Override
    public String toString() {
        return HovaTen + " : " + Sdt + " : " + GioiTinh + " : " + ngsinh;
    }
}

