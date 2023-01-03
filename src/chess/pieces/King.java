package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	public King(Board board, Color color) {
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

		// above
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn());
		toMove(auxiliaryPosition, auxiliaryBoard);

		// below
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn());
		toMove(auxiliaryPosition, auxiliaryBoard);

		// left
		auxiliaryPosition.setValues(position.getRow(), position.getColumn() - 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// right
		auxiliaryPosition.setValues(position.getRow(), position.getColumn() + 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// north-west
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// north-east
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// south-west
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		// south-east
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
		toMove(auxiliaryPosition, auxiliaryBoard);

		return auxiliaryBoard;
	}

	@Override
	public String toString() {
		return "K";
	}
}