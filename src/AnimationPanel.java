import javax.swing.*;
import java.awt.*;

public class AnimationPanel extends JPanel {
    private int attackerX = 50;
    private int attackerY = 200;
    private int defenderX = 600;
    private int defenderY = 200;
    private int attackerSize = 100; // Ajuste o tamanho para imagens
    private int defenderSize = 100; // Ajuste o tamanho para imagens

    private State currentState;
    private Game game;

    public AnimationPanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
        currentState = new IdleState(); // Estado inicial é ocioso
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha as barras de HP para o jogador atual
        g.setColor(Color.GREEN);
        g.fillRect(50, 20, game.getCurrentPlayer().getCurrentPokemon().getHpBar().getValue() * 2, 20);
        g.drawRect(50, 20, 200, 20);

        // Desenha as barras de HP para o jogador oponente
        g.setColor(Color.GREEN);
        g.fillRect(600, 20, game.getOpponentPlayer().getCurrentPokemon().getHpBar().getValue() * 2, 20);
        g.drawRect(600, 20, 200, 20);

        // Desenha círculos para os Pokémon
        g.setColor(Color.RED);
        g.fillOval(attackerX, attackerY, attackerSize, attackerSize);
        g.setColor(Color.BLUE);
        g.fillOval(defenderX, defenderY, defenderSize, defenderSize);
    }

    public void animate() {
        currentState.animate(this);
    }

    public void setState(State state) {
        this.currentState = state;
        animate();
    }

    public int getAttackerX() {
        return attackerX;
    }

    public void setAttackerX(int attackerX) {
        this.attackerX = attackerX;
    }

    public int getDefenderX() {
        return defenderX;
    }

    public void setDefenderX(int defenderX) {
        this.defenderX = defenderX;
    }
}
