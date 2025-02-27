# WordHunt Solver

Fast Solver for the popular iMessage game WordHunt

# What is Wordhunt?

WordHunt is a game where players are presented with a 4x4 grid of letters and must drag their finger across the board to form as many valid words as possible within a 1-minute-and-30-second time limit. Players earn points based on the number of words they create, with longer words yielding higher scores. At the end of the round, the player with the highest total score wins the game.

# How it Works:

The solver is coded using HTML/CSS/JS for the frontend UI and Java (Spring Boot) for the backend.

The backend implements a Trie data structure to store all valid words and utilizes a depth-first search (DFS) algorithm through the 4x4 grid of letters with a visited array to find all valid words within a board. 

The backend then pushes this data to the frontend through an array, and the valid words are displayed on a grid to the user, sorted from longest to shortest for maximum points.




