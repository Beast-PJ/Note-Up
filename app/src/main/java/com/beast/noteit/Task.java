package com.beast.noteit;
public class Task {

    private String task;
    private String details;
    private int difficulty;
    private int xp;
    private String reward;
    private String category;

    public Task(String task, String details, int difficulty, int xp, String reward) {
        this.task = task;
        this.details = details;
        this.difficulty = difficulty;
        this.xp = xp;
        this.reward = reward;
        this.category = category;
    }




    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
