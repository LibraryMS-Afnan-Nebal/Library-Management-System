package com.library;

/**
 * Abstract class representing a role in the Library Management System.
 * <p>
 * This serves as a base class for different types of accounts, such as
 * {@link Admin} and {@link User}. Each role has an ID, username, password, email,
 * and login status.
 * </p>
 *
 * <p>Provides basic getters and setters, including validation for email format
 * and management of login status.</p>
 *
 * @author Nebal
 * @version 1.0
 * @see Admin
 * @see User
 */
public abstract class Role {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected boolean isLoggedIn;
    /**
     * Constructs a Role with the specified ID, username, password, and email.
     *
     * @param id the unique ID of the role
     * @param username the username for the role
     * @param password the password for the role
     * @param email the email address for the role (must be valid format)
     * @throws IllegalArgumentException if the email format is invalid
     */
    public Role(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        setEmail(email);
        this.isLoggedIn = false;
    }

    /** Returns the username */
    public String getUsername() {
        return username;
    }

    /** Sets the username */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Returns the password */
    public String getPassword() {
        return password;
    }

    /** Sets the password */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Returns the email address */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address after validating its format.
     *
     * @param email the email to set
     * @throws IllegalArgumentException if the email format is invalid
     */
    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    /** Returns true if the role is currently logged in */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /** Sets the login status of the role */
    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    /** Returns the unique ID of the role */
    public Integer getId() {
        return id;
    }
}