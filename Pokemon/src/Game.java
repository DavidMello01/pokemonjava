import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    // Instância única da classe
    private static Game instance;

    private Player currentPlayer;
    private Player opponentPlayer;
    private boolean attacksRolled = false;
    private Map<Type, Attack[]> attackPool;
    private List<GameObserver> observers = new ArrayList<>();

    // Construtor privado para evitar instâncias externas
    private Game(Player player1, Player player2) {
        this.currentPlayer = player1;
        this.opponentPlayer = player2;
        initializeAttackPool();
    }

    // Método estático para obter a única instância da classe
    public static synchronized Game getInstance(Player player1, Player player2) {
        if (instance == null) {
            instance = new Game(player1, player2);
        }
        return instance;
    }

    private void initializeAttackPool() {
        attackPool = new HashMap<>();

        attackPool.put(Type.ELECTRIC, new Attack[]{
            new Attack("Tackle", 10, Type.ELECTRIC),
            new Attack("Thunderbolt", 40, Type.ELECTRIC),
            new Attack("Thunder", 60, Type.ELECTRIC),
            new Attack("Quick Attack", 20, Type.ELECTRIC)
        });

        attackPool.put(Type.FIRE, new Attack[]{
            new Attack("Ember", 30, Type.FIRE),
            new Attack("Scratch", 20, Type.FIRE),
            new Attack("Flamethrower", 40, Type.FIRE),
            new Attack("Fire Fang", 40, Type.FIRE)
        });

        attackPool.put(Type.WATER, new Attack[]{
            new Attack("Water Gun", 30, Type.WATER),
            new Attack("Bubble", 20, Type.WATER),
            new Attack("Hydro Pump", 60, Type.WATER),
            new Attack("Surf", 40, Type.WATER)
        });

        attackPool.put(Type.GROUND, new Attack[]{
            new Attack("Earthquake", 50, Type.GROUND),
            new Attack("Dig", 40, Type.GROUND),
            new Attack("Sand Attack", 20, Type.GROUND),
            new Attack("Mud Slap", 30, Type.GROUND)
        });
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void switchTurn() {
        Player temp = currentPlayer;
        currentPlayer = opponentPlayer;
        opponentPlayer = temp;
        notifyTurnSwitch();
    }

    public boolean attacksRolled() {
        return attacksRolled;
    }

    public void rollAttacks() {
        if (!attacksRolled) {
            randomizeAttacks(currentPlayer.getCurrentPokemon());
            randomizeAttacks(opponentPlayer.getCurrentPokemon());
            attacksRolled = true;
        }
    }

    private void randomizeAttacks(Pokemon pokemon) {
        ArrayList<Attack> availableAttacks = new ArrayList<>(List.of(attackPool.get(pokemon.getType())));
        Collections.shuffle(availableAttacks);
        Attack[] newAttacks = availableAttacks.subList(0, 4).toArray(new Attack[0]);
        pokemon.setAttacks(newAttacks);
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyPokemonFainted(Pokemon pokemon) {
        for (GameObserver observer : observers) {
            observer.onPokemonFainted(pokemon);
        }
    }

    private void notifyGameOver(Player winner) {
        for (GameObserver observer : observers) {
            observer.onGameOver(winner);
        }
    }

    private void notifyTurnSwitch() {
        for (GameObserver observer : observers) {
            observer.onTurnSwitch(currentPlayer, opponentPlayer);
        }
    }

    public void performPlayerAttack(int attackIndex) {
        Player currentPlayer = getCurrentPlayer();
        Player opponentPlayer = getOpponentPlayer();
        Attack attack = currentPlayer.getCurrentPokemon().getAttacks()[attackIndex];
        opponentPlayer.getCurrentPokemon().takeDamage(attack.getPower());
        notifyTurnSwitch();

        if (opponentPlayer.getCurrentPokemon().isFainted()) {
            notifyPokemonFainted(opponentPlayer.getCurrentPokemon());
            if (!opponentPlayer.hasAvailablePokemon()) {
                notifyGameOver(currentPlayer);
            }
        } else {
            switchTurn();
        }
    }

    public void performOpponentAttack(int attackIndex) {
        Player opponentPlayer = getOpponentPlayer();
        Player currentPlayer = getCurrentPlayer();
        Attack attack = opponentPlayer.getCurrentPokemon().getAttacks()[attackIndex];
        currentPlayer.getCurrentPokemon().takeDamage(attack.getPower());
        notifyTurnSwitch();

        if (currentPlayer.getCurrentPokemon().isFainted()) {
            notifyPokemonFainted(currentPlayer.getCurrentPokemon());
            if (!currentPlayer.hasAvailablePokemon()) {
                notifyGameOver(opponentPlayer);
            }
        } else {
            switchTurn();
        }
    }

    public void performOpponentHeal() {
        Player opponentPlayer = getOpponentPlayer();
        Pokemon currentPokemon = opponentPlayer.getCurrentPokemon();
        currentPokemon.heal(20); // Curar 20 pontos de vida
        notifyTurnSwitch();
        switchTurn();
    }
}
