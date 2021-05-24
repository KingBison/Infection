import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;


public class Game_Window  {
    private JFrame window;
    public Canvas canvas;

    private String label;
    private ImageIcon icon; 
    private int width, height;

    public Game_Window(String label, int width, int height){
        this.label = label;
        this.width = width;
        this.height = height;
        this.icon = new ImageIcon("icon_disease.png");

        createGameWindow();
        createGameCanvas();
    }

    private void createGameWindow(){
        window = new JFrame(label);
        window.setSize(width,height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setIconImage(icon.getImage());
    }

    private void createGameCanvas(){
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setMaximumSize(new Dimension(width,height));
        canvas.setMinimumSize(new Dimension(width,height));

        window.add(canvas);
        window.pack();




    }

}
