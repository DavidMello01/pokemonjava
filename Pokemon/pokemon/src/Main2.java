import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main2 {
    private static Pokemon pikachu, charmander, squirtle, sandshrew;

    public static void main(String[] args) {
        initializePokemons();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartMenu().setVisible(true);
            }
        });
    }

    private static void initializePokemons() {
        // Inicialize os Pokémon
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

        Attack watergun = new Attack("Water Gun", 30, Type.WATER);
        Attack bubble = new Attack("Bubble", 20, Type.WATER);
        Attack hydropump = new Attack("Hydro Pump", 60, Type.WATER);
        Attack surf = new Attack("Surf", 40, Type.WATER);
        Attack[] squirtleAttacks = {watergun, bubble, hydropump, surf};

        Attack earthquake = new Attack("Earthquake", 50, Type.GROUND);
        Attack dig = new Attack("Dig", 40, Type.GROUND);
        Attack sandattack = new Attack("Sand Attack", 20, Type.GROUND);
        Attack mudslap = new Attack("Mud Slap", 30, Type.GROUND);
        Attack[] sandshrewAttacks = {earthquake, dig, sandattack, mudslap};

        pikachu = new Pokemon.Builder("Pikachu", Type.ELECTRIC)
                .hp(100)
                .attacks(pikachuAttacks)
                .build();

        charmander = new Pokemon.Builder("Charmander", Type.FIRE)
                .hp(100)
                .attacks(charmanderAttacks)
                .build();

        squirtle = new Pokemon.Builder("Squirtle", Type.WATER)
                .hp(100)
                .attacks(squirtleAttacks)
                .build();

        sandshrew = new Pokemon.Builder("Sandshrew", Type.GROUND)
                .hp(100)
                .attacks(sandshrewAttacks)
                .build();
    }

    private static class StartMenu extends JFrame {
        private JTextField playerNameField;

        public StartMenu() {
            setTitle("Pokemon Battle - Choose Your Pokemon");
            setSize(400, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel namePanel = new JPanel();
            namePanel.add(new JLabel("Enter your name:"));
            playerNameField = new JTextField(20);
            namePanel.add(playerNameField);
            add(namePanel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridLayout(2, 2));

            JButton pikachuButton = new JButton("Pikachu");
            pikachuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame(pikachu, getOpponentPokemon());
                }
            });

            JButton charmanderButton = new JButton("Charmander");
            charmanderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame(charmander, getOpponentPokemon());
                }
            });

            JButton squirtleButton = new JButton("Squirtle");
            squirtleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame(squirtle, getOpponentPokemon());
                }
            });

            JButton sandshrewButton = new JButton("Sandshrew");
            sandshrewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame(sandshrew, getOpponentPokemon());
                }
            });

            buttonPanel.add(pikachuButton);
            buttonPanel.add(charmanderButton);
            buttonPanel.add(squirtleButton);
            buttonPanel.add(sandshrewButton);
            add(buttonPanel, BorderLayout.CENTER);
        }

        private Pokemon getOpponentPokemon() {
            // Aqui você pode adicionar lógica para escolher um Pokémon oponente de maneira aleatória ou fixa
            return charmander; // Exemplo: oponente fixo
        }

        private void startGame(Pokemon playerPokemon, Pokemon opponentPokemon) {
            String playerName = playerNameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Player player1 = new Player(playerName, new Pokemon[]{playerPokemon});
            Player player2 = new Player("Gary", new Pokemon[]{opponentPokemon});

            Game game = Game.getInstance(player1, player2); // Singleton instantiation
            new GameGUI(game, playerName).setVisible(true);
            dispose();
        }
    }
}
