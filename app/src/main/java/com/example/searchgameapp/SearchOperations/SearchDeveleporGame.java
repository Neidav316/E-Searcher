package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;
//import java.util.List;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchDeveleporGame implements SearchStrategy {

	private String developerComapny;

	public SearchDeveleporGame(String developerComapny) {
		this.developerComapny = developerComapny;
	}


	public void setDeveloperComapny(String developerComapny) {
		this.developerComapny = developerComapny;
	}

	
	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<>();
		for (Game currentGame : list) {
			if (currentGame.getDeveloperCompany().equalsIgnoreCase(developerComapny))
				foundGames.add(currentGame);
		}
		return foundGames;
	}

}
