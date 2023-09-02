# E-Searcher

This app helps searching for games by preference of varity of search options and can add the games to your user favorite list of games,
to see the common attributes of the games and use those to search other games alike the games.

## Description

What my app currently does: games can be searched by name, company, genre, avaliable platforms, and game modes,
can sign up or sing in, in which it allows the user to add games to their favorite games list.
Technologies and why i used them:

Firebase: using the Firebase Authentication for user managment, and real-time database for storing and loading the games and the list of a signed in user.

Fragment Manager: the navigation through views is by a fragment manager, in which there is an one activity and on top of it there is a fragment container for fragments to change by navigation.

## Preview

<img src="Preview.gif" width="500" height="600"/>
## Using The App:

1. Search Games:
   - by navigating to the search tab, the full list of games is loaded and can go to their game page.
   - By clicking the search button in the top, the search options is displayed and user can enter text input for game name, company names, and in addition can choose the genres, game modes and platforms.

2. Add Favorite Games:
   - User need to sign in, in order to use this function
   - In the search tab, every game has an star botton to add the game to the user's favorite games list.
   - In the favorite tab, the user can see all the favorite games he added.
   - Favorited games can be removed by clicking again the star botton.

3. Sign Up/ Sign In:
   - currently user can enter any email to sign up.


