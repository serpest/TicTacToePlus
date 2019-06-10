package model.players;

import model.TicTacToeGame;
import model.base.Point;
import model.exceptions.GridSizeException;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.MaximumPlayerNumberExceededException;

/**
 * It's an an automatic player.
 */
public abstract class VirtualPlayer extends Player {

	/**
	 * The reference of the <code>TicTacToeGame</code> instance.
	 */
	private TicTacToeGame game;
	public TicTacToeGame getGame() {
		return game;
	}
	/**
	 * @param game the game
	 * @throws GridSizeException if the size of the grid isn't 3x3 
	 * @throws InvalidNumberOfPlayersException if the players aren't 2
	 */
	public void setGame(TicTacToeGame game) throws GridSizeException, InvalidNumberOfPlayersException {
		if (game.getGrid().getContent().length != 3 || game.getGrid().getContent()[0].length != 3) {
			throw new GridSizeException();
		}
		if (game.getPlayers().length != 2) {
			throw new InvalidNumberOfPlayersException();
		}
		this.game = game;
	}



	/**
	 * @return the new point selected by the <code>VirtualPlayer</code> automatically.
	 */
	public abstract Point getNewPawnPoint();



	/**
	 * It creates a player with a custom name, but a fixed pawn.
	 * 
	 * @param name the player's name
	 * @throws MaximumPlayerNumberExceededException if the players are too many for the pawn fixed values
	 */
	public VirtualPlayer(String name) throws MaximumPlayerNumberExceededException {
		super(name);
	}

	/**
	 * It creates a player with default name and pawn.
	 * 
	 * @param nPLayer the number of the player (from 0)
	 */
	public VirtualPlayer(int nPLayer) {
		super(nPLayer);
	}



}
