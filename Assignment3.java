/* 
* @author Burcinnur Alkan 
* @version 1.1
* @date 2024-11-28
*/
import java.util.Scanner;

public class Assignment3 {

    public static int menuDisplay(String[] items, Scanner input) {
        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + " - " + items[i]);
        }
        System.out.println("0 - Quit");
        System.out.print("Please enter your selection >> ");
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            System.out.println("The choice entered was " + choice);
            return choice;
        } else {
            System.out.println("ERROR: Invalid input. Exiting.");
            input.next();
            return -1;
        }
    }

    public static void bankLogin(int[] acctNums, String[] acctNames, String[] acctSurnames, String[] acctPINs, double[] acctBalances) {
        Scanner input = new Scanner(System.in);

        System.out.print("Please enter your account number >> ");
        if (!input.hasNextInt()) {
            System.out.println("ERROR: Invalid account number. Exiting login.");
            return;
        }
        int acctNum = input.nextInt();

        System.out.print("Please enter your PIN >> ");
        if (!input.hasNext()) {
            System.out.println("ERROR: Invalid PIN. Exiting login.");
            return;
        }
        String pin = input.next();

        int index = findAcct(acctNums, acctNum);
        if (index == -1 || !acctPINs[index].equals(pin)) {
            System.out.println("ERROR: Account/PIN combination not found. Exiting login.");
            return;
        }

        atm(acctNames, acctSurnames, acctBalances, index, input);
    }

    public static int findAcct(int[] acctNums, int acctNum) {
        for (int i = 0; i < acctNums.length; i++) {
            if (acctNums[i] == acctNum) {
                return i;
            }
        }
        return -1;
    }

    public static void atm(String[] names, String[] surnames, double[] balances, int index, Scanner input) {
        String[] items = {"Account Balance", "Deposit", "Withdrawal", "Change Name", "Cash Breakdown"};
        int choice;
        do {
            System.out.println("Hello " + names[index] + " " + surnames[index]);
            choice = menuDisplay(items, input);
            if (choice == -1) return;
            switch (choice) {
                case 1:
                    System.out.println("Your current balance is: " + balances[index]);
                    break;
                case 2:
                    System.out.print("How much are you depositing? ");
                    if (!input.hasNextDouble()) {
                        System.out.println("ERROR: Invalid deposit amount. Exiting login.");
                        input.next();
                        return; 
                    }
                    double depositAmount = input.nextDouble();
                    if (validDeposit(depositAmount)) {
                        balances[index] += depositAmount;
                        System.out.println("Deposit successful. New balance: " + balances[index]);
                    } else {
                        System.out.println("ERROR: Invalid deposit amount. Exiting login.");
                        return;
                    }
                    break;
                case 3:
                    System.out.print("How much are you withdrawing? ");
                    if (!input.hasNextDouble()) {
                        System.out.println("ERROR: Invalid withdrawal amount. Exiting login.");
                        input.next();
                        return;
                    }
                    double withdrawAmount = input.nextDouble();
                    if (validWithdrawal(balances[index], withdrawAmount)) {
                        balances[index] -= withdrawAmount;
                        System.out.println("Withdrawal successful. New balance: " + balances[index]);
                        System.out.println("You will receive: ");
                        System.out.println(cashGiven(withdrawAmount));
                    } else {
                        System.out.println("ERROR: Invalid withdrawal amount. Exiting login.");
                        return;
                    }
                    break;
                case 4:
                    System.out.print("Please enter your name: ");
                    String newName = input.next();
                    newName = newName.substring(0, 1).toUpperCase() + newName.substring(1).toLowerCase();
                    names[index] = newName;
                    System.out.print("Please enter your surname: ");
                    String newSurname = input.next();
                    newSurname = newSurname.toUpperCase();
                    surnames[index] = newSurname;
                    break;
                case 5:
                    System.out.print("Enter the amount to break down: ");
                    if (!input.hasNextDouble()) {
                        System.out.println("ERROR: Invalid amount for breakdown. Exiting login.");
                        input.next();
                        return;
                    }
                    double amount = input.nextDouble();
                    System.out.println(cashGiven(amount));
                    break;
                case 0:
                    System.out.println("Thank you for using our ATM. Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid selection. Exiting login.");
                    return;
            }
        } while (choice != 0);
    }

    public static boolean validDeposit(double amount) {
        return amount > 0;
    }

    public static boolean validWithdrawal(double balance, double amount) {
        return amount > 0 && amount <= balance;
    }

    public static String cashGiven(double amount) {
        double[] money = {200, 100, 50, 20, 10, 5, 1, 0.5, 0.25, 0.1, 0.05, 0.01};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < money.length; i++) {
            int moneyPart = (int) (amount / money[i]);
            if (moneyPart != 0) {
                result.append(moneyPart).append(" - ").append(money[i]).append("\n");
                amount -= moneyPart * money[i];
            }
        }
        return result.toString();
    }
}
