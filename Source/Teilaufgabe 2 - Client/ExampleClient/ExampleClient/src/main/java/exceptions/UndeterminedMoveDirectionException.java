package exceptions;

public class UndeterminedMoveDirectionException extends RuntimeException{

		private static final long serialVersionUID = 1L;

		public UndeterminedMoveDirectionException(String info) {
			super(info);
		}
}
