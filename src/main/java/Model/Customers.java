package Model;

public class Customers {

    private final long id_customer;
    private final String login;
    private  String  password;
    private String name;
    private  String surname;
    private String phone_number;
    private boolean is_active;

    public Customers(long id_customer, String login, String password, String name, String surname, String phone_number, boolean is_active) {
        this.id_customer = id_customer;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone_number = phone_number;
        this.is_active = is_active;
    }

    public long getId_customer() {
        return id_customer;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id_customer=" + id_customer +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", is_active=" + is_active +
                '}';
    }
}
