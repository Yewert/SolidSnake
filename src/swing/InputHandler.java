package swing;

import java.awt.Component;
import java.awt.event.*;

public class InputHandler implements KeyListener
{
    private boolean[] keys = new boolean[256];

    public InputHandler(Component c)
    {
        c.addKeyListener(this);
    }

    public boolean isKeyDown(int keyCode) {
        return keyCode > 0 && keyCode < 256 && keys[keyCode];
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() > 0 && e.getKeyCode() < 256)
        {
            keys[e.getKeyCode()] = true;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() > 0 && e.getKeyCode() < 256)
        {
            keys[e.getKeyCode()] = false;
        }
    }

    public void keyTyped(KeyEvent e){}
}