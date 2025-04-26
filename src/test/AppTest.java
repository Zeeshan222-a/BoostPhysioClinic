package test;

import org.junit.Before;
import org.junit.Test;

import bpc.BookingSystem;
import bpc.Patient;
import bpc.Physiotherapist;
import bpc.Treatment;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AppTest {
    private BookingSystem bookingSystem;

    @Before
    public void setUp() {
        // Create a fresh BookingSystem for each test
        bookingSystem = new BookingSystem(new Scanner(""));
    }

    @Test
    public void testAddPatient() {
        // Prepare test input
        Patient testPatient = new Patient(11, "Patient 11", "Abc 123", "123456789");
        
        // Execute
        int initialPatientCount = bookingSystem.getPatients().size();
        bookingSystem.getPatients().add(testPatient);

        
        // Verify
        int expectedPatientsCount = initialPatientCount + 1;
        int actualPatientsCount = bookingSystem.getPatients().size();
        assertEquals(expectedPatientsCount, actualPatientsCount);

        assertNotNull(bookingSystem.findPatientById(11));
    }

    @Test
    public void testRemovePatient(){
        // Prepare test input
        Patient testPatient = new Patient(11, "Patient 11", "Abc 123", "123456789");
        bookingSystem.getPatients().add(testPatient);
        
        // Execute
        int initialPatientCount = bookingSystem.getPatients().size();
        bookingSystem.getPatients().remove(testPatient);

        // Verify
        int expectedPatientsCount = initialPatientCount - 1;
        int actualPatientsCount = bookingSystem.getPatients().size();
        assertEquals(expectedPatientsCount, actualPatientsCount);

        assertNull(bookingSystem.findPatientById(11));
    }

    @Test
    public void testBookAppointment()
    {
        // Prepare test input
        Patient testPatient = new Patient(11, "Patient 11", "Abc 123", "123456789");
        bookingSystem.getPatients().add(testPatient);
        
        Physiotherapist testPhysio = new Physiotherapist(1, "Dr. Test", "Clinic", "555-0000", 
                                       List.of("Physiotherapy"));
        bookingSystem.getPhysios().add(testPhysio);

        LocalDateTime testTime = LocalDateTime.now().plusDays(1);
        Treatment testTreatment = new Treatment("Massage", testTime, testPhysio);
        
        // Execute
        testPhysio.getTreatments().add(testTreatment);
        Treatment verifyTreatment = testPhysio.getTreatments().get(0);
        
        
        // Verify
        boolean actualResult = verifyTreatment.book(testPatient);
        assertTrue("Appointment should be booked successfully", actualResult);

        String expectedStatus = "booked";
        String actualStatus = verifyTreatment.getStatus();
        assertEquals(expectedStatus, actualStatus);

        assertEquals(testPatient, verifyTreatment.getPatient());
    }

    @Test
    public void testCancelAppointment()
    {
        // Prepare test input
        Patient testPatient = new Patient(11, "Patient 11", "Abc 123", "123456789");
        bookingSystem.getPatients().add(testPatient);
        
        Physiotherapist testPhysio = new Physiotherapist(1, "Dr. Test", "Clinic", "555-0000", 
                                       List.of("Physiotherapy"));
        bookingSystem.getPhysios().add(testPhysio);

        LocalDateTime testTime = LocalDateTime.now().plusDays(1);
        Treatment testTreatment = new Treatment("Massage", testTime, testPhysio);
        
        // Execute
        testTreatment.book(testPatient);
        testPhysio.getTreatments().add(testTreatment);
        Treatment verifyTreatment = testPhysio.getTreatments().get(0);

        testTreatment.cancel();

        // Verify
        String expectedStatus = "cancelled";
        String actualStatus = verifyTreatment.getStatus();
        assertEquals(expectedStatus, actualStatus);

        Patient actualResult = testTreatment.getPatient();
        assertNull("Patient should be removed", actualResult);
    }

    @Test
    public void testAttendAppointment()
    {
        // Prepare test input
        Patient testPatient = new Patient(11, "Patient 11", "Abc 123", "123456789");
        bookingSystem.getPatients().add(testPatient);
        
        Physiotherapist testPhysio = new Physiotherapist(1, "Dr. Test", "Clinic", "555-0000", 
                                       List.of("Physiotherapy"));
        bookingSystem.getPhysios().add(testPhysio);

        LocalDateTime testTime = LocalDateTime.now().plusDays(1);
        Treatment testTreatment = new Treatment("Massage", testTime, testPhysio);

        // Execute
        testTreatment.book(testPatient);
        testPhysio.getTreatments().add(testTreatment);
        Treatment verifyTreatment = testPhysio.getTreatments().get(0);

        testTreatment.attend();

        // Verify
        String expectedStatus = "attended";
        String actualStatus = verifyTreatment.getStatus();
        assertEquals(expectedStatus, actualStatus);

        assertEquals(testPatient, testTreatment.getPatient());

    }
}
