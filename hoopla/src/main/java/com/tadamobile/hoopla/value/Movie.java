package com.tadamobile.hoopla.value;

public class Movie {

	public int titleId;
	public String title;
	public int kindId;
	public String kind;
	public String artistName;
	public Boolean demo;
	public Boolean pa;
	public Boolean edited;
	public String artKey;
	
	
	@Override
	public String toString() {
		return "Movie [titleId=" + titleId + ", title=" + title + ", kindId="
				+ kindId + ", kind=" + kind + ", artistName=" + artistName
				+ ", demo=" + demo + ", pa=" + pa + ", edited=" + edited
				+ ", artKey=" + artKey + "]";
	}
	
	

}
