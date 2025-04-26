package bpc;

import java.util.*;
import java.time.LocalDateTime;

// Booking system to manage physiotherapists, patients, and appointments
public class BookingSystem {
    private List<Patient> patients;
    private List<Physiotherapist> physios;
    private Scanner scanner;

    public BookingSystem(Scanner scanner) {
        this.scanner = scanner;
        this.patients = new ArrayList<>();
        this.physios = new ArrayList<>();
        preloadData();
    }

    private void preloadData() {
        // Create physiotherapists with their expertise areas
        Physiotherapist p1 = new Physiotherapist(1, "Dr. Awais", "CMH Rawalpindi", "09863773632",
                Arrays.asList("Physiotherapy", "Rehabilitation"));
        Physiotherapist p2 = new Physiotherapist(2, "Dr. Shakeel", "DHA Lahore", "0987654321",
                Arrays.asList("Osteopathy", "Physiotherapy"));
        Physiotherapist p3 = new Physiotherapist(3, "Dr. Syed Haider", "Jada Jhelum", "0765432189",
                Arrays.asList("Physiotherapy", "Rehabilitation"));

        // Create 4-week timetable for each physiotherapist
        createFourWeekTimetable(p1, p2, p3);

        physios.addAll(Arrays.asList(p1, p2, p3));

        // Add example patients
        patients.add(new Patient(101, "Ammad", "12 Cardigan Street, Faisalabad", "0711111111"));
        patients.add(new Patient(102, "Zeeshan", "45 Oxford Lane, Jhelum", "0722222222"));
        patients.add(new Patient(103, "Danish", "12 Street, Lahore", "0733333333"));
        patients.add(new Patient(104, "Jawad", "Rawalpindi", "0744444444"));
        patients.add(new Patient(105, "Shahzaib", "Islamabad", "0755555555"));
        patients.add(new Patient(201, "Ali Khan", "House 5, Sector F-8/3, Islamabad", "03001234567"));
        patients.add(new Patient(202, "Fatima Ahmed", "Flat 12, Gulberg III, Lahore", "03119876543"));
        patients.add(new Patient(203, "Usman Malik", "25-B Satellite Town, Rawalpindi", "03335557788"));
        patients.add(new Patient(204, "Ayesha Raza", "14 Commercial Area, DHA Phase 5, Karachi", "03451112233"));
        patients.add(new Patient(205, "Bilal Hassan", "House 45, University Town, Peshawar", "03007894561"));
    }

    private void createFourWeekTimetable(Physiotherapist p1, Physiotherapist p2, Physiotherapist p3) {
        // Week 1 treatments
        p1.addTreatment("Massage", LocalDateTime.of(2025, 5, 5, 10, 0));
        p1.addTreatment("Neural mobilisation", LocalDateTime.of(2025, 5, 5, 14, 0));
        p2.addTreatment("Spine Mobilisation", LocalDateTime.of(2025, 5, 5, 12, 0));
        p3.addTreatment("Acupuncture", LocalDateTime.of(2025, 5, 5, 11, 0));
        
        // Week 2 treatments
        p1.addTreatment("Massage", LocalDateTime.of(2025, 5, 12, 10, 0));
        p2.addTreatment("Joint Mobilisation", LocalDateTime.of(2025, 5, 12, 12, 0));
        p3.addTreatment("Pool Rehabilitation", LocalDateTime.of(2025, 5, 12, 11, 0));
        
        // Week 3 treatments
        p1.addTreatment("Exercise Therapy", LocalDateTime.of(2025, 5, 19, 10, 0));
        p2.addTreatment("Spine Mobilisation", LocalDateTime.of(2025, 5, 19, 12, 0));
        p3.addTreatment("Acupuncture", LocalDateTime.of(2025, 5, 19, 11, 0));
        
        // Week 4 treatments
        p1.addTreatment("Neural mobilisation", LocalDateTime.of(2025, 5, 26, 14, 0));
        p2.addTreatment("Joint Mobilisation", LocalDateTime.of(2025, 5, 26, 12, 0));
        p3.addTreatment("Pool Rehabilitation", LocalDateTime.of(2025, 5, 26, 11, 0));
    }

