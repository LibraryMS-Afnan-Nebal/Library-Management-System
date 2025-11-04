public class BookStats {
    private String ISBN;
    private String title;
    private String author;
    private int totalCopies;
    private int borrowedCopies;
    private int availableCopies;

   public BookStats(String ISBN,String title, String author,int totalCopies, int borrowedCopies, int availableCopies)
   {
       this.ISBN = ISBN;
       this.title = title;
       this.author = author;
       this.totalCopies = totalCopies;
       this.borrowedCopies = borrowedCopies;
       this.availableCopies = availableCopies;
   }

    public String getTitle() { return title;}
    public String getAuthor() { return author;}
    public String getISBN() { return ISBN;}
    public int getTotalCopies() {return totalCopies;}
    public int getBorrowedCopies() {return borrowedCopies;}
    public int getAvailableCopies() {return availableCopies;}




}
