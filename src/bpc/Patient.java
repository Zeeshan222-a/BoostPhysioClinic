package bpc;

// This class stores information about a patient
public class Patient {
    private int id;
    private String name;
    private String address;
    private String phone;

    // Constructor to create a new patient
    public Patient(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getter methods to access private attributes
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    // String representation of the patient object
    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name + ", Address: " + address + ", Phone: " + phone;
    }
} 
