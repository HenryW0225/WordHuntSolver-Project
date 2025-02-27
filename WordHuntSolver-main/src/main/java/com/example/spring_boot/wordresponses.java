package com.example.spring_boot;

public class wordresponses {
	String path;
	String word;
	int xpos;
	int ypos;
	public wordresponses(String _path, String _word, int _xpos, int _ypos) {
		path = _path;
		word = _word;
		xpos = _xpos;
		ypos = _ypos;
	}

    // Getters and setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }
}
