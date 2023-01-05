package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	private void toMove(Position auxiliaryPosition, boolean[][] auxiliaryBoard) {
		if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
		}
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] auxiliaryBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position auxiliaryPosition = new Position(0, 0);

		// north-west
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
			auxiliaryPosition.setValues(auxiliaryPosition.getRow() - 1, auxiliaryPosition.getColumn() - 1);
		}
		toMove(auxiliaryPosition, auxiliaryBoard);

		// north-east
		auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
			auxiliaryPosition.setValues(auxiliaryPosition.getRow() - 1, auxiliaryPosition.getColumn() + 1);
		}
		toMove(auxiliaryPosition, auxiliaryBoard);

		// south-east
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
			auxiliaryPosition.setValues(auxiliaryPosition.getRow() + 1, auxiliaryPosition.getColumn() + 1);
		}
		toMove(auxiliaryPosition, auxiliaryBoard);

		// south-west
		auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
			auxiliaryPosition.setValues(auxiliaryPosition.getRow() + 1, auxiliaryPosition.getColumn() - 1);
		}
		toMove(auxiliaryPosition, auxiliaryBoard);

		return auxiliaryBoard;
	}

	@Override
	public String toString() {
		return "B";
	}
}