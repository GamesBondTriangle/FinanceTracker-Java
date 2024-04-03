package ui;

import model.EventLog;
import model.ExpenseAccount;
import model.ExpenseTracker;
import persistence.JsonReaderExpense;
import persistence.JsonWriterExpense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.List;

// Represents the Finance Tracker Application Graphic User Interface
// Work Cited: CPSC 210 DrawingPlayer and Java Swing Library
public class GuiMenu extends JFrame implements ActionListener {
    private JButton addExpenseButton;
    private JButton deleteExpenseButton;
    private JButton saveButton;
    private JButton loadButton;

    private JLabel descriptionLabel;
    private JLabel amountLabel;
    private JLabel dateLabel;
    private JLabel categoryLabel;
    private JLabel separator1Label;
    private JLabel separator2Label;
    private JLabel separator3Label;

    private JTextArea displayArea;

    private ExpenseTracker expenseTracker;
    private JsonReaderExpense jsonReaderExpense;
    private JsonWriterExpense jsonWriterExpense;
    private static final String JSON_STORE_EXPENSE = "./data/expense.json";

    // EFFECTS: Constructs GUI and opens the separate pop-up window
    public GuiMenu() {
        super("Finance Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initialize();

        createPanel();

        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLoggedEvents();
            }
        });
    }


    //EFFECTS: initializes fields, buttons, labels and display field
    private void initialize() {

        expenseTracker = new ExpenseTracker();
        jsonReaderExpense = new JsonReaderExpense(JSON_STORE_EXPENSE);
        jsonWriterExpense = new JsonWriterExpense(JSON_STORE_EXPENSE);

        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(this);

        deleteExpenseButton = new JButton("Delete Expense");
        deleteExpenseButton.addActionListener(this);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        loadButton = new JButton("Load");
        loadButton.addActionListener(this);

        displayArea = new JTextArea(30,60);
        displayArea.setEditable(false);

        descriptionLabel = new JLabel("Description");
        separator1Label = new JLabel("|");
        amountLabel = new JLabel("Amount $");
        separator2Label = new JLabel("|");
        dateLabel = new JLabel("Date (YYYY-MM-DD)");
        separator3Label = new JLabel("|");
        categoryLabel = new JLabel("Category");

    }

    // EFFECTS: creates panel with with buttons and labels
    private void createPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1,3,5,5));
        buttonPanel.add(addExpenseButton);
        buttonPanel.add(deleteExpenseButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        JPanel labelPanel = new JPanel(new GridLayout(1,7));
        labelPanel.add(descriptionLabel);
        labelPanel.add(separator1Label);
        labelPanel.add(amountLabel);
        labelPanel.add(separator2Label);
        labelPanel.add(dateLabel);
        labelPanel.add(separator3Label);
        labelPanel.add(categoryLabel);

        add(buttonPanel, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.CENTER);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);
    }


    //EFFECTS: Chooses the specific action to be performed
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addExpenseButton) {
            addExpense();
        } else if (e.getSource() == deleteExpenseButton) {
            deleteExpense();
        } else if (e.getSource() == saveButton) {
            saveExpense();
        } else if (e.getSource() == loadButton) {
            loadExpense();
        }

    }

    //MODIFIES: this
    //EFFECTS: Deletes an expense from the list
    private void deleteExpense() {

        String row = JOptionPane.showInputDialog(this, "Enter Row of Expense to be Deleted");

        try {
            int index = Integer.parseInt(row) - 1;
            if (index < expenseTracker.getTransactions().size()) {
                expenseTracker.deleteTransaction(index);
                resetScreen();
                displayExpenses();
                showImagePopUp();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid row. Please enter a valid row");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid row. Please enter a valid row");
        }
    }

    //MODIFIES: this
    //EFFECTS: Loads expenses from persistence data
    private void loadExpense() {
        try {
            ExpenseTracker fileExpenseTransactions = jsonReaderExpense.read();

            if (!isContainsExpense(fileExpenseTransactions.getTransactions())) {
                expenseTracker.addTransactionsFromFile(fileExpenseTransactions.getTransactions());
                resetScreen();
                displayExpenses();
                showImagePopUp();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to Load Expense");
        }
    }

    //MODIFIES: this
    //EFFECTS: Saves expenses to persistence data
    private void saveExpense() {
        try {
            jsonWriterExpense.open();
            jsonWriterExpense.write(expenseTracker);
            jsonWriterExpense.close();
            JOptionPane.showMessageDialog(this, "Expenses Saved");
            showImagePopUp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to Save Expenses");
        }

    }

    //MODIFIES: this
    //EFFECTS: Adds an expense to the list
    private void addExpense() {
        String description = JOptionPane.showInputDialog(this, "Enter Description:");
        String amountString = JOptionPane.showInputDialog(this, "Enter Amount:");
        String date = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):");
        String category = JOptionPane.showInputDialog(this, "Enter Category:");

        try {
            double amount = Double.parseDouble(amountString);
            expenseTracker.addTransaction(description, amount, date, category);
            resetScreen();
            displayExpenses();
            showImagePopUp();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format.");
        }
    }

    //EFFECTS: Resets the screen
    private void resetScreen() {
        displayArea.setText("");
    }

    //EFFECTS: Displays expenses on the screen
    private void displayExpenses() {
        for (int i = 0; i < expenseTracker.getTransactions().size(); i++) {
            displayArea.append(expenseTracker.getAccountTransaction(i).getDescription() + " | $"
                    + expenseTracker.getAccountTransaction(i).getAmount() + " | "
                    + expenseTracker.getAccountTransaction(i).getDate()
                    + " | " + expenseTracker.getAccountTransaction(i).getCategory() + "\n");

        }
    }

    //EFFECTS: Determines if the data that was recently added is contained by the persistence data
    //         False if the data is not contained, True otherwise
    private boolean isContainsExpense(List<ExpenseAccount> fileTransactions) {
        boolean isContains = false;
        for (int i = 0; i < expenseTracker.getTransactions().size(); i++) {
            for (int j = 0; j < fileTransactions.size(); j++) {
                if (expenseTracker.getAccountTransaction(i).getDescription().equals(fileTransactions.get(j)
                        .getDescription())
                        && expenseTracker.getAccountTransaction(i).getAmount() == fileTransactions.get(j).getAmount()
                        && expenseTracker.getAccountTransaction(i).getDate().equals(fileTransactions.get(j).getDate())
                        && expenseTracker.getAccountTransaction(i).getCategory().equals(fileTransactions
                        .get(j).getCategory())) {
                    isContains = true;
                }
            }
        }
        return isContains;
    }

    //EFFECTS: Displays a visual element for 3 seconds after the action is complete
    private void showImagePopUp() {
        JFrame popUp = new JFrame("Action Complete");
        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popUp.setSize(320,350);
        popUp.setLocationRelativeTo(this);
        JLabel logoLabel = new JLabel(new ImageIcon("visual/image.png"));
        popUp.add(logoLabel);
        popUp.setVisible(true);
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUp.dispose();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    //EFFECTS: prints to the console all the events that occur while program runs
    private void printLoggedEvents() {
        EventLog eventLog = EventLog.getInstance();
        System.out.println("Logged Events: ");
        for (model.Event event : eventLog) {
            System.out.println(event);
        }
    }
}
