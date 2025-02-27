package com.example.spring_boot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		Words a = new Words();
		int arrsize = a.getArrSize();
		char[][]board = new char[arrsize][arrsize];
		File file = new File("src/main/resources/grid.txt");
		int i = 0;
		try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for(int j = 0; j < arrsize; j++) {
                	board[i][j] = line.charAt(j);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
		a.setBoard(board);
		a.findAllWords();
		ArrayList<wordresponses>vals = a.getValidWords();
		/*for(int k = 0; k < vals.size(); k++) {
			System.out.println(vals.get(k).path + " " + vals.get(k).word + " " + vals.get(k).xpos + " " + vals.get(k).ypos);
		}*/
	}

}
