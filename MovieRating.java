//Author: Anthony Panisales
package usflix;

import java.util.*;  

public class MovieRating extends Object {

	private Movie movie;
	private float rating;

	public MovieRating(Movie m, float r) {
		movie = m;
		rating = r;
	}

	public String toString() {
		if (rating > 0.0) {
			return rating + "";
		} else {
			return movie.getAverageRating() + "(average rating)";
		}
	}

	public Movie getMovie() {
		return movie;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float r) {
		rating = r;
	}

}