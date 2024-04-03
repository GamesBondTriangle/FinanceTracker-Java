# Personal Finance Tracking Application

## Overview

Have you ever felt like that you do not know what's going on with your 
finances, and money just flows out of your bank account without ever spending a day there?
If it is the case, I am pleased to introduce to you Personal Finance Tracker!
This fantastic application is designed to keep you aware of your expenses and income.
My Finance Tracker will provide customers with a user-friendly platform to track income and expenses, 
categorize expenses and gain insights into their spending habits. 

## Motivation

At the age of 20, I managed to put myself in the position of earning income;
However, it did not feel like that my bank account was carrying more funds. 
I started *manually* tracking my expenses, and it helped me to identify my poor habits.
I want to streamline the process of tracking expenses and income through the 
Personal Finance Tracking Application.

## User Stories

The following capabilities will be accessible to users:

- As a user, I want to be able to *add* income and expenses to my income and expense lists, respectively.
- As a user, I want to be able to *view* the list of income and expense on my transaction list given the timeframe.
- As a user, I want to be able to *categorize* my expenses and income.
- As a user, I want to be able to *delete* income or expenses from my lists.
- As a user, I want to be able to *update amount, description, date or category* on my transaction list.
- As a user, I want to be able to *see* my gain or loss in the given timeframe.
- As a user, I want to be able to *save* my finance tracker to a file.
- As a user, I want to be able to *load* my finance tracker from a file.
 
## Phase 4: Task 2
Representative sample of the events that occur when the program runs:

Logged Events:
Mon Apr 01 20:08:00 PDT 2024
Expense added: car $6000.9 2023-01-09 transportation

Mon Apr 01 20:08:42 PDT 2024
Expense added: gym membership $13.87 2023-01-15 subscriptions

Mon Apr 01 20:08:45 PDT 2024
Expense added: banana $32.01 2024-10-01 fruit

Mon Apr 01 20:08:45 PDT 2024
Expense added: jeans $100.89 2022-01-01 clothes

Mon Apr 01 20:08:55 PDT 2024
Expense deleted: banana $32.01 2024-10-01 fruit

## Phase 4: Task 3
The following improvements can be done in order to improve the 
readability of application implementation:

- There is a lot of duplication in ExpenseTracker <-> IncomeTracker 
and ExpenseAccount <-> IncomeAccount. In order to remove the duplication,
ExpenseTracker and IncomeTracker can be combined into TransactionTracker 
and instead of ArrayList, Hashset could have been used with expense 
and income collections, ExpenseAccount and IncomeAccount could have been
combined to TransactionAccount.
- Menu and GuiMenu classes are responsible for a lot of tasks. 
In order to improve cohesion in Menu and GuiMenu classes, I would split 
the responsibility of these classes into the different classes to increase cohesion.
- More methods can be implemented to refactor duplicate code in Menu class
to reduce duplication and improve readability.
