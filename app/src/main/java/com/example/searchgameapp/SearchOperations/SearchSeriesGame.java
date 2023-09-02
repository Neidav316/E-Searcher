package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;
//import java.util.List;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchSeriesGame implements SearchStrategy {

	private String series;

	public SearchSeriesGame(String series) {
		this.series = series;
	}

	public void setSeries(String series) {
		this.series = series;
	}
	
	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<>();
		for (Game currentGame : list) {
			if (currentGame.getSeries().equals(series))
				foundGames.add(currentGame);
		}
		return foundGames;
	}

	

}
