package org.arhdeez;

public final class Main {

    public Main() {
    }

    public static void main(String[] args) {
        try {
            final var timeFrameForPingPong = 5 * 60 * 1000;
            final var fileName = "inputUserData.json";
            final var fraudDetectionSystem = new FraudDetectionSystem();
            fraudDetectionSystem.fetchRealTimeDataFromFile(fileName, timeFrameForPingPong);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}