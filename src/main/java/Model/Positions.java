package Model;

public class Positions {
    private final long id_position;
    private String name;
    private double salary;

    public Positions(long id_position, String name, double salary) {
        this.id_position = id_position;
        this.name = name;
        this.salary = salary;
    }

    public long getId_position() {
        return id_position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "id_position=" + id_position +
                ", name=" + name +
                ", salary=" + salary;
    }
}
