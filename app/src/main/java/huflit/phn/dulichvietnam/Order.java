package huflit.phn.dulichvietnam;

public class Order {
    private int orderId;
    private String phone;

    public Order() {
    }

    public Order(int orderId, String phone) {
        this.orderId = orderId;
        this.phone = phone;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", phone='" + phone + '\'' +
                '}';
    }
}
