/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author MUJ
 */
public class Snake extends Application {
    
    Stage stage;
    Group root;
    Scene scene;
    ImageView head=new ImageView(new Image(getClass().getResourceAsStream("head.png"))),appleim=new ImageView(new Image(getClass().getResourceAsStream("apple.png"))),turn=new ImageView(new Image(getClass().getResourceAsStream("turn.png")));
    int size=25;
    double width,height;
    ArrayList<Position> obj=new ArrayList<>();
    Point apple;
    int direction,prevdirection;
    boolean dirchange;
    boolean eated;
    Text score;
    final Timeline t=new Timeline(new KeyFrame(Duration.millis(250), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent evt)
            {   
                move();
                draw();
            }
        }));
    @Override
    public void start(Stage primaryStage) {
        stage=primaryStage;
        root=new Group();
        javafx.geometry.Rectangle2D screenres=Screen.getPrimary().getBounds();
        width=screenres.getWidth();
        height=screenres.getHeight();
        scene=new Scene(root, width,height);
        ImageView back=new ImageView(new Image(getClass().getResourceAsStream("grass.png")));
        back.setX(0);
        back.setY(0);
        back.setFitHeight(height);
        back.setFitWidth(width);
        root.getChildren().add(back);
        initial();
        draw();
        t.setCycleCount(10000000);
        t.play();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                checkKey(e);
            }
        });
        stage.sizeToScene();
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
    void checkKey(KeyEvent e)
    {
        switch(e.getCode())
        {
            case LEFT:if(direction==3||direction==4)
            {prevdirection=direction;direction=1;dirchange=true;}
                break;
            case RIGHT:if(direction==3||direction==4)
                {prevdirection=direction;direction=2;dirchange=true;}
                break;                
            case UP:if(direction==1||direction==2)
                {prevdirection=direction;direction=3;dirchange=true;}
                break;
            case DOWN:if(direction==1||direction==2)
                {prevdirection=direction;direction=4;dirchange=true;}
                break;
        }
    }
    void addHead()
    {
        head.setFitWidth(size);
        head.setFitHeight(size);
        switch(direction)
        {
            case 1:head.setX(obj.get(0).x-size);
        head.setY(obj.get(0).y);
        head.setRotate(0);break;
                case 2:head.setX(obj.get(0).x+size);
        head.setY(obj.get(0).y);
        head.setRotate(180);break;
                    case 3:head.setX(obj.get(0).x);
        head.setY(obj.get(0).y-size);
        head.setRotate(90);break;
                        case 4:head.setX(obj.get(0).x);
        head.setY(obj.get(0).y+size);
        head.setRotate(270);break;
                        
        }
        
        root.getChildren().add(head);
    }
    void draw()
    {
        root.getChildren().remove(2, root.getChildren().size());
        addHead();
        for(int i=0;i<obj.size();i++)
        {
            ImageView iv=obj.get(i).iv;
            iv.setX(obj.get(i).x);
            iv.setY(obj.get(i).y);
            iv.setFitWidth(size);
            iv.setFitHeight(size);
            root.getChildren().add(iv);
        }
        appleim.setX(apple.x);
        appleim.setY(apple.y);
        root.getChildren().add(appleim);
        stage.sizeToScene();
    }
    void initial()
    {
        score=new Text(width-200, height-30,"SCORE");
        score.setFont(new Font("ARIAL", 30));
        root.getChildren().add(score);
        appleim.setFitHeight(size);
        appleim.setFitWidth(size);
        obj.add(new Position(200,size*11));
        obj.add(new Position(200,size*10));
        obj.add(new Position(200,size*9));
        obj.get(0).iv.setRotate(90);
        obj.get(1).iv.setRotate(270);
        obj.get(2).iv.setRotate(90);
        for(int i=0;i<3;i++)
            System.out.println(obj.get(i).x+" I"+obj.get(i).y+" "+" " +obj.get(i).iv.getRotate());
        direction=4;
        apple=new Point(ThreadLocalRandom.current().nextInt((int)(width/size))*size,ThreadLocalRandom.current().nextInt((int)(height/size))*size);
    }
    void move()
    {
        if(dirchange)
            add(prevdirection);
        else
            add(direction);
        obj.remove(obj.size()-1);
       checkEat();
       //checkCollision();
    }
    void checkCollision()
    {
        for(int i=1;i<obj.size();i++)
            if(obj.get(0).x==obj.get(i).x&&obj.get(0).y==obj.get(i).y)
                System.exit(0);
    }
    void add(int n)
    {
        int x=0;
        switch(n)
        {
            case 1:obj.add(x, new Position(obj.get(x).x-size,obj.get(x).y));break;//left
            case 2:obj.add(x, new Position(obj.get(x).x+size,obj.get(x).y));break;//right
            case 3:obj.add(x, new Position(obj.get(x).x,obj.get(x).y-size));break;//up
            case 4:obj.add(x, new Position(obj.get(x).x,obj.get(x).y+size));break;//down
        }
        int rot=0;
        if(dirchange)
        {
            obj.get(0).iv.setImage(new Image(getClass().getResourceAsStream("turn.png")));
            obj.get(0).turn=true;
            switch(prevdirection)
            {
                case 1:rot=(direction==3?270:0);break;
                case 2:rot=(direction==3?180:90);break;
                case 3:rot=(direction==1?90:0);break;
                case 4:rot=(direction==1?180:270);break;
            }
            obj.get(0).iv.setRotate(rot+270);
        }
        else
        {
            if(obj.get(1).turn)
            {
                switch(prevdirection)
                {
                    case 1:rot=(direction==3?90:270);break;
                    case 2:rot=(direction==3?270:90);break;
                    case 3:rot=(direction==1?0:180);break;
                    case 4:rot=(direction==1?180:0);break;
                }
                obj.get(0).iv.setRotate(rot);
            }
            else
            {
        switch(((int)obj.get(1).iv.getRotate())%360){
            case 0:obj.get(0).iv.setRotate(180);break;
            case 90:obj.get(0).iv.setRotate(270);break;
            case 180:obj.get(0).iv.setRotate(0);break;
            case 270:obj.get(0).iv.setRotate(90);break;
        }}
        }
         dirchange=false;
    }
    void checkEat()
    {
        if(obj.get(0).x==apple.x&&obj.get(0).y==apple.y)
        {
            eated=true;
            switch(direction)
            {
                case 1:add(2);break;
                case 2:add(1);break;
                case 3:add(4);break;
                case 4:add(3);break;
            }
            eated=false;
            apple.x=ThreadLocalRandom.current().nextInt((int)(width/size))*size;
            apple.y=ThreadLocalRandom.current().nextInt((int)(height/size))*size;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
