# TicTacToePlus
Tic Tac Toe is a game for two players who take turns marking the spaces in a 3×3 grid.

TicTacToePlus is an evolution of the classic game: you can decide the number of players, the grid size and the tic tac toe number.

![TicTacToePlusPreview](https://user-images.githubusercontent.com/49209517/61174349-1ad41300-a59f-11e9-9e97-2c7a000ebc1b.png)



## Game Modes
In TicTacToePlus you can play in multiplayer in a classic or in a custom game setup or in single-player with two different difficult levels in a classic game setup.

In specific there are the follow modes:

Multiplayer: Classic Game - 3x3 grid, 2 players, tic tac toe number: 3

Multiplayer: Custom Game - custom grid, custom players, custom tic tac toe number

Single-player: Normal - 3x3 grid, normal opponent, tic tac toe number: 3

Single-player: Legend - 3x3 grid, invincible opponent, tic tac toe number: 3



## UI
In TicTacToePlus there are two UI: Simple CLI, where all modes are available, and Simple GUI, where the custom setup modes aren't available.



## Implementation Details
The software architecture implements the Model-View-Controller (MVC) pattern.

TicTacToePlus is fast and powerful in big grids using multithreading tic tac toe checkers (Maybe multitreading is useless in this case, but this code can be used in matrix analysis).



## Run Instructions
Download the last release and run the .bat file on Windows or the .sh file on Linux.



## Inspiration
GUI graphics: https://commons.wikimedia.org/wiki/File:Tic_Tac_Toe.png