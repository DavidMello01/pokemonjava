public interface GameObserver {
    void onPokemonFainted(Pokemon pokemon);
    void onGameOver(Player winner);
    void onTurnSwitch(Player currentPlayer, Player opponentPlayer);
}
