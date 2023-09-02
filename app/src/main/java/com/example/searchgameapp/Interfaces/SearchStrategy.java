package com.example.searchgameapp.Interfaces;

import java.util.ArrayList;
//import java.util.List;

import com.example.searchgameapp.Classes.Game;

public interface SearchStrategy {

	ArrayList<Game> doSearch(ArrayList<Game> list);
}
