package Test;

import Database.DatabaseConnection;
import Model.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Test {

    public void test() throws SQLException {
        DatabaseConnection dbc = new DatabaseConnection("jdbc:oracle:thin:@DESKTOP-NJJMCEP:1521:xe","c##cinema_user","cinema_user");

        try {


            System.out.println("-------------------1------------------------------");
            dbc.insertMovie("Superman", 140, "xx distributor", new Timestamp(2022 - 1900, 2 - 1, 1, 12, 0, 0, 0), new Timestamp(2022, 2, 1, 12, 0, 0, 0), 'D', false);
            dbc.insertMovie("Spiderman", 120, "xx distributor", new Timestamp(2022 - 1900, 2 - 1, 1, 12, 0, 0, 0), new Timestamp(2022, 2, 1, 12, 0, 0, 0), 'D', false);
            System.out.println("-------------------2------------------------------");
            dbc.insertScreeningRoom(8, 10);
            dbc.insertScreeningRoom(7, 10);
            dbc.insertScreeningRoom(9, 10);

            System.out.println("-------------------3------------------------------");
            Timestamp timestamp1 = new Timestamp(2022 - 1900, 2-1, 1, 12, 5, 0, 0);
            Timestamp timestamp2 = new Timestamp(2022 - 1900, 2-1, 1, 14, 50, 0, 0);
            Timestamp timestamp3 = new Timestamp(2022 - 1900, 2-1, 1, 15, 15, 0, 0);
            Timestamp timestamp4 = new Timestamp(2022 - 1900, 2-1, 1, 19, 0, 0, 0);

            dbc.insertSeance(timestamp1, timestamp2, 15.303333d, 1, 2);
            dbc.insertSeance(timestamp3, timestamp4, 20.3773d, 2, 1);
            System.out.println("-------------------4------------------------------");
            dbc.insertPosition("sprzedawca", 3000d);
            dbc.insertPosition("kierownik", 7000d);
            System.out.println("-------------------5------------------------------");
            dbc.insertEmployee("empl1", "sed3", "Julixa", "Noxwako", "65734903", timestamp1, 2);
            dbc.insertEmployee("1", "1", "Julia", "Nowako", "65734503", timestamp1, 2);
            dbc.insertEmployee("empl2", "pwe4", "Pawel", "Towak", "923401903", timestamp1, 1);
            dbc.insertEmployee("empl3", "p554", "Julian", "Nowak", "567401903", timestamp1, 1);
            System.out.println("-------------------6------------------------------");
            dbc.insertCustomer("1", "1", "Jan", "Nowak", "758345903");
            dbc.insertCustomer("8dsdf", "45rrrf", "Pawel", "Nowak", "958301903");
            dbc.insertCustomer("555df", "4678rf", "Tomasz", "Nowak", "954561903");
            System.out.println("-------------------7------------------------------");
            dbc.insertTransactions(30, 2, 0, 1, 1);
            dbc.insertTransactions(20, 1, 0, 2, 2);
            System.out.println("-------------------8------------------------------");
            dbc.insertResTeakenSeats('A', 5, 1, 1);
            dbc.insertResTeakenSeats('A', 6, 1, 1);
            dbc.insertResTeakenSeats('B', 4, 2, 2);
            System.out.println("-------------------9------------------------------");
            dbc.insertTeakenSeats('A', 7, 1, 0, 0);  //moze wystapic blad z 0
            dbc.insertTeakenSeats('B', 2, 1, 0, 0);
            dbc.insertTeakenSeats('B', 3, 1, 0, 0);
            System.out.println("-------------------10------------------------------");
            dbc.customerAccountDeactivation(2);
            System.out.println("-------------------11------------------------------");
            dbc.changeCustomer(3, "xxxxx", "Juliusz", "Slowacki", "888561903");
            System.out.println("-------------------12------------------------------");
            dbc.fireEmployee(3, timestamp4);
            System.out.println("-------------------13------------------------------");
            dbc.changePositionName(1, "kier1");
            System.out.println("-------------------14------------------------------");
            dbc.changePositionSalary(1, 4500);
            System.out.println("-------------------15------------------------------");
            dbc.changeReservationToTeaken(3, 2);
            System.out.println("-------------------16------------------------------");
            dbc.deleteReservation(2, 1);

            System.out.println("-------------------17------------------------------");
            List<Customers> list1 = dbc.getCustomersList();
            System.out.println("-------------------18------------------------------");
            List<Employees> list2 = dbc.getEmployeesList();
            System.out.println("-------------------19------------------------------");
            List<Movies> list3 = dbc.getMoviesList();
            System.out.println("-------------------20------------------------------");
            List<Positions> list4 = dbc.getPositionsList();
            System.out.println("-------------------21------------------------------");
            List<ScreeningRooms> list5 = dbc.getScreeningRoomsList();
            System.out.println("-------------------22------------------------------");
            List<Seances> list6 = dbc.getSeancesList();
            System.out.println("-------------------23------------------------------");
            List<TakenSeats> list7 = dbc.getTakenSeatsList();
            System.out.println("-------------------24------------------------------");
            List<Transactions> list8 = dbc.getTransactionsList();

            System.out.println(dbc.isEmployeeAnManager(2));


            System.out.println(list1.toString());
            System.out.println(list2.toString());
            System.out.println(list3.toString());
            System.out.println(list4.toString());
            System.out.println(list5.toString());
            System.out.println(list6.toString());
            System.out.println(list7.toString());
            System.out.println(list8.toString());

            System.out.println(dbc.getTransactionId());


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
