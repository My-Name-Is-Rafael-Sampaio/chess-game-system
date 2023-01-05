package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);
		return piece == null || piece.getColor() != getColor();
	}

	private void toMove(Position auxiliaryPosition, boolean[][] auxiliaryBoard) {
		if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
		}
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] auxiliaryBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position auxiliaryPosition = new Position(0, 0);

		// first possibility
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() - 2);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// second possibility
		auxiliaryPosition.setValues(position.getRow() - 2, position.getColumn() - 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// third possibility
		auxiliaryPosition.setValues(position.getRow() - 2, position.getColumn() + 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// fourth possibility
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 2);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// fifth possibility
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 2);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// sixth possibility
		auxiliaryPosition.setValues(position.getRow() + 2, position.getColumn() + 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// seventh possibility
		auxiliaryPosition.setValues(position.getRow() + 2, position.getColumn() - 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// eighth possibility
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 2);
		toMove(auxiliaryPosition, auxiliaryBoard);

		return auxiliaryBoard;
	}

	@Override
	public String toString() {
		return "N";
	}
}
