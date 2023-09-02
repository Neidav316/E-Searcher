package com.example.searchgameapp.SearchOperations;

import java.util.ArrayList;
//import java.util.List;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Interfaces.SearchStrategy;


public class SearchContaxt {

	private SearchStrategy search;

	public SearchContaxt(SearchStrategy search) {
		this.search = search;

	}

	public ArrayList<Game> executeSearch(ArrayList<Game> list) {
		return search.doSearch(list);
	}

}
