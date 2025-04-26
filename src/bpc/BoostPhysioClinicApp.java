package bpc;

import java.util.*;

// Main class to run the application
public class BoostPhysioClinicApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingSystem bookingSystem = new BookingSystem(scanner);

        int choice;
        do {
            // Display menu
            System.out.println("\n=== Boost Physio Clinic Booking System ===");
            System.out.println("1. Add Patient");
            System.out.println("2. Remove Patient");
            System.out.println("3. View Patients");
            System.out.println("4. Book Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Change Appointment");
            System.out.println("7. Attend Appointment");
            System.out.println("8. Generate Report");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    bookingSystem.addPatient();
                    break;
                case 2:
                    bookingSystem.removePatient();
                    break;
                case 3:
                    bookingSystem.viewPatients();
                    break;
                case 4:
                    bookingSystem.bookAppointment();
                    break;
                case 5:
                    bookingSystem.cancelAppointment();
                    break;
                case 6:
                    bookingSystem.changeAppointment();
                    break;
                case 7:
                    bookingSystem.markAppointmentAsAttended();
                    break;
                case 8:
                    bookingSystem.generateReport();
                    break;
                case 9:
                    System.out.println("Exiting system. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 7);

        scanner.close();
    }
}

