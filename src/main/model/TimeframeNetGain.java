package model;

import java.text.DecimalFormat;
import java.util.List;

// Given the timeframe, the class sums all the expenses and income
// If income > expenses then Net Gain
// If expenses > income then Net Loss
public class TimeframeNetGain {


    //EFFECTS: Creates Net Gain (or Loss) provided the timeframe
    public TimeframeNetGain() {
    }


    //REQUIRES: endDate > startDate
    //EFFECTS: returns Net Gain (or Loss) over the specified timeframe in $
    public double calculateNetGain(IncomeTracker incomeTransactions,
                                   ExpenseTracker expenseTransactions, String startDate, String endDate) {
        double totalIncome = calculateTotalIncome(incomeTransactions.getTransactions(), startDate, endDate);
        double totalExpense = calculateTotalExpense(expenseTransactions.getTransactions(), startDate, endDate);
        double netGain = totalIncome - totalExpense;
        return roundToTwoDigits(netGain);
    }


    //REQUIRES: endDate > startDate
    //EFFECTS: returns total income over the specified timeframe in $
    private double calculateTotalIncome(List<IncomeAccount> transactions, String startDate, String endDate) {
        double totalIncome = 0.0;
        for (IncomeAccount transaction : transactions) {
            if (isWithinTimeframe(transaction.getDate(), startDate, endDate)) {
                totalIncome = totalIncome + transaction.getAmount();
            }
        }
        return totalIncome;
    }

    //REQUIRES: endDate > startDate
    //EFFECTS: returns total expense over the specified timeframe in $
    private double calculateTotalExpense(List<ExpenseAccount> transactions, String startDate, String endDate) {
        double totalExpense = 0.0;
        for (ExpenseAccount transaction : transactions) {
            if (isWithinTimeframe(transaction.getDate(), startDate, endDate)) {
                totalExpense = totalExpense + transaction.getAmount();
            }
        }
        return totalExpense;
    }

    //EFFECTS: returns true if the date is within the specified timeframe, false otherwise
    private boolean isWithinTimeframe(String date,String startDate, String endDate) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    //EFFECTS: rounds double to two digits after the point
    private double roundToTwoDigits(double result) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(result));
    }


}
