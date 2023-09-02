package com.example.searchgameapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Classes.Profile;
import com.example.searchgameapp.Interfaces.SendDataInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SendDataInterface {

    private FirebaseDatabase database;
    private DatabaseReference mainRef;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private NavHostFragment navHostFragment;
    private Profile signInUser;
    private ArrayList<Profile> profileList;
    private ArrayList<String> genres, platforms , gameModes;

    private ArrayList<Game> listOfGames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        mainRef = database.getReference("ESearcher");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navHostFragment = (NavHostFragment)  getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        signInUser = null;
        setData();

    }

    @Override
    public void sendNewLogedUser(Profile p) {
        signInUser = p;
    }
    @Override
    public void registerNewUser(Profile p){

        DatabaseReference profileRef = mainRef.child("profiles");
        profileRef.child(""+profileList.size()).setValue(p);
        profileList.add(p);
        sendNewLogedUser(p);
    }

    @Override
    public Profile getLogedUser() {
        return signInUser;
    }

    @Override
    public ArrayList<Game> getAllGames() {
        return listOfGames;
    }

    @Override
    public ArrayList<Profile> getAllUsers() {
        return profileList;
    }

    @Override
    public ArrayList<String> getAllGenres(){ return genres;}
    @Override
    public ArrayList<String> getAllPlatforms(){return platforms;}
    @Override
    public ArrayList<String> getAllGameModes(){ return gameModes; }

    @Override
    public void addFavorite(Game game, Profile p) {
        DatabaseReference profileRef = mainRef.child("profiles/"+profileList.indexOf(p));
        profileRef.child("favorites/"+p.getFavorites().size()).setValue(game);
    }

    @Override
    public void removeFavorite(Game game, Profile p) {
        DatabaseReference profileRef = mainRef.child("profiles/"+profileList.indexOf(p));
        profileRef.child("favorites/"+p.getFavorites().indexOf(game)).removeValue();
        p.removeFavorite(game);
        profileRef.child("favorites").setValue(p.getFavorites());
    }

    private void setData() {

        listOfGames = new ArrayList<>();

        DatabaseReference gamesRef = mainRef.child("games");

        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot subDatasnapshot : snapshot.getChildren())
                    listOfGames.add(subDatasnapshot.getValue(Game.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileList = new ArrayList<>();

        DatabaseReference profilesRef = mainRef.child("profiles");

        profilesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot subSnapshot : snapshot.getChildren())
                    profileList.add(subSnapshot.getValue(Profile.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " );
            }
        });
        genres = getAllSelectedSearchOptions("genres");
        platforms = getAllSelectedSearchOptions("platforms");
        gameModes = getAllSelectedSearchOptions("gamemodes");


    }

    public ArrayList<String> getAllSelectedSearchOptions(String searchCategoryName){
        DatabaseReference ref = mainRef.child(searchCategoryName);
        ArrayList<String> arr = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot subSnapshot : snapshot.getChildren())
                    arr.add(subSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed " );
            }
        });
        return arr;
    }
}