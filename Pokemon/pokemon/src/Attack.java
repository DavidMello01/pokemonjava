public class Attack {
    private String name;
    private int power;
    private Type type;

    public Attack(String name, int power, Type type) {
        this.name = name;
        this.power = power;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public Type getType() {
        return type;
    }
}
