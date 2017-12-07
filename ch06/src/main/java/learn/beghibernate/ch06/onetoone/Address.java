package learn.beghibernate.ch06.onetoone;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;

    private String inCityLocation;

    @OneToOne(mappedBy = "address")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInCityLocation() {
        return inCityLocation;
    }

    public void setInCityLocation(String inCityLocation) {
        this.inCityLocation = inCityLocation;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", inCityLocation='" + inCityLocation + '\'' +
                '}';
    }
}
