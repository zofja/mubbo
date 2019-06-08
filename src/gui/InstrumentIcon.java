package gui;

import core.Symbol;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import static sound.MusicBox.NUMBER_OF_INSTRUMENTS;

class InstrumentIcon extends ImageIcon {

    /**
     * Number of available instruments.
     */
    private static final int IMG_WIDTH = 70;
    private static final int IMG_HEIGHT = 70;

    /**
     * Number of available instruments.
     */
    private static final int instrumentsNumber = NUMBER_OF_INSTRUMENTS;

    private static final Hashtable<Symbol, BufferedImage>[] instrumentImages = new Hashtable[instrumentsNumber];

    InstrumentIcon(Hashtable<Integer, Symbol> instruments) {
        this.setImage(getCombinedImage(instruments, IMG_WIDTH, IMG_HEIGHT));
    }

    private InstrumentIcon(Hashtable<Integer, Symbol> instruments, int width, int height) {
        this.setImage(getCombinedImage(instruments, width, height));
    }

    private BufferedImage getCombinedImage(Hashtable<Integer, Symbol> instruments, int width, int height) {
        final BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinedImage.createGraphics();
        for (var i : instruments.entrySet()) {
            g.drawImage(instrumentImages[i.getKey()].get(i.getValue()), 0, 0, null);
        }
        g.dispose();
        return combinedImage;
    }

    static void generateImages() {
        for (int i = 0; i < instrumentsNumber; i++) {
            instrumentImages[i] = new Hashtable<>();
        }

        for (int i = 0; i < instrumentsNumber; i++) {
            try {
                BufferedImage imgL = ImageIO.read(InstrumentIcon.class.getResource("assets/left/" + i + ".png"));
                BufferedImage imgR = ImageIO.read(InstrumentIcon.class.getResource("assets/right/" + i + ".png"));
                BufferedImage imgU = ImageIO.read(InstrumentIcon.class.getResource("assets/up/" + i + ".png"));
                BufferedImage imgD = ImageIO.read(InstrumentIcon.class.getResource("assets/down/" + i + ".png"));
                BufferedImage collision = ImageIO.read(InstrumentIcon.class.getResource("assets/collision/" + i + ".png"));
                BufferedImage empty = ImageIO.read(InstrumentIcon.class.getResource("assets/colours/" + i + ".png"));
                instrumentImages[i].put(Symbol.LEFT, imgL);
                instrumentImages[i].put(Symbol.RIGHT, imgR);
                instrumentImages[i].put(Symbol.UP, imgU);
                instrumentImages[i].put(Symbol.DOWN, imgD);
                instrumentImages[i].put(Symbol.COLLISION, collision);
                instrumentImages[i].put(Symbol.EMPTY, empty);
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Couldn't load icon image, check path");
                System.exit(1);
            }
        }
    }

    static ImageIcon getIcon(Symbol symbol, int i) {
        Hashtable<Integer, Symbol> ht = new Hashtable<>();
        ht.put(i, symbol);
        return new InstrumentIcon(ht, 16, 16);
    }
}