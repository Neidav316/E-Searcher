package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;
//import java.util.List;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchPlatform implements SearchStrategy {

	private ArrayList<String> platformList;

	public SearchPlatform(ArrayList<String> platformList) {
		this.platformList = platformList;
	}

	public void setPlatform(ArrayList<String> platform) {
		this.platformList = platform;
	}

	
	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<>();
		boolean found;
		for (Game currentGame : list) {
			found = false;
			for (String platform : platformList) {
				for (String gamePlatform : currentGame.getPlatforms()) {
					if (gamePlatform.equals(platform)) {
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
