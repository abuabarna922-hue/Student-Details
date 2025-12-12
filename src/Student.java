import java.sql.*;
import java.util.Scanner;

public class Student {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Database details
        String url = "jdbc:mysql://localhost:3306/student_db";
        String user = "root";
        String pass = "";

        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            while (true) {
                System.out.println("\n1. Add  2. View  3. Search  4. Delete  5. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();

                // ADD STUDENT
                if (ch == 1) {
                    System.out.print("Enter Roll: ");
                    int roll = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();

                    String query = "INSERT INTO student VALUES (?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setInt(1, roll);
                    pst.setString(2, name);
                    pst.setInt(3, age);
                    pst.executeUpdate();

                    System.out.println("Student Added!");
                }

                // VIEW ALL
                else if (ch == 2) {
                    String query = "SELECT * FROM student";
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        System.out.println("Roll: " + rs.getInt(1) +
                                "  Name: " + rs.getString(2) +
                                "  Age: " + rs.getInt(3));
                    }
                }

                // SEARCH
                else if (ch == 3) {
                    System.out.print("Enter Roll to Search: ");
                    int roll = sc.nextInt();

                    String query = "SELECT * FROM student WHERE roll = ?";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setInt(1, roll);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        System.out.println("Roll: " + rs.getInt(1) +
                                "  Name: " + rs.getString(2) +
                                "  Age: " + rs.getInt(3));
                    } else {
                        System.out.println("Student not found!");
                    }
                }

                // DELETE
                else if (ch == 4) {
                    System.out.print("Enter Roll to Delete: ");
                    int roll = sc.nextInt();

                    String query = "DELETE FROM student WHERE roll = ?";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setInt(1, roll);
                    int rows = pst.executeUpdate();

                    if (rows > 0)
                        System.out.println("Student Deleted!");
                    else
                        System.out.println("Student not found!");
                }

                // EXIT
                else if (ch == 5) {
                    System.out.println("Exiting...");
                    break;
                }

                else {
                    System.out.println("Invalid choice!");
                }
            }

            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
