package com.example.searchgameapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.searchgameapp.Classes.Game;
import com.example.searchgameapp.Classes.Profile;
import com.example.searchgameapp.Interfaces.SendDataInterface;

import java.util.ArrayList;

public class GameRecViewAdapter extends  RecyclerView.Adapter<GameRecViewAdapter.ViewHolder> {


    private ArrayList<Game> gamePageCards;
    private ArrayList<Game> allGamePageCards;
    private SendDataInterface sendDataInterface;
    private Profile p;
    public GameRecViewAdapter(ArrayList<Game> gamePCards,SendDataInterface sendDataInterface, Profile p){
        gamePageCards = gamePCards;
        allGamePageCards = new ArrayList<>(gamePageCards);
        this.sendDataInterface = sendDataInterface;
        this.p = p;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.game_info_list,parent,false);

        return new ViewHolder(view,p);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtGameName.setText(gamePageCards.get(position).getName());
        holder.txtGenres.setText(gamePageCards.get(position).getSpecifiedListString(1));
        holder.txtPlatfroms.setText(gamePageCards.get(position).getSpecifiedListString(2));
        holder.txtGaneModes.setText(gamePageCards.get(position).getSpecifiedListString(3));
        if(getGame(holder.txtGameName.getText().toString()).getSeries().equals(""))
            holder.txtSeries.setVisibility(View.INVISIBLE);
        else
            holder.txtSeries.setText("Series: "+getGame(holder.txtGameName.getText().toString()).getSeries());
        if(getGame(holder.txtGameName.getText().toString()).getPhotoUrl() != null)
            Glide.with(holder.gamePic.getContext()).load(getGame(holder.txtGameName.getText().toString()).getPhotoUrl().get(0)).into(holder.gamePic);
        holder.favoriteCBox.setOnCheckedChangeListener(null);


        if(p != null && p.hasGame(gamePageCards.get(position)))
            holder.favoriteCBox.setChecked(true);
        holder.favoriteCBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                sendDataInterface.addFavorite(gamePageCards.get(position),p);
                p.addFavorite(getGame(holder.txtGameName.getText().toString()));
            }
            else{
                sendDataInterface.removeFavorite(gamePageCards.get(position),p);

            }
        });
        holder.shareBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Can't share yet ;) ", Toast.LENGTH_LONG).show();
            }
        });

        holder.gamePageBt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Fragment fragment = GamePageFragment.getInstance(getGame(holder.txtGameName.getText().toString()));
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (gamePageCards != null)
            return gamePageCards.size();
        else
            return 0;
    }

    public void setGamePageCards(ArrayList<Game> gamesPages){
        this.gamePageCards = gamesPages;
        notifyDataSetChanged();
    }
    public Game getGame(String gameName){
        for(Game game: allGamePageCards){
            if(game.getName().equals(gameName))
                return game;
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtGameName, txtPlatfroms, txtGenres, txtGaneModes, txtSeries;
        private CardView parent;
        private Button gamePageBt, shareBt;
        private CheckBox favoriteCBox;
        private ImageView gamePic;
        public ViewHolder(@NonNull View itemView, Profile profile) {
            super(itemView);
            this.txtGameName = itemView.findViewById(R.id.txtGameName);
            this.txtPlatfroms = itemView.findViewById(R.id.txtPlatfroms);
            this.txtGenres = itemView.findViewById(R.id.txtGenres);
            this.txtGaneModes = itemView.findViewById(R.id.txtGameModes);
            this.txtSeries = itemView.findViewById(R.id.txtSeries);
            this.gamePic = itemView.findViewById(R.id.game_image_card);
            this.gamePageBt = itemView.findViewById(R.id.GamePageBtn);
            this.shareBt = itemView.findViewById(R.id.shareBtn);
            this.favoriteCBox = itemView.findViewById(R.id.addFavoriteBtn);
            if (profile != null){
                favoriteCBox.setClickable(true);
                favoriteCBox.setVisibility(View.VISIBLE);

              }
            else {
                favoriteCBox.setClickable(false);
                favoriteCBox.setVisibility(View.INVISIBLE);
            }
            this.parent = itemView.findViewById(R.id.parent);
        }
    }
}
