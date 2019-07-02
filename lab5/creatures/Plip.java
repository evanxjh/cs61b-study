package creatures;


import static huglife.HugLifeUtils.*;

import edu.princeton.cs.algs4.StdRandom;
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
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

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
    private double MoveEnergyLose=0.15;
    /**
     * Units of energy gaining after a STAY action
     */
    private double StayEnergyGain=0.2;
    /**
     * Max energy of a Creature
     */
    private int MaxEnergy=2;
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
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        if (e>MaxEnergy){
            energy=MaxEnergy;
        }else if (e<MinEnergy){
            energy=MinEnergy;
        }else  energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        r = 99;
        b = 76;
        g =(int) (96* energy +63);
        return color(r, g, b);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        energy=energy-MoveEnergyLose;
        if (energy<MinEnergy) energy=MinEnergy;
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        energy=energy+StayEnergyGain;
        if (energy>MaxEnergy) energy=MaxEnergy;
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        double babyEnergy = energy * repEnergyGiven;
        energy = energy * repEnergyRetained;
        return new Plip(babyEnergy);
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        boolean anyClorus = false;
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        for (Direction key:neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")){
                emptyNeighbors.addLast(key);
            }
            if (neighbors.get(key).name().equals("clorus")){
                anyClorus=true;
            }
        }
        // no empty adjacent space
        if (emptyNeighbors.size()==0) {
            return new Action(Action.ActionType.STAY);
        }
        // Random direction for Rule 2,3
        Direction randomdir=randomEntry(emptyNeighbors);
        // Rule 2
        if (energy>=1){
            return new Action(Action.ActionType.REPLICATE,randomdir);
        }

        // Rule 3
        double pro= StdRandom.uniform();
        if (pro<0.5 && anyClorus) {
            return new Action(Action.ActionType.MOVE,randomdir);
        }

        // Rule 4
        return new Action(Action.ActionType.STAY);
    }
}
