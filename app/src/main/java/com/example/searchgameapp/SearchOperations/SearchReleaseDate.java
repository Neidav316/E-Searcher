package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchReleaseDate implements SearchStrategy {

	private long releaseDate;

	public SearchReleaseDate(long releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setReleaseDate(long releaseDate) {
		this.releaseDate = releaseDate;
	}

	
	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<Game>();
		for (Game currentGame : list) {

			if (currentGame.getReleaseDate() < releaseDate || currentGame.getReleaseDate() == releaseDate)
				foundGames.add(currentGame);
		}

		return foundGames;
	}

}
