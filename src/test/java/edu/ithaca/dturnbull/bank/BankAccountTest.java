package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        // check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));
    }

}