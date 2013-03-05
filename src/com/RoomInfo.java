package com;
import java.util.List;
import java.util.ArrayList;
public class RoomInfo {
	private String roomName;
	private int person;
	private List<String> user=new ArrayList<String>();
	public RoomInfo(String name){
		this.roomName=name;
		person=0;
	}
	public RoomInfo(){
		
	}
	public String getName(){
		return this.roomName;
	}
	public void setName(String name){
		this.roomName=name;
	}
	public int getPerson(){
		return this.person;
	}
	public List<String> getUser(){
		return this.user;
	}
	public void addUser(String name){
		++person;
		user.add(name);
	}
	public void removeUser(String name){
		--person;
		user.remove(name);
	}
}
