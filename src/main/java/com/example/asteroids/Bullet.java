package com.example.asteroids;

/*
I create the bullets at the start of the game and after this they won´t be deleted to safe some performance.
They just get invisible then...
That is why the field isActive exists.
Number of bullets active at the same time : 10
 */
public class Bullet implements iBullet {

    //fields
    private double coordX;
    private double coordY;
    private double speed = 30;
    private final double radius = 20;
    private double angle;
    private boolean isActive = true;
    private double angleInRadians;
    private static Player player;
    private static boolean shootableFlag = true;
    private static final long shootableInterval = 150;



    public Bullet(double coordX, double coordY, double angle){



        angleInRadians = Math.toRadians(player.getImageView().getRotate()) - (Math.PI / 2);
        this.coordX = coordX;
        this.coordY = coordY;
        this.angle = angle;

    }//end of constructor

    public void moveBullet() {

        // Calculate the bullet's velocity components
        double velocityX = Math.cos(angleInRadians) * this.getSpeed();
        double velocityY = Math.sin(angleInRadians) * this.getSpeed();

        // Update the bullet's position based on the velocity components
        this.setCoordX(this.getCoordX() + velocityX);
        this.setCoordY(this.getCoordY() + velocityY);

    }

    public static void attachPlayer(Player p){
        player = p;
    }//end of attachPlayer

    public static void spawnBullet(){
        if(player.getBullets().size() > 10){player.getBullets().pop();}
        player.getBullets().push(new Bullet(
                player.getImageView().getBoundsInParent().getCenterX()-18,
                player.getImageView().getBoundsInParent().getCenterY()
                ,player.getImageView().getRotate()));

        System.out.println(player.getBullets().size());
    }//end of spawnBullet





    //getters
    public double getCoordX() {return this.coordX;}

    public double getCoordY() {return this.coordY;}

    public double getAngle() {return this.angle;}

    public double getRadius() {return this.radius;}

    public boolean isActive(){return this.isActive;}

    public double getSpeed() {return this.speed;}

    public boolean isShootable(){return this.shootableFlag;}

    public static long getShootableInterval(){return shootableInterval;}

    //setters
    public void setCoordX(double coordX) {this.coordX = coordX;}

    public void setCoordY(double coordY) {this.coordY = coordY;}

    public void toggleActive(){this.isActive = !this.isActive;}

    public static void setShootable(boolean s){shootableFlag = s;}


}//end of Bullet
