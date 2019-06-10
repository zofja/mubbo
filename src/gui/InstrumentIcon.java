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

    private static final Hashtable<Integer, BufferedImage> instrumentImages = new Hashtable();
    private static final Hashtable<Integer, BufferedImage> instrumentListImages = new Hashtable();
    private static final Hashtable<Integer, BufferedImage> colourImages = new Hashtable();
    private static final Hashtable<Symbol, BufferedImage> symbolImages = new Hashtable();

    InstrumentIcon(Hashtable<Integer, Symbol> instruments, boolean colorBlindOn) {
        this.setImage(getCombinedImage(instruments, IMG_WIDTH, IMG_HEIGHT, colorBlindOn));
    }

    private InstrumentIcon(Hashtable<Integer, Symbol> instruments, int width, int height, boolean colorBlindOn) {
        this.setImage(getCombinedListImage(instruments, width, height, colorBlindOn));
    }

    private BufferedImage getCombinedImage(Hashtable<Integer, Symbol> instruments, int width, int height, boolean colorBlindOn) {
        final BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinedImage.createGraphics();
        for (var i : instruments.entrySet()) {
            if (colorBlindOn) {
                g.drawImage(instrumentImages.get(i.getKey()), 0, 0, null);
            } else {
                g.drawImage(colourImages.get(i.getKey()), 0, 0, null);
            }
            g.drawImage(symbolImages.get(i.getValue()), 0, 0, null);
        }
        g.dispose();
        return combinedImage;
    }

    private BufferedImage getCombinedListImage(Hashtable<Integer, Symbol> instruments, int width, int height, boolean colorBlindOn) {
        final BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinedImage.createGraphics();
        for (var i : instruments.entrySet()) {
            if (colorBlindOn) {
                g.drawImage(instrumentListImages.get(i.getKey()), 0, 0, null);
            } else {
                g.drawImage(colourImages.get(i.getKey()), 0, 0, null);
            }
        }
        g.dispose();
        return combinedImage;
    }


    static void generateImages() {

        for (int i = 0; i < instrumentsNumber; i++) {
            try {
                BufferedImage instrument = ImageIO.read(InstrumentIcon.class.getResource("assets/instruments/" + i + ".png"));
                BufferedImage listInstrument = ImageIO.read(InstrumentIcon.class.getResource("assets/listinstruments/" + i + ".png"));
                BufferedImage colour = ImageIO.read(InstrumentIcon.class.getResource("assets/colours/" + i + ".png"));
                instrumentImages.put(i, instrument);
                instrumentListImages.put(i, listInstrument);
                colourImages.put(i, colour);
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Couldn't load icon image, check path");
                System.exit(1);
            }
        }

        try {
            BufferedImage left = ImageIO.read(InstrumentIcon.class.getResource("assets/symbols/left.png"));
            BufferedImage up = ImageIO.read(InstrumentIcon.class.getResource("assets/symbols/up.png"));
            BufferedImage right = ImageIO.read(InstrumentIcon.class.getResource("assets/symbols/right.png"));
            BufferedImage down = ImageIO.read(InstrumentIcon.class.getResource("assets/symbols/down.png"));
            BufferedImage collision = ImageIO.read(InstrumentIcon.class.getResource("assets/symbols/collision.png"));
            symbolImages.put(Symbol.LEFT, left);
            symbolImages.put(Symbol.UP, up);
            symbolImages.put(Symbol.RIGHT, right);
            symbolImages.put(Symbol.DOWN, down);
            symbolImages.put(Symbol.COLLISION, collision);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Couldn't load icon image, check path");
            System.exit(1);
        }
    }

    static ImageIcon getListIcon(Symbol symbol, int i, boolean colorBlindOn) {
        Hashtable<Integer, Symbol> ht = new Hashtable<>();
        ht.put(i, symbol);
        return new InstrumentIcon(ht, 16, 16, colorBlindOn);
    }
}