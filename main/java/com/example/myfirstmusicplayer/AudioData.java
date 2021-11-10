package com.example.myfirstmusicplayer;

public class AudioData {

    private String name;
    private String path;

    public AudioData(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public AudioData() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
