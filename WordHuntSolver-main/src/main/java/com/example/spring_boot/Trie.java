package com.example.spring_boot;

import com.example.spring_boot.TrieNode;

import java.util.ArrayList;
import java.util.Map;

public class Trie {
    public final TrieNode root;
    public ArrayList<String>val;
    // Constructor to initialize the Trie with an empty root node
    public Trie() {
        root = new TrieNode();
        val = new ArrayList<String>();
    }

    // Method to insert a word into the Trie
    public void insert(String word) {
        TrieNode current = root;
        // Traverse the Trie, adding nodes for each character in the word
        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        // Mark the end of the word
        current.isEndOfWord = true;
    }

    // Method to search for a word in the Trie
    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            TrieNode node = current.children.get(c);
            if (node == null) {
                return false; // Character not found, word does not exist
            }
            current = node;
        }
        // Check if the word exists and is marked as a complete word
        return true;
    }
    public boolean searchWord(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            TrieNode node = current.children.get(c);
            if (node == null) {
                return false; // Character not found, word does not exist
            }
            current = node;
        }
        // Check if the word exists and is marked as a complete word
        return current.isEndOfWord;
    }
    // Method to check if there is any word in the Trie that starts with the given prefix
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            TrieNode node = current.children.get(c);
            if (node == null) {
                return false; // Prefix not found
            }
            current = node;
        }
        return true; // Prefix exists
    }

    // Print all words in the Trie (helper method for debugging)
    public ArrayList<String> printAllWords() {
        printWordsFromNode(root, "");
        return val;
    }
    private void printWordsFromNode(TrieNode node, String prefix) {
        if (node.isEndOfWord) {
            val.add(prefix);
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            printWordsFromNode(entry.getValue(), prefix + entry.getKey());
        }
    }
}