package learn.beghibernate.ch04.general;

import javax.persistence.*;

@Entity
public class SimpleObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String key;

    @Column
    private Long value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SimpleObject)) return false;

        SimpleObject that = (SimpleObject) o;

        if (getId() != null
                ? !getId().equals(that.getId())
                : that.getId() != null) {
            return false;
        }
        if (getKey() != null
                ? !getKey().equals(that.getKey())
                : that.getKey() != null) {
            return false;
        }
        return getValue() != null
                ? getValue().equals(that.getValue())
                : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SimpleObject{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
