package edu.ithaca.dturnbull.bank;

import java.util.ArrayList;

public class BankAccount {

    private String email;
    private double balance;
    
    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if(isAmountValid(startingBalance) == false){
            throw new IllegalArgumentException("Cannot create account with negative balance or with more than 2 digits after decimal");
        }
        else{
            if (isEmailValid(email)){
                this.email = email;
                this.balance = startingBalance;
            }
            else {
                throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
            }
        }
    }
    // TODO: Add a method to check if the deciaml amount is valid

    public static boolean isAmountValid(double amount){
        if (amount < 0) {
            return false;
        }
        String amountString = Double.toString(amount);

        String[] amountParts = amountString.split("\\.");

        if (amountParts.length > 1){
            if (amountParts[1].length() > 2){
                return false;
            }
        }
        return true;
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (BankAccount.isAmountValid(amount) == false){
            throw new IllegalArgumentException("Cannot withdraw negative amount");
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }
    // New method to check for special characters
    public static boolean specialChar(char c){
        String specialChars = "!#$%&'*+/=?^_`{|}~-.";
        if (specialChars.indexOf(c) != -1){
            return true;
        }
        return false;
    }

    public static boolean isEmailValid(String email){
        String[] emailParts = email.split("@");

        if (email.indexOf('@') == -1 || emailParts.length != 2){
            return false;
        }
        String emailLocal = emailParts[0];
        String emailDomain = emailParts[1];
        
        // All Local Checks 
        if (BankAccount.specialChar(emailLocal.charAt(0))|| BankAccount.specialChar(emailLocal.charAt(emailLocal.length() - 1))) {
            return false;
        }
        if (emailLocal.indexOf("..") != -1){
            return false;
        }
        if (emailDomain.isEmpty() || !emailDomain.contains(".")) {
            return false;
        }

        // Domain Checks
        if (emailDomain.indexOf("..") != -1){
            return false;
        }

        String [] domainParts = emailDomain.split("\\.");

        if (domainParts.length < 2) {
            return false; 
        }
        // Breaks off tdl 
        String tld = domainParts[domainParts.length - 1]; 
        
        if (tld.length() < 2 || tld.length() > 63) {
            return false;
        }
        // experimental regex code
        String regex = "[^a-zA-Z0-9.-]"; 

        if (emailDomain.matches(".*" + regex + ".*")) {
        return false; 
        }

        return true;
}
}