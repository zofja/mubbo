//package src;

import core.Brain;
import core.particle.Direction;
import core.particle.Particle;

// TODO PROTECTED?

public class Main {

    public static void main(String[] args) {
        turnTest();
        brainTest();
    }

    public static void brainTest() {
        Brain brain = new Brain(9, 100, 10);
        brain.init(8);
        brain.go();
    }

    public static void turnTest() {
        Particle l = new Particle(2);
        System.out.println(l.getDirectionName());

        for (int i = 0; i < Direction.getNoDirections(); i++) {
            l.collide();
            System.out.println(l.getDirectionName());
        }
        System.out.println();
    }
}
