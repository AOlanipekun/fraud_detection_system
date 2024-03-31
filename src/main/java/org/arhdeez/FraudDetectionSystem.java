package org.arhdeez;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FraudDetectionSystem {
    private static final Logger log = LoggerFactory.getLogger(FraudDetectionSystem.class);
    private final Map<String, List<Transaction>> userTransactions = new HashMap<>();
    private long timeframeForPingPong;

    public FraudDetectionSystem() {
        //empty constructor
    }

    public void addTransactionToUsersTransactions(final Transaction transaction) {
        this.userTransactions.computeIfAbsent(transaction.getUserID(), k -> new ArrayList<>()).add(transaction);

        this.checkFraudPattern(transaction);
    }

    private void checkFraudPattern(final Transaction transaction) {
        final var userId = transaction.getUserID();
        final var timestamp = transaction.getTimestamp();
        final var amount = transaction.getAmount();
        final var serviceId = transaction.getServiceID();
        if (this.hasMultipleServicesWithinTimeWindow(userId, timestamp, 5, 3)) {
            this.generateAlert(userId, "Multiple services within a 5-minute window");
        }

        if (this.isAmountSignificantlyHigher(userId, amount)) {
            this.generateAlert(userId, "Significantly higher transaction amount");
        }

        if (this.isPingPongActivity(userId, serviceId, timestamp, this.timeframeForPingPong)) {
            this.generateAlert(userId, "Ping-pong activities detected");
        }
    }

    private boolean hasMultipleServicesWithinTimeWindow(final String userID, final long timestamp, final int windowSize,
                                                        final int limit) {
        final var userTransactionsLocal = this.userTransactions.getOrDefault(userID, new ArrayList<>());

        final var startTime = timestamp - ((long) windowSize * 60 * 1000); // Convert minutes to milliseconds
        final Set<String> services = new HashSet<>();
        for (final var transaction : userTransactionsLocal) {
            if (transaction.getTimestamp() >= startTime && transaction.getTimestamp() <= timestamp) {
                services.add(transaction.getServiceID());
            }
        }

        return services.size() > limit;
    }

    private boolean isAmountSignificantlyHigher(final String userID, final double amount) {
        final var userTransactionsLocal = this.userTransactions.getOrDefault(userID, new ArrayList<>());

        double sum = 0;
        for (final var transaction : userTransactionsLocal) {
            sum += transaction.getAmount();
        }
        final var average = userTransactionsLocal.isEmpty() ? 0 : sum / userTransactionsLocal.size();

        return amount > 5 * average;
    }

    private boolean isPingPongActivity(final String userID, final String serviceID, final long timestamp, final long timeFrame) {
        final var userTransactionsLocal = this.userTransactions.getOrDefault(userID, new ArrayList<>());

       final var startTime = timestamp - (timeFrame); // Convert minutes to milliseconds
        for (final var transaction : userTransactionsLocal) {
            if (transaction.getTimestamp() >= startTime && transaction.getServiceID().equals(serviceID)) {
                return true;
            }
        }
        return false;
    }

    private void generateAlert(final String userID, final String message) {
        log.error("ALERT: User {} flagged for {}", userID, message);
    }

    private Transaction parseTransactionFromJson(final JsonElement jsonElement) {
        final var gson = new Gson();
        return gson.fromJson(jsonElement, Transaction.class);
    }

    public void fetchRealTimeDataFromFile(final String filename, final long timeframeForPingPong) throws FileNotFoundException {
        log.info("Reading transactions from file: {}", filename);
        this.timeframeForPingPong = timeframeForPingPong;

        final var jsonArray = this.readJsonArrayFromFile(filename);

        for (final var element : jsonArray) {
            final var transaction = this.parseTransactionFromJson(element);
            this.addTransactionToUsersTransactions(transaction);
        }

        log.info("transactions:::{}", this.userTransactions);

    }

    private JsonArray readJsonArrayFromFile(final String filename) throws FileNotFoundException {
        try (final Reader reader = new FileReader(filename)) {
            return JsonParser.parseReader(reader).getAsJsonArray();
        } catch (Exception e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
