package com.example.moneyapp;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private TableView<User> tableView;
    private TextField textField; // Объявляем textField как поле класса
    private User currentUser; // Переменная для хранения текущего пользователя
    private static final String FILENAME = "User.csv";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CashApp");
        tableView = new TableView<>();
        Button btnCard = new Button("Карта клиента");
        Button btnPut = new Button("Пополнение");
        Button btnService = new Button("Служебные");
        Button btnExit = new Button("Выход");


        btnExit.setOnAction(e -> System.exit(0));
        btnService.setOnAction(e -> createServiceWindow());
        btnCard.setOnAction(e -> CreateCardWindow());
        btnPut.setOnAction(e -> openCalculatorWindow());

        TableView<User> tableView = new TableView<>();

        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, Float> balanceColumn = new TableColumn<>("Баланс");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        tableView.getColumns().addAll(idColumn, balanceColumn, nameColumn, phoneColumn);



        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(btnCard, btnPut, btnService, btnExit);

        HBox mainLayout = new HBox(20);
        mainLayout.getChildren().addAll(buttonBox, tableView);

        Scene scene = new Scene(mainLayout, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void createServiceWindow() {
        Stage serviceStage = new Stage();
        serviceStage.setTitle("Служебное меню");

        Button btnCloseShift = new Button("Закрыть смену");
        Button btnXReport = new Button("X-отчёт");
        Button btnZReport = new Button("Z-отчёт");
        Button btnOpenShift = new Button("Открыть смену");

        btnCloseShift.setOnAction(e -> System.out.println("Закрыть смену"));
        btnZReport.setOnAction(e -> System.out.println("Z-отчёт"));
        btnOpenShift.setOnAction(e -> System.out.println("Открыть смену"));
        btnXReport.setOnAction(e -> {
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(btnCloseShift, btnXReport, btnZReport, btnOpenShift);

        Scene scene = new Scene(vbox, 300, 200);
        serviceStage.setScene(scene);
        serviceStage.setResizable(false);
        serviceStage.initModality(Modality.APPLICATION_MODAL);
        serviceStage.show();
    }

    public void CreateCardWindow(){
        Stage cardStage = new Stage();

        TextField textField = new TextField();
        textField.setPrefColumnCount(20);
        Button btnFind = new Button("Find");

        btnFind.setOnAction(event -> {
            try {
                int id = Integer.parseInt(textField.getText());
                User user = findUserById(id);
                if (user != null) {
                    ObservableList<User> data = FXCollections.observableArrayList(user);
                    tableView.setItems(data);
                } else {
                    System.out.println("Пользователь с ID " + id + " не найден.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(textField, btnFind);
        Scene scene = new Scene(vbox, 250, 200);

        cardStage.setScene(scene);
        cardStage.setTitle("Id Card");
        cardStage.show();

    }

    public void openCalculatorWindow() {
        Stage calculatorStage = new Stage();
        calculatorStage.setTitle("Пополнить");

        TextField inputField = new TextField();
        inputField.setPrefWidth(250);
        inputField.setStyle("-fx-font-size: 16px; -fx-pref-height: 40px;");

        Button[] numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            int number = i;
            numberButtons[i] = new Button(String.valueOf(i));
            numberButtons[i].setOnAction(e -> inputField.setText(inputField.getText() + number));
            numberButtons[i].setStyle("-fx-font-size: 16px; -fx-pref-width: 60px; -fx-pref-height: 40px;");
        }

        Button btn00 = new Button("00");
        btn00.setOnAction(e -> inputField.setText(inputField.getText() + "00"));
        btn00.setStyle("-fx-font-size: 16px; -fx-pref-width: 60px; -fx-pref-height: 40px;");

        Button btn000 = new Button("000");
        btn000.setOnAction(e -> inputField.setText(inputField.getText() + "000"));
        btn000.setStyle("-fx-font-size: 16px; -fx-pref-width: 60px; -fx-pref-height: 40px;");

        Button btnDot = new Button(".");
        btnDot.setOnAction(e -> {
            if (!inputField.getText().contains(".")) {
                inputField.setText(inputField.getText() + ".");
            }
        });
        btnDot.setStyle("-fx-font-size: 16px; -fx-pref-width: 60px; -fx-pref-height: 40px;");

        Button btnDelete = new Button("Del");
        btnDelete.setOnAction(e -> {
            String currentText = inputField.getText();
            if (currentText.length() > 0) {
                inputField.setText(currentText.substring(0, currentText.length() - 1));
            }
        });
        btnDelete.setStyle("-fx-font-size: 16px; -fx-pref-width: 60px; -fx-pref-height: 40px;");

        Button btnClear = new Button("Clear");
        btnClear.setOnAction(e -> inputField.clear());
        btnClear.setStyle("-fx-font-size: 16px; -fx-pref-width: 60px; -fx-pref-height: 40px;");

        Button btnConfirm = new Button("OK");
        btnConfirm.setOnAction(e -> {
            try {
                float enteredAmount = Float.parseFloat(inputField.getText());
                System.out.println("Entered Amount: " + enteredAmount);
                calculatorStage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input.");
            }
        });
        btnConfirm.setStyle("-fx-font-size: 16px; -fx-pref-width: 60px; -fx-pref-height: 40px;");


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        int index = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                if (index < 10) {
                    gridPane.add(numberButtons[index], col, row);
                    index++;
                }
            }
        }
        gridPane.add(btn00, 0, 3);
        gridPane.add(btn000, 1, 3);
        gridPane.add(btnDot, 2, 3);
        gridPane.add(btnDelete, 0, 4);
        gridPane.add(btnClear, 1, 4);
        gridPane.add(btnConfirm, 2, 4);
        gridPane.add(inputField, 0, 5, 3, 1);

        btnConfirm.setOnAction(e -> {
            try {
                float enteredAmount = Float.parseFloat(inputField.getText());
                System.out.println("Entered Amount: " + enteredAmount);

                if (currentUser != null) { // Проверяем, что пользователь найден
                    int userId = currentUser.getId(); // Получаем ID текущего пользователя
                    float newBalance = currentUser.getBalance() + enteredAmount;
                    updateUser(userId, newBalance, null, null); // Обновляем пользователя
                    calculatorStage.close(); // Закрываем окно, если оно открыто
                } else {
                    System.out.println("Пользователь не найден, проверьте ID.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input.");
            } catch (IOException ioEx) {
                System.out.println("Error updating user: " + ioEx.getMessage());
            }
        });

        Scene scene = new Scene(gridPane, 300, 350);
        calculatorStage.setScene(scene);
        calculatorStage.initModality(Modality.APPLICATION_MODAL);
        calculatorStage.show();
    }
    public List<User> readUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                float balance = Float.parseFloat(fields[1]);
                String name = fields[2];
                String phone = fields[3];
                users.add(new User(id, balance, name, phone));
            }
        }
        return users;
    }

    public void addUser(int id, float balance, String name, String phone) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
            if (new File(FILENAME).length() == 0) {
                bw.write("ID,Balance,Name,Phone");
                bw.newLine();
            }
            bw.write(id + "," + balance + "," + name + "," + phone);
            bw.newLine();
        }
    }

    public boolean updateUser(int id, Float balance, String name, String phone) throws IOException {
        List<User> users = readUsers();
        boolean updated = false;

        for (User user : users) {
            if (user.getId() == id) {
                if (balance != null) user.setBalance(balance);
                if (name != null) user.setName(name);
                if (phone != null) user.setPhone(phone);
                updated = true;
                break;
            }
        }

        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
                bw.write("ID,Balance,Name,Phone");
                bw.newLine();
                for (User user : users) {
                    bw.write(user.getId() + "," + user.getBalance() + "," + user.getName() + "," + user.getPhone());
                    bw.newLine();
                }
            }
        }
        return updated;
    }
    public User findUserById(int id) throws IOException {
        List<User> users = readUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
