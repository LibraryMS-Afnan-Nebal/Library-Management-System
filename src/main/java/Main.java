////public class Main {
////    public static void main(String[] args) {
////        AdminManager adminManager = AdminManager.getInstance();
////        LibrarianManager librarianManager = LibrarianManager.getInstance();
////        UserManager userManager = UserManager.getInstance();
////
////        System.out.println("=== SIGNING UP USERS ===");
////        adminManager.signUp("AdminOne", "pass123", "admin1@test.com");
////        adminManager.signUp("AdminTwo", "pass456", "admin2@test.com");
////        librarianManager.signUp("LibOne", "lib123", "lib1@test.com");
////        librarianManager.signUp("LibTwo", "lib456", "lib2@test.com");
////        userManager.signUp("UserOne", "user123", "user1@test.com");
////        userManager.signUp("UserTwo", "user456", "user2@test.com");
////        userManager.signUp("UserThree", "user789", "user3@test.com");
////
////        System.out.println("\n=== LOGGING IN USERS ===");
////        System.out.println("AdminOne login: " + adminManager.login("AdminOne", "pass123"));
////        System.out.println("LibTwo login: " + librarianManager.login("LibTwo", "lib456"));
////        System.out.println("UserThree login: " + userManager.login("UserThree", "user789"));
////        System.out.println("Wrong password login: " + userManager.login("UserTwo", "wrongpass"));
////
////        System.out.println("\n=== CHANGING USERNAMES/PASSWORDS ===");
////        System.out.println("Change UserThree username to 'UserX': " + userManager.changeUsername("UserThree", "UserX"));
////        System.out.println("Change UserThree password to 'newpass': " + userManager.changePassword("UserX", "newpass"));
////
////        System.out.println("\n=== LOGGING OUT USERS ===");
////        System.out.println("AdminOne logout: " + adminManager.logout("AdminOne"));
////        System.out.println("UserX logout: " + userManager.logout("UserX"));
////
////        System.out.println("\n=== VERIFY DATA IN SYSTEM ===");
////        System.out.println("Admins:");
////        adminManager.accounts.values().forEach(a -> System.out.println(
////                a.getUsername() + " | loggedIn=" + a.isLoggedIn()
////        ));
////
////        System.out.println("Librarians:");
////        librarianManager.accounts.values().forEach(l -> System.out.println(
////                l.getUsername() + " | loggedIn=" + l.isLoggedIn()
////        ));
////
////        System.out.println("Users:");
////        userManager.accounts.values().forEach(u -> System.out.println(
////                u.getUsername() + " | loggedIn=" + u.isLoggedIn()
////        ));
////
////        System.out.println("\n=== TEST FINES AND BORROWING ===");
////        User user1 = userManager.accounts.values().iterator().next(); // get first user
////        System.out.println("Initial canBorrow: " + user1.canBorrow());
////        user1.addFineAmount(50);
////        System.out.println("After adding fine, canBorrow: " + user1.canBorrow() + ", fineBalance=" + user1.getFineBalance());
////        user1.payFine(50);
////        System.out.println("After paying fine, canBorrow: " + user1.canBorrow() + ", fineBalance=" + user1.getFineBalance());
////    }
////}
//
//
///*public class Main {
//    public static void main(String[] args) {
//
//        UserManager userManager = UserManager.getInstance();
//        AdminManager adminManager = AdminManager.getInstance();
//        LibrarianManager librarianManager = LibrarianManager.getInstance();
//
//        System.out.println("=== SIGN UP ===");
//        System.out.println("User sign up: " +
//                userManager.signUp("nebal", "1234", "nebal@gmail.com"));
//        System.out.println("Admin sign up: " +
//                adminManager.signUp("admin", "adminpass", "admin@gmail.com"));
//        System.out.println("Librarian sign up: " +
//                librarianManager.signUp("lib", "libpass", "lib@gmail.com"));
//
//        System.out.println("\n=== LOGIN ===");
//        System.out.println("User login: " +
//                userManager.login("nebal", "1234"));
//        System.out.println("Admin login: " +
//                adminManager.login("admin", "adminpass"));
//        System.out.println("Librarian login: " +
//                librarianManager.login("lib", "libpass"));
//
//        System.out.println("\n=== CHANGE USERNAME ===");
//        System.out.println("Change username (must be logged in): " +
//                userManager.changeUsername("nebal", "nebz"));
//
//        System.out.println("Login with new username: " +
//                userManager.login("nebz", "1234"));
//
//        System.out.println("\n=== CHANGE PASSWORD ===");
//        System.out.println("Change password: " +
//                userManager.changePassword("nebz", "9999"));
//
//        System.out.println("Login with new password: " +
//                userManager.login("nebz", "9999"));
//
//        System.out.println("\n=== LOGOUT ===");
//        System.out.println("User logout: " +
//                userManager.logout("nebz"));
//        System.out.println("Admin logout: " +
//                adminManager.logout("admin"));
//        System.out.println("Librarian logout: " +
//                librarianManager.logout("lib"));
//
//        System.out.println("\n=== DONE ===");
//    }
//}
//*/
//
////NEW
//import io.github.cdimascio.dotenv.Dotenv;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Set;
//
//public class Main {
//        private static Scanner scanner ;
//        private static UserManager userManager ;
//        private static AdminManager adminManager ;
//        private static LibrarianManager librarianManager;
//        private static BookManager bookManager ;
//        private static CDManager cdManager ;
//        private static LoanManager loanManager ;
//        private static ReminderService reminderService ;
//        private static EmailService emailService;
//        private static Role currentUser ;
//        private static Book book1, book2, book3, book4;
//        private static CD cd1, cd2, cd3;
//
//
//        private static void changerUsernameOrPassword()
//        {
//            System.out.println("Choose:");
//            System.out.println("1. Change username");
//            System.out.println("2. Change password");
//            String ch = scanner.nextLine();
//            if ("1".equals(ch)) {
//                System.out.print("Enter new username: ");
//                String newU = scanner.nextLine();
//                try {
//                    boolean ok = userManager.changeUsername(currentUser.getUsername(), newU);
//                    if (ok) {
//                        System.out.println("Username changed successfully to " + newU);
//                        // currentUser object is the same instance; username updated inside manager
//                    } else {
//                        System.out.println("Failed to change username. It may be taken or you must be logged in.");
//                    }
//                } catch (IllegalArgumentException ex) {
//                    System.out.println("Error: " + ex.getMessage());
//                }
//            } else if ("2".equals(ch)) {
//                System.out.print("Enter new password: ");
//                String newP = scanner.nextLine();
//                boolean ok = userManager.changePassword(currentUser.getUsername(), newP);
//                if (ok) {
//                    System.out.println("Password changed successfully.");
//                } else {
//                    System.out.println("Failed to change password. Make sure you are logged in.");
//                }
//            } else {
//                System.out.println("Invalid option.");
//            }
//        }
//    private static void searchMediaMenu(Object person) {
//        System.out.println("Choose Media Type to Search:");
//        System.out.println("1. Book");
//        System.out.println("2. CD");
//        String searchType = scanner.nextLine();
//
//        switch (searchType) {
//            case "1": // Book search
//                System.out.println("Search Book by:");
//                System.out.println("1. Title");
//                System.out.println("2. Author");
//                System.out.println("3. ISBN");
//                String bkOpt = scanner.nextLine();
//                switch (bkOpt) {
//                    case "1":
//                        System.out.print("Enter Book title: ");
//                        String bt = scanner.nextLine();
//                        BookManager.getInstance().displaySearchByTitle(bt, person);
//                        break;
//                    case "2":
//                        System.out.print("Enter Book author: ");
//                        String ba = scanner.nextLine();
//                        BookManager.getInstance().displaySearchByAuthor(ba, person);
//                        break;
//                    case "3":
//                        System.out.print("Enter full ISBN (13 digits): ");
//                        String bi = scanner.nextLine();
//                        BookManager.getInstance().displaySearchByISBN(bi, person);
//                        break;
//                    default:
//                        System.out.println("Invalid option.");
//                }
//                break;
//
//            case "2": // CD search
//                System.out.println("Search CD by:");
//                System.out.println("1. Title");
//                System.out.println("2. Author");
//                String cdOpt = scanner.nextLine();
//                switch (cdOpt) {
//                    case "1":
//                        System.out.print("Enter CD title: ");
//                        String ct = scanner.nextLine();
//                        CDManager.getInstance().displaySearchByTitle(ct, person);
//                        break;
//                    case "2":
//                        System.out.print("Enter CD artist: ");
//                        String ca = scanner.nextLine();
//                        CDManager.getInstance().displaySearchByAuthor(ca, person);
//                        break;
//                    default:
//                        System.out.println("Invalid option.");
//                }
//                break;
//
//            default:
//                System.out.println("Invalid media type.");
//        }
//    }
//        Main()
//        {
//             scanner = new Scanner(System.in);
//             userManager = UserManager.getInstance();
//            adminManager = AdminManager.getInstance();
//            librarianManager = LibrarianManager.getInstance();
//            bookManager = BookManager.getInstance();
//             cdManager = CDManager.getInstance();
//            loanManager = LoanManager.getInstance();
//             reminderService = new ReminderService(loanManager);
//             Role currentUser = null;
//        }
//
//
//    public static void main(String[] args) {
//            Main main = new Main();
//        userManager.signUp("u","1","s12217921@stu.najah.edu");
//        userManager.signUp("user2","pass2","user2@gmail.com");
//        adminManager.signUp("a","1","admin1@gmail.com");
//        adminManager.signUp("admin2","pass22","admin2@gmail.com");
//        librarianManager.signUp("l","1","librarian@gmail.com");
//
//         book1 = new Book("book1","author1","1234567890");
//         book4 = new Book("book1","author11","1234567893");
//         book2 = new Book("book2","author2","1234567891");
//        book3 = new Book("book3","author3","1234567892");
//        BookManager.getInstance().add(book1,1);    BookManager.getInstance().add(book4,2);
//        BookManager.getInstance().add(book2,2);
//        BookManager.getInstance().add(book3,3);
//
//         cd1 = new CD("CD1","authorCd1");
//        cd3 = new CD("CD1","authorCd2");
//        cd2 = new CD("CD2","authorCd2");
//        CDManager.getInstance().add(cd1,1);CDManager.getInstance().add(cd3,1);
//        CDManager.getInstance().add(cd2,2);
//
//        boolean exit = false;
//
//        while (!exit) {
//            System.out.println("\n======= Welcome to Library Management System =======");
//            System.out.println("1. SIGN UP");
//            System.out.println("2. LOG IN");
//            System.out.println("3. EXIT");
//            System.out.print("Choose an option: ");
//
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1":
//                    signUpMenu();
//                    break;
//                case "2":
//                    logInMenu();
//                    break;
//                case "3":
//                    System.out.println("Exiting... Goodbye!");
//                    exit = true;
//                    break;
//                default:
//                    System.out.println("Invalid option. Try again.");
//            }
//        }
//    }
//
//    private static void signUpMenu() {
//        System.out.println("\n--------- SIGN UP ---------");
//            System.out.print("Enter username: ");
//            String username = scanner.nextLine();
//            System.out.print("Enter password: ");
//            String password = scanner.nextLine();
//            System.out.print("Enter email: ");
//            String email = scanner.nextLine();
//
//            boolean created =false;
//            try {
//                created = userManager.signUp(username, password, email);
//            } catch (IllegalArgumentException e) {
//                System.out.println(e.getMessage() + " Try again.");
//
//            }
//
//            if (created)
//                System.out.println("Account created successfully! You can now LOG IN.");
//    }
//
//
//    private static void logInMenu() {
//        if (currentUser != null) {
//            System.out.println("Already logged in as " + currentUser.getUsername());
//            return;
//        }
//
//        System.out.println("\n--------- LOG IN ---------");
//
//            System.out.print("Enter username: ");
//            String username = scanner.nextLine();
//            System.out.print("Enter password: ");
//            String password = scanner.nextLine();
//
//            try {
//                if (adminManager.login(username, password)) {
//                    currentUser = adminManager.accounts.get(adminManager.usernameToId.get(username.toLowerCase()));
//                    System.out.println("Welcome Admin " + currentUser.getUsername());
//                    adminMenu();
//                } else if (userManager.login(username, password)) {
//                    currentUser = userManager.accounts.get(userManager.usernameToId.get(username.toLowerCase()));
//                    System.out.println("Welcome User " + currentUser.getUsername());
//                    userMenu();
//                } else if (librarianManager.login(username, password)) {
//                    currentUser = librarianManager.accounts.get(librarianManager.usernameToId.get(username.toLowerCase()));
//                    System.out.println("Welcome Librarian " + currentUser.getUsername());
//                    librarianMenu();
//                } else {
//                    System.out.println("Invalid username or password! Please try again.");
//                }
//            } catch (IllegalArgumentException exception) {
//                System.out.println(exception.getMessage() + " Try again.");
//            }
//    }
//
//    private static void logOutMenu() {
//        if (currentUser == null) {
//            System.out.println("No user is currently logged in.");
//            return;
//        }
//
//        if (currentUser instanceof Admin) {
//            adminManager.logout(currentUser.getUsername());
//        } else if (currentUser instanceof User) {
//            userManager.logout(currentUser.getUsername());
//        }else if(currentUser instanceof Librarian){
//            librarianManager.logout(currentUser.getUsername());
//        }
//        System.out.println("Logged out successfully from " + currentUser.getUsername());
//        currentUser = null;
//    }
//
//    private static void adminMenu() {
//        boolean back = false;
//        while (!back && currentUser != null) {
//            System.out.println("\n---------- Admin Menu ----------");
//            System.out.println("1. Add Librarian");
//            System.out.println("2. Unregister User");
//            System.out.println("3. Add Media");
//            System.out.println("4. Search Media");
//            System.out.println("5. Send reminder");
//            System.out.println("6. Change Username or Password");
//            System.out.println("7. Logout");
//            System.out.print("Choose an option: ");
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1": // Add Librarian
//                    System.out.print("Enter librarian username: ");
//                    String libUsername = scanner.nextLine();
//                    System.out.print("Enter password: ");
//                    String libPassword = scanner.nextLine();
//                    System.out.print("Enter email: ");
//                    String libEmail = scanner.nextLine();
//                    try {
//                        if (librarianManager.signUp(libUsername, libPassword, libEmail)) {
//                            System.out.println("Librarian added successfully!");
//                        }
//                    } catch (IllegalArgumentException ex) {
//                        System.out.println("Failed to add librarian: " + ex.getMessage());
//                    }
//                    break;
//
//                case "2":
//                    System.out.println("Current users:");
//                    userManager.printAllUsers();
//
//                    System.out.print("Enter User ID to unregister: ");
//                    int id = Integer.parseInt(scanner.nextLine());
//
//                    if (((Admin) currentUser).unregisterUser(id)) {
//                        System.out.println("User unregistered successfully.");
//                    } else {
//                        System.out.println("Failed to unregister user.");
//                    }
//                    break;
//
//                case "3": // Add Media
//                    System.out.println("Choose Media Type:");
//                    System.out.println("1. Book");
//                    System.out.println("2. CD");
//                    String mediaChoice = scanner.nextLine();
//
//                    switch (mediaChoice) {
//                        case "1": // Book
//                            System.out.print("Enter Book title: ");
//                            String bookTitle = scanner.nextLine();
//                            System.out.print("Enter Book author: ");
//                            String bookAuthor = scanner.nextLine();
//                            System.out.println("Enter the ISBN (10 digits only, no spaces or letters)");
//                            System.out.print("Starts automatically with 978 → ISBN: 978");
//                            String isbn = scanner.nextLine();
//
//                            System.out.print("Enter number of copies: ");
//                            int bookCopies = Integer.parseInt(scanner.nextLine());
//
//
//                            try {
//                                Book newBook = new Book(bookTitle, bookAuthor, isbn);
//                                BookManager.getInstance().add(newBook, bookCopies);
//                                System.out.println("Book added successfully!");
//                            } catch (IllegalArgumentException ex) {
//                                System.out.println("Failed to add book: " + ex.getMessage());
//                            }
//                            break;
//
//                        case "2": // CD
//                            System.out.print("Enter CD title: ");
//                            String cdTitle = scanner.nextLine();
//                            System.out.print("Enter CD artist: ");
//                            String cdArtist = scanner.nextLine();
//                            System.out.print("Enter number of copies: ");
//                            int cdCopies = Integer.parseInt(scanner.nextLine());
//
//
//                            try {
//                                CD newCD = new CD(cdTitle, cdArtist);
//                                CDManager.getInstance().add(newCD, cdCopies);
//                                System.out.println("CD added successfully!");
//                            } catch (IllegalArgumentException ex) {
//                                System.out.println("Failed to add CD: " + ex.getMessage());
//                            }
//                            break;
//
//                        default:
//                            System.out.println("Invalid media type.");
//                            break;
//                    }
//                    break;
//
//                case "4": // Search Media
//                            searchMediaMenu(currentUser);
//                            break;
//
//                case "5": // Send Reminder
//                    Dotenv dotenv = Dotenv.load();
//                    String email = dotenv.get("user");
//                    String appPassword = dotenv.get("password");
//                    if (email == null || appPassword == null) {
//                        System.out.println("Email credentials not found in .env file!");
//                        break;
//                    }
//                    email = email.trim();
//                    appPassword = appPassword.replaceAll("\\s+", "");
//                    emailService = new EmailService(email, appPassword);
//                    EmailNotifier emailNotifier = new EmailNotifier(emailService);
//                    reminderService.addObserver(emailNotifier);
//                    reminderService.sendReminders();
//                    System.out.println("Reminders sent successfully!");
//                    break;
//
//                         case "6": // change Username or password
//                           changerUsernameOrPassword();
//                             break;
//
//                        case "7": // Logout
//                            logOutMenu();
//                            back = true;
//                            break;
//
//                        default:
//                            System.out.println("Invalid option.");
//                            break;
//
//            }
//        }
//    }
//
//        private static  void userMenu() {
//        boolean back = false;
//
//            Loan oldLoan = new Loan(book1, (User) currentUser);
//            LoanManager.getInstance().getLoanList().add(oldLoan);
//            book1.setIsBorrowed(true);
//            ((User) currentUser).addBorrowedMedia(book1);
//
//            Loan oldLoan2 = new Loan(cd1, (User) currentUser);
//            LoanManager.getInstance().getLoanList().add(oldLoan2);
//            cd1.setIsBorrowed(true);
//            ((User) currentUser).addBorrowedMedia(cd1);
//
//        while (!back && currentUser != null) {
//            System.out.println("\n---------- User Menu ----------");
//            System.out.println("1. Search media");
//            System.out.println("2. Borrow media");
//            System.out.println("3. Return media");
//            System.out.println("4. Overdue report");
//            System.out.println("5. Pay Fine");
//            System.out.println("6. Change Username or passwoed");
//            System.out.println("7. Logout");
//            System.out.print("Choose an option: ");
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1": // Search Media
//                    searchMediaMenu(currentUser);
//                    break;
//
//                case "2": // Borrow Media
//                    System.out.println("Choose Media Type to Borrow:");
//                    System.out.println("1. Book");
//                    System.out.println("2. CD");
//                    String borrowType = scanner.nextLine();
//
//                    switch (borrowType) {
//                        case "1": // Borrow Book
//                            System.out.print("Enter Book title: ");
//                            String bookTitle = scanner.nextLine();
//                            List<Book> foundBooks = BookManager.getInstance().searchByTitle(bookTitle);
//
//                            if (foundBooks.isEmpty()) {
//                                System.out.println("Book not found");
//                                break;
//                            }
//
//                            // جمع المؤلفين المتاحين
//                            Set<String> availableAuthors = new HashSet<>();
//                            for (Book b : foundBooks) {
//                                if (!b.getIsBorrowed()) availableAuthors.add(b.getAuthor());
//                            }
//
//                            if (availableAuthors.isEmpty()) {
//                                System.out.println("Book not available");
//                                break;
//                            }
//
//                            Book selectedBook = null;
//                            if (availableAuthors.size() == 1) {
//                                selectedBook = foundBooks.stream()
//                                        .filter(b -> !b.getIsBorrowed())
//                                        .findFirst()
//                                        .get();
//                            } else {
//                                System.out.println("Multiple authors found for this title:");
//                                BookManager.getInstance().displaySearchByTitle(bookTitle, currentUser);
//
//                                System.out.println("Enter the author to borrow:");
//                                String authorInput = scanner.nextLine();
//                                selectedBook = foundBooks.stream()
//                                        .filter(b -> b.getAuthor().equalsIgnoreCase(authorInput) && !b.getIsBorrowed())
//                                        .findFirst()
//                                        .orElse(null);
//
//                                if (selectedBook == null) {
//                                    System.out.println("No book found for this author.");
//                                    break;
//                                }
//                            }
//
//                            LoanManager.getInstance().borrow(selectedBook, (User) currentUser);
//                            break;
//
//                        case "2": // Borrow CD
//                            System.out.print("Enter CD title: ");
//                            String cdTitle = scanner.nextLine();
//                            List<CD> foundCDs = CDManager.getInstance().searchByTitle(cdTitle);
//
//                            if (foundCDs.isEmpty()) {
//                                System.out.println("CD not found");
//                                break;
//                            }
//
//                            // جمع الفنانين المتاحين
//                            Set<String> availableArtists = new HashSet<>();
//                            for (CD c : foundCDs) {
//                                if (!c.getIsBorrowed()) availableArtists.add(c.getAuthor()); // أو getArtist()
//                            }
//
//                            if (availableArtists.isEmpty()) {
//                                System.out.println("CD not available");
//                                break;
//                            }
//
//                            CD selectedCD = null;
//                            if (availableArtists.size() == 1) {
//                                selectedCD = foundCDs.stream()
//                                        .filter(c -> !c.getIsBorrowed())
//                                        .findFirst()
//                                        .get();
//                            } else {
//                                System.out.println("Multiple artists found for this title:");
//                                CDManager.getInstance().displaySearchByTitle(cdTitle, currentUser);
//
//                                System.out.println("Enter the artist to borrow:");
//                                String artistInput = scanner.nextLine();
//                                selectedCD = foundCDs.stream()
//                                        .filter(c -> c.getAuthor().equalsIgnoreCase(artistInput) && !c.getIsBorrowed())
//                                        .findFirst()
//                                        .orElse(null);
//
//                                if (selectedCD == null) {
//                                    System.out.println("No CD found for this artist.");
//                                    break;
//                                }
//                            }
//
//                            LoanManager.getInstance().borrow(selectedCD, (User) currentUser);
//                            break;
//
//                        default:
//                            System.out.println("Invalid media type.");
//                    }
//                    break;
//
//                case "3": // Return Media
//                    System.out.println("Choose Media Type to Return:");
//                    System.out.println("1. Book");
//                    System.out.println("2. CD");
//                    String returnType = scanner.nextLine();
//
//                    switch (returnType) {
//                        case "1", "2": // Book or CD
//                            boolean isBook = returnType.equals("1");
//                            System.out.print("Enter " + (isBook ? "Book" : "CD") + " title to return: ");
//                            String titleInput = scanner.nextLine();
//
//                            List<? extends Media> foundItems = isBook
//                                    ? BookManager.getInstance().searchByTitle(titleInput)
//                                    : CDManager.getInstance().searchByTitle(titleInput);
//
//                            if (foundItems.isEmpty()) {
//                                System.out.println((isBook ? "Book" : "CD") + " not found");
//                                break;
//                            }
//
//                            // Filter to items actually borrowed by current user
//                            List<? extends Media> userBorrowedItems = foundItems.stream()
//                                    .filter(m -> m.getIsBorrowed() &&
//                                            ((User) currentUser).getBorrowedMedia().stream()
//                                                    .anyMatch(bm -> bm.getTitle().equalsIgnoreCase(m.getTitle())
//                                                            && bm.getAuthor().equalsIgnoreCase(m.getAuthor())))
//                                    .toList();
//
//                            if (userBorrowedItems.isEmpty()) {
//                                System.out.println("You did not borrow this " + (isBook ? "book" : "CD") + ".");
//                                break;
//                            }
//
//                            Media itemToReturn = null;
//                            Set<String> authors = new HashSet<>();
//                            for (Media m : userBorrowedItems) authors.add(m.getAuthor());
//
//                            if (authors.size() == 1) {
//                                itemToReturn = userBorrowedItems.get(0);
//                            } else {
//                                System.out.println("Multiple authors found for this borrowed title:");
//                                if (isBook) BookManager.getInstance().displaySearchByTitle(titleInput, currentUser);
//                                else CDManager.getInstance().displaySearchByTitle(titleInput, currentUser);
//
//                                System.out.print("Enter the author/artist of the item you want to return: ");
//                                String authorInput = scanner.nextLine();
//                                itemToReturn = userBorrowedItems.stream()
//                                        .filter(m -> m.getAuthor().equalsIgnoreCase(authorInput))
//                                        .findFirst()
//                                        .orElse(null);
//
//                                if (itemToReturn == null) {
//                                    System.out.println("No borrowed item found for this author/artist.");
//                                    break;
//                                }
//                            }
//
//                            LoanManager.getInstance().returnMedia(itemToReturn, (User) currentUser);
//                            break;
//
//                        default:
//                            System.out.println("Invalid media type.");
//                    }
//                    break;
//
//                case "4": // Overdue Report
//                    String report = LoanManager.getInstance().generateOverdueReport((User) currentUser);
//                    System.out.println(report);
//                    break;
//
//                case "5": // Pay Fine
//                    System.out.print("Enter amount to pay: ");
//                    double amount = Double.parseDouble(scanner.nextLine());
//                    boolean paid = ((User) currentUser).payFine(amount);
//                    if (paid) {
//                        System.out.println("Payment successful! New balance: " + ((User) currentUser).getFineBalance());
//                    } else {
//                        System.out.println("Invalid payment amount.");
//                    }
//                    break;
//
//                case "6": // Change Username or password
//                    changerUsernameOrPassword();
//                    break;
//
//                case "7": // Logout
//                    logOutMenu();
//                    back = true;
//                    break;
//
//                default:
//                    System.out.println("Invalid option.");
//            }
//        }
//    }
//
//    private static void librarianMenu() {
//        boolean back = false;
//        while (!back && currentUser != null) {
//            System.out.println("\n---------- Librarian Menu ----------");
//            System.out.println("1. Detect & apply overdue fines");
//            System.out.println("2. Change Username or Password ");
//            System.out.println("3. Logout");
//            System.out.print("Choose an option: ");
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1": // Detect & apply overdue fines
//                    List<Loan> overdueLoans = ((Librarian) currentUser).detectOverdueMedia();
//                    System.out.println("Overdue detection finished and fines accrued where applicable.\n");
//
//                    if (overdueLoans.isEmpty()) {
//                        System.out.println("No overdue items at the moment.");
//                        break;
//                    }
//
//                    System.out.println("======================== Overdue Books ========================");
//                    for (Loan loan : overdueLoans) {
//                        if (loan.getMedia() instanceof Book) {
//                            Book b = (Book) loan.getMedia();
//                            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
//                            System.out.printf("Title: %s | Author: %s | Overdue: %d days | Fine: %.2f NIS\n",
//                                    b.getTitle(), b.getAuthor(), overdueDays, b.getDailyFineRate() * overdueDays);
//                        }
//                    }
//
//                    System.out.println("\n======================== Overdue CDs ========================");
//                    for (Loan loan : overdueLoans) {
//                        if (loan.getMedia() instanceof CD) {
//                            CD c = (CD) loan.getMedia();
//                            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
//                            System.out.printf("Title: %s | Artist: %s | Overdue: %d days | Fine: %.2f NIS\n",
//                                    c.getTitle(), c.getAuthor(), overdueDays, c.getDailyFineRate() * overdueDays);
//                        }
//                    }
//                    break;
//
//
//                case "2": // Change Username or Password
//                    changerUsernameOrPassword();
//                    break;
//
//                case "3": // Logout
//                    logOutMenu();
//                    back = true;
//                    break;
//
//                default:
//                    System.out.println("Invalid option.");
//            }
//        }
//    }
//
//}
