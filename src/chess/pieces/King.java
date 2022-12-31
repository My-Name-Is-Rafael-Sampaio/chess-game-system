package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);
		return piece == null || piece.getColor() != getColor();
	}

	@Override
	protected void toMove(Position position, boolean[][] tempMatrix) {
		if (getBoard().positionExists(position) && canMove(position)) {
			tempMatrix[position.getRow()][position.getColumn()] = true;
		}
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] temporary = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position auxiliaryPosition = new Position(0, 0);

		// above
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn());
		toMove(auxiliaryPosition, temporary);

		// below
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn());
		toMove(auxiliaryPosition, temporary);

		// left
		auxiliaryPosition.setValues(position.getRow(), position.getColumn() - 1);
		toMove(auxiliaryPosition, temporary);

		// right
		auxiliaryPosition.setValues(position.getRow(), position.getColumn() + 1);
		toMove(auxiliaryPosition, temporary);

		// north-west
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
		toMove(auxiliaryPosition, temporary);

		// north-east
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
		toMove(auxiliaryPosition, temporary);

		// south-west
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
		toMove(auxiliaryPosition, temporary);

		// south-east
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
		toMove(auxiliaryPosition, temporary);

		return temporary;
	}
}