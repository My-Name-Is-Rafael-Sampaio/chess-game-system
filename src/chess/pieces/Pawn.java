package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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

	private void specialMoveOne(Position auxiliaryPosition, boolean[][] auxiliaryBoard) {
		if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
			auxiliaryBoard[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
		}
	}

	private void specialMoveTwo(boolean[][] auxiliaryBoard) {
		if (getColor() == Color.WHITE) {
			Position leftPosition = new Position(position.getRow(), position.getColumn() - 1);
			if (getBoard().positionExists(leftPosition) && isThereOpponentPiece(leftPosition)
					&& getBoard().piece(leftPosition) == this.chessMatch.getEnPassantVulnerable()) {
				auxiliaryBoard[leftPosition.getRow() - 1][leftPosition.getColumn()] = true;
			}
			Position rightPosition = new Position(position.getRow(), position.getColumn() + 1);
			if (getBoard().positionExists(rightPosition) && isThereOpponentPiece(rightPosition)
					&& getBoard().piece(rightPosition) == this.chessMatch.getEnPassantVulnerable()) {
				auxiliaryBoard[rightPosition.getRow() - 1][rightPosition.getColumn()] = true;
			}
		} else {
			Position leftPosition = new Position(position.getRow(), position.getColumn() - 1);
			if (getBoard().positionExists(leftPosition) && isThereOpponentPiece(leftPosition)
					&& getBoard().piece(leftPosition) == this.chessMatch.getEnPassantVulnerable()) {
				auxiliaryBoard[leftPosition.getRow() + 1][leftPosition.getColumn()] = true;
			}
			Position rightPosition = new Position(position.getRow(), position.getColumn() + 1);
			if (getBoard().positionExists(rightPosition) && isThereOpponentPiece(rightPosition)
					&& getBoard().piece(rightPosition) == chessMatch.getEnPassantVulnerable()) {
				auxiliaryBoard[rightPosition.getRow() + 1][rightPosition.getColumn()] = true;
			}
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
			specialMoveOne(auxiliaryPosition, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
			specialMoveOne(auxiliaryPosition, auxiliaryBoard);

			// special move en passant
			if (position.getRow() == 3) {
				specialMoveTwo(auxiliaryBoard);
			}
		} else {
			// black pawn
			auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn());
			toMove(auxiliaryPosition, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() + 2, position.getColumn());
			Position auxiliaryPositionTwo = new Position(position.getRow() + 1, position.getColumn());
			toMove(auxiliaryPosition, auxiliaryPositionTwo, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
			specialMoveOne(auxiliaryPosition, auxiliaryBoard);

			auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
			specialMoveOne(auxiliaryPosition, auxiliaryBoard);

			// special move en passant
			if (position.getRow() == 4) {
				specialMoveTwo(auxiliaryBoard);
			}
		}
		return auxiliaryBoard;
	}

	@Override
	public String toString() {
		return "P";
	}
}
