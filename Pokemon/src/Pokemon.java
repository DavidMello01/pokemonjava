import javax.swing.JProgressBar;

public class Pokemon {
	 private String name;
	    private int hp;
	    private int maxHp;
	    private Attack[] attacks;
	    private JProgressBar hpBar;

	    public Pokemon(String name, int hp, Attack[] attacks) {
	        this.name = name;
	        this.hp = hp;
	        this.maxHp = hp;
	        this.attacks = attacks;
	        this.hpBar = new JProgressBar(0, maxHp);
	        this.hpBar.setValue(hp);
	        this.hpBar.setStringPainted(true);
	    }

	    public String getName() {
	        return name;
	    }

	    public int getHp() {
	        return hp;
	    }

	    public void takeDamage(int damage) {
	        this.hp -= damage;
	        if (this.hp < 0) {
	            this.hp = 0;
	        }
	        updateHpBar();
	    }

	    public void heal(int amount) {
	        this.hp += amount;
	        if (this.hp > maxHp) {
	            this.hp = maxHp;
	        }
	        updateHpBar();
	    }

	    public boolean isFainted() {
	        return this.hp == 0;
	    }

	    public Attack[] getAttacks() {
	        return attacks;
	    }

	    public JProgressBar getHpBar() {
	        return hpBar;
	    }

	    private void updateHpBar() {
	        hpBar.setValue(hp);
	    }
}
