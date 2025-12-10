package com.library;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a user in the Library Management System.
 * <p>
 * Each user has a unique ID, username, password, email, fine balance, borrowing status,
 * and a list of borrowed media. Users cannot borrow new items if they have unpaid fines.
 * </p>
 *
 * <p>
 * Provides methods to manage borrowed media, track total borrowed items,
 * and calculate or pay fines using a {@link FineCalculator}.
 * </p>
 *
 * @author Nebal
 * @version 1.0
 * @see Media
 * @see FineCalculator
 */
public class User extends Role{

    private double fineBalance;
    private boolean canBorrow;
    private List<Media> borrowedMedia;
    private FineCalculator fineCalculator;
    private int totalBorrowedCount = 0;
    /**
     * Constructs a new User with the specified ID, username, password, and email.
     * Initializes fine balance to 0, borrowing allowed, and an empty list of borrowed media.
     *
     * @param id the unique ID of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     */
    public User(int id, String username, String password, String email) {
        super(id, username, password, email);
        this.fineBalance = 0;
        this.canBorrow = true;
        this.borrowedMedia = new ArrayList<>();
        this.fineCalculator = new FineCalculator(new RegularFineStrategy());
    }

    /** Sets whether the user can borrow items */
    public void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }

    /** Returns the current fine balance */
    public double getFineBalance() {
        return fineBalance;
    }

    /** Returns true if the user is allowed to borrow items */
    public boolean canBorrow() {
        return canBorrow;
    }

    /** Returns the list of media items currently borrowed by the user */
    public List<Media> getBorrowedMedia() {
        return borrowedMedia;
    }

    /** Adds a media item to the user's borrowed list */
    public void addBorrowedMedia(Media m) {
        this.borrowedMedia.add(m);
    }

    /** Removes a media item from the user's borrowed list */
    public void removeBorrowedMedia(Media m) {
        borrowedMedia.remove(m);
    }

    /** Returns the fine calculator associated with the user */
    public FineCalculator getFineCalculator() {
        return fineCalculator;
    }

    /** Returns the total number of items the user has borrowed historically */
    public int getTotalBorrowedCount() {
        return totalBorrowedCount;
    }

    /** Sets the user's fine balance */
    public void setFineBalance(double fineBalance) {
        this.fineBalance = fineBalance;
    }

    /**
     * Adds a fine amount to the user's balance.
     * <p>
     * If the fine balance becomes positive, borrowing is disabled.
     * </p>
     *
     * @param amount the fine amount to add (must be positive)
     * @return true if the amount is valid and added, false otherwise
     */
    public boolean addFineAmount(double amount) {
        if (amount <= 0) return false;
        this.fineBalance += amount;
        this.canBorrow = false;
        return true;
    }
    /** Increments the total count of items the user has borrowed */
    public void incrementTotalBorrowedCount() {
        totalBorrowedCount++;
    }


    /**
     * Pays part or all of the user's fine.
     * <p>
     * Borrowing is re-enabled only when the balance reaches 0.
     * </p>
     *
     * @param amount the payment amount (must be positive and ≤ current balance)
     * @return true if the payment is valid and processed, false otherwise
     */
    public boolean payFine(double amount) {
        if (amount <= 0 || amount > fineBalance) return false;
        fineBalance -= amount;
        if (fineBalance == 0) canBorrow = true;
        return true;
    }



}