package com;

public class User {
	
	private int alive; //标记用户存活状态 1为活 0为死
	private int info; //用户的身份
	private String username; //用户的名字
	private int cnt; //被投票的票数
	private int ready;
	public User(String username){
		this.alive = 1;
		this.info = -1;
		this.username = username;
		this.cnt = 0;
		this.ready = 0;
	}
	public void reset() {
		this.alive = 1;
		this.info = -1;
		this.cnt = 0;
		this.ready = 0;
	}
	public boolean isAlive() {
		if(this.alive == 1)
			return true;
		else
			return false;
	}
	public void setInfo(int info) {
		this.info = info;
	}
	public void setDeadSatus() {
		this.alive = 0;
	}
	public void setAliveStatus() {
		this.alive = 1;
	}
	public int getInfo() {
		return this.info;
	}
	public String getName() {
		return this.username;
	}
	public void addVote() {
		this.cnt++;
	}
	public int getVote() {
		return this.cnt;
	}
	public int getReady() {
		return this.ready;
	}
	public void setReady() {
		this.ready = 1;
	}
	public void moveReady() {
		this.ready = 0;
	}
}
