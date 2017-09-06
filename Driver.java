package usflix;

import java.util.*; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Driver class manages user interaction: input and output between the program
 * and users
 * 
 * @author EJ Jung unless otherwise noted
 */
public class Driver {

	/**
	 * There will be only one movie database and one user database in the system,
	 * so they are declared as static.
	 */
	private static MovieDatabase movieDB;
	private static UserDatabase userDB;

	/**
	 * The first program argument (args[0]) may contain the movie information
	 * file name, and the second program argument (args[1]) may contain the user
	 * information file name.
	 * 
	 * @param args
	 * @author Anthony Panisales
	 */

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		userDB = new UserDatabase();

		String fileExist = "n";
		while (fileExist.equals("n")) {
			try {
				if (args.length > 1 && !args[0].equals("")) {
					movieDB = new MovieDatabase(args[0]);
					loadUsers(args[1]);
					fileExist = "y";
				} else if (args.length > 0 && !args[0].equals("")) {
					movieDB = new MovieDatabase(args[0]);
					fileExist = "y";
				} else if (args.length == 0 || args[0].equals("")) {
					System.out.print("Please enter the movie database file name: ");
					movieDB = new MovieDatabase(scan.nextLine());
					fileExist = "y";
				}
			} catch (FileNotFoundException e) {
				System.out.println("User file not found. No information was loaded." + "\n");
				if (args.length > 0) {
					args[0] = "";
				}
			} catch (NumberFormatException e) {
				System.out.println("File has wrong format" + "\n");
				if (args.length > 0) {
					args[0] = "";
				}
			}
		}
		int choice = -1;
		while (choice != 0) {
			try {
				System.out.println("\n" + "Welcome to USFlix! Select an option from the menu.");
				System.out.println("1 to load users and their ratings from a file");
				System.out.println("2 to login");
				System.out.println("3 to create a new account");
				System.out.println("0 to quit");
				try {
					System.out.print("Enter your choice: ");
					choice = Integer.parseInt(scan.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Invalid Input. Type a number.");
					choice = -1;
				}

				switch (choice) {
				case 0:
					System.out.println("Bye!");
					break;
				case 1:
					System.out.print("Enter the file name: ");
					loadUsers(scan.nextLine());
					System.out.println("Load was successful" + "\n");
					break;
				case 2:
					System.out.print("Enter the username: ");
					String username = scan.nextLine();
					System.out.print("Enter the password: ");
					String password = scan.nextLine();
					User u = userDB.login(username, password);
					if (u == null)
						System.out.println("Login error" + "\n");
					else {
						System.out.print("\n");
						userMenu(u);
					}
					break;
				case 3:
					System.out.print("Enter your first name: ");
					String firstName = scan.nextLine();
					System.out.print("Enter your last name: ");
					String lastName = scan.nextLine();
					System.out.print("Enter a username: ");
					username = scan.nextLine();
					System.out.print("Enter a password: ");
					password = scan.nextLine();
					User x = userDB.createAccount(firstName, lastName, username, password);
					if (x != null) {
						System.out.println("New account created" + "\n");
					} else {
						System.out.println("Something went wrong. Make sure you type a valid username and password." + "\n");
					}
					break;
				}

			} catch (InputMismatchException e) {
				System.out.println("Invalid choice" + "\n");
			} catch (FileNotFoundException e) {
				System.out.println("User file is not found" + "\n");
			} catch (NumberFormatException e) {
				System.out.println("File has wrong format" + "\n");
			} catch (NoSuchElementException e) {
				System.out.println("File has wrong format" + "\n");
			}

		}
	}

