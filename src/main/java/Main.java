import java.util.Scanner;

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
