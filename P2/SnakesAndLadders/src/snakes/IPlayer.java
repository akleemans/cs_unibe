package snakes;

public interface IPlayer {

	public abstract void joinGame(Game game);

	public abstract int position();

	public abstract void moveForward(int moves);

	public abstract void moveTo(ISquare destination);

	public abstract ISquare square();

	public abstract boolean wins();

}