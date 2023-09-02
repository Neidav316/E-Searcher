package com.example.searchgameapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.searchgameapp.Classes.Game;

import java.time.LocalDate;

public class GamePageFragment extends Fragment {

    private Game game;
    private AppCompatActivity mAppCompact;
    private static GamePageFragment gamePageFragment;


    public GamePageFragment() {
        // Required empty public constructor
    }
    public GamePageFragment(Game game){
        this.game = game;
    }


    public static GamePageFragment newInstance(Game game) {
        return new GamePageFragment(game);
    }
    public static GamePageFragment getInstance(Game game){
     if (gamePageFragment == null){
         gamePageFragment = GamePageFragment.newInstance(game);
         return gamePageFragment;
     }
     else{
         gamePageFragment.setGame(game);
         return gamePageFragment;
     }
    }

    public void setGame(Game game){
        this.game = game;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppCompact = (AppCompatActivity) getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_page, container, false);
        Toolbar topBarTools = view.findViewById(R.id.top_toolbar_game_page);


        mAppCompact.setSupportActionBar(topBarTools);
        mAppCompact.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAppCompact.getSupportActionBar().setDisplayShowHomeEnabled(true);
        topBarTools.setNavigationOnClickListener(v -> mAppCompact.getSupportFragmentManager().popBackStack());
        TextView txtInfo = view.findViewById(R.id.game_info);
        TextView txtPublisher = view.findViewById(R.id.publish_company);
        TextView txtDeveloper = view.findViewById(R.id.developer_company);
        TextView txtPlatforms = view.findViewById(R.id.platforms);
        TextView txtGameModes = view.findViewById(R.id.game_modes);
        TextView txtGenres = view.findViewById(R.id.genres);
        TextView txtSeries = view.findViewById(R.id.series_game_page);
        TextView txtReleaseDate = view.findViewById(R.id.release_date);
        ImageView imageView = view.findViewById(R.id.game_image);
        topBarTools.setTitle(game.getName());
        txtInfo.append(game.getInfo());
        txtPublisher.append(game.getPublishCompany());
        txtDeveloper.append(game.getDeveloperCompany());
        txtGenres.setText(game.getSpecifiedListString(1));
        txtPlatforms.setText(game.getSpecifiedListString(2));
        txtGameModes.setText(game.getSpecifiedListString(3));
        txtReleaseDate.append(LocalDate.ofEpochDay(game.getReleaseDate()).toString());
        if(game.getSeries().equals(""))
            txtSeries.setVisibility(View.INVISIBLE);
        else
            txtSeries.setText("Series: "+ game.getSeries());
        Glide.with(this).load(game.getPhotoUrl().get(0)).into(imageView);



        return view;
    }


}
