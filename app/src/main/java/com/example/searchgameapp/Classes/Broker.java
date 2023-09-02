package com.example.searchgameapp.Classes;

import java.util.ArrayList;

import com.example.searchgameapp.Interfaces.SearchOrder;
import com.example.searchgameapp.Interfaces.SearchStrategy;


public class Broker implements SearchOrder {

	private ArrayList<Game> listFoundGames;
	private ArrayList<SearchStrategy> searchList;

	public Broker() {
		this.listFoundGames = new ArrayList<Game>();
		searchList = new ArrayList<SearchStrategy>();
	}


	public void addSearch(SearchStrategy search) {
		searchList.add(search);
	}

	@Override
	public ArrayList<Game> execute(ArrayList<Game> originalGamesList) {
		listFoundGames.clear();
		
		if(searchList.isEmpty())
			return originalGamesList;
		
		else {
			listFoundGames = originalGamesList;
			for (SearchStrategy search : searchList) {
				if(listFoundGames.isEmpty())
					break;
				listFoundGames = search.doSearch(listFoundGames);
			}
		}
		searchList.clear();
		return listFoundGames;
	}

}
