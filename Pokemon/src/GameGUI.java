import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private Game game;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel currentPokemonLabel;
    private JTextArea battleLog;
    private JButton[] attackButtons;
    private JButton rollAttacksButton;
    private JPanel hpPanel;

    public GameGUI(Game game) {
        this.game = game;
        setupUI();
    }

    private void setupUI() {
        setTitle("Pokemon Battle");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        player1Label = new JLabel("Player 1: " + game.getCurrentPlayer().getName());
        player2Label = new JLabel("Player 2: " + game.getOpponentPlayer().getName());
        topPanel.add(player1Label);
        topPanel.add(player2Label);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        currentPokemonLabel = new JLabel("Current Pokemon: " + game.getCurrentPlayer().getCurrentPokemon().getName());
        centerPanel.add(currentPokemonLabel, BorderLayout.NORTH);

        battleLog = new JTextArea();
        battleLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(battleLog);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        hpPanel = new JPanel(new GridLayout(1, 2));
        hpPanel.add(game.getCurrentPlayer().getCurrentPokemon().getHpBar());
        hpPanel.add(game.getOpponentPlayer().getCurrentPokemon().getHpBar());
        centerPanel.add(hpPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5));
        attackButtons = new JButton[4];
        for (int i = 0; i < attackButtons.length; i++) {
            attackButtons[i] = new JButton();
            final int attackIndex = i;
            attackButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performAttack(attackIndex);
                }
            });
            bottomPanel.add(attackButtons[i]);
        }

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
        bottomPanel.add(rollAttacksButton);

        add(bottomPanel, BorderLayout.SOUTH);

        updateUI();
    }

    private void performAttack(int attackIndex) {
        Player currentPlayer = game.getCurrentPlayer();
        Player opponentPlayer = game.getOpponentPlayer();
        Attack attack = currentPlayer.getCurrentPokemon().getAttacks()[attackIndex];
        battleLog.append(currentPlayer.getName() + " used " + attack.getName() + "!\n");
        opponentPlayer.getCurrentPokemon().takeDamage(attack.getPower());
        battleLog.append(opponentPlayer.getCurrentPokemon().getName() + " took " + attack.getPower() + " damage!\n");

        if (opponentPlayer.getCurrentPokemon().isFainted()) {
            battleLog.append(opponentPlayer.getCurrentPokemon().getName() + " fainted!\n");
            if (!opponentPlayer.hasAvailablePokemon()) {
                battleLog.append(currentPlayer.getName() + " wins!\n");
            }
        }

        game.switchTurn();
        updateUI();
    }

    private void updateUI() {
        Player currentPlayer = game.getCurrentPlayer();
        currentPokemonLabel.setText("Current Pokemon: " + currentPlayer.getCurrentPokemon().getName());
        Attack[] attacks = currentPlayer.getCurrentPokemon().getAttacks();
        for (int i = 0; i < attackButtons.length; i++) {
            if (i < attacks.length) {
                attackButtons[i].setText(attacks[i].getName());
                attackButtons[i].setEnabled(true);
            } else {
                attackButtons[i].setText("");
                attackButtons[i].setEnabled(false);
            }
        }

        // Update HP bars
        hpPanel.removeAll();
        hpPanel.add(game.getCurrentPlayer().getCurrentPokemon().getHpBar());
        hpPanel.add(game.getOpponentPlayer().getCurrentPokemon().getHpBar());
        hpPanel.revalidate();
        hpPanel.repaint();
    }       
}
