package bpc;

import java.util.*;
import java.time.LocalDateTime;

// This class represents a physiotherapist and their schedule
public class Physiotherapist {
    private int id;
    private String name;
    private String address;
    private String phone;
    private List<String> expertise; // List of expertise areas
    private List<Treatment> treatments; // List of scheduled treatments

    // Constructor
    public Physiotherapist(int id, String name, String address, String phone, List<String> expertise) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.expertise = expertise;
        this.treatments = new ArrayList<>();
    }

    // Add a treatment session to the physiotherapist's schedule
    public void addTreatment(String treatmentName, LocalDateTime dateTime) {
        Treatment treatment = new Treatment(treatmentName, dateTime, this); // Pass 'this' physiotherapist
        treatments.add(treatment);
    }

    public List<Treatment> getTreatmentsByStatus(String status) {
        List<Treatment> result = new ArrayList<>();
        for (Treatment t : treatments) {
            if (t.getStatus().equals(status)) {
                result.add(t);
            }
        }
        return result;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public List<String> getExpertise() { return expertise; }
    public List<Treatment> getTreatments() { return this.treatments; }

    // String representation
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Expertise: " + expertise + ", Phone: " + phone;
    }
} 
