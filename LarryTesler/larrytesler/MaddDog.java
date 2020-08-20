package larrytesler;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * MaddDog - a robot by (your name here)
 */
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.RateControlRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.RobotStatus;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.Robot;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;

public class MaddDog extends Robot {


	
	private boolean isStopped = false;
	private boolean isGunPointingStraight = false;
	private boolean isFirstTime = true;
	private boolean isAhead = true;
	private double bulletPower = 2;
	private double largestDistance = 0;
	private int counter =1;


	public void run() {

		if(getBattleFieldWidth()> getBattleFieldHeight()){
			largestDistance = getBattleFieldWidth();
		}else{
			largestDistance = getBattleFieldHeight();
		}

		turnLeft(getHeading() % 90);
		ahead(largestDistance);
		turnGunRight(90);
			

		while (true) {
			if(!isGunPointingStraight && !isStopped){
				out.println("!isGunPointingStraight");
				turnGunRight(90 - (getGunHeading() - getHeading()));
				isGunPointingStraight = true;
			}
			
			out.println(getGunHeading() - getHeading() + "");
			if(isAhead){
				out.println("isAhead");
				ahead(largestDistance);
			}else{
				out.println("back");
			    back(largestDistance);
			}

		}
		
	}

	public void onHitWall(HitWallEvent e){
		out.println("onHitWall");
		if(isFirstTime){
			out.println("isFirstTime");
			isFirstTime = false;
			counter = 1;
			turnRight(90);
			return;
		}

		if(counter % 2 == 0){
			out.println("counter % 2");
			counter = 1; 
			isAhead= !isAhead;
			return;
		}

		if(isAhead){
			tunRight90Degrees();
			counter ++;
		}else{
			tunLeft90Degrees();
			counter ++;
		}
		
	}
	
	public void onHitRobot(HitRobotEvent e) {
		out.println("onHitRobot");
		counter = 1;
		isFirstTime = true;

		if(isAhead){
			back(100);
			tunRight90Degrees();
		}else{
			ahead(100);
			tunLeft90Degrees();
		}
		
	}

	public void tunRight90Degrees(){
		turnRight(90);
		turnRight(getHeading() % 90);
	}

	public void tunLeft90Degrees(){
		turnLeft(90);
		turnLeft(getHeading() % 90);
	}

  	public void onBulletHit(BulletHitEvent hre) {
  		if(bulletPower < 10){
  			bulletPower ++;
  		}
	}

    public void onBulletMissed(BulletMissedEvent e) {
    	bulletPower = 2;
    }

	public void onScannedRobot(ScannedRobotEvent e) {
      	fire(bulletPower);
	    if(getHeading() % 90 == 0){
			stop();
			isStopped = true;
			scan();
			resume();	
			isStopped = false;
	    }
	}

	public void onHitByBullet(HitByBulletEvent e) {
		if(isAhead){	
			tunRight90Degrees();
		}else{
			tunLeft90Degrees();
		}
	}

}