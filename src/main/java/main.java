import java.util.Scanner;

public class main {

    public static void main(String[] args)
    {
        Scanner input = new Scanner (System.in);
        int choice;
        BookManager library = new BookManager();



        do {
            System.out.println("\n**************************************************************************************");
            System.out.println("1. Add book\n2. Search about book");
            choice=input.nextInt();
            input.nextLine(); // Consume the newline character

            System.out.println();

            switch (choice)
            {
                // adding book to the inventory
                case 1:
                    Book book = null;

                    System.out.print("Enter the title: ");
                    String title = input.nextLine();

                    System.out.print("Enter the author: ");
                    String author = input.nextLine();

                    boolean valid = false;
                    System.out.println("Enter the ISBN (10 digits only, no spaces or letters)");
                    System.out.print("Starts automatically with 978 → ISBN: 978");
                    do
                    {
                        try
                        {
                            String isbn = input.nextLine();
                            book = new Book(title, author, isbn);
                            valid  = true;

                        } catch (IllegalArgumentException e)
                        {
                            System.out.print("\n❌ Invalid ISBN:  "+e.getMessage() + ", Try again: 978");
                        }

                    }while (!valid );

                    library.addBook(book);
                    System.out.println("\n✅ Book added successfully!");
                    break;


               //Search about book
                case 2:
                    System.out.print("Choose how to search for the book:\n" +
                            "1. Title\n" +
                            "2. Author\n" +
                            "3. ISBN\n" +
                            "Enter 1, 2, or 3: ");
                    String s = input.nextLine();
                    int number = Integer.parseInt(s);

                    while(number<=0 || number >=4)
                    {
                        System.out.print("Invalid choice! Please enter 1, 2, or 3: ");
                        s = input.nextLine();
                        number = Integer.parseInt(s);
                    }

                   switch (number)
                   {
                       case 1:
                           System.out.print("Enter the Title of the Book: ");
                           String t = input.nextLine();
                           library.searchBookByTitle(t);
                           break;

                       case 2:
                           System.out.print("Enter the Author of the Book: ");
                           String a = input.nextLine();
                           library.searchBookByAuthor(a);
                           break;

                       case 3:
                           System.out.print("Enter the ISBN of the Book: ");
                           String i = input.nextLine();
                           library.searchBookByIsbn(i);
                           break;
                   }

                    break;


                case 10:
                    break;



            }

        }while(choice!=10);

    }
}
