import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameGUI extends JFrame implements GameObserver {
    private Game game;
    private JLabel player1Label;
    private JLabel player2Label;
    private JTextArea battleLog;
    private JButton[] attackButtons;
    private JButton rollAttacksButton;
    private JButton usePotionButton;
    private JButton exitButton;
    private AnimationPanel animationPanel;
    private String playerName;

    public GameGUI(Game game, String playerName) {
        this.game = game;
        this.playerName = playerName;
        game.addObserver(this);
        setupUI();
    }

    private void setupUI() {
        setTitle("Pokemon Battle");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        player1Label = new JLabel("Player 1: " + playerName);
        player2Label = new JLabel("Player 2: " + game.getOpponentPlayer().getName());
        topPanel.add(player1Label);
        topPanel.add(player2Label);
        add(topPanel, BorderLayout.NORTH);

        animationPanel = new AnimationPanel(game);
        add(animationPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // Panel for attack buttons
        JPanel attackPanel = new JPanel(new GridLayout(2, 2));
        attackButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            attackButtons[i] = new JButton();
            final int attackIndex = i;
            attackButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.performPlayerAttack(attackIndex);
                    animationPanel.setState(new AttackingState()); // Muda o estado para Atacando
                }
            });
            attackPanel.add(attackButtons[i]);
        }
        bottomPanel.add(attackPanel);

        // Panel for other actions
        JPanel actionPanel = new JPanel(new GridLayout(3, 1));
        rollAttacksButton = new JButton("Rolar Ataques");
        rollAttacksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!game.attacksRolled()) {
                    game.rollAttacks();
                    updateUI();
                    rollAttacksButton.setEnabled(false);
                }
            }
        });
        actionPanel.add(rollAttacksButton);

        usePotionButton = new JButton("Poções");
        usePotionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usePotion();
            }
        });
        actionPanel.add(usePotionButton);

        exitButton = new JButton("Sair Do Jogo");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        actionPanel.add(exitButton);

        bottomPanel.add(actionPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Log panel
        JPanel logPanel = new JPanel(new BorderLayout());
        battleLog = new JTextArea();
        battleLog.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(battleLog);
        logPanel.add(logScrollPane, BorderLayout.CENTER);
        logPanel.setPreferredSize(new Dimension(200, getHeight()));
        add(logPanel, BorderLayout.EAST);

        updateUI();
    }

    private void usePotion() {
        Player currentPlayer = game.getCurrentPlayer();
        Pokemon currentPokemon = currentPlayer.getCurrentPokemon();
        currentPokemon.heal(20); // Curar 20 pontos de vida
        battleLog.append(playerName + " usou uma poção! " + currentPokemon.getName() + " foi curado!\n");
        updateUI();
    }

    private void updateUI() {
        Player currentPlayer = game.getCurrentPlayer();
        Attack[] attacks = currentPlayer.getCurrentPokemon().getAttacks();
        for (int i = 0; i < attackButtons.length; i++) {
            if (i < attacks.length) {
                attackButtons[i].setText(attacks[i].getName());
                attackButtons[i].setEnabled(currentPlayer.getName().equals(playerName)); // Habilita os botões apenas no turno do jogador
            } else {
                attackButtons[i].setText("");
                attackButtons[i].setEnabled(false);
            }
        }

        rollAttacksButton.setEnabled(!game.attacksRolled() && currentPlayer.getName().equals(playerName));
        usePotionButton.setEnabled(currentPlayer.getName().equals(playerName));
        animationPanel.repaint();
    }

    @Override
    public void onPokemonFainted(Pokemon pokemon) {
        battleLog.append(pokemon.getName() + " fainted!\n");
    }

    @Override
    public void onGameOver(Player winner) {
        battleLog.append(winner.getName() + " wins!\n");
        disableAllButtons();
        showVictoryMessage(winner);
    }

    @Override
    public void onTurnSwitch(Player currentPlayer, Player opponentPlayer) {
        battleLog.append(currentPlayer.getName() + " está atacando!\n");
    
        if (currentPlayer == game.getOpponentPlayer()) {
            animationPanel.setState(new AttackingState());
            performRandomAction();
            updateUI();
            
            battleLog.append("Agora é a vez de " + opponentPlayer.getName() + "!\n");
            game.switchTurn();
        }
        
          // Assegura que os jogadores são alternados após cada turno
    }

    private void performRandomAction() {
        Player opponentPlayer = game.getOpponentPlayer();
        Random random = new Random();
        if (random.nextBoolean()) {
            int attackIndex = random.nextInt(opponentPlayer.getCurrentPokemon().getAttacks().length);
            game.performOpponentAttack(attackIndex);
        } else {
            game.performOpponentHeal();
            battleLog.append(opponentPlayer.getName() + " usou uma poção! ");
        }
        updateUI(); // Atualiza a UI após a ação do oponente
    }

    private void disableAllButtons() {
        for (JButton button : attackButtons) {
            button.setEnabled(false);
        }
        rollAttacksButton.setEnabled(false);
        usePotionButton.setEnabled(false);
    }

    private void showVictoryMessage(Player winner) {
        JOptionPane.showMessageDialog(this, winner.getName() + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
