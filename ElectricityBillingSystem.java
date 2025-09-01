import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class ElectricityBillingSystem extends Application {

    private Stage primaryStage;
    private TextField usernameField;
    private PasswordField passwordField;

    // User dataset
    private Map<String, String> users = new HashMap<>();

    // In-memory bill storage: username -> list of bills
    private Map<String, List<Bill>> userBills = new HashMap<>();

    // Slab rates (Tamil Nadu)
    private final double[] slabUnits = {100, 100, 200, Double.MAX_VALUE};
    private final double[] slabRates = {3.50, 4.60, 6.60, 7.70};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Electricity Billing System - Login");

        // Initialize users
        users.put("admin", "admin123");
        users.put("user1", "password1");
        users.put("user2", "password2");

        showLoginPage();
    }

    private void showLoginPage() {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(20));
        loginGrid.setVgap(10);
        loginGrid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> handleLogin());

        loginGrid.add(usernameLabel, 0, 0);
        loginGrid.add(usernameField, 1, 0);
        loginGrid.add(passwordLabel, 0, 1);
        loginGrid.add(passwordField, 1, 1);
        loginGrid.add(loginButton, 1, 2);

        Scene loginScene = new Scene(loginGrid, 400, 250);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            showBillingDashboard(username);
        } else {
            showAlert("Login Error", "Invalid username or password.");
        }
    }

    private void showBillingDashboard(String username) {
        primaryStage.setTitle("Electricity Billing System - Dashboard");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Customer Name:");
        TextField nameField = new TextField();
        Label unitsLabel = new Label("Units Consumed:");
        TextField unitsField = new TextField();

        Button calculateButton = new Button("Calculate Bill");
        Button logoutButton = new Button("Logout");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(5);

        // Line Chart for usage
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Bill Number");
        yAxis.setLabel("Units Consumed");
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setPrefHeight(250);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Usage Trend");
        lineChart.getData().add(series);

        calculateButton.setOnAction(e -> {
            String customerName = nameField.getText();
            String unitsText = unitsField.getText();
            if(customerName.isEmpty() || unitsText.isEmpty()) {
                showAlert("Error", "Please enter both customer name and units consumed.");
                return;
            }

            try {
                double units = Double.parseDouble(unitsText);
                double amount = calculateSlabBill(units);
                Bill bill = new Bill(customerName, units, amount, false);

                userBills.computeIfAbsent(username, k -> new ArrayList<>()).add(bill);

                outputArea.setText("Bill for " + customerName + ": ₹" + String.format("%.2f", amount));

                // Update chart
                series.getData().clear();
                List<Bill> bills = userBills.get(username);
                for (int i = 0; i < bills.size(); i++) {
                    series.getData().add(new XYChart.Data<>(i + 1, bills.get(i).getUnits()));
                }

            } catch (NumberFormatException ex) {
                showAlert("Error", "Units must be a number.");
            }
        });

        logoutButton.setOnAction(e -> showLoginPage());

        // Layout
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(unitsLabel, 0, 1);
        grid.add(unitsField, 1, 1);
        grid.add(calculateButton, 0, 2);
        grid.add(logoutButton, 1, 2);
        grid.add(outputArea, 0, 3, 2, 1);

        VBox root = new VBox(10, grid, lineChart);
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
    }

    // Slab calculation
    private double calculateSlabBill(double units) {
        double remainingUnits = units;
        double bill = 0;
        for(int i=0; i<slabUnits.length; i++) {
            double currentUnits = Math.min(remainingUnits, slabUnits[i]);
            bill += currentUnits * slabRates[i];
            remainingUnits -= currentUnits;
            if(remainingUnits <= 0) break;
        }
        return bill;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Bill class
    static class Bill {
        private String customerName;
        private double units;
        private double amount;
        private boolean paid;

        public Bill(String customerName, double units, double amount, boolean paid) {
            this.customerName = customerName;
            this.units = units;
            this.amount = amount;
            this.paid = paid;
        }

        public double getUnits() {
            return units;
        }
    }
}
