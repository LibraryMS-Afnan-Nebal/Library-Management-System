import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanManager {

    private static LoanManager instance = null;   // singleton instance
    private List<Loan> listOfLoans;

    private LoanManager()
    {
        listOfLoans = new ArrayList<>();
    }
    public static LoanManager getInstance()
    {
        if (instance == null)
            instance = new LoanManager();
        return instance;
    }

    public void setListOfLoans(Loan loan)
    {
        this.listOfLoans.add(loan);
    }

    public boolean borrowBook(String title, String author, User user) {
        Book.validateTitle(title);
        Book.validateAuthor(author);
        if (user == null)
        {
            System.out.println("User cannot be null");
            return false;
        }

        boolean found = false;
        for (Book book : BookManager.listOfBooks)
        {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author))
            {
                found = true;
                if (!book.getIsBorrowed())
                {
                    book.setIsBorrowed(true);
                    Loan loan = new Loan(book, user);
                    listOfLoans.add(loan);
                    user.setBorrowedBooks(book);
                    System.out.println("You successfully borrowed the book.\nReturn it by: " + loan.getDueDate());
                    return true;
                }
            }
        }
        if (found)
            System.out.println("Sorry, all copies of this book are currently borrowed");
        else
            System.out.println("Book with this title and author not found");
        return false;
    }

    public boolean returnBook(String title, String author, User user)
    {
        Book.validateTitle(title); Book.validateAuthor(author);
        if (user == null) return false;
        boolean foundBook = false;

        for (Book book : user.getBorrowedBooks())
        {
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


    public List<Loan> detectOverdueBooks()
    {
        if (listOfLoans.isEmpty())
        {
            System.out.println("There are no loans");
            return null;
        }

        List<Loan> overdueLoans = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for(Loan loan : listOfLoans )
        {
            if (today.isAfter(loan.getDueDate()))
            {
                loan.setIsOverdue(true);
                overdueLoans.add(loan);
            }
        }
        return overdueLoans;
    }





}