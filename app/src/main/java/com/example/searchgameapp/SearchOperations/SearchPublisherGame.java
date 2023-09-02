package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchPublisherGame implements SearchStrategy {

	private String publisherComapny;

	public SearchPublisherGame(String publisherComapny) {
		this.publisherComapny = publisherComapny;
	}


	public void setDeveloperComapny(String publisherComapny) {
		this.publisherComapny = publisherComapny;
	}
	
	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<>();
		for (Game currentGame : list) {
			if (currentGame.getPublishCompany().equalsIgnoreCase(publisherComapny))
				foundGames.add(currentGame);
		}
		return foundGames;
	}

}
