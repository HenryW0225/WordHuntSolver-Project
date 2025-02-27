package com.example.spring_boot;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SolverController {
	
    @PostMapping("/solve")
    public ResponseEntity<ArrayList<wordresponses>> solveWordHunt(@RequestBody String[][] request) {
        char[][] grid = new char[4][4];
    	for(int i = 0; i < 4; i++) {
    		for(int j = 0; j < 4; j++) {
    			grid[i][j] = request[i][j].charAt(0);
    		}
    	}
        // List to hold possible words found in the grid
    	Words board = new Words();
    	board.setBoard(grid);
    	board.findAllWords();
        ArrayList<wordresponses> possibleWords = board.getValidWords();
        //System.out.println(possibleWords.size());
        // Return the response as JSON
        return ResponseEntity.ok(possibleWords);
    }
}