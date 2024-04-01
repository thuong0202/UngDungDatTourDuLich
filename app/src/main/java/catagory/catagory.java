package catagory;

import java.util.List;

import layout.Anhbamien;

public class catagory {
    private String nameCatagory;
    private List<Anhbamien> anhbamiens;

    public catagory(String nameCatagory, List<Anhbamien> anhbamiens) {
        this.nameCatagory = nameCatagory;
        this.anhbamiens = anhbamiens;
    }

    public String getNameCatagory() {
        return nameCatagory;
    }

    public void setNameCatagory(String nameCatagory) {
        this.nameCatagory = nameCatagory;
    }

    public List<Anhbamien> getAnhbamiens() {
        return anhbamiens;
    }

    public void setAnhbamiens(List<Anhbamien> anhbamiens) {
        this.anhbamiens = anhbamiens;
    }
}
