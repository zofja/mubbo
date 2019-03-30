import core.Brain;
import core.Symbol;
import core.particle.Direction;
import core.particle.Particle;

// TODO PROTECTED?

public class Main {

    public static void main(String[] args) {
        turnTest();
        brainTest();
    }

    public static void brainTest() {
        Brain brain = new Brain(9, 10000, 5);
        brain.initRandomTest(9);
//        brain.initDirectionTest();
//        brain.initMaxTestRandomDirection();
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
