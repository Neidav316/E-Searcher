package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;

public class SearchGameMode implements SearchStrategy {

	private ArrayList<String> gameModesList;

	public  SearchGameMode(ArrayList<String> gameModesList){
		this.gameModesList =  gameModesList;
	}

	public void setGameModes(ArrayList<String> gameModes) {
		this.gameModesList = gameModes;
	}

	@Override
	public ArrayList<Game> doSearch(ArrayList<Game> list) {
		ArrayList<Game> foundGames = new ArrayList<>();
		boolean found;

		for (Game currentGame : list) {
			found = false;
			for (String currentGameMode : gameModesList) {
				for (String gameGameMode : currentGame.getGameModes()) {
					if (gameGameMode.equals(currentGameMode)) {
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
