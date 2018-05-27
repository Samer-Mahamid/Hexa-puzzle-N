/**
 * 
 */
package org.hexa.examples;

import java.text.MessageFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Board.
 */
public class BoardTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */

	private static final int DIM = 4;;

	public BoardTest(String testName) {
		super(testName);
		
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(BoardTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public static void testBoardShuffle() {
		Board board = new Board(DIM);
		board.shuffle();
		int oneDim = DIM * DIM;
		int[] array = new int[oneDim];
		for (int i = 0; i < oneDim; ++i) {
			array[i] = 0;
		}
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; ++j) {
				array[board.get(i, j)]++;
			}
		}
		
		for (int i = 0; i < oneDim; ++i) {
			assertTrue(array[i] == 1);
		}
		
		// actually , this may happen in probability (1/(DIM^2)!)
		assertFalse(board.isInOrder());
		board.show();

	}
	
	public static void testGetSpaceIndex() {
		Board board = new Board(DIM);
		board.shuffle();
		int idx = board.getSpaceIndex();
		assertTrue(board.get(idx / DIM, idx % DIM) == 0);
	}
	
	public static void testInit() {
		Board board = new Board(DIM);
		board.init();
		board.show();
		int idx = 0;
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; ++j) {
				if ((i == DIM -1) && (j == DIM -1)) {
					assertTrue(MessageFormat.format("({0},{1})={2}, idx = {3}",i ,j , board.get(i, j), idx),board.get(i, j) == 0);
				} else {
					assertTrue(MessageFormat.format("({0},{1})={2}, idx = {3}",i ,j , board.get(i, j), idx),board.get(i, j) == ++idx);
				}
			}
		}
	}
	
	public static void testIsInOrder() {
		Board board = new Board(DIM);
		board.init();
		assertTrue(board.isInOrder());
	}
	
	public static void testIsLegalIndexes() {
		Board board = new Board(DIM);
		assertTrue(board.IsLegalIndexes(0, 0));
		assertFalse(board.IsLegalIndexes(-1, 0));
		assertFalse(board.IsLegalIndexes(0, -1));
		assertTrue(board.IsLegalIndexes(DIM - 1, DIM - 1));
		assertFalse(board.IsLegalIndexes(DIM, 0));
		assertFalse(board.IsLegalIndexes(0, DIM));
	}
	
	public static void testReplace() {
		Board board = new Board(DIM);
		board.shuffle();
		int a = board.get(0, DIM-1);
		int b = board.get(DIM-1, 0);
		board.replace(0, DIM -1, DIM -1, 0);
		int c = board.get(0, DIM-1);
		int d = board.get(DIM-1, 0);
		assertEquals(a, d);
		assertEquals(b, c);
	}


}
