import javax.swing.JProgressBar;

public class Pokemon {
    private String name;
    private int hp;
    private int maxHp;
    private Type type;
    private Attack[] attacks;
    private JProgressBar hpBar;

    public Pokemon(String name, int hp, Type type, Attack[] attacks) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.type = type;
        this.attacks = attacks;
        this.hpBar = new JProgressBar(0, hp);
        this.hpBar.setValue(hp);
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public Type getType() {
        return type;
    }

    public Attack[] getAttacks() {
        return attacks;
    }

    public void setAttacks(Attack[] attacks) {
        this.attacks = attacks;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
        hpBar.setValue(hp);
    }

    public void heal(int amount) {
        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        }
        hpBar.setValue(hp);
    }

    public boolean isFainted() {
        return hp == 0;
    }

    public JProgressBar getHpBar() {
        return hpBar;
    }
}
