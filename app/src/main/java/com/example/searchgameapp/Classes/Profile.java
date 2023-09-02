package com.example.searchgameapp.Classes;

import java.util.ArrayList;

public class Profile {

	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private long birthDate;
	private ArrayList<Game> favorites;

	
	public Profile() {
		this.favorites = new ArrayList<>();
	}
	public Profile(String userName, String firstName, String lastName, String email, String password, long birthDate) {
		this(userName,firstName,lastName,email,password,birthDate,new ArrayList<>());
	}
	public Profile(String userName, String firstName, String lastName, String email, String password, long birthDate, ArrayList<Game> favorites) {
		setUserName(userName);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
		setBirthDate(birthDate);
		setFavorites(favorites);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(long birthDate) {
		this.birthDate = birthDate;
	}

	public void setFavorites(ArrayList<Game> favorites) {this.favorites = favorites;}
	public ArrayList<Game> getFavorites() {return favorites;}
	public void addFavorite(Game game){favorites.add(game);}
	public void removeFavorite(Game game){favorites.remove(game);}
	public boolean hasGame(Game game) {	return favorites.contains(game);}
	@Override
	public boolean equals(Object o){
		Profile p = (Profile) o;
		return this.email.equals(p.getEmail()) && this.userName.equals(p.getUserName());
	}
}
