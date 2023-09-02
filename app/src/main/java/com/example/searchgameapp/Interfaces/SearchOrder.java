package com.example.searchgameapp.Interfaces;

import java.util.ArrayList;

import com.example.searchgameapp.Classes.Game;

public interface SearchOrder {

	ArrayList<Game> execute(ArrayList<Game> originalGamesList);
}
