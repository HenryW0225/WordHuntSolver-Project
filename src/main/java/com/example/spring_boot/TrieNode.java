package com.example.spring_boot;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    // Map to hold children TrieNodes
    Map<Character, TrieNode> children = new HashMap<>();
    // Boolean flag to mark the end of a word
    boolean isEndOfWord = false;
}