	/**
	 * loadUsers() method loads the user information from a file. If the username
	 * is available, then the new account is created and his or her rating information 
	 * gets added to the movie database and also to the user object. If the username
	 * is not available, then this method skips to the next user information (until it
	 * sees "done"). 
	 * 
	 * @param filename the user information file name
	 * @author Anthony Panisales
	 * @throws FileNotFoundException if the user information file is not found, 
	 * then the main method catches the exception and asks user for another file name.
	 */
	private static void loadUsers(String filename) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(filename));

		while (scan.hasNextLine()) {
			//TODO: account creation
			String firstName = scan.nextLine();
			String lastName = scan.nextLine();
			String userName = scan.nextLine();
			String password = scan.nextLine();
			User u = userDB.createAccount(firstName, lastName, userName, password);
			String title = scan.nextLine();
			if (u != null) {
				while (!title.equals("done")) {
					Movie m = movieDB.getMovieByTitle(title);
					if (m == null) {
						m = new Movie(title, 0, null);
						movieDB.addMovie(m);
					}
					String tmp = scan.nextLine();
					u.addRating(m, Float.parseFloat(tmp));
					title = scan.nextLine();
				}
			} else {
				while (!title.equals("done")) {
					title = scan.nextLine();
				}
			}
		}

	}

	/**
	 * userMenu handles menu options that are available after logging in,
	 * such as search by title (and director if you have it) and the
	 * list of movies the user has seen before. 
	 * 
	 * @param u 
	 * @author Anthony Panisales
	 */
	private static void userMenu(User u) {
		Scanner scan = new Scanner(System.in);
		int choice = -1;
		while (choice != 0) {
			if (u.getSeenMovies().size() == 0 && u.newUser == true) {
				System.out.println("Hi new user! Please use the search function to find and rate movies you have seen before.");
				u.newUser = false;
				choice = 1;
			} else {
				System.out.println("\n" + "Welcome, " + u.getFirstName()
						+ "! Select an option from the menu.");
				System.out.println("1 to search movies by title or director");
				System.out.println("2 to see the list of movies you have seen before");
				System.out.println("3 to see the recommended movies");
				System.out.println("0 to logout");

				try {
					System.out.print("Enter your choice: ");
					choice = Integer.parseInt(scan.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Invalid Input. Type a number.");
					choice = -1;
				}
			}
			switch (choice) {
			case 1:
				String searchBy = "";
				while (!searchBy.equals("title") && !searchBy.equals("director")) {
					System.out.print("Search by title or director: ");
					searchBy = scan.nextLine().toLowerCase();
				}
				System.out.print("Enter keywords: ");
				String[] keywords = scan.nextLine().toLowerCase().split(" ");
				if (searchBy.equals("title")) {
					ArrayList<Movie> searchResults = movieDB.searchByTitle(keywords);
					listMenu(u, searchResults);
				} 
				if (searchBy.equals("director")) {
					ArrayList<Movie> searchResults = movieDB.searchByDirector(keywords);
					listMenu(u, searchResults);
				}
				break;
			case 2:
				listMenu(u, u.getSeenMovies());
				break;
			case 3:
				listMenu(u, getRecMovies(u));
				break;
			case 0:
				System.out.print("\n");
				return;
			}
		}
	}

	/**
	 * listMenu handles printing the list of Movies with the appropriate
	 * rating (user's own if available, average otherwise), letting user
	 * rate any of the Movies in the list.  
	 * 
	 * @param u
	 * @param list search result or the list of movies user has seen
	 * @author Anthony Panisales
	 */
	private static void listMenu(User u, ArrayList<Movie> list) {
		int choice = -1;
		Scanner scan = new Scanner(System.in);
		if (list.size() == 0) {
			System.out.println("No results found");
			choice = 0;
		} else {
			if (list.equals(getRecMovies(u))) {
				for (int i = 0; i < list.size(); i++) {
					Movie m = list.get(i);
					System.out.println((i + 1) + ". " + m.title + ": " + m.getAverageRating() + " (expected)" + "\n");
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					Movie m = list.get(i);
					System.out.println((i + 1) + ". " + m + "\n" + u.getRating(m) + "\n");
				}
			}
		}
	
		while (choice != 0) {
			System.out.println("\n" + "Select the movie number to rate or watch");
			System.out.println("0 to go back to previous menu");
			try {
				System.out.print("Enter your choice: ");
				choice = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input. Type a number.");
				choice = -1;
			}
			if (choice == 0) {
				System.out.print("\n");
				return;
			} else if (choice - 1 >= 0 && choice <= list.size()) {
				Movie m = list.get(choice - 1);
				String decision1 = "a";
				while (!(decision1.equals("y")) && !(decision1.equals("n"))) {
					System.out.print("Do you want to watch " + m.title + " (y/n)? ");
					decision1 = scan.nextLine().toLowerCase();
				}
				String decision2 = "a";
				while (!(decision2.equals("y")) && !(decision2.equals("n"))) {
					if (decision1.equals("y")) {
						System.out.print(m.title + " has finished. Do you want to rate " + m.title + " (y/n)? ");
					} else if (decision1.equals("n")) {
						if (u.getSeenMovies().contains(m)){
							System.out.print("Do you want to rate " + m.title + " (y/n)? ");
						} else {
							System.out.print("You have not watched " + m.title + " before. You cannot rate this movie." + "\n");
							break;
						}
					}
					decision2 = scan.nextLine().toLowerCase();
				}
					
					
				if (decision2.equals("y")) {
					float rating = (float) -1.0;
					while (rating == (float) -1.0) {
						try {
							System.out.println("How did you like " + m.title
											+ "? (0.5~5 stars)");
							rating = Float.parseFloat(scan.nextLine());
							if (rating == (float) 0.0) {
								throw new IllegalArgumentException();
							}
							u.addRating(m, rating);
						} catch (NumberFormatException e) {
							System.out.println("Invalid Input. Type a number.");
							rating = (float) -1.0;
						} catch (IllegalArgumentException e) {
							System.out.println("Invalid Input. Type a number between 0.5 and 5.");
							rating = (float) -1.0;
						}
					}
				} else if (decision2.equals("n")) {
					/*When user watches a new movie, then the rating is set automatically to 
					be 0 to indicate that the user has watched the movie but hasn't rated.*/
					u.addRating(m, (float) 0.0);
				}
				
			}
		}
	}

	//Author: Anthony Panisales
	public static ArrayList<Movie> getRecMovies(User u) {
		HashMap<User,Float> similarities = new HashMap<User,Float>();
		ArrayList<User> sameOnes = new ArrayList<User>();
		ArrayList<Movie> recMovies = new ArrayList<Movie>();
		for (User user : userDB.getUserDB().values()) {
			float common = 0;
			float total = 0;
			for (MovieRating m : u.getSeenMovies2()) {
				if (user.getSeenMovies().contains(m.getMovie()) && !u.equals(user)) {
					float j = (float) 0.0;
					//Finds movie rating for other user
					for (MovieRating p : user.getSeenMovies2()) {
						if (p.getMovie().equals(m.getMovie())) {
							j = p.getRating();
						}
					}
					//Makes sure no common/total is negative
					float difference = Math.abs(m.getRating() - j);
					common += difference;
					total++;
					
				} else if (u.equals(user)) {
					//Makes sure it doesn't matter if original user compares itself with itself
					common = Integer.MAX_VALUE;
					total = 1;
				} else {
					common += 5;
					//Just in case two users have nothing in common
					if (total == 0) {
						total += 1;
					}
				}
			}

			float similar = (float) common/total;
			similarities.put(user, similar);
			
		}

		float smallest = (float) Integer.MAX_VALUE;
		
	   
	   //Finding smallest common/total
	    for(User key : similarities.keySet()) {
	        if (similarities.get(key) < smallest) {
	            smallest = similarities.get(key);
	        }
	    }


	    /*If there are multiple users that have the smallest common/total,
	    this will make sure all of them are printed*/
	    for (User small : similarities.keySet()) {
	    	if (similarities.get(small) == smallest) {
	    		sameOnes.add(small);
	    	}
	    }

	    //Fills up the list of recommended movies to be printed
	    for (User s : sameOnes) {
	    	for (MovieRating mr : s.getSeenMovies2()) {
	    		if (!u.getSeenMovies().contains(mr.getMovie()) && mr.getRating() >= 3.0 && !recMovies.contains(mr.getMovie())) {
	    			recMovies.add(mr.getMovie());
	    		}
	    	}
	    }

	    return recMovies;

	}
}
