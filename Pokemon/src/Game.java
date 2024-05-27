import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Player currentPlayer;
    private Player opponentPlayer;
    private boolean attacksRolled = false;
    private Map<Type, Attack[]> attackPool;

    public Game(Player player1, Player player2) {
        this.currentPlayer = player1;
        this.opponentPlayer = player2;
        initializeAttackPool();
    }

    private void initializeAttackPool() {
        attackPool = new HashMap<>();
//tirar os q nao sao do tipo, o pokemon ja nasce c uns atk pre-setado
        attackPool.put(Type.ELECTRIC, new Attack[]{
            new Attack("Tackle", 10, Type.ELECTRIC),
            new Attack("Thunderbolt", 40, Type.ELECTRIC),
            new Attack("Thunder", 60, Type.ELECTRIC),
            new Attack("Quick Attack", 20, Type.ELECTRIC),
            new Attack("Thunder Fang", 30, Type.ELECTRIC)
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
}
