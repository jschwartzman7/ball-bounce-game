package javafiles;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class BallGame {

    public static double scale = 1;
    //public static Gun gun;
    //public static Trail gunTrail;
    //public static Ball ball;
    
    //public static LevelBarriers levelBarrierData = new LevelBarriers();
    public static LinkedList<Collision> collisionPoints = new LinkedList<Collision>();
    public static int curScore = 0;
    public static int totalScore = 0;
    public static int curLevel = 1;
    public static int numLevels = 2;
    public static int backgroundRed = 103;
    public static int backgroundGreen = 116;
    public static int backgroundBlue = 133;
   
    /*public BallGame(LinkedList<Barrier> levelBarriers){
        this.barriers = levelBarriers;
    }*/

    public static double[] checkMovingObjectWallCollision(double nextX, double nextY, double angle, double radius, boolean ball){
        if(Math.abs(nextX) + radius > BallGame.scale){
            if(angle > 0){
                angle = Math.PI - angle;
            }
            else{
                angle = -Math.PI - angle;
            }
            if(nextX - radius < -BallGame.scale){
                nextX += 2*(radius-nextX-BallGame.scale);
            }
            else{
                nextX -= 2*(nextX+radius-BallGame.scale);
            }
        }
        if(Math.abs(nextY) + radius > BallGame.scale){
            angle *= -1;
            if(nextY - radius < -BallGame.scale){
                nextY += 2*(radius-nextY-BallGame.scale);
            }
            else{
                nextY -= 2*(nextY+radius-BallGame.scale);
            }
        }
        double[] norm = Barrier.overlaps(new double[]{nextX, nextY}, radius);
        if(norm.length == 2){
            double edgeAngle = Math.atan(norm[0]/norm[1]);
            angle = -angle - 2*(edgeAngle);
            nextX -= 2*norm[0];
            nextY -= 2*norm[1];
        }
        return new double[]{nextX, nextY, angle};
    }

    public static boolean gunOutOfBounds(){
        if(StdDraw.mouseX() + Gun.radius > scale || StdDraw.mouseX() - Gun.radius < -scale || StdDraw.mouseY() + Gun.radius > scale || StdDraw.mouseY() - Gun.radius < -scale){
            return true;
        }
        return false;
    }

    public static void drawBorder(){
        StdDraw.setPenColor(Color.white);
        StdDraw.line(scale, -scale, scale, scale);
        StdDraw.line(scale, scale, -scale, scale);
        StdDraw.line(-scale, -scale, -scale, scale);
        StdDraw.line(-scale, -scale, scale, -scale);
        StdDraw.text(0, 1.1*scale, "Score " + (totalScore + curScore) + " - Space to launch ball - \"r\" to reset", 0);
    }

    public static void updateAndDrawShapes(){
        StdDraw.setPenColor(38, 38, 38);
        StdDraw.filledSquare(0, 0, 2*scale);
        StdDraw.setPenColor(backgroundRed, backgroundGreen, backgroundBlue);
        StdDraw.filledSquare(0, 0, scale);
        drawBorder();
        Gun.updateBarrelAngle();
        Trail.updateAndDrawTrail();
        if(Ball.isActive){
            Ball.updateBall();
            Ball.drawBall();
        }
        Collision.drawCollisions();
        Barrier.drawBarriers();
        Gun.draw();
    }

    public void playLevels(){
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-1.3*scale, 1.3*scale);
        boolean mousePressed = false;
        boolean resetBoardCooldown = false;
        Barrier.getLevelBarriers(curLevel);
        while(true){
                StdDraw.clear();
                updateAndDrawShapes();
                if(Barrier.aliveBarrier()){
                    if(Gun.placed){
                        if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && !Gun.cooldown){
                            curScore = 0;
                            Barrier.resetBarriers();
                            new Ball(Gun.x, Gun.y, Gun.barrelAngle);
                            Gun.cooldown = true;
                            Gun.animationFrame = 0;
                        }
                        if(!StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                            Gun.cooldown = false;
                        }
                        if(StdDraw.isMousePressed()){
                            mousePressed = true;
                        }
                        if(!StdDraw.isMousePressed() && mousePressed){
                            mousePressed = false;
                            double mouseX = StdDraw.mouseX();
                            double mouseY = StdDraw.mouseY();
                            if(Barrier.overlaps(new double[]{mouseX, mouseY}, Gun.radius).length == 3 && !gunOutOfBounds()){
                                Gun.x = mouseX;
                                Gun.y = mouseY;
                            }
                        }
                    }
                    else{
                        Gun.alpha = 128;
                        double mouseX = StdDraw.mouseX();
                        double mouseY = StdDraw.mouseY();
                        if(Barrier.overlaps(new double[]{mouseX, mouseY}, Gun.radius).length == 3 && !gunOutOfBounds()){
                            Gun.x = mouseX;
                            Gun.y = mouseY;
                        }
                        if(StdDraw.isMousePressed()){
                            Gun.placed = true;
                            Gun.alpha = 255;
                        }
                    }
                }
                else{
                    StdDraw.setPenColor(30, 30, 30);
                    StdDraw.filledRectangle(0, 0, .6, .3);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.text(0, 0, "Score: " + (totalScore + curScore), 0);
                    StdDraw.text(0, -.15, "Press Space to play next level", 0);
                    if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                        totalScore += curScore;
                        curScore = 0;
                        Gun.cooldown = true;
                        Barrier.resetBarriers();
                        Ball.isActive = false;
                        ++curLevel;
                        if(curLevel > numLevels){
                            StdDraw.text(0, 0, "Game over, score: " + totalScore, 0);
                            System.out.println("nice");
                        }
                        else{
                            Barrier.getLevelBarriers(curLevel);
                        }
                        Gun.placed = false;
                    }
                    StdDraw.show();
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_R) && !resetBoardCooldown){
                    resetBoardCooldown = true;
                    Ball.isActive = false;
                    curScore = 0;
                    Gun.placed = false;
                    Barrier.resetBarriers();
                }
                if(!StdDraw.isKeyPressed(KeyEvent.VK_R)){
                    resetBoardCooldown = false;
                }
                StdDraw.show(20);
        }
        
    }



   
    public static void main(String[] args){
        BallGame game = new BallGame();
        game.playLevels();
    }
}