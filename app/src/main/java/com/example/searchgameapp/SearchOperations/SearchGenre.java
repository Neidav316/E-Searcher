package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;
//import java.util.List;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchGenre implements SearchStrategy {

	private ArrayList<String> genreList;

	public SearchGenre(ArrayList<String> genreList) {
		this.genreList = genreList;
	}


	public void setGenre(ArrayList<String> genreList) {
		this.genreList = genreList;
	}

	
	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<>();
		boolean found;
		for (Game currentGame : list) {
			found = false;
			for (String currentGenre : genreList) {
				for (String gameGenre : currentGame.getGenres()) {
					if (gameGenre.equals(currentGenre)) {
						foundGames.add(currentGame);
						found = true;
						break;
					}
				}
				if (found)
					break;
			}
		}
		return foundGames;
	}
}
