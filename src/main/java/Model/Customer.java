package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    String id;
    String title;
    String name;
    LocalDate dob;
    Double salary;
    String address;
    String city;
    String province;
    String postalCode;

    public String getFullName(){
        return title +" "+ name;
    }



}
