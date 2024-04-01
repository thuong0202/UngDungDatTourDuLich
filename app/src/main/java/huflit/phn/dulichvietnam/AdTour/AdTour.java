package huflit.phn.dulichvietnam.AdTour;

public class AdTour {
    private String Pro_name,Pro_image,address,des,ngay_di,ngay_ve,hotel;
    private double price;
    private int number,id_SC,id_Cate,proId;

    public AdTour() {
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getPro_name() {
        return Pro_name;
    }

    public void setPro_name(String pro_name) {
        Pro_name = pro_name;
    }

    public String getPro_image() {
        return Pro_image;
    }

    public void setPro_image(String pro_image) {
        Pro_image = pro_image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getNgay_di() {
        return ngay_di;
    }

    public void setNgay_di(String ngay_di) {
        this.ngay_di = ngay_di;
    }

    public String getNgay_ve() {
        return ngay_ve;
    }

    public void setNgay_ve(String ngay_ve) {
        this.ngay_ve = ngay_ve;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId_SC() {
        return id_SC;
    }

    public void setId_SC(int id_SC) {
        this.id_SC = id_SC;
    }

    public int getId_Cate() {
        return id_Cate;
    }

    public void setId_Cate(int id_Cate) {
        this.id_Cate = id_Cate;
    }

    @Override
    public String toString() {
        return "AdTour{" +
                "Pro_name='" + Pro_name + '\'' +
                ", Pro_image='" + Pro_image + '\'' +
                ", address='" + address + '\'' +
                ", des='" + des + '\'' +
                ", ngay_di='" + ngay_di + '\'' +
                ", ngay_ve='" + ngay_ve + '\'' +
                ", hotel='" + hotel + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", id_SC=" + id_SC +
                ", id_Cate=" + id_Cate +
                '}';
    }
}
