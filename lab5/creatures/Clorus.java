package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;


import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

/**
 * @author EvenHsia
 * @ClassName Clorus.java
 * @Description creature named Clorus
 * @createTime 2019-07-02- 12:59
 */
public class Clorus extends Creature{
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;
    /**
     * Units of energy losing after a MOVE action
     */
    private double MoveEnergyLose=0.03;
    /**
     * Units of energy losing after a STAY action
     */
    private double StayEnergyLose=0.01;
    /**
     * Min energy of a Creature
     */
    private int MinEnergy=0;
    /**
     * fraction of energy to retain when replicating.
     */
    private double repEnergyRetained = 0.5;
    /**
     * fraction of energy to bestow upon offspring.
     */
    private double repEnergyGiven = 0.5;
    /**
     * creates a Clorus with energy equal to e.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a Clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * Clorus should always return red=34,green=0,blue=231
     */
    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }
    /**
     *  If a Clorus attacks another creature,it should gain that creature's energy
     */
    public void attack(Creature c){
        energy+=c.energy();
    }

    /**
     * Plips should lose 0.03 units of energy when moving.
     */
    public void move() {
        energy=energy-MoveEnergyLose;
        if (energy<MinEnergy) energy=MinEnergy;
    }

    /**
     * Plips should lose 0.01 units of energy when staying due to photosynthesis.
     */
    public void stay() {
        energy=energy-StayEnergyLose;
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Clorus replicate() {
        double babyEnergy = energy * repEnergyGiven;
        energy = energy * repEnergyRetained;
        return new Clorus(babyEnergy);
    }

    /**
     * Cloruses should obey exactly the following behavioral rules:
     * 1.If there are no empty squares, the Clorus will STAY (even if there are Plips nearby they could
     * attack since plip squares do not count as empty squares).
     * 2.Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3.Otherwise, if the Clorus has energy greater than or equal to one, it will REPLICATE to a random empty square.
     * 4.Otherwise, the Clorus will MOVE to a random empty square.
     */
    public Action chooseAction(Map<Direction,Occupant> neighbors){
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors=new ArrayDeque<>();
        for (Direction key:neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")){
                emptyNeighbors.addLast(key);
            }
            if (neighbors.get(key).name().equals("plip")){
                plipNeighbors.addLast(key);
            }
        }
        // no empty adjacent space
        if (emptyNeighbors.size()==0) {
            return new Action(Action.ActionType.STAY);
        }
        //Random direction for Rule 2
        //Rule 2
        if (plipNeighbors.size()!=0){
            Direction randomdir=huglife.HugLifeUtils.randomEntry(plipNeighbors);
            return new Action(Action.ActionType.ATTACK,randomdir);
        }
        //Random direction for Rule 3,4
        Direction randomdire=huglife.HugLifeUtils.randomEntry(emptyNeighbors);
        //Rule 3
        if (energy>=1){
            return new Action(Action.ActionType.REPLICATE,randomdire);
        }
        return new Action(Action.ActionType.MOVE,randomdire);
        //Rule 4
    }
}
