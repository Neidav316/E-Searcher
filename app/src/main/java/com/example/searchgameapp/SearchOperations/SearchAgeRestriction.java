package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchAgeRestriction implements SearchStrategy {
	
	private int ageRestriction;
	public void setAgeRestriction(int ageRestriction) {
		this.ageRestriction = ageRestriction;
	}

	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<>();
		for (Game currentGame : list) {
			if(currentGame.getAgeRestriction() >= ageRestriction)
				foundGames.add(currentGame);
		}
		return foundGames;
	}

}
