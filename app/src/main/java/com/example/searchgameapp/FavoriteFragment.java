package com.example.searchgameapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Classes.Profile;
import com.example.searchgameapp.Interfaces.SendDataInterface;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {

    private ArrayList<Game> listFavoriteGames = new ArrayList<>();

    private Activity mContext;
    private static FavoriteFragment fragment;
    private SendDataInterface sendDataInterface;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    public static FavoriteFragment getInstance(){
        if (fragment == null)
            fragment = newInstance();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        try {
            sendDataInterface = (SendDataInterface) mContext;
        }catch (RuntimeException e) {
            throw new RuntimeException(mContext + " need to implement a method");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        TextView text = view.findViewById(R.id.txtFavorite);
        RecyclerView recyclerView = view.findViewById(R.id.GamePagesRecViewFavorite);
        Profile profile = sendDataInterface.getLogedUser();
        if(profile != null ) {
            recyclerView.setVisibility(View.VISIBLE);
            text.setVisibility(View.INVISIBLE);
            buildListGames(profile);
            initRecyclerView(view, profile);
        }
        else {
            recyclerView.setVisibility(View.INVISIBLE);
            text.setVisibility(View.VISIBLE);
            text.setText("Need to log in to show \n your favorites");
        }
        if( profile != null && listFavoriteGames.isEmpty()){
            text.setVisibility(View.VISIBLE);
            text.setText("You don't have favorites yet \n please add from the search tab");
        }
        return view;
    }

    private void initRecyclerView(View view,Profile p) {
        RecyclerView recyclerView = view.findViewById(R.id.GamePagesRecViewFavorite);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        GameRecViewAdapter adapter = new GameRecViewAdapter(listFavoriteGames, sendDataInterface, p);
        recyclerView.setAdapter(adapter);
    }

    private void buildListGames(Profile profile) {

        listFavoriteGames.clear();
        listFavoriteGames.addAll(profile.getFavorites());

    }
}