import javax.swing.SwingUtilities;

public class Main2 {
    public static void main(String[] args) {
        Attack tackle = new Attack("Tackle", 10, Type.ELECTRIC);
        Attack thunderbolt = new Attack("Thunderbolt", 40, Type.ELECTRIC);
        Attack thunder = new Attack("Thunder", 60, Type.ELECTRIC);
        Attack quickattack = new Attack("Quick Attack", 20, Type.ELECTRIC);
        Attack[] pikachuAttacks = {tackle, thunderbolt, thunder, quickattack};

        Attack ember = new Attack("Ember", 30, Type.FIRE);
        Attack scratch = new Attack("Scratch", 20, Type.FIRE);
        Attack flamethrower = new Attack("Flamethrower", 40, Type.FIRE);
        Attack firefang = new Attack("Fire Fang", 40, Type.FIRE);
        Attack[] charmanderAttacks = {ember, scratch, flamethrower, firefang};

        Pokemon pikachu = new Pokemon("Pikachu", 100, Type.ELECTRIC, pikachuAttacks);
        Pokemon charmander = new Pokemon("Charmander", 100, Type.FIRE, charmanderAttacks);

        Player player1 = new Player("Ash", new Pokemon[]{pikachu});
        Player player2 = new Player("Gary", new Pokemon[]{charmander});

        Game game = new Game(player1, player2);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameGUI(game).setVisible(true);
            }
        });
    }
}
