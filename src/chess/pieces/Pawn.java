package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	private void toMove(Position auxiliaryPosition, boolean[][] auxiliaryBoard) {
		if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
		}
	}

	private void toMove(Position auxiliaryPosition, Position auxiliaryPositionTwo, boolean[][] auxiliaryBoard) {
		if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)
				&& getBoard().positionExists(auxiliaryPositionTwo) && !getBoard().thereIsAPiece(auxiliaryPositionTwo)
				&& getMoveCount() == 0) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
		}
	}

	private void moveSpecial(Position auxiliaryPosition, boolean[][] auxiliaryBoard) {
		if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
		}
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] auxiliaryBoard = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position auxiliaryPosition = new Position(0, 0);

		// white pawn
		if (getColor() == Color.WHITE) {
			auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn());
			toMove(auxiliaryPosition, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() - 2, position.getColumn());
			Position auxiliaryPositionTwo = new Position(position.getRow() - 1, position.getColumn());
			toMove(auxiliaryPosition, auxiliaryPositionTwo, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
			moveSpecial(auxiliaryPosition, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
			moveSpecial(auxiliaryPosition, auxiliaryBoard);
		} else {
			// black pawn
			auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn());
			toMove(auxiliaryPosition, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() + 2, position.getColumn());
			Position auxiliaryPositionTwo = new Position(position.getRow() + 1, position.getColumn());
			toMove(auxiliaryPosition, auxiliaryPositionTwo, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
			moveSpecial(auxiliaryPosition, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
			moveSpecial(auxiliaryPosition, auxiliaryBoard);
		}
		return auxiliaryBoard;
	}

	@Override
	public String toString() {
		return "P";
	}
}
