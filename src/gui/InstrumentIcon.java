package gui;

import core.Symbol;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;

import static sound.MusicBox.NUMBER_OF_INSTRUMENTS;

class InstrumentIcon extends ImageIcon {

    /**
     * Number of available instruments.
     */
    private static final int IMG_WIDTH = 40;
    private static final int IMG_HEIGHT = 40;

    /**
     * Number of available instruments.
     */
    private static final int instrumentsNumber = NUMBER_OF_INSTRUMENTS + 1;

    private static final Hashtable<Symbol, BufferedImage> instrumentImages[] = new Hashtable[instrumentsNumber];

    InstrumentIcon(HashSet<Integer> instruments, Symbol direction) {
        final BufferedImage combinedImage = new BufferedImage(
                IMG_WIDTH,
                IMG_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinedImage.createGraphics();
        for (var i : instruments) {
            g.drawImage(instrumentImages[i].get(direction), 0, 0, null);
        }
        g.dispose();
        this.setImage(combinedImage);
    }

    public static void generateImages() {
        for (int i = 0; i < instrumentsNumber; i++) {
            instrumentImages[i] = new Hashtable<>();
        }
        for (int i = 0; i < instrumentsNumber; i++) {
            try {
                BufferedImage imgL = ImageIO.read(InstrumentIcon.class.getResource("assets/left.png"));
                BufferedImage imgR = ImageIO.read(InstrumentIcon.class.getResource("assets/right.png"));
                BufferedImage imgU = ImageIO.read(InstrumentIcon.class.getResource("assets/up.png"));
                BufferedImage imgD = ImageIO.read(InstrumentIcon.class.getResource("assets/down.png"));
                instrumentImages[i].put(Symbol.LEFT, imgL);
                instrumentImages[i].put(Symbol.RIGHT, imgR);
                instrumentImages[i].put(Symbol.UP, imgU);
                instrumentImages[i].put(Symbol.DOWN, imgD);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedImage img = ImageIO.read(InstrumentIcon.class.getResource("assets/down/0.png"));
            instrumentImages[0].put(Symbol.DOWN, img);
            img = ImageIO.read(InstrumentIcon.class.getResource("assets/down/3.png"));
            instrumentImages[3].put(Symbol.DOWN, img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}