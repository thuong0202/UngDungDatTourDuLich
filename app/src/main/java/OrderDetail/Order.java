
package OrderDetail;

public class Order {
    private String fullName;
    private String gender;
    private int age;
    private String phoneNumber;

    public Order(String fullName, String gender, int age, String phoneNumber) {
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String toString() {
        return "Full Name: " + fullName + ", Gender: " + gender + ", Age: " + age + ", Phone Number: " + phoneNumber;
    }
}
