package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
	private List<ChessPiece> capturedPieces = new ArrayList<>();
	private boolean check;
	private boolean checkMate;

	public ChessMatch() {
		this.board = new Board(8, 8);
		this.turn = 1;
		this.currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[this.board.getRows()][this.board.getColumns()];
		for (int i = 0; i < this.board.getRows(); i++) {
			for (int j = 0; j < this.board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) this.board.piece(i, j);
			}
		}
		return mat;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
		this.piecesOnTheBoard.add(piece);
	}

	private void validateSourcePosition(Position posistion) {
		if (!this.board.thereIsAPiece(posistion)) {
			throw new ChessException("There is no piece on source position");
		}
		if (this.currentPlayer != ((ChessPiece) this.board.piece(posistion)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!this.board.piece(posistion).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!this.board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return this.board.piece(position).possibleMoves();
	}

	private void makeKingSpecialMove(ChessPiece piece, Position source, Position target) {
		// castling king side rook
		if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
			Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) this.board.removePiece(rookSource);
			this.board.placePiece(rook, rookTarget);
			rook.increaseMoveCount();
		}

		// castling queen side rook
		if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
			Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) this.board.removePiece(rookSource);
			this.board.placePiece(rook, rookTarget);
			rook.increaseMoveCount();
		}
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece piece = (ChessPiece) this.board.removePiece(source);
		piece.increaseMoveCount();
		Piece capturedPiece = this.board.removePiece(target);
		this.board.placePiece(piece, target);

		if (capturedPiece != null) {
			this.piecesOnTheBoard.remove((ChessPiece) capturedPiece);
			this.capturedPieces.add((ChessPiece) capturedPiece);
		}

		makeKingSpecialMove(piece, source, target);

		return capturedPiece;
	}

	private void undoKingSpecialMove(ChessPiece piece, Position source, Position target) {
		// castling king side rook
		if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
			Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) this.board.removePiece(rookTarget);
			this.board.placePiece(rook, rookSource);
			rook.decreaseMoveCount();
		}

		// castling queen side rook
		if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
			Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) this.board.removePiece(rookTarget);
			this.board.placePiece(rook, rookSource);
			rook.decreaseMoveCount();
		}
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece piece = (ChessPiece) this.board.removePiece(target);
		piece.decreaseMoveCount();
		this.board.placePiece(piece, source);

		if (capturedPiece != null) {
			this.board.placePiece(capturedPiece, target);
			this.capturedPieces.remove((ChessPiece) capturedPiece);
			this.piecesOnTheBoard.add((ChessPiece) capturedPiece);
		}

		undoKingSpecialMove(piece, source, target);
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> listPieces = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece piece : listPieces) {
			if (piece instanceof King) {
				return (ChessPiece) piece;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = this.piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece piece : opponentPieces) {
			boolean[][] possibleMoves = piece.possibleMoves();
			if (possibleMoves[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> listPieces = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece piece : listPieces) {
			boolean[][] possibleMoves = piece.possibleMoves();
			for (int i = 0; i < this.board.getRows(); i++) {
				for (int j = 0; j < this.board.getColumns(); j++) {
					if (possibleMoves[i][j]) {
						Position source = ((ChessPiece) piece).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece movedPiece = makeMove(source, target);
						boolean auxiliaryTestCheck = testCheck(color);
						undoMove(source, target, movedPiece);
						if (!auxiliaryTestCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void nextTurn() {
		this.turn++;
		this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(this.currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}

		this.check = (testCheck(opponent(this.currentPlayer))) ? true : false;

		if (testCheckMate(opponent(this.currentPlayer))) {
			this.checkMate = true;
		} else {
			nextTurn();
		}

		return (ChessPiece) capturedPiece;
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(this.board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(this.board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(this.board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(this.board, Color.WHITE));
		placeNewPiece('e', 1, new King(this.board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(this.board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(this.board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(this.board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('f', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(this.board, Color.WHITE));

		placeNewPiece('a', 8, new Rook(this.board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(this.board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(this.board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(this.board, Color.BLACK));
		placeNewPiece('e', 8, new King(this.board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(this.board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(this.board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(this.board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('f', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('h', 7, new Pawn(this.board, Color.BLACK));
	}
}