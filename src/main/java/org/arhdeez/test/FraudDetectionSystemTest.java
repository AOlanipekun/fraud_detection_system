package org.arhdeez.test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import org.arhdeez.FraudDetectionSystem;
import org.junit.Test;

public class FraudDetectionSystemTest {

    @Test
    public void testFraudDetectionSystem() throws FileNotFoundException {

        final var fraudDetectionSystem = new FraudDetectionSystem();
        fraudDetectionSystem.fetchRealTimeDataFromFile("inputUserData.json", 5 * 60 * 1000);

       assertTrue(true);

    }

    @Test
    public void testNoValidInputData() {
        final var fraudDetectionSystem = new FraudDetectionSystem();

        assertThrows(FileNotFoundException.class, () ->
            fraudDetectionSystem.fetchRealTimeDataFromFile("nodata.json", 5 * 60 * 1000));
    }

}
