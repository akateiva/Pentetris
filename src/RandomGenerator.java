/**
 * Authors: Kalli Buchanan
 *		   Kamil Bujnarowski
 *		   Aleksas Kateiva
 *		   Daniel Mihăltan
 *		   Alexander Steckelberg
 *		   Carla Wrede
 *
 * Version: 2
 * 
 * Date:    8 December 2015
 *
 * Summary: Random Generator
 * 			SEE: http://tetris.wikia.com/wiki/Random_Generator
 *
 * 			QUOTE:
 * 			Random Generator generates a sequence of all seven one-sided tetrominoes (I, J, L, O, S, T, Z) permuted randomly,
 * 			as if they were drawn from a bag. Then it deals all seven tetrominoes to the piece sequence before generating another bag.
 * 			There are 7!, or 5,040, permutations of seven elements, and it is believed that Tetris assigns a nearly equal probability to each of these,
 * 			making it much less likely that the player will get an obscenely long run without a desired tetromino.
 *
 * 			So this class is pretty much this, except with pentominoes instead of tetrominoes.
 *
 */

import java.util.Random;
import java.util.Vector;

public class RandomGenerator {
    Random m_random;
    Vector<Pentomino> m_bag;

    /**
     * Constructor
     */
    public RandomGenerator(){
        m_random = new Random();
        m_bag = new Vector<>();
        generateBag();
    }

    /**
     * Generate the new random bag with 12 pentominos
     */
    private void generateBag(){
        Vector<Integer> unusedTypes = new Vector<>();
        for(int i = 0; i < 12; i++){
            unusedTypes.add(i);
        }
        for(int i = 0; i < 12; i++){
            Pentomino newPentomino = new Pentomino(unusedTypes.remove(m_random.nextInt(unusedTypes.size())), m_random.nextBoolean());
            newPentomino.setX(3);
            m_bag.add(newPentomino);
        }

    }

    public Pentomino draw(){
        if(m_bag.isEmpty()) {
            generateBag();
        }
        return m_bag.remove(0);
    }
}
