//Author: Anthony Panisales
package usflix;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class User extends Object {
	
	private String firstName;
	private String lastName;
	private String password;
	private ArrayList<MovieRating> seenMovies;
	private String userName;
	protected boolean newUser;

	public User(String f, String l, String u, String p) {
		firstName = f;
		lastName = l;
		userName = u;
		password = p;
		seenMovies = new ArrayList<MovieRating>();
		newUser = true;
	}

	public boolean login(String p) {
		if (p.equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	public void addRating(Movie m, Float r) {
		int notIn = 0;
		for (MovieRating s : seenMovies) {
			if (s.getMovie().equals(m)) {
				if (r != (float) 0.0) {
					m.removeRating(s.getRating());
					m.addRating(r);
					s.setRating(r);
				}
			} else {
				notIn += 1;
			}
		}
		if (notIn == seenMovies.size()) {
			m.addRating(r);
			seenMovies.add(new MovieRating(m , r));
		}
	}

	public java.lang.String getRating(Movie m) {
		for (MovieRating s : seenMovies) {
			if (m.equals(s.getMovie())) {
				if (s.getRating() != (float) 0.0) {
					return s.getRating() + " (your rating)";
				} else {
					return m.getAverageRating() + " (average rating)";
				}
			}	
		}
		return m.getAverageRating() + " (average rating)";
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Movie> getSeenMovies() {
		ArrayList<Movie> sMovies = new ArrayList<Movie> ();
		for (MovieRating s : seenMovies) {
			sMovies.add(s.getMovie());
		}
		return sMovies;
	}

	public ArrayList<MovieRating> getSeenMovies2() {
		return seenMovies;
	}
}