package battleship;

public class Player {
    //attributes
    private final String name;
    BattleArray battleArray;

    //constructor
    Player(String name, BattleArray battleArray) {
        this.name = name;
        this.battleArray = new BattleArray();
    }

    //getters
    public String getName() {
        return name;
    }
    public BattleArray getbattleArray() {
        return battleArray;
    }

}
