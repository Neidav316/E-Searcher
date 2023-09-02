package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;
import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchGameName implements SearchStrategy {

	private String searchName;
	
	public SearchGameName(String searchName) {
		this.searchName = searchName;
	}


	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	
	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<>();
		for (Game currentGame : list) {
			if(currentGame.getName().toLowerCase().contains(searchName.toLowerCase()))
				foundGames.add(currentGame);
			}
		return foundGames;
	}

}
