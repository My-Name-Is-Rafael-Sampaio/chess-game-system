package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
	public Rook(Board board, Color color) {
		super(board, color);
	}

	private void opponentHere(Position position, boolean[][] tempMatrix) {
		if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
			tempMatrix[position.getRow()][position.getColumn()] = true;
		}
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] temporary = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position auxiliaryPosition = new Position(0, 0);

		// above
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn());
		while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			temporary[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
			auxiliaryPosition.setRow(auxiliaryPosition.getRow() - 1);
		}
		opponentHere(auxiliaryPosition, temporary);

		// left
		auxiliaryPosition.setValues(position.getRow(), position.getColumn() - 1);
		while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			temporary[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
			auxiliaryPosition.setColumn(auxiliaryPosition.getColumn() - 1);
		}
		opponentHere(auxiliaryPosition, temporary);

		// right
		auxiliaryPosition.setValues(position.getRow(), position.getColumn() + 1);
		while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			temporary[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
			auxiliaryPosition.setColumn(auxiliaryPosition.getColumn() + 1);
		}
		opponentHere(auxiliaryPosition, temporary);

		// below
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn());
		while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			temporary[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
			auxiliaryPosition.setRow(auxiliaryPosition.getRow() + 1);
		}
		opponentHere(auxiliaryPosition, temporary);

		return temporary;
	}

	@Override
	public String toString() {
		return "R";
	}
}