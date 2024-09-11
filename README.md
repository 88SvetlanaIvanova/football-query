# football-query

The project is Java program (MVC), that identifies 
the pair of football players 
who have played together in common matches the longest time 
and the duration for each of those matches. The result is displayed with PlayerPairController on http://localhost:8080/api/longest-playing-players
The algorithm for the solution requires combining pairs of players an the time they played together(some of the code is in @Query form in RecordRepository findPlayerPairsWithTimePlayedTogether),
later the pairs are compared to find the same pair ids in findLongestPlayingPlayersDifferentTeamsSamePair, their time on the field is added. The result is saved 
in an Object, and limited to 10. I tried to use a custom Class for the results, but struggled to aggregate data.


The input data can be loaded to the programfrom a CSV file.
More than one date format to be supported, extra points if all date formats are supported.(In DataUtils class)
Implement persistenceof the data - data can be saved to MySQL database.
CRUD for Players, Teams, Matches implemented partially.
