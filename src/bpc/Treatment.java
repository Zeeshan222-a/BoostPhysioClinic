package bpc;

import java.time.LocalDateTime;

// This class represents a treatment session
public class Treatment {
    private String name;
    private LocalDateTime dateTime;
    private Patient patient; // null means not booked
    private String status; // booked, attended, cancelled
    private Physiotherapist physiotherapist;

    // Constructor
    public Treatment(String name, LocalDateTime dateTime, Physiotherapist physiotherapist) {
        this.name = name;
        this.dateTime = dateTime;
        this.patient = null; // initially unbooked
        this.status = "available";
        this.physiotherapist = physiotherapist;  // Set the physiotherapist
    }

    // Getter for physiotherapist
    public Physiotherapist getPhysiotherapist() {
        return physiotherapist;
    }

    // Book this treatment for a patient
    public boolean book(Patient patient) {
        if (this.patient == null) {
            this.patient = patient;
            this.status = "booked";
            return true;
        }
        return false;
    }

    // Cancel the appointment
    public void cancel() {
        if (this.patient != null) {
            this.status = "cancelled";
            this.patient = null;
        }
    }

    // Mark the appointment as attended
    public void attend() {
        if (this.patient != null && status.equals("booked")) {
            this.status = "attended";
        }
    }

    // Getters
    public String getName() { return name; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Patient getPatient() { return patient; }
    public String getStatus() { return status; }


    @Override
public String toString() {
    String date = dateTime.toLocalDate().toString();
    String time = dateTime.toLocalTime().toString();
    return String.format("%s with %s on %s at %s (Status: %s)",
        name,
        physiotherapist.getName(),
        date,
        time,
        status);
}

}
