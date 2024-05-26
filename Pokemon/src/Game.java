public class Game {
    private Player player1;
    private Player player2;
    private boolean player1Turn;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Turn = true; // Player 1 starts first
    }

    public Player getCurrentPlayer() {
        return player1Turn ? player1 : player2;
    }

    public Player getOpponentPlayer() {
        return player1Turn ? player2 : player1;
    }

    public void switchTurn() {
        player1Turn = !player1Turn;
    }

    public boolean isGameOver() {
        return !player1.hasAvailablePokemon() || !player2.hasAvailablePokemon();
    }

    public Player getWinner() {
        if (!player1.hasAvailablePokemon()) {
            return player2;
        } else if (!player2.hasAvailablePokemon()) {
            return player1;
        }
        return null; // No winner yet
    }
}
