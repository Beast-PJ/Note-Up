package com.beast.noteit;

public class Task {
    private String task;
    private String details;
    private int difficulty; // 1: Easy, 2: Medium, 3: Hard
    private int xp;
    private String reward;
    private boolean isCompleted;

    public Task(String task, String details, int difficulty, int xp, String reward) {
        this.task = task;
        this.details = details;
        this.difficulty = difficulty;
        this.xp = xp;
        this.reward = reward;
        this.isCompleted = false;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
