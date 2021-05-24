import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.lang.Math;
import java.lang.Thread;

public class Game implements Runnable{
    private Game_Window game;
    public int height, width;
    private boolean running = false;
    private String label;
    Entity[] entities = new Entity[100];

    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;



    
    public Game(String label, int width, int height){
        this.width = width;
        this.height = height;
        this.label = label;

        

    }

    private void init() {
        game = new Game_Window(label, width, height);

        //creation of entities

        Random randy = new Random();
        int infected_entity = randy.nextInt(25);
        System.out.println(infected_entity);
        double cordx = 50;
        double cordy = 50;


        for(int i = 0; i<25; i++){


            double dxFull = randy.nextInt(201)-100;
            double dx = dxFull/100;
            double dy;

            if(dxFull%2 == 0) {
                dy = Math.sqrt(1-(dx*dx));
            }else{
                dy = Math.sqrt(1-(dx*dx)) * -1;
            }

            if(i==infected_entity)
                entities[i] = new Entity(cordx, cordy, 30, dx, dy, true);
            else
                entities[i] = new Entity(cordx, cordy, 30, dx, dy, false);

            cordx+=100;
            if(cordx>450){
                cordx = 50;
                cordy+=100;
            }
            
            
        }
    }

    private void tick(){
        for(int i = 0; i<25; i++){
            
            Entity e = entities[i];

            



            for(int k = 0; k<25; k++){
                Entity compareE = entities[k];
                if(!(compareE==e)){
                    //collision
                    if(Math.sqrt((e.y-compareE.y)*(e.y-compareE.y)+(e.x-compareE.x)*(e.x-compareE.x))<30) {
                        
                        double tempdx = e.dx;
                        double tempdy = e.dy;
                        e.dx = compareE.dx;
                        e.dy = compareE.dy;
                        compareE.dx = tempdx;
                        compareE.dy = tempdy;
        
                        if(e.infected || compareE.infected){
                            e.infected = true;
                            compareE.infected = true;
                        }
                    }

                }
            }

            e.x = e.x+e.dx;
            e.y = e.y+e.dy;

            if(e.x<15 || e.x>485){
                e.dx*=-1;
            }
            if(e.y<15 || e.y>485){
                e.dy*=-1;
            }

            

            e.x = e.x+e.dx;
            e.y = e.y+e.dy;


        }
    }

    private void render(){
        bs = game.canvas.getBufferStrategy();
        if(bs == null){
            game.canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        //draw background

        g.setColor(Color.black);
        g.fillRect(0,0,500,500);
        
        for(int i = 0; i<25; i++){

            Entity e = entities[i];
            
            if(e.infected){
                g.setColor(Color.red);
                
            }else{
                g.setColor(Color.green);
            }
            
            g.fillOval((int)e.x-15,(int)e.y-15,e.radius,e.radius);
            
        }

        bs.show();
        g.dispose();
    }

    public void run() {
        init();

        while(running){
            tick();
            render();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        stop();
    }

    public synchronized void start() {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
