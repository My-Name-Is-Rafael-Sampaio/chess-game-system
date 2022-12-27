package boardGame;

public abstract class Piece {
	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}

	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] captureMovements = possibleMoves();
		for (int i = 0; i < captureMovements.length; i++) {
			for (int j = 0; j < captureMovements.length; j++) {
				if (captureMovements[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}