package tm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CustomerTM {
    private String id;
    private String title;
    private String name;
    private Date dob;
    private String city;
    private String province;
    private String postalCode;
    private String address;
    private Double salary;

    public CustomerTM(String id, String title, String name, Date dob, String city, String province, String postalCode, String address, Double salary) {
        this.id = id;
        this.name = title + " " + name;
        this.dob = dob;
        this.salary = salary;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;


    }
}
