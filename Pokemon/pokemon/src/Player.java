
public class Player {
    private String name;
    private Pokemon[] pokemons;
    private int currentPokemonIndex;

    public Player(String name, Pokemon[] pokemons) {
        this.name = name;
        this.pokemons = pokemons;
        this.currentPokemonIndex = 0;
    }

    public String getName() {
        return name;
    }

    public Pokemon getCurrentPokemon() {
        return pokemons[currentPokemonIndex];
    }

    public void switchPokemon(int index) {
        if (index >= 0 && index < pokemons.length) {
            currentPokemonIndex = index;
        }
    }

    public boolean hasAvailablePokemon() {
        for (Pokemon pokemon : pokemons) {
            if (!pokemon.isFainted()) {
                return true;
            }
        }
        return false;
    }
}
