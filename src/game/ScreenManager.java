package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

public class ScreenManager {

    private GraphicsDevice gDevice;

    public ScreenManager() {
        GraphicsEnvironment environment = 
        		GraphicsEnvironment.getLocalGraphicsEnvironment();
        gDevice = environment.getDefaultScreenDevice();
    }
    
    public DisplayMode findFirstCompatibleMode(DisplayMode modes[]) {
        DisplayMode goodModes[] = gDevice.getDisplayModes();
        for (int i = 0; i < modes.length; i++) {
            for (int j = 0; j < goodModes.length; j++) {
                if (displayModesMatch(modes[i], goodModes[j])) {
                    return modes[i];
                }
            }
        }

        return null;
    }
    
    public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2) {
        if (mode1.getWidth() != mode2.getWidth() ||
        	mode1.getHeight() != mode2.getHeight()) 
        {
            return false;
        }

        if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
            mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
            mode1.getBitDepth() != mode2.getBitDepth())
        {
            return false;
        }

        if (mode1.getRefreshRate() !=
            DisplayMode.REFRESH_RATE_UNKNOWN &&
            mode2.getRefreshRate() !=
            DisplayMode.REFRESH_RATE_UNKNOWN &&
            mode1.getRefreshRate() != mode2.getRefreshRate())
         {
             return false;
         }

         return true;
    }

    public void setFullScreen(DisplayMode displayMode) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);

        gDevice.setFullScreenWindow(frame);

        if (displayMode != null && gDevice.isDisplayChangeSupported())
        {
            try {
                gDevice.setDisplayMode(displayMode);
            }
            catch (IllegalArgumentException ex) {}

            // fix for mac os x
            frame.setSize(displayMode.getWidth(), displayMode.getHeight());
        }
        
        // avoid potential deadlock in 1.4.1_02
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    frame.createBufferStrategy(2);
                }
            });
        }
        catch (InterruptedException ex) {
            // ignore
        }
        catch (InvocationTargetException  ex) {
            // ignore
        }
    }

    public Graphics2D getGraphics() {
        Window window = gDevice.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            return (Graphics2D)strategy.getDrawGraphics();
        }
        else {
            return null;
        }
    }

    public void update() {
        Window window = gDevice.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }
        }
        
        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }

    public JFrame getFullScreenWindow() {
        return (JFrame)gDevice.getFullScreenWindow();
    }

    public int getWidth() {
        Window window = gDevice.getFullScreenWindow();
        if (window != null) {
            return window.getWidth();
        }
        else {
            return 0;
        }
    }

    public int getHeight() {
        Window window = gDevice.getFullScreenWindow();
        if (window != null) {
            return window.getHeight();
        }
        else {
            return 0;
        }
    }

    public void restoreScreen() {
        Window window = gDevice.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        gDevice.setFullScreenWindow(null);
    }
}
