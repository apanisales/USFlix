//Author: Anthony Panisales
package usflix;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class MovieDatabase extends Object {

	private ArrayList<Movie> movies;

	public MovieDatabase() {
		movies = new ArrayList<Movie>();
	}
	
	public MovieDatabase(String filename) throws FileNotFoundException {
		movies = new ArrayList<Movie>();
		Scanner filescan = new Scanner(new File(filename));
		while (filescan.hasNextLine()) {
			String title = filescan.nextLine();
			String tempInt = filescan.nextLine();
			int year = Integer.parseInt(tempInt);
			String director = filescan.nextLine();
			movies.add(new Movie(title, year, director));
		}
	}

	public boolean addMovie(Movie m) {
		movies.add(m);
		if (movies.contains(m)) {
			return true;
		}
		return false;
	}

	public ArrayList<Movie> searchByTitle(String[] keywords) {
		ArrayList<Movie> selected = new ArrayList <Movie>();
		//Searches the movie array for the user's input
		for (Movie m : movies) {
			int in = 0;
			for(String k : keywords) {
				if (m.title.toLowerCase().contains(k)) {
					in++;
				}
			}
			if (in == keywords.length) {
				selected.add(m);
			}
		}
		return selected;
	}

	public ArrayList<Movie> searchByDirector(String[] keywords) {
		ArrayList<Movie> selected = new ArrayList <Movie>();
		//Searches the movie array for the user's input
		for (Movie m : movies) {
			int in = 0;
			for(String k : keywords) {
				try {
					if (m.director.toLowerCase().contains(k)) {
						in++;
					}
				} catch (NullPointerException e) {
					
				}
			}
			if (in == keywords.length) {
				selected.add(m);
			}
		}
		return selected;
	}

	public Movie getMovieByTitle(String title) {
		for (Movie m : movies) {
			if (title.equals(m.title)) {
				return m;
			}
		}
		return null;
	}
}