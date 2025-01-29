package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        bankAccount.withdraw(100);
        bankAccount.withdraw(50);
        bankAccount.withdraw(50);
        // Checks after multiple withdrawals
        assertEquals(0,bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        // Passing Tests
        bankAccount.withdraw(1);
        assertEquals(99, bankAccount.getBalance());
        // Tests larger Withdrawals
        bankAccount.withdraw(98);
        assertEquals(1, bankAccount.getBalance());
        // Tests Withdrawal that equals to 0
        bankAccount.withdraw(1);
        assertEquals(0, bankAccount.getBalance());
        // Tests that Fail
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(1));
        // Tests for negative values
        assertThrows(IllegalArgumentException.class,()-> bankAccount.withdraw(-1));
        assertThrows(IllegalArgumentException.class,()-> bankAccount.withdraw(1.111));
        
    }

    @Test
    void isEmailValidTest() {
        assertFalse(BankAccount.isEmailValid("")); // empty string

        // prefix test cases
        assertTrue(BankAccount.isEmailValid("abc@mail.com")); // valid email address
        assertTrue(BankAccount.isEmailValid("abc#def@mail.com")); // valid character in prefix
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com")); // valid character in prefix
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com")); // valid character in prefix
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com")); // valid character in prefix

        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); // invalid character at end of prefix
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com")); // two consecutive periods
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // period at start of prefix

        // domain test cases
        assertTrue(BankAccount.isEmailValid("abc.def@mail.cc")); // valid TLD
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com")); // valid special character in domain
        assertTrue(BankAccount.isEmailValid("abc.def@mail.org")); // valid TLD
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com")); // valid TLD

        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); // TLD too short
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // invalid character in domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // two consecutive periods
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); // no TLD
        assertFalse(BankAccount.isEmailValid("ithaca.edu@qsmith")); // no TLD
    }
    /// Good test I have no complaints, did a good job implementing the EP and BVA testing methods
    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        double amount = bankAccount.getBalance();
        assertTrue(BankAccount.isAmountValid(amount));
        // check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("qsmith@ithaca.edu",-100));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("qsmith@ithaca.edu",100.111));
    }
    @Test
    void isAmountValidTest(){
        //Passes
        assertTrue(BankAccount.isAmountValid(0));
        assertTrue(BankAccount.isAmountValid(10.1));
        assertTrue(BankAccount.isAmountValid(10.01));
        assertTrue(BankAccount.isAmountValid(10.22));
        //Fails if Negative or more that 2 decimal places
        assertFalse(BankAccount.isAmountValid(-1));
        assertFalse(BankAccount.isAmountValid(10.111));
    }
    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("qsmith@ithaca.edu", 100);
        BankAccount bankAccount2 = new BankAccount("bbienus@ithaca.edu", 100);
        //Valid transfer
        bankAccount.transfer(50, bankAccount2);
        bankAccount2.transfer(.1, bankAccount);
        //Ten cent transfer
        assertEquals(149.9, bankAccount2.getBalance());
        assertEquals(50.1, bankAccount.getBalance());
        //one cent transfer
        bankAccount.transfer(.01, bankAccount2);
        assertEquals(149.91, bankAccount2.getBalance());
        //Invalid Transfer Due not enough funds
        assertThrows(InsufficientFundsException.class, () -> bankAccount.transfer(100, bankAccount2));
        //Invalid Amount Formats Check
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(-50, bankAccount2));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(50.111, bankAccount2));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(50.001, bankAccount2));

    }

    @Test
    void depositTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("qsmith@ithaca.edu", 100);
        bankAccount.deposit(100);
        //Valid amount deposit
        assertEquals(200, bankAccount.getBalance());
        //multiple deposits in a row
        bankAccount.deposit(100);
        bankAccount.deposit(100);
        assertEquals(400, bankAccount.getBalance());
        //Tries to deposit a negative and invalid decimal amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(100.111));
    }
}
