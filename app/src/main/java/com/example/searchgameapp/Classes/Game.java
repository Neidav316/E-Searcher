package com.example.searchgameapp.Classes;

import java.util.ArrayList;
public  class Game {

	
	private String name;
	private long releaseDate;
	private String publishCompany;
	private String developerCompany;
	private int ageRestriction;
	private ArrayList<String> genres;
	private ArrayList<String> platforms;
	private ArrayList<String> gameModes;
	private String series;
	private String trailer;
	private ArrayList<String> photoUrl;
	private String info;

	public Game(){}

	public Game(String name, long releaseDate, String publishCompany, String developerCompany,
				int ageRestriction, ArrayList<String> genres, ArrayList<String> platforms,
				ArrayList<String> gameModes, String series) {

		this.name = name;
		this.releaseDate = releaseDate;
		this.publishCompany = publishCompany;
		this.developerCompany = developerCompany;
		this.ageRestriction = ageRestriction;
		this.genres = genres;
		this.platforms = platforms;
		this.gameModes = gameModes;
		this.series = series;
	}
	public Game(String pName, long pReleaseDate, String pPublishCompany, String pDeveloperCompany, int pAgeRestriction,
				ArrayList<String> pGenres, ArrayList<String> pPlatforms, ArrayList<String> pGameModes, String pSeries, String pTrailer,
				ArrayList<String> pPhoto, String pInfo) {

		setName(pName);
		setReleaseDate(pReleaseDate);
		setPublishCompany(pPublishCompany);
		setDeveloperCompany(pDeveloperCompany);
		setAgeRestriction(pAgeRestriction);
		setGenres(pGenres);
		setPlatforms(pPlatforms);
		setGameModes(pGameModes);
		setSeries(pSeries);
		setTrailer(pTrailer);
		setPhotoUrl(pPhoto);
		setInfo(pInfo);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(long releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getPublishCompany() {
		return publishCompany;
	}

	public void setPublishCompany(String publishCompany) {
		this.publishCompany = publishCompany;
	}

	public String getDeveloperCompany() {
		return developerCompany;
	}

	public void setDeveloperCompany(String developerCompany) {
		this.developerCompany = developerCompany;
	}

	public int getAgeRestriction() {
		return ageRestriction;
	}

	public void setAgeRestriction(int ageRestriction) {
		this.ageRestriction = ageRestriction;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	public ArrayList<String> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(ArrayList<String> platforms) {
		this.platforms = platforms;
	}

	public ArrayList<String> getGameModes() {
		return gameModes;
	}

	public void setGameModes(ArrayList<String> gameModes) {
		this.gameModes = gameModes;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public ArrayList<String> getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(ArrayList<String> photoUrl) {
		if(photoUrl != null)
			this.photoUrl = photoUrl;
		else
			this.photoUrl = new ArrayList<>();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}


	public String getSpecifiedListString(int i){
		ArrayList<String> chosenList = null;
		String type = "";
		switch(i){
			case 1:
				chosenList = genres;
				type = "Genres";
				break;
			case 2:
				chosenList = platforms;
				type = "Platforms";
				break;
			case 3:
				chosenList = gameModes;
				type = "GameModes";
				break;
		}
		return 	getToStringOfArray(chosenList,type);
	}

	public String getToStringOfArray(ArrayList<String> list, String type){

		StringBuilder strbuf = new StringBuilder(type+": ");
		if(list != null) {
			for (int i = 0; i < 3 && i< list.size(); i++) {
				strbuf.append(list.get(i) + (i != 2 ? ", " : ""));
			}
			if (list.size() >= 3)
				strbuf.append("...");
		}
		return strbuf.toString();
	}
	@Override
	public boolean equals(Object o){
		Game g = (Game) o;
		return this.getName().equals(g.getName());
	}
}