    public List<Patient> getPatients() {
        return this.patients;
    }

    public List<Physiotherapist> getPhysios() {
        return this.physios;
    }

    public void viewPhysiotherapists() {
        for (Physiotherapist p : physios) {
            System.out.println(p);
            for (Treatment t : p.getTreatments()) {
                System.out.println("  - " + t); // This will now show status via toString()
            }
        }
    }

    public void addPatient() {
        System.out.println("Adding a new patient:");
        
        System.out.print("Enter patient ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        // Check if patient ID already exists
        if (findPatientById(id) != null) {
            System.out.println("Patient with this ID already exists!");
            return;
        }
        
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter patient address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter patient phone: ");
        String phone = scanner.nextLine();
        
        Patient newPatient = new Patient(id, name, address, phone);
        patients.add(newPatient);
        System.out.println("Patient added successfully!");
    }

    // Method to remove a patient
    public void removePatient() {
        System.out.print("Enter Patient ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Patient patient = findPatientById(id);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        // Cancel all appointments for this patient
        for (Physiotherapist p : physios) {
            for (Treatment t : p.getTreatments()) {
                if (t.getPatient() != null && t.getPatient().getId() == id) {
                    t.cancel();
                }
            }
        }

        patients.remove(patient);
        System.out.println("Patient removed successfully.");
    }

    // Method to view patients
    public void viewPatients() {
        for (Patient p : patients) {
            System.out.println(p);
        }
    }

    //Method to book appointment
    public void bookAppointment() {
        System.out.print("Enter your Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("\nHow would you like to book your appointment?");
        System.out.println("1. By expertise area");
        System.out.println("2. By physiotherapist name");
        System.out.print("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            bookByExpertise(patient);
        } else if (choice == 2) {
            bookByPhysiotherapist(patient);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void bookByExpertise(Patient patient) {
        // Get all unique expertise areas
        Set<String> expertiseAreas = new HashSet<>();
        for (Physiotherapist p : physios) {
            expertiseAreas.addAll(p.getExpertise());
        }

        System.out.println("\nAvailable Expertise Areas:");
        int index = 1;
        List<String> expertiseList = new ArrayList<>(expertiseAreas);
        for (String expertise : expertiseList) {
            System.out.println(index + ". " + expertise);
            index++;
        }

        System.out.print("Select expertise area (1-" + expertiseList.size() + "): ");
        int expertiseChoice = scanner.nextInt();
        scanner.nextLine();

        if (expertiseChoice < 1 || expertiseChoice > expertiseList.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String selectedExpertise = expertiseList.get(expertiseChoice - 1);

        List<Treatment> allTreatments = new ArrayList<>();

        // Find all treatments matching the expertise
        for (Physiotherapist p : physios) {
        if (p.getExpertise().contains(selectedExpertise)) {
            allTreatments.addAll(p.getTreatments());
        }
        }
        
        displayAndBookTreatment(allTreatments, patient);

    }

    private void bookByPhysiotherapist(Patient patient) {
        System.out.println("\nAvailable Physiotherapists:");
        for (int i = 0; i < physios.size(); i++) {
            System.out.println((i + 1) + ". " + physios.get(i).getName());
        }

        System.out.print("Select physiotherapist (1-" + physios.size() + "): ");
        int physioChoice = scanner.nextInt();
        scanner.nextLine();

        if (physioChoice < 1 || physioChoice > physios.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Physiotherapist selectedPhysio = physios.get(physioChoice - 1);


        // Get all available treatments for this physio
        List<Treatment> allTreatments = new ArrayList<>(selectedPhysio.getTreatments());
        displayAndBookTreatment(allTreatments, patient);

    }


    private void displayAndBookTreatment(List<Treatment> treatments, Patient patient) {
        if (treatments.isEmpty()) {
            System.out.println("No treatments found.");
            return;
        }
    
        System.out.println("\nAll Treatments:");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-3s %-25s %-20s %-12s %-8s %-10s%n", 
                         "No.", "Treatment", "Physiotherapist", "Date", "Time", "Status");
        System.out.println("------------------------------------------------------------");
        
        for (int i = 0; i < treatments.size(); i++) {
            Treatment t = treatments.get(i);
            String date = t.getDateTime().toLocalDate().toString();
            String time = t.getDateTime().toLocalTime().toString();
            String status = t.getPatient() == null ? "Available" : "Booked";
            
            System.out.printf("%-3d %-25s %-20s %-12s %-8s %-10s%n",
                (i + 1),
                t.getName(),
                t.getPhysiotherapist().getName(),
                date,
                time,
                status);
        }
    
        System.out.println("------------------------------------------------------------");
        System.out.print("\nSelect treatment to book (1-" + treatments.size() + "): ");
        int treatmentChoice = scanner.nextInt();
        scanner.nextLine();
    
        if (treatmentChoice < 1 || treatmentChoice > treatments.size()) {
            System.out.println("Invalid choice.");
            return;
        }
    
        Treatment selectedTreatment = treatments.get(treatmentChoice - 1);
        
        if (selectedTreatment.getPatient() != null) {
            System.out.println("\nThis appointment is already booked and cannot be reserved.");
            return;
        }
    
        boolean booked = selectedTreatment.book(patient);
        if (booked) {
            System.out.println("\nAppointment booked successfully!");
            System.out.println("Details: " + selectedTreatment);
        } else {
            System.out.println("\nFailed to book appointment.");
        }
    }

    //Method to cancel appointment
    public void cancelAppointment() {
        System.out.print("Enter your Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();
    
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
    
        // Find current appointments
        List<Treatment> currentAppointments = new ArrayList<>();
        for (Physiotherapist p : physios) {
            for (Treatment t : p.getTreatments()) {
                if (t.getPatient() != null && t.getPatient().getId() == patientId 
                    && !t.getStatus().equals("cancelled")) {
                    currentAppointments.add(t);
                }
            }
        }
    
        if (currentAppointments.isEmpty()) {
            System.out.println("No active appointments found to cancel.");
            return;
        }
    
        System.out.println("\nYour Appointments:");
        for (int i = 0; i < currentAppointments.size(); i++) {
            Treatment t = currentAppointments.get(i);
            System.out.printf("%d. %s with %s on %s (Status: %s)%n",
                i + 1,
                t.getName(),
                t.getPhysiotherapist().getName(),
                t.getDateTime(),
                t.getStatus());
        }
    
        System.out.print("Select appointment to cancel (1-" + currentAppointments.size() + "): ");
        int appointmentChoice = scanner.nextInt();
        scanner.nextLine();
    
        if (appointmentChoice < 1 || appointmentChoice > currentAppointments.size()) {
            System.out.println("Invalid choice.");
            return;
        }
    
        Treatment appointmentToCancel = currentAppointments.get(appointmentChoice - 1);
        appointmentToCancel.cancel();
        System.out.println("Appointment cancelled successfully!");
        System.out.println("Cancelled appointment: " + appointmentToCancel);
    }

    //Method to change appointment
    public void changeAppointment() {
        System.out.print("Enter your Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        // Find current appointment
        List<Treatment> currentAppointments = new ArrayList<>();
        for (Physiotherapist p : physios) {
            for (Treatment t : p.getTreatments()) {
                if (t.getPatient() != null && t.getPatient().getId() == patientId) {
                    currentAppointments.add(t);
                }
            }
        }

        if (currentAppointments.isEmpty()) {
            System.out.println("No existing appointments found.");
            return;
        }

        System.out.println("\nYour Current Appointments:");
        for (int i = 0; i < currentAppointments.size(); i++) {
            Treatment t = currentAppointments.get(i);
            System.out.printf("%d. %s with %s on %s%n",
                i + 1,
                t.getName(),
                t.getPhysiotherapist().getName(),
                t.getDateTime());
        }

        System.out.print("Select appointment to change (1-" + currentAppointments.size() + "): ");
        int appointmentChoice = scanner.nextInt();
        scanner.nextLine();

        if (appointmentChoice < 1 || appointmentChoice > currentAppointments.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Treatment oldAppointment = currentAppointments.get(appointmentChoice - 1);

        System.out.println("\nHow would you like to book your new appointment?");
        System.out.println("1. By expertise area");
        System.out.println("2. By physiotherapist name");
        System.out.print("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        List<Treatment> availableTreatments = new ArrayList<>();
        if (choice == 1) {
            availableTreatments = getTreatmentsByExpertise();
        } else if (choice == 2) {
            availableTreatments = getTreatmentsByPhysio();
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        if (availableTreatments.isEmpty()) {
            System.out.println("No available treatments found.");
            return;
        }

        // Display available treatments
        System.out.println("\nAvailable Treatments:");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-3s %-25s %-20s %-20s %-12s%n", 
                         "No.", "Treatment", "Physiotherapist", "Date/Time", "Status");
        System.out.println("------------------------------------------------------------");
        
        for (int i = 0; i < availableTreatments.size(); i++) {
            Treatment t = availableTreatments.get(i);
            String status = t.getPatient() == null ? "Available" : "Booked";
            System.out.printf("%-3d %-25s %-20s %-20s %-12s%n",
                (i + 1),
                t.getName(),
                t.getPhysiotherapist().getName(),
                t.getDateTime(),
                status);
        }

        System.out.print("\nSelect new treatment to book (1-" + availableTreatments.size() + "): ");
        int treatmentChoice = scanner.nextInt();
        scanner.nextLine();

        if (treatmentChoice < 1 || treatmentChoice > availableTreatments.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Treatment newTreatment = availableTreatments.get(treatmentChoice - 1);
        
        if (newTreatment.getPatient() != null) {
            System.out.println("This appointment is already booked.");
            return;
        }

        // Cancel old appointment and book new one
        oldAppointment.cancel();
        newTreatment.book(patient);
        System.out.println("Appointment changed successfully!");
        System.out.println("New appointment: " + newTreatment);
    }

    // Helper methods for changeAppointment
    private List<Treatment> getTreatmentsByExpertise() {
        Set<String> expertiseAreas = new HashSet<>();
        for (Physiotherapist p : physios) {
            expertiseAreas.addAll(p.getExpertise());
        }

        System.out.println("\nAvailable Expertise Areas:");
        int index = 1;
        List<String> expertiseList = new ArrayList<>(expertiseAreas);
        for (String expertise : expertiseList) {
            System.out.println(index + ". " + expertise);
            index++;
        }

        System.out.print("Select expertise area (1-" + expertiseList.size() + "): ");
        int expertiseChoice = scanner.nextInt();
        scanner.nextLine();

        if (expertiseChoice < 1 || expertiseChoice > expertiseList.size()) {
            System.out.println("Invalid choice.");
            return Collections.emptyList();
        }

        String selectedExpertise = expertiseList.get(expertiseChoice - 1);
        List<Treatment> availableTreatments = new ArrayList<>();

        for (Physiotherapist p : physios) {
            if (p.getExpertise().contains(selectedExpertise)) {
                for (Treatment t : p.getTreatments()) {
                    if (t.getPatient() == null) {
                        availableTreatments.add(t);
                    }
                }
            }
        }

        return availableTreatments;
    }

    private List<Treatment> getTreatmentsByPhysio() {
        System.out.println("\nAvailable Physiotherapists:");
        for (int i = 0; i < physios.size(); i++) {
            System.out.println((i + 1) + ". " + physios.get(i).getName());
        }

        System.out.print("Select physiotherapist (1-" + physios.size() + "): ");
        int physioChoice = scanner.nextInt();
        scanner.nextLine();

        if (physioChoice < 1 || physioChoice > physios.size()) {
            System.out.println("Invalid choice.");
            return Collections.emptyList();
        }

        Physiotherapist selectedPhysio = physios.get(physioChoice - 1);
        List<Treatment> availableTreatments = new ArrayList<>();

        for (Treatment t : selectedPhysio.getTreatments()) {
            if (t.getPatient() == null) {
                availableTreatments.add(t);
            }
        }

        return availableTreatments;
    }

    public void markAppointmentAsAttended() {
        System.out.print("Enter your Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();
    
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
    
        // Find all booked appointments for this patient
        List<Treatment> patientAppointments = new ArrayList<>();
        for (Physiotherapist p : physios) {
            for (Treatment t : p.getTreatments()) {
                if (t.getPatient() != null && t.getPatient().getId() == patientId 
                    && !t.getStatus().equals("attended")) {
                    patientAppointments.add(t);
                }
            }
        }
    
        if (patientAppointments.isEmpty()) {
            System.out.println("No unmarked appointments found for this patient.");
            return;
        }
    
        System.out.println("\nYour Appointments:");
        for (int i = 0; i < patientAppointments.size(); i++) {
            Treatment t = patientAppointments.get(i);
            System.out.printf("%d. %s with %s on %s (Status: %s)%n",
                i + 1,
                t.getName(),
                t.getPhysiotherapist().getName(),
                t.getDateTime(),
                t.getStatus());
        }
    
        System.out.print("Select appointment to mark as attended (1-" + patientAppointments.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
    
        if (choice < 1 || choice > patientAppointments.size()) {
            System.out.println("Invalid choice.");
            return;
        }
    
        Treatment selected = patientAppointments.get(choice - 1);
        selected.attend();
        System.out.println("Appointment marked as attended: " + selected);
    }


    public void generateReport() {
        // Current appointments report
        System.out.println("\n=== Current Treatment Report ===");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-20s %-25s %-20s %-15s %-15s%n",
            "Physiotherapist", "Treatment", "Date/Time", "Status", "Patient");
        System.out.println("------------------------------------------------------------");
        
        for (Physiotherapist p : physios) {
            for (Treatment t : p.getTreatments()) {
                System.out.printf("%-20s %-25s %-20s %-15s %-15s%n",
                    p.getName(),
                    t.getName(),
                    t.getDateTime(),
                    t.getStatus(),
                    t.getPatient() != null ? t.getPatient().getName() : "None");
            }
        }
        
        // Cancelled appointments report
        System.out.println("\n=== Cancelled Appointments Report ===");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-20s %-25s %-20s %-15s%n",
            "Physiotherapist", "Treatment", "Date/Time", "Cancelled By");
        System.out.println("------------------------------------------------------------");
        
        for (Physiotherapist p : physios) {
            for (Treatment t : p.getTreatments()) {
                if (t.getStatus().equals("cancelled")) {
                    System.out.printf("%-20s %-25s %-20s %-15s%n",
                        p.getName(),
                        t.getName(),
                        t.getDateTime(),
                        t.getPatient() != null ? t.getPatient().getName() : "System");
                }
            }
        }
        
        // Physiotherapist performance report
        System.out.println("\n=== Physiotherapist Performance Report ===");
        physios.stream()
            .sorted((p1, p2) -> Long.compare(
                p2.getTreatments().stream().filter(t -> t.getStatus().equals("attended")).count(),
                p1.getTreatments().stream().filter(t -> t.getStatus().equals("attended")).count()))
            .forEach(p -> {
                long attended = p.getTreatments().stream()
                    .filter(t -> t.getStatus().equals("attended")).count();
                long cancelled = p.getTreatments().stream()
                    .filter(t -> t.getStatus().equals("cancelled")).count();
                System.out.printf("%s: %d attended, %d cancelled%n", 
                    p.getName(), attended, cancelled);
            });
    }

    public Patient findPatientById(int id) {
        for (Patient p : patients) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private Physiotherapist findPhysioById(int id) {
        for (Physiotherapist p : physios) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
