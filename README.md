# OOP-Sudoku
Tutorial work for the University of Edinburgh Object-Oriented Programming course

# goal
This repository will contain all my solutions to the tutorial exercises for the course "Object-Oriented Programming"
Some parts of the code are in fact templates, downloaded and used (with permission from @vsee) as a template for my own implementations.

# usage
  1) **clone the repository** to your local machine
  2) [optional] **make** any desired **alterations**
  3) **compile** the source code
  4) **launch** the program:
      **java sudoku.Sudoku01 [path|--interactive|--folder]**
      1) **java sudoku.Sudoku01 path_to_sudoku_file**<br>
          the first option is to specify the *absolute* path to a sudoku (.sd file).
          the user will have several options regarding how to handle the specified sudoku
      2) **java sudoku.Sudoku01 --interactive**<br>
            run in interactive mode: will inspect the games folder, and let the user pick from a variety of possibilities
      3) **java sudoku.Sudoku01 --folder**<br>
            print the rank for every eligible sudoku in the games folder
