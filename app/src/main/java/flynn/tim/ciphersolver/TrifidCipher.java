package flynn.tim.ciphersolver;

import java.io.IOException;

/**
 * Implementation of the Trifid cypher.
 * <p>Author: <a href="http://gplus.to/tzrlk">Peter Cummuskey</a></p>
 */
public class TrifidCipher {

	public static void main(String[] args) throws IOException {
	}

	public static String decode(String encoded, String key, int period) {
		return decode(encoded, createCube(key), period);
	}

	public static String decode(String encoded, char[][][] cube, int period) {
		return null;
	}

	public static String encode(String decoded, String key, int period) {
		return encode(decoded, createCube(key), period);
	}

	public static String encode(String decoded, char[][][] cube, int period) {

		char[] array = decoded.toCharArray();

		int[][] workspace = {
			new int[array.length],
			new int[array.length],
			new int[array.length]
		};

		for (int letter = 0; letter < array.length; letter++) {
			int[] coords = findCoords(cube, array[letter]);

			if (coords == null) {
				workspace[1][letter] = -1;
				workspace[2][letter] = -1;
				workspace[3][letter] = -1;
				continue;
			}

			for (int coord = 0; coord < coords.length; coord++) {
				workspace[coord][letter] = coords[coord];
			}

		}

		char[] encoded = new char[array.length];

		for (int letter = 0; letter < encoded.length; letter++) {

			int column = getCoord(workspace, letter, 0, period);
			int row = getCoord(workspace, letter, 1, period);
			int square = getCoord(workspace, letter, 2, period);

			encoded[letter] = cube[square][row][column];

		}

		return new String(encoded);

	}

	private static int getCoord(int[][] workspace, int letterIndex, int offset, int period) {
		int column = ( letterIndex * 3 + 1 ) % period;
		int row = column % 3;
		return workspace[row][column];
	}

	private static int[] findCoords(char[][][] cube, char target) {

		for (int square = 0; square < 3; square++) {
			for (int row = 0; row < 3; row++) {
				for (int column = 0; column < 3; column++) {

					if (cube[square][row][column] == target) {
						return new int[] { square, row, column };
					}

				}
			}
		}

		return null;

	}

	private static char[][][] createCube(String key) {

		assert key.length() == 3 * 3 * 3;

		char[][][] cube = new char[3][3][3];
		char[] array = key.toCharArray();

		for (int letter = 0; letter < array.length; letter++) {

			int column = letter % 3;
			int row = column % 3;
			int square = row % 3;

			cube[square][row][column] = array[letter];

		}

		return cube;

	}

}
