public class UserManager extends AccountManager<User> {
    private static UserManager instance = null;

    private UserManager() {}
    public static UserManager getInstance() {
        if (instance == null) instance = new UserManager();
        return instance;
    }




    @Override
    protected User buildAccount(int id, String username, String password, String email) {
        return new User(id, username, password, email);
    }
   // make sure to update this
/*
    /**
     * Checks if a user can be unregistered.
     * A user can only be removed if they have:
     * - No unpaid fines
     * - No active (not returned) loans
     *
     * @param user the user to check
     * @return true if the user can be unregistered, false otherwise
     *
    public boolean canUnregister(User user) {
        if (user == null) return false;
        if (user.getFineBalance() > 0) {
            return false;
        }
        for (Loan loan : LoanManager.getInstance().getLoanList()) {
            if (loan.getUser().equals(user) && !loan.getReturned()) {
                return false;
            }
        }

        return true;
    }


    /**
     * Removes a user from the system if they meet the conditions
     * (no unpaid fines and no active loans).
     *
     * @param userId the ID of the user to remove
     * @return true if the user was successfully removed, false if not
     *
    public boolean unregisterUser(int userId) {
        User user = users.get(userId);
        if (user == null) return false;
        if (!canUnregister(user)) {
            return false;
        }
        users.remove(userId);
        usernameToId.remove(user.getUsername().toLowerCase());

        return true;
    }

*/
   public void evaluateAndPromoteUser(User user) {
       int count = user.getTotalBorrowedCount();

       // access the user's FineCalculator
       FineCalculator calculator = user.getFineCalculator();

       if (count >= 15) {
           calculator.setFineStrategy(new GoldFineStrategy());
           System.out.println(user.getUsername() + " is now a GOLD member! ");
       } else if (count >= 5) {
           calculator.setFineStrategy(new SilverFineStrategy());
           System.out.println(user.getUsername() + " is now a SILVER member!");
       } else {
           calculator.setFineStrategy(new RegularFineStrategy());
       }
   }
}






     /**
      * Attempts to borrow a book for the specified user while enforcing borrowing restrictions.
      * <p>
      * This method ensures that the user meets all borrowing conditions before delegating
      * the actual borrowing operation to the {@link BookManager}. A user cannot borrow a book if:
      * <ul>
      *     <li>They have unpaid fines (fineBalance > 0).</li>
      *     <li>Their borrowing privileges are revoked (canBorrow == false).</li>
      * </ul>
      * If all conditions are satisfied, the method calls {@link BookManager#borrowBook(String, User)}
      * to complete the borrowing process.
      * </p>
      *
      * @param isbnPart     The last part of the ISBN (without the "978" prefix).
      * @param user         The user attempting to borrow the book.
      * @param bookManager  The {@link BookManager} responsible for managing book operations.
      * @return {@code true} if the borrowing is successful; {@code false} otherwise.

     */
/*
    public boolean borrowBookForUser(String isbnPart, User user, BookManager bookManager) {
        if (user.getFineBalance() > 0) {
           System.out.println("Borrowing blocked: You have unpaid fines.");
         return false;
        }

        if (!user.canBorrow()) {
            System.out.println("Borrowing blocked: You have overdue books or borrowing privileges revoked.");
            return false;
        }

        return bookManager.borrowBook(isbnPart, user);

        /////this will be replaced with a loan obj
    */

