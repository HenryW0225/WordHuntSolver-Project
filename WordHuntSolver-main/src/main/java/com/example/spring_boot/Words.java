package com.example.spring_boot;

import java.util.*;
import java.io.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Words {
    private Map<String, List<String>>allWords;
    private Trie allWordsinTree;
    private int arrsize;
    public char[][]board;
    public Trie validWordssearcher;
    public ArrayList<wordresponses>returnvals;
    public ArrayList<wordresponses> getValidWords() {
		Collections.sort(returnvals, (s1, s2) -> Integer.compare(s2.word.length(), s1.word.length()));
    	return returnvals;
    }
    public int getArrSize() {
    	return arrsize;
    }
    public Words() {
    	ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("wordlist.json")) {
            if (inputStream != null) {
                allWords = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<String>>>() {});
            } else {
                System.out.println("File not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        allWordsinTree = new Trie();
        for (Map.Entry<String, List<String>> entry : allWords.entrySet()) {
            List<String> value = entry.getValue();
            for(int i = 0; i < value.size(); i++) {
            	allWordsinTree.insert(value.get(i));
            }
        }
        validWordssearcher = new Trie();
        returnvals = new ArrayList<wordresponses>();
        arrsize = 4;
        board = new char[arrsize][arrsize];
    }
    public void setBoard(char[][]val) {
        for(int i = 0; i < arrsize; i++) {
            for(int j = 0; j < arrsize; j++) {
                board[i][j] = Character.toLowerCase(val[i][j]);
            }
        }
    }
    public void findAllWords() {
        for(int i = 0; i < arrsize; i++) {
            for(int j = 0; j < arrsize; j++) {
            	boolean[][]visited = new boolean[arrsize][arrsize];
                dfs("", "", visited, i, j);
            }
        }
    }
    private void dfs(String sb, String path, boolean[][]visited, int i, int j) {
        if(i < 0 || i >= arrsize || j < 0 || j >= arrsize || visited[i][j] == true || !allWordsinTree.search(sb)) {
            return;
        }
        sb+=board[i][j];
        visited[i][j] = true;
        int[]movex = new int[]{-1,1,0,0, 1, 1, -1, -1};
        int[]movey = new int[]{0,0,1,-1, -1, 1, 1, -1};
        if(sb.length() >= 3 &&  !validWordssearcher.search(sb) && allWordsinTree.searchWord(sb)) {
        	validWordssearcher.insert(sb);
        	returnvals.add(new wordresponses(path, sb, i, j));
        }
        for(int k = 0; k < 8; k++) {
            dfs(sb, path+""+k, visited, i + movex[k], j + movey[k]);
        }
        visited[i][j] = false;
    }
}