package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	private boolean testRookCastling(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);
		return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
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

	private void specialMove(boolean[][] auxiliaryBoard) {
		// castling to the right
		Position rookPositionRight = new Position(position.getRow(), position.getColumn() + 3);
		if (testRookCastling(rookPositionRight)) {
			Position rightAxillaryPositionOne = new Position(position.getRow(), position.getColumn() + 1);
			Position rightAxillaryPositionTwo = new Position(position.getRow(), position.getColumn() + 2);
			if (getBoard().piece(rightAxillaryPositionOne) == null
					&& getBoard().piece(rightAxillaryPositionTwo) == null) {
				auxiliaryBoard[position.getRow()][position.getColumn() + 2] = true;
			}
		}
		// castling to the left
		Position rookPositionLeft = new Position(position.getRow(), position.getColumn() - 4);
		if (testRookCastling(rookPositionLeft)) {
			Position leftAxillaryPositionOne = new Position(position.getRow(), position.getColumn() - 1);
			Position leftAxillaryPositionTwo = new Position(position.getRow(), position.getColumn() - 2);
			Position leftAxillaryPositionThree = new Position(position.getRow(), position.getColumn() - 3);
			if (getBoard().piece(leftAxillaryPositionOne) == null && getBoard().piece(leftAxillaryPositionTwo) == null
					&& getBoard().piece(leftAxillaryPositionThree) == null) {
				auxiliaryBoard[position.getRow()][position.getColumn() - 2] = true;
			}
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

		// special move castling
		if (getMoveCount() == 0 && !this.chessMatch.getCheck()) {
			specialMove(auxiliaryBoard);
		}

		return auxiliaryBoard;
	}

	@Override
	public String toString() {
		return "K";
	}
}