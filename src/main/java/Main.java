public class Main {
    public static void main(String[] args) {
        AdminManager adminManager = AdminManager.getInstance();
        LibrarianManager librarianManager = LibrarianManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        System.out.println("=== SIGNING UP USERS ===");
        adminManager.signUp("AdminOne", "pass123", "admin1@test.com");
        adminManager.signUp("AdminTwo", "pass456", "admin2@test.com");
        librarianManager.signUp("LibOne", "lib123", "lib1@test.com");
        librarianManager.signUp("LibTwo", "lib456", "lib2@test.com");
        userManager.signUp("UserOne", "user123", "user1@test.com");
        userManager.signUp("UserTwo", "user456", "user2@test.com");
        userManager.signUp("UserThree", "user789", "user3@test.com");

        System.out.println("\n=== LOGGING IN USERS ===");
        System.out.println("AdminOne login: " + adminManager.login("AdminOne", "pass123"));
        System.out.println("LibTwo login: " + librarianManager.login("LibTwo", "lib456"));
        System.out.println("UserThree login: " + userManager.login("UserThree", "user789"));
        System.out.println("Wrong password login: " + userManager.login("UserTwo", "wrongpass"));

        System.out.println("\n=== CHANGING USERNAMES/PASSWORDS ===");
        System.out.println("Change UserThree username to 'UserX': " + userManager.changeUsername("UserThree", "UserX"));
        System.out.println("Change UserThree password to 'newpass': " + userManager.changePassword("UserX", "newpass"));

        System.out.println("\n=== LOGGING OUT USERS ===");
        System.out.println("AdminOne logout: " + adminManager.logout("AdminOne"));
        System.out.println("UserX logout: " + userManager.logout("UserX"));

        System.out.println("\n=== VERIFY DATA IN SYSTEM ===");
        System.out.println("Admins:");
        adminManager.accounts.values().forEach(a -> System.out.println(
                a.getUsername() + " | loggedIn=" + a.isLoggedIn()
        ));

        System.out.println("Librarians:");
        librarianManager.accounts.values().forEach(l -> System.out.println(
                l.getUsername() + " | loggedIn=" + l.isLoggedIn()
        ));

        System.out.println("Users:");
        userManager.accounts.values().forEach(u -> System.out.println(
                u.getUsername() + " | loggedIn=" + u.isLoggedIn()
        ));

        System.out.println("\n=== TEST FINES AND BORROWING ===");
        User user1 = userManager.accounts.values().iterator().next(); // get first user
        System.out.println("Initial canBorrow: " + user1.canBorrow());
        user1.addFineAmount(50);
        System.out.println("After adding fine, canBorrow: " + user1.canBorrow() + ", fineBalance=" + user1.getFineBalance());
        user1.payFine(50);
        System.out.println("After paying fine, canBorrow: " + user1.canBorrow() + ", fineBalance=" + user1.getFineBalance());
    }
}


