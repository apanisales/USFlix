//Author: Anthony Panisales
package usflix;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Movie {
	
	protected final String title;
	protected final int year;
	protected final String director;
	private ArrayList<Float> ratings;

	public Movie (String t, int y, String d) {
		title = t;
		year = y;
		director = d;
		ratings = new ArrayList<Float>();
	}

	public void addRating(float r) {
		//Checks ratings min and max requirements
		if (r <= 5.0 && r >= 0.5 || r == (float) 0.0) {
			ratings.add(r);
		} else {
			throw new IllegalArgumentException("Invalid rating value");
		}
	}

	public void removeRating(float r) {
		ratings.remove(r);
	}

	public float getAverageRating() {
		float sum = (float) 0.0;
		for (float r : ratings) {
				sum += r;
		}
		/*This takes into account if the user has watched but not rated a movie. Since
		the rating is automatically 0, it should not be counted or else the average rating
		will drop*/
		if (ratings.size() != 0 && sum != (float) 0.0){
			int newRatingSize = 0;
			for (float r : ratings) {
				if (r != (float) 0.0) {
					newRatingSize++;
				}
			}
			float avgRating = sum/newRatingSize;
			return avgRating;
		} else {
			return (float) 0.0;
		}
	}

	public String toString() {
		return title + " (" + year + ") " + "\n" + "Director: " + director;
	}

}