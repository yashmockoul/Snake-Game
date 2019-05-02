/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author MUJ
 */
public class Position {
    double x;
    double y;
    boolean turn;
    ImageView iv;
    Position(double x,double y)
    {
        this.x=x;
        this.y=y;
        iv=new ImageView(new Image(getClass().getResourceAsStream("body.png")));
    }
}
