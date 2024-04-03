package ui;

import model.ExpenseAccount;
import model.ExpenseTracker;
import model.IncomeAccount;
import model.IncomeTracker;
import model.TimeframeNetGain;
import persistence.JsonReaderExpense;
import persistence.JsonReaderIncome;
import persistence.JsonWriterExpense;
import persistence.JsonWriterIncome;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

// Represents the Finance Tracker Application
public class Menu {

    private static final String JSON_STORE_EXPENSE = "./data/expense.json";
    private static final String JSON_STORE_INCOME = "./data/income.json";

    private IncomeTracker incomeTracker;
    private ExpenseTracker expenseTracker;
    private TimeframeNetGain result;
    private Scanner scanner;
    private JsonReaderExpense jsonReaderExpense;
    private JsonWriterExpense jsonWriterExpense;
    private JsonReaderIncome jsonReaderIncome;
    private JsonWriterIncome jsonWriterIncome;

    //EFFECTS: Creates a constructor and start the console application
    public Menu() {
        this.incomeTracker = new IncomeTracker();
        this.expenseTracker = new ExpenseTracker();
        this.scanner = new Scanner(System.in);
        this.result = new TimeframeNetGain();
        this.jsonReaderExpense = new JsonReaderExpense(JSON_STORE_EXPENSE);
        this.jsonWriterExpense = new JsonWriterExpense(JSON_STORE_EXPENSE);
        this.jsonReaderIncome = new JsonReaderIncome(JSON_STORE_INCOME);
        this.jsonWriterIncome = new JsonWriterIncome(JSON_STORE_INCOME);
        launchMenu();
    }


    //EFFECTS: Displays the main menu
    private void displayMenu() {
        System.out.println("\n+++++++ Finance Tracker Menu +++++++");
        System.out.println("1. Add Income Transaction");
        System.out.println("2. Add Expense Transaction");
        System.out.println("3. View Income Transactions");
        System.out.println("4. View Expense Transactions");
        System.out.println("5. Delete Income Transaction");
        System.out.println("6. Delete Expense Transaction");
        System.out.println("7. Update Income Transaction Details");
        System.out.println("8. Update Expense Transaction Details");
        System.out.println("9. Net Gain of the Timeframe");
        System.out.println("10. Save Income");
        System.out.println("11. Save Expenses");
        System.out.println("12. Load Income");
        System.out.println("13. Load Expenses");
        System.out.println("0. Exit");
        System.out.println("+++++++++++++++++++++++++++++++++++++");
    }


    //EFFECTS: Launches menu
    private void launchMenu() {
        int choice;
        while (true) {
            displayMenu();
            choice = getUserChoice();
            if (choice == 0) {
                System.out.println("Exiting Tracker Menu.\n See you next time!");
                break;
            }
            processChoice(choice);
        }
    }


    //MODIFIES: this
    //EFFECTS: Asks user for their choice
    private int getUserChoice() {
        System.out.println("Enter your choice number: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    //MODIFIES: this
    //EFFECTS: Process user's choice and pick correct operation to execute
    @SuppressWarnings("methodlength")
    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                addIncomeTransaction();
                break;
            case 2:
                addExpenseTransaction();
                break;
            case 3:
                viewIncomeTransactions();
                break;
            case 4:
                viewExpenseTransactions();
                break;
            case 5:
                deleteIncomeTransaction();
                break;
            case 6:
                deleteExpenseTransaction();
                break;
            case 7:
                updatedIncomeDetails();
                break;
            case 8:
                updatedExpenseDetails();
                break;
            case 9:
                netGain();
                break;
            case 10:
                saveIncome();
                break;
            case 11:
                saveExpenses();
                break;
            case 12:
                loadIncome();
                break;
            case 13:
                loadExpenses();
                break;
            case 0:
                System.out.println("Exiting Tracker Menu.\n See you next time!");
                break;
            default:
                System.out.println("Invalid choice, Please, try again");
        }
    }

    //EFFECTS: saves income to file
    private void saveIncome() {
        try {
            jsonWriterIncome.open();
            jsonWriterIncome.write(incomeTracker);
            jsonWriterIncome.close();
            System.out.println("Saved income to file: " + JSON_STORE_INCOME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_INCOME);
        }
    }

