package com.example.searchgameapp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchgameapp.Classes.Broker;
import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Classes.Profile;
import com.example.searchgameapp.Interfaces.SendDataInterface;
import com.example.searchgameapp.SearchOperations.SearchDeveleporGame;
import com.example.searchgameapp.SearchOperations.SearchGameMode;
import com.example.searchgameapp.SearchOperations.SearchGameName;
import com.example.searchgameapp.SearchOperations.SearchGenre;
import com.example.searchgameapp.SearchOperations.SearchPlatform;
import com.example.searchgameapp.SearchOperations.SearchPublisherGame;
import com.example.searchgameapp.SearchOperations.SearchSeriesGame;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private ArrayList<Game> allListGames = new ArrayList<>();
    private ArrayList<Game> searchedListGames = new ArrayList<>();
    private Toolbar topBarTools;
    private Context mContext;
    private ArrayList<String> genres, platforms, gameModes;
    private ConstraintLayout frontLayout;
    private RelativeLayout backLayout;
    private RelativeLayout.LayoutParams lp;
    private ChipGroup genrasSearch, platformsSearch, gameModesSearch;
    private EditText gameNameEd, publisherEd, developerEd, seriesEd;
    private Broker searcher;
    private GameRecViewAdapter adapter;
    private MenuItem itemCheck;
    private SendDataInterface sendDataInterface;
    private static SearchFragment fragment ;
    boolean showBackLayout;


    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public static SearchFragment getInstance(){
        if (fragment == null)
            fragment = newInstance();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        searcher = new Broker();
        try{
            sendDataInterface = (SendDataInterface) mContext;
        } catch (RuntimeException e) {
            throw new RuntimeException(mContext + " need to implement a method");
        }
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_bar_menu, menu);
        itemCheck = menu.findItem(R.id.app_bar_search);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.app_bar_search){
            dropLayout();
            return true;
    }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        topBarTools = view.findViewById(R.id.top_toolbar);
        showBackLayout = false;
        frontLayout = view.findViewById(R.id.front_search_layout);
        backLayout = view.findViewById(R.id.back_search_layout);
        gameNameEd = view.findViewById(R.id.Game_name_input);
        publisherEd = view.findViewById(R.id.Publisher_input);
        developerEd = view.findViewById(R.id.Developer_input);
        seriesEd = view.findViewById(R.id.series_input);



        if(genrasSearch == null || platformsSearch == null || gameModesSearch == null){

            genrasSearch = backLayout.findViewById(R.id.Genras_choices);
            platformsSearch = backLayout.findViewById(R.id.Platfroms_choices);
            gameModesSearch = backLayout.findViewById(R.id.GameModes_choices);

            platforms = sendDataInterface.getAllPlatforms();
            genres = sendDataInterface.getAllGenres();
            gameModes = sendDataInterface.getAllGameModes();


            for(String string : genres){
                Chip newGenre = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout,null,false);
                newGenre.setText(string);
                newGenre.setCheckable(true);
                newGenre.setClickable(true);
                genrasSearch.addView(newGenre);
            }
            for(String string : platforms){
                Chip newGenre = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout,null,false);
                newGenre.setText(string);
                newGenre.setCheckable(true);
                newGenre.setClickable(true);
                platformsSearch.addView(newGenre);
            }
            for(String string : gameModes){
                Chip newGenre = (Chip) getLayoutInflater().inflate(R.layout.single_chip_layout,null,false);
                newGenre.setText(string);
                newGenre.setCheckable(true);
                newGenre.setClickable(true);
                gameModesSearch.addView(newGenre);
            }

        }

        ((AppCompatActivity)mContext).setSupportActionBar(topBarTools);
        if(allListGames.isEmpty()) {
            buildListGames();
            searchedListGames = allListGames;
        }

        initRecyclerView(view,sendDataInterface.getLogedUser());
        return view;
    }

    private void initRecyclerView(View view, Profile p) {
        RecyclerView recyclerView = view.findViewById(R.id.GamePagesRecViewSearch);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new GameRecViewAdapter(searchedListGames,sendDataInterface,p);
        recyclerView.setAdapter(adapter);
    }

    private void buildListGames() {
        allListGames.clear();
        allListGames.addAll(sendDataInterface.getAllGames());
    }


    private void dropLayout(){
        showBackLayout = !showBackLayout;
        lp = (RelativeLayout.LayoutParams) frontLayout.getLayoutParams();

        if(showBackLayout) {
            ValueAnimator var = ValueAnimator.ofInt(backLayout.getHeight());
            var.setDuration(100);
            var.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    lp.setMargins(0, (Integer) valueAnimator.getAnimatedValue(), 0, 0);
                    frontLayout.setLayoutParams(lp);
                }
            });
            var.start();
        }

        else
            {
                adapter.setGamePageCards(SearchGames(allListGames));

                lp.setMargins(0,0,0,0);
                frontLayout.setLayoutParams(lp);
                TranslateAnimation anim = new TranslateAnimation(
                        0,0,backLayout.getHeight(),0
                );
                anim.setStartOffset(0);
                anim.setDuration(200);
                frontLayout.setAnimation(anim);
            }
    }

     public ArrayList<Game> SearchGames(ArrayList<Game> list) {

         ArrayList<String> selectedGenres = getChipGroupChecked(genrasSearch);
         ArrayList<String> selectedPlatforms = getChipGroupChecked(platformsSearch);
         ArrayList<String> selectedGameModes = getChipGroupChecked(gameModesSearch);

         String gameName = gameNameEd.getText().toString();
         String publisher = publisherEd.getText().toString();
         String developer = developerEd.getText().toString();
         String series = seriesEd.getText().toString();
         if(!selectedGenres.isEmpty())
              searcher.addSearch(new SearchGenre(selectedGenres));
         if(!selectedPlatforms.isEmpty())
             searcher.addSearch(new SearchPlatform(selectedPlatforms));
         if(!selectedGameModes.isEmpty())
             searcher.addSearch(new SearchGameMode(selectedGameModes));
         if(!gameName.isEmpty())
            searcher.addSearch(new SearchGameName(gameName));
         if(!publisher.isEmpty())
             searcher.addSearch(new SearchPublisherGame(publisher));
         if(!developer.isEmpty())
             searcher.addSearch(new SearchDeveleporGame(developer));
         if(!series.isEmpty())
             searcher.addSearch(new SearchSeriesGame(series));


         return searcher.execute(list);
    }

    public ArrayList<String> getChipGroupChecked(ChipGroup group) {
        ArrayList<String> checkedChipsText = new ArrayList<>();
        if (!group.getCheckedChipIds().isEmpty()){
            for (int i = 0; i<group.getChildCount();i++){
                if(((Chip)group.getChildAt(i)).isChecked())
                    checkedChipsText.add(((Chip)group.getChildAt(i)).getText().toString());
            }

        }
        return checkedChipsText;
    }
}
