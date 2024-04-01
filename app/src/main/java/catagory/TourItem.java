package catagory;

public class TourItem {
    private String thoiGianDatTour;
    private String tenTour;

    public TourItem(String thoiGianDatTour, String tenTour) {
        this.thoiGianDatTour = thoiGianDatTour;
        this.tenTour = tenTour;
    }

    public String getThoiGianDatTour() {
        return thoiGianDatTour;
    }

    public String getTenTour() {
        return tenTour;
    }
}