    //MODIFIES: this
    // EFFECTS: loads income from file
    private void loadIncome() {
        try {
            incomeTracker = jsonReaderIncome.read();
            System.out.println("Loaded income from file: " + JSON_STORE_INCOME);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_INCOME);
        }
    }

    //EFFECTS: saves expenses to file
    private void saveExpenses() {
        try {
            jsonWriterExpense.open();
            jsonWriterExpense.write(expenseTracker);
            jsonWriterExpense.close();
            System.out.println("Saved expenses to file: " + JSON_STORE_EXPENSE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_EXPENSE);
        }
    }

    //MODIFIES: this
    // EFFECTS: loads expenses from file
    private void loadExpenses() {
        try {
            expenseTracker = jsonReaderExpense.read();
            System.out.println("Loaded expenses from file: " + JSON_STORE_EXPENSE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_EXPENSE);
        }
    }


    //MODIFIES: this
    //EFFECTS: adds the income transaction
    private void addIncomeTransaction() {
        System.out.print("Enter income description: ");
        String description = scanner.nextLine();
        System.out.print("Enter income amount ($X.XX): $");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter income date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter income category: ");
        String category = scanner.nextLine();

        incomeTracker.addTransaction(description, amount, date, category);
        System.out.print("Income Transaction Added Successfully!");
    }

    //MODIFIES: this
    //EFFECTS: adds the expense transaction
    private void addExpenseTransaction() {
        System.out.print("Enter expense description: ");
        String description = scanner.nextLine();
        System.out.print("Enter expense amount ($X.XX): $");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter expense date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter expense category: ");
        String category = scanner.nextLine();

        expenseTracker.addTransaction(description, amount, date, category);
        System.out.print("Expense Transaction Added Successfully!");
    }


    //EFFECTS: Views the Income Transaction
    private void viewIncomeTransactions() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        List<IncomeAccount> transactions = incomeTracker.viewTransaction(startDate, endDate);

        if (transactions.isEmpty()) {
            System.out.print("No transaction found in the specified timeframe!");
        } else {
            for (IncomeAccount transaction : transactions) {
                displayIncomeTransactionDetails(transaction);
            }
        }

    }


    //EFFECTS: Displays the income transactions of the screen
    private void displayIncomeTransactionDetails(IncomeAccount transaction) {
        System.out.println("Income Transaction Details :");
        System.out.println("Description: " + transaction.getDescription());
        System.out.println("Amount: $" + roundToTwoDigits(transaction.getAmount()));
        System.out.println("Date: " + transaction.getDate());
        System.out.println("Category: " + transaction.getCategory());
        System.out.println("+++++++++++++++++++++++++++++++");
    }

    //EFFECTS: Views the Expense Transaction
    private void viewExpenseTransactions() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        List<ExpenseAccount> transactions = expenseTracker.viewTransaction(startDate, endDate);

        if (transactions.isEmpty()) {
            System.out.print("No transaction found in the specified timeframe!");
        } else {
            for (ExpenseAccount transaction : transactions) {
                displayExpenseTransactionDetails(transaction);
            }
        }

    }


    //EFFECTS: Displays the expense transactions of the screen
    private void displayExpenseTransactionDetails(ExpenseAccount transaction) {
        System.out.println("Expense Transaction Details :");
        System.out.println("Description: " + transaction.getDescription());
        System.out.println("Amount: $" + roundToTwoDigits(transaction.getAmount()));
        System.out.println("Date: " + transaction.getDate());
        System.out.println("Category: " + transaction.getCategory());
        System.out.println("+++++++++++++++++++++++++++++++");
    }


    //MODIFIES: this
    //EFFECTS: deletes the income transaction from the list
    private void deleteIncomeTransaction() {
        int index = getTransactionIndex();
        if (index != -1) {
            incomeTracker.deleteTransaction(index);
            System.out.print("Transaction Deleted Successfully!");
        } else {
            System.out.print("Invalid index. Transaction not deleted");
        }
    }

    //MODIFIES: this
    //EFFECTS: deletes the expense transaction from the list
    private void deleteExpenseTransaction() {
        int index = getTransactionIndex();
        if (index != -1) {
            expenseTracker.deleteTransaction(index);
            System.out.print("Transaction Deleted Successfully!");
        } else {
            System.out.print("Invalid index. Transaction not deleted");
        }
    }


    //EFFECTS: asks user for the index of a transaction
    private int getTransactionIndex() {
        System.out.print("Enter index of the transaction: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
            return -1;
        }
    }

    //EFFECTS: Menu for the update options
    private void printUpdateOptions() {
        System.out.println("\n+++++++ Update Options +++++++");
        System.out.println("1. Recategorize Transaction");
        System.out.println("2. Update Transaction Amount");
        System.out.println("3. Update Transaction Description");
        System.out.println("4. Update Transactions Date");
    }

    //MODIFIES: this
    //EFFECTS: Selects the operation to update income
    private void updatedIncomeDetails() {
        printUpdateOptions();
        int choice = getUserChoice();
        switch (choice) {
            case 1:
                recategorizeIncomeTransaction();
                break;
            case 2:
                updateIncomeAmount();
                break;
            case 3:
                updateIncomeDescription();
                break;
            case 4:
                updateIncomeDate();
                break;

        }
    }

    //MODIFIES: this
    //EFFECTS: updates income category
    private void recategorizeIncomeTransaction() {
        int index = getTransactionIndex();
        if (index != -1) {
            System.out.print("Enter updated category: ");
            String newCategory = scanner.nextLine();
            incomeTracker.recategorizeTransaction(index, newCategory);
            System.out.print("Transaction Recategorized Successfully!");
        } else {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
        }
    }


    //MODIFIES: this
    //EFFECTS: updates income amount
    private void updateIncomeAmount() {
        int index = getTransactionIndex();
        if (index != -1) {
            System.out.print("Enter updated amount: $");
            double newAmount = Double.parseDouble(scanner.nextLine());
            incomeTracker.updateAmountTransaction(index, newAmount);
            System.out.print("Amount Updated Successfully!");
        } else {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
        }
    }


    //MODIFIES: this
    //EFFECTS: updates income description
    private void updateIncomeDescription() {
        int index = getTransactionIndex();
        if (index != -1) {
            System.out.print("Enter updated description: ");
            String newDescription = scanner.nextLine();
            incomeTracker.updatedDescriptionTransaction(index, newDescription);
            System.out.print("Description Updated Successfully!");
        } else {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
        }
    }


    //MODIFIES: this
    //EFFECTS: updates income date
    private void updateIncomeDate() {
        int index = getTransactionIndex();
        if (index != -1) {
            System.out.print("Enter updated date (YYYY-MM-DD): ");
            String newDate = scanner.nextLine();
            incomeTracker.updatedDateTransaction(index, newDate);
            System.out.print("Date Updated Successfully!");
        } else {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
        }
    }

    //MODIFIES: this
    //EFFECTS: Selects the operation to update expense
    private void updatedExpenseDetails() {
        printUpdateOptions();
        int choice = getUserChoice();
        switch (choice) {
            case 1:
                recategorizeExpenseTransaction();
                break;
            case 2:
                updateExpenseAmount();
                break;
            case 3:
                updateExpenseDescription();
                break;
            case 4:
                updateExpenseDate();
                break;

        }
    }


    //MODIFIES: this
    //EFFECTS: updates expense category
    private void recategorizeExpenseTransaction() {
        int index = getTransactionIndex();
        if (index != -1) {
            System.out.print("Enter updated category: ");
            String newCategory = scanner.nextLine();
            expenseTracker.recategorizeTransaction(index, newCategory);
            System.out.print("Transaction Recategorized Successfully!");
        } else {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
        }
    }


    //MODIFIES: this
    //EFFECTS: updates expense amount
    private void updateExpenseAmount() {
        int index = getTransactionIndex();
        if (index != -1) {
            System.out.print("Enter updated amount: $");
            double newAmount = Double.parseDouble(scanner.nextLine());
            expenseTracker.updateAmountTransaction(index, newAmount);
            System.out.print("Amount Updated Successfully!");
        } else {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
        }
    }


    //MODIFIES: this
    //EFFECTS: updates expense description
    private void updateExpenseDescription() {
        int index = getTransactionIndex();
        if (index != -1) {
            System.out.print("Enter updated description: ");
            String newDescription = scanner.nextLine();
            expenseTracker.updatedDescriptionTransaction(index, newDescription);
            System.out.print("Description Updated Successfully!");
        } else {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
        }
    }


    //MODIFIES: this
    //EFFECTS: updates expense date
    private void updateExpenseDate() {
        int index = getTransactionIndex();
        if (index != -1) {
            System.out.print("Enter updated date (YYYY-MM-DD): ");
            String newDate = scanner.nextLine();
            expenseTracker.updatedDateTransaction(index, newDate);
            System.out.print("Date Updated Successfully!");
        } else {
            System.out.print("Invalid index. Operation failed. Please enter a valid index");
        }
    }

    //EFFECTS: displays the net gain
    private void netGain() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        double netGain = result.calculateNetGain(incomeTracker, expenseTracker,
                startDate, endDate);
        System.out.print("Net Gain is $" + roundToTwoDigits(netGain));

    }

    //MODIFIES: this
    //EFFECTS: rounds double to two digits after the point
    private double roundToTwoDigits(double result) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(result));
    }

}
