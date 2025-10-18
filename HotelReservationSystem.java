import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "root@123";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n==== HOTEL MANAGEMENT SYSTEM ====");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> reserveRoom(connection, scanner);
                    case 2 -> viewReservations(connection);
                    case 3 -> getRoomNumber(connection, scanner);
                    case 4 -> updateReservation(connection, scanner);
                    case 5 -> deleteReservation(connection, scanner);
                    case 0 -> {
                        exit();
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            scanner.nextLine();
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter contact number: ");
            String contactNumber = scanner.nextLine();

            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, guestName);
                ps.setInt(2, roomNumber);
                ps.setString(3, contactNumber);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "Reservation successful!" : "Reservation failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection) throws SQLException {
        String sql = "SELECT * FROM reservations";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\nCurrent Reservations:");
            System.out.println("+----+-----------------+------------+-------------------+---------------------+");
            System.out.println("| ID | Guest Name      | Room No.   | Contact Number    | Date                |");
            System.out.println("+----+-----------------+------------+-------------------+---------------------+");
            while (rs.next()) {
                System.out.printf("| %-2d | %-15s | %-10d | %-17s | %-19s |\n",
                        rs.getInt("reservation_id"),
                        rs.getString("guest_name"),
                        rs.getInt("room_number"),
                        rs.getString("contact_number"),
                        rs.getTimestamp("reservation_date").toString());
            }
            System.out.println("+----+-----------------+------------+-------------------+---------------------+");
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            String sql = "SELECT room_number FROM reservations WHERE reservation_id = ? AND guest_name = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("Room Number: " + rs.getInt("room_number"));
                } else {
                    System.out.println("No matching reservation found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (!reservationExists(connection, id)) {
                System.out.println("Reservation not found.");
                return;
            }

            System.out.print("Enter new guest name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new room number: ");
            int room = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new contact number: ");
            String contact = scanner.nextLine();

            String sql = "UPDATE reservations SET guest_name=?, room_number=?, contact_number=? WHERE reservation_id=?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setInt(2, room);
                ps.setString(3, contact);
                ps.setInt(4, id);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "Updated successfully!" : "Update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter reservation ID: ");
            int id = scanner.nextInt();
            if (!reservationExists(connection, id)) {
                System.out.println("Reservation not found.");
                return;
            }
            String sql = "DELETE FROM reservations WHERE reservation_id=?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "Deleted successfully!" : "Delete failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection connection, int id) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id=?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        for (int i = 5; i > 0; i--) {
            System.out.print(".");
            Thread.sleep(1000);
        }
        System.out.println("\nThank you for using Hotel Reservation System!");
    }
}
