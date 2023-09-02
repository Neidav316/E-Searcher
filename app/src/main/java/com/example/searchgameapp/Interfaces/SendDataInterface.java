package com.example.searchgameapp.Interfaces;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Classes.Profile;

import java.util.ArrayList;

public interface SendDataInterface {

        void sendNewLogedUser(Profile p);
        void registerNewUser(Profile p);
        Profile getLogedUser();
        ArrayList<Game> getAllGames();
        ArrayList<Profile> getAllUsers();

        ArrayList<String> getAllGenres();
        ArrayList<String> getAllPlatforms();
        ArrayList<String> getAllGameModes();

        void addFavorite(Game game, Profile p);
        void removeFavorite(Game game, Profile p);
}
