package org.hexa.examples;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

	private static final String InstructionMsg = "type U|up D|down R|right L|left to move the empty tile";  
	private static final String IllegalInputMsg = "ERROR: Illegal input";
	private static final String IllegalMoveMsg = "ERROR: Illegal move"; 
	private static final String DoneMsg = "Great Job! you did it in %d moves"; 

	private enum Direction {
		Right, Left, Up, Down
	};

	private Map<String, Direction> _dir;
	Board _board;

	int _moves;

	public App(int dim) {
		_board = new Board(dim);
		_moves = 0;
		initDirectionsMap();

	}

	private void initDirectionsMap() {
		_dir = new HashMap<String, App.Direction>(8);
		_dir.put("L", Direction.Left);
		_dir.put("left", Direction.Left);

		_dir.put("R", Direction.Right);
		_dir.put("right", Direction.Right);

		_dir.put("U", Direction.Up);
		_dir.put("up", Direction.Up);

		_dir.put("D", Direction.Down);
		_dir.put("down", Direction.Down);
	}

	private static void printMessage(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	private void inMoveDisplay() {
		_board.show();
		printMessage(InstructionMsg);
	}

	private boolean isLegalMove(int i, int j) {
		boolean res = true;
		if (!_board.IsLegalIndexes(i, j)) {
			printMessage(IllegalMoveMsg);
			res = false;
		}
		return res;
	}
	
	private void move(int i1, int j1, int i2, int j2) throws IllegalArgumentException {
		_board.replace(i1, j1, i2, j2);
		_moves++;
	}

	private void move(String dir) throws IllegalArgumentException, IllegalStateException {
		if (_dir.get(dir) == null) {
			printMessage(IllegalInputMsg);
			return;
		}
		int idx = _board.getSpaceIndex();
		int spaceI = idx / _board.getDim();
		int spaceJ = idx % _board.getDim();

		switch (_dir.get(dir)) {
		case Up:
			if (isLegalMove(spaceI - 1, spaceJ)) {;
				move(spaceI, spaceJ, spaceI - 1, spaceJ);
			}
			break;
		case Down:
			if (isLegalMove(spaceI + 1, spaceJ)) {;
				move(spaceI, spaceJ, spaceI + 1, spaceJ);
			}
			break;
		case Right:
			if (isLegalMove(spaceI, spaceJ + 1)) {
				move(spaceI, spaceJ, spaceI, spaceJ + 1);
			}
			break;
		case Left:
			if (isLegalMove(spaceI, spaceJ - 1)) {
				move(spaceI, spaceJ, spaceI, spaceJ - 1);
			}
			break;
		default:
			throw new IllegalArgumentException("Something strange happend");
		}
		
	}



	public void runGame(InputStream source) {
		Scanner console = new Scanner(source);
		try {
			_board.shuffle();
			// better solution is to check solvability here. by running algorithm that tries to solve it.
			while (!_board.isInOrder()) {
				inMoveDisplay();
				move(console.next());
			}
			printMessage(DoneMsg, _moves);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		} finally {
			console.close();
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello To puzzle - 15 game!");
		App app = new App(2);
		app.runGame(System.in);
		
	}
}
