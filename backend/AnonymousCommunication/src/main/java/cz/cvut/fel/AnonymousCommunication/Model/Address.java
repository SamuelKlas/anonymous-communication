package cz.cvut.fel.AnonymousCommunication.Model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    @Basic
    private String street;

    @Basic
    private String city;

    @Basic
    private int houseNumber;

    @Basic
    private String postalCode;
}