/*public class Main {
    public static void main(String[] args) {

        UserManager userManager = UserManager.getInstance();
        AdminManager adminManager = AdminManager.getInstance();
        LibrarianManager librarianManager = LibrarianManager.getInstance();

        System.out.println("=== SIGN UP ===");
        System.out.println("User sign up: " +
                userManager.signUp("nebal", "1234", "nebal@gmail.com"));
        System.out.println("Admin sign up: " +
                adminManager.signUp("admin", "adminpass", "admin@gmail.com"));
        System.out.println("Librarian sign up: " +
                librarianManager.signUp("lib", "libpass", "lib@gmail.com"));

        System.out.println("\n=== LOGIN ===");
        System.out.println("User login: " +
                userManager.login("nebal", "1234"));
        System.out.println("Admin login: " +
                adminManager.login("admin", "adminpass"));
        System.out.println("Librarian login: " +
                librarianManager.login("lib", "libpass"));

        System.out.println("\n=== CHANGE USERNAME ===");
        System.out.println("Change username (must be logged in): " +
                userManager.changeUsername("nebal", "nebz"));

        System.out.println("Login with new username: " +
                userManager.login("nebz", "1234"));

        System.out.println("\n=== CHANGE PASSWORD ===");
        System.out.println("Change password: " +
                userManager.changePassword("nebz", "9999"));

        System.out.println("Login with new password: " +
                userManager.login("nebz", "9999"));

        System.out.println("\n=== LOGOUT ===");
        System.out.println("User logout: " +
                userManager.logout("nebz"));
        System.out.println("Admin logout: " +
                adminManager.logout("admin"));
        System.out.println("Librarian logout: " +
                librarianManager.logout("lib"));

        System.out.println("\n=== DONE ===");
    }
}
*/
/*import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            UserManager userManager =UserManager.getInstance();
            userManager.signUp("user1","pass1","email1@gmail.com");
            userManager.signUp("user2","pass2","email2@gmail.com");

            AdminManager adminManager = AdminManager.getInstance();
            adminManager.signUp("admin1","pass1");
            adminManager.signUp("admin2","pass2");

            LibrarianManager librarianManager =LibrarianManager.getInstance();
            librarianManager.signUp("librarian","pass1");
Authentication auth = Authentication.getInstance();
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n===== Library System =====");
                System.out.println("1. Sign up");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose: ");

                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {

                    // ------------------------------------
                    // USER SIGNUP (ONLY USERS)
                    // ------------------------------------
                    case 1:
                        System.out.println("\n--- User Signup ---");
                        System.out.print("Username: ");
                        String newUser = sc.nextLine();
                        System.out.print("Password: ");
                        String newPass = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        try {
                            auth.signUpUser(newUser, newPass, email,userManager);
                            System.out.println("Signup successful! You can login now.");
                        } catch (Exception e) {
                            System.out.println("Signup failed: " + e.getMessage());
                        }
                        break;

                    // ------------------------------------
                    // LOGIN (NO ROLE SELECTION)
                    // ------------------------------------
                    case 2:
                        System.out.println("\n--- Login ---");
                        System.out.print("Username: ");
                        String u = sc.nextLine();
                        System.out.print("Password: ");
                        String p = sc.nextLine();

                        //change to method
                        Account acc = auth.login(u, p);
                        if (acc == null) {
                            System.out.println("Invalid username or password.");
                        } else {
                            System.out.println("Login successful! Welcome " + acc.getUsername());
                            //change parameter to role
                            handleRoleMenu(sc, acc);
                        }
                        break;

                    // ------------------------------------
                    // EXIT
                    // ------------------------------------
                    case 3:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }

        // =====================================================
        // ROLE MENUS — AUTOMATICALLY CHOSEN BASED ON LOGIN
        // =====================================================
        private static void handleRoleMenu(Scanner sc, Account acc) {

            switch (acc.getRole()) {

                case USER:
                    userMenu(sc, acc);
                    break;

                case ADMIN:
                    adminMenu(sc, acc);
                    break;

                case LIBRARIAN:
                    librarianMenu(sc, acc);
                    break;
            }
        }

        // ================================
        // USER MENU
        // ================================
        private static void userMenu(Scanner sc, Account acc) {
            UserManager userManager = UserManager.getInstance();

            while (true) {
                System.out.println("\n===== USER MENU =====");
                System.out.println("1. Search media");
                System.out.println("2. Borrow media");
                System.out.println("3. View fines");
                System.out.println("4. Logout");
                System.out.print("Choose: ");

                int c = sc.nextInt();
                sc.nextLine();

                switch (c) {
                    // add case to return a book
                    case 1:
                        System.out.println("Searching media...");
                        //Search either Book or cd
                        // TODO hook search here
                        break;
                    case 2:
                        System.out.println("Borrowing media...");
                        // TODO borrow implementation
                        break;
                    case 3:
                        System.out.println("Your fines: ");
                        // to options when views fine : 1. pay fine 2.return book
                        // TODO fines for this user
                        break;
                    case 4:
                        System.out.println("Logout successful.");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }

        // ================================
        // ADMIN MENU
        // ================================
        private static void adminMenu(Scanner sc, Account acc) {
            AdminManager adminManager = AdminManager.getInstance();

            while (true) {
                System.out.println("\n===== ADMIN MENU =====");
                System.out.println("1. Add new Admin");
                System.out.println("2. Manage system");
                System.out.println("3. Logout");
                System.out.print("Choose: ");

                int c = sc.nextInt();
                sc.nextLine();

                switch (c) {
                    case 1:
                        System.out.print("New admin username: ");
                        String adminU = sc.nextLine();
                        System.out.print("Password: ");
                        String adminP = sc.nextLine();

                        try {
                            adminManager.addAdmin(adminU, adminP);
                            System.out.println("Admin created!");
                        } catch (Exception e) {
                            System.out.println("Failed: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.println("Managing system...");
                        // 1. addBook , 2. searchBook , 3. Send remainder
                        System.out.println("1. Add Book");
                        System.out.println("2. Add CD");
                        // TODO admin actions
                        break;

                    case 3:
                        System.out.println("Logout successful.");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }

        // ================================
        // LIBRARIAN MENU
        // ================================
        private static void librarianMenu(Scanner sc, Account acc) {
            LibrarianManager lm = LibrarianManager.getInstance();

            while (true) {
                System.out.println("\n===== LIBRARIAN MENU =====");

                System.out.println("3. Generate overdue report");
                System.out.println("4. Logout");
                System.out.print("Choose: ");

                int c = sc.nextInt();
                sc.nextLine();

                switch (c) {
                    case 1:
                        System.out.println("Add book...");
                        // TODO add book
                        break;
                    case 2:
                        System.out.println("Add CD...");
                        // TODO add CD
                        break;
                    case 3:
                        System.out.println("Generating mixed-media overdue report...");
                        // TODO call fine system
                        break;
                    case 4:
                        System.out.println("Logout successful.");
                        return;

                        //add case for detect overdue and issues fines
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }









    }









    }
*/