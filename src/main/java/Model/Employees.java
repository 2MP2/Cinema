package Model;
import java.sql.Timestamp;

public class Employees {
    private final long id_employee;
    private final String login;
    private String password;
    private String name;
    private String surname;
    private String phone_number;
    private Timestamp hire_date;
    private Timestamp fire_date;
    private long id_position;

    public Employees(long id_employee, String login, String password, String name, String surname, String phone_number, Timestamp hire_date, Timestamp fire_date, long id_position) {
        this.id_employee = id_employee;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone_number = phone_number;
        this.hire_date = hire_date;
        this.fire_date = fire_date;
        this.id_position = id_position;
    }

    public long getId_employee() {
        return id_employee;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Timestamp getHire_date() {
        return hire_date;
    }

    public void setHire_date(Timestamp hire_date) {
        this.hire_date = hire_date;
    }

    public Timestamp getFire_date() {
        return fire_date;
    }

    public void setFire_date(Timestamp fire_date) {
        this.fire_date = fire_date;
    }

    public long getId_position() {
        return id_position;
    }

    public void setId_position(long id_position) {
        this.id_position = id_position;
    }

    @Override
    public String toString() {
        return "id_employee=" + id_employee +
                ", name=" + name +
                ", surname=" + surname+
                ", phone_number=" + phone_number +
                ", hire_date=" + hire_date +
                ", fire_date=" + fire_date +
                ", id_position=" + id_position;
    }
}
