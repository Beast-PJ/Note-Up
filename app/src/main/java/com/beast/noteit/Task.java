package com.beast.noteit;

public class Task{
    private String title;
    private String description;
    private Level level;
    private boolean completed;

    public Task(String title, String description, Level level) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

public enum Level {
    EASY(10),
    MEDIUM(20),
    HARD(30);

    private int xp;

    Level(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }
}