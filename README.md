# USFlix
Allows the user to rate movies and receive movie recommendations after creating an account. 

* movies.txt is the file that should be used for movie information and users.txt is the file that should be used for user information. 
* OriginalDriver.txt is the code that was given to me before I made my changes. 
* To recommend movies for a particular user, I compared the movie ratings that the user has to the movie ratings of other users in order to find the users who have the most similar movie ratings to the original user. 
    * Only the movie ratings for the movies that the original user and the other users have watched are taken into account in the comparison. 
    * The movies that the most similar users have seen but the original user has not seen are the recommended movies for the original user.
