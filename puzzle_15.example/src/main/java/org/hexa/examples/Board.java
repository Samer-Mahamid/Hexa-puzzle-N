package org.hexa.examples;

import java.text.MessageFormat;
import java.util.Random;

public class Board {

	private int[][] _board;

	private int _dim;

	public Board(int n) {
		_dim = n;
		_board = new int[n][n];
	}

	/*
	 * separated from shuffle for unitest uses
	 */
	protected void init() {
		for (int i = 0; i < _dim; ++i) {
			for (int j = 0; j < _dim; ++j) {
				_board[i][j] = _dim * i + j + 1;
			}
		}
		_board[_dim - 1][_dim - 1] = 0; 
	}

	public void shuffle() {
		/*
		 * we can use //int[] rand = random.ints(streamSize, randomNumberOrigin, randomNumberBound) 
		 * instead of the algo below. 
		 * the algo below avoids getting the same random value twice
		 * by randomizing on the index , and replacement 
		 */
		init();
		int rand;
		for (int i = 0; i < _dim; ++i) {
			for (int j = 0; j < _dim; ++j) {
				rand = randInt(i * _dim + j, _dim * _dim - 1);
				swap(_board, i, j, (int) rand / _dim, rand % _dim);
			}
		}
	}

	/* for unitest use only */
	protected int get(int i, int j) throws IllegalArgumentException {
		checkArrayBounds(_board, i, j);
		return _board[i][j];
	}

	public int getSpaceIndex() throws IllegalStateException {
		for (int i = 0; i < _dim; ++i) {
			for (int j = 0; j < _dim; ++j) {
				if (_board[i][j] == 0) {
					return i * _dim + j;
				}
			}
		}
		throw new IllegalStateException();
	}

	public void show() {

		for (int[] row : _board) {
			System.out.println();
			for (int i = 0; i < row.length; i++) {
				System.out.print(String.format("%3s", "|"));
				System.out.print(String.format("%3s", row[i] == 0 ? " " : row[i]));
			}
			System.out.print(String.format("%3s", "|"));
			System.out.println();
		}
		System.out.println();

	}

	public boolean IsLegalIndexes(int i, int j) {
		return isInArrayBounds(_board, i, j);
	}

	public int getDim() {
		return _dim;
	}

	public void replace(int i1, int j1, int i2, int j2) throws IllegalArgumentException {
		swap(_board, i1, j1, i2, j2);
	}

	public boolean isInOrder() {
		for (int i = 0; i < _dim; ++i) {
			for (int j = 0; j < _dim; ++j) {
				
				if ((i == _dim - 1) && (j == _dim - 1)) {
					if (_board[i][j] != 0) {
						return false;
					}
				} else {
					if (_board[i][j] != i * _dim + j + 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static int randInt(int min, int max) {
		Random random = new Random();
		int range = (max == Integer.MAX_VALUE) ? Integer.MAX_VALUE : max - min + 1;
		return random.nextInt(range) + min;
	}

	private static boolean isInArrayBounds(int[][] a, int i, int j) {

		return ((i >= 0) && (i < a.length) && (j >= 0) && (j < a[i].length));

	}

	private static void checkArrayBounds(int[][] a, int i, int j) throws IllegalArgumentException {
		if (!isInArrayBounds(a, i, j)) {
			throw new IllegalArgumentException(
					MessageFormat.format("out of {2}X{2} board range ({0},{1})", i, j, a.length));
		}
	}

	private static void swap(int[][] a, int i1, int j1, int i2, int j2) throws IllegalArgumentException {

		checkArrayBounds(a, i1, j1);
		checkArrayBounds(a, i2, j2);

		int t = a[i1][j1];
		a[i1][j1] = a[i2][j2];
		a[i2][j2] = t;
	}
}
