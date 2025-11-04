import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanManager {
    private List<Loan> listOfLoans;
    public LoanManager() {listOfLoans = new ArrayList<>();}
    public List<Loan>getLoanList(){return this.listOfLoans;}

    public boolean borrowBook(String title, String author, User user) {
        Book.validateTitle(title);
        Book.validateAuthor(author);
        if (user == null) {
            System.out.println("User cannot be null");
            return false;
        }

        boolean found = false;
        for (Book book : BookManager.listOfBooks) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                found = true;
                if (!book.getIsBorrowed()) {
                    book.setIsBorrowed(true);
                    Loan loan = new Loan(book, user);
                    listOfLoans.add(loan);
                    user.setBorrowedBooks(book);
                    System.out.println("You successfully borrowed the book.\nReturn it by: " + loan.getDueDate());
                    return true;
                }
            }
        }
        if (found) {
            System.out.println("Sorry, all copies of this book are currently borrowed");
        } else System.out.println("Book with this title and author not found");
        return false;
    }

    public boolean returnBook(String title, String author, User user)
    {
        Book.validateTitle(title); Book.validateAuthor(author);
        if (user == null) return false;
        boolean foundBook = false;

        for (Book book : user.getBorrowedBooks()) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author))
            {
                foundBook = true;
                book.setIsBorrowed(false);

                for (Loan loan : listOfLoans)
                {
                    if (loan.getUser().equals(user) && loan.getBook().equals(book))
                    {
                        loan.setReturned(true);
                        loan.setReturnDate(LocalDate.now());
                        user.getBorrowedBooks().remove(book);
                        System.out.println("You successfully returned the book.");
                        return true;
                    }
                }
                System.out.println("Book found but no matching loan record — data inconsistency!");
                return false;
            }
        }

        if (!foundBook)
            System.out.println("You didn’t borrow this book.");

        return false;
    }
}