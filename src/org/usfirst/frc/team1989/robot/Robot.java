/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1989.robot;

import org.usfirst.frc.team1989.robot.AutoRoutines.StartLeftSwitchLeft;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;


import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Double angle;
	Double inches1;
	Double inches2;
	Double inches3;
	Boolean flimit;
	Boolean rlimit;

	
	

	String gameData;
	

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		Components.frontLeft.setInverted(true);
		Components.frontRight.setInverted(true);
		Components.backLeft.setInverted(true);
		Components.armsRight.setInverted(true);
		Components.towerLeft.setNeutralMode(NeutralMode.Brake);
		Components.towerRight.setNeutralMode(NeutralMode.Brake);
		Components.towerLeft.set(ControlMode.Follower, 5);
		CameraServer.getInstance().startAutomaticCapture();
		
		SharedStuff.cmdlist.add(Components.mDrive);
		SharedStuff.cmdlist.add(Components.arms);
		SharedStuff.cmdlist.add(Components.tower);
		SharedStuff.cmdlist.add(Components.write);
		SharedStuff.cmdlist.add(Components.cam);
		Components.r1.setAutomaticMode(true);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		Components.timer.stop();
		Components.timer.reset();
		Components.timer.start();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		Components.r1.setAutomaticMode(true);

	}



	/**
	 * This function is called periodically during autonomous.
	 */
	int startState = 0;
	@Override
	public void autonomousPeriodic() {

		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).autonomousPeriodic();
		}
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (startState == 0 ) {
			AutoCommands.towerMove(1);
			if(AutoCommands.actionFlag == false) {
				startState++;
			}
		}
		if(startState == 1) {
			if(gameData.length() > 0) {
			
				if (SmartDashboard.getBoolean("DB/Button 0", true)) {
							if (SmartDashboard.getBoolean("DB/Button 3", true)) {
								if (gameData.charAt(1) == 'L') {
									// StartLeftScaleLeft.run();
								} else {
									// StartLeftScaleRight.run();
								}
							} else {
								if (gameData.charAt(0) == 'L') {
									StartLeftSwitchLeft.run();
								} else {
									// StartLeftSwitchRight.run();
								}
							}
				
						} else if (SmartDashboard.getBoolean("DB/Button 1", true)) {
							if (SmartDashboard.getBoolean("DB/Button 3", true)) {
								if (gameData.charAt(1) == 'L') {
									// StartCenterScaleLeft.run();
								} else {
								// StartCenterScaleRight.run();
								}
							} else {
								if (gameData.charAt(0) == 'L') {
									// StartCenterSwitchLeft.run();
								} else {
									// StartCenterSwitchRight.run();
								}
							}
						} else if (SmartDashboard.getBoolean("DB/Button 2", true)) {
							if (SmartDashboard.getBoolean("DB/Button 3", true)) {
								if (gameData.charAt(1) == 'L') {
									// StartRightScaleLeft.run();
								} else {
									// StartRightScaleRight.run();
								}
							} else {
							if (gameData.charAt(0) == 'L') {
									// StartRightSwitchLeft.run();
							} else {
									// StartRightSwitchRight.run();
								}
							}
						} else {
							// DriveForward.run();
				 		}
				}		
			}
	}	 

		
	

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopInit() {
		Components.frontLeft.setNeutralMode(NeutralMode.Brake);
		Components.frontRight.setNeutralMode(NeutralMode.Brake);
		Components.backLeft.setNeutralMode(NeutralMode.Brake);
		Components.backRight.setNeutralMode(NeutralMode.Brake);
		Components.towerLeft.setNeutralMode(NeutralMode.Brake);
		Components.towerRight.setNeutralMode(NeutralMode.Brake);
		

		
		
		
		
	}
	
	
	@Override
	public void teleopPeriodic() {
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).teleopPeriodic();
		}

		angle = Components.gyro.getAngle();
		
		inches2 = Components.r2.getRangeInches();
		
		rlimit = Components.towerRight.getSensorCollection().isRevLimitSwitchClosed();
		flimit = Components.towerRight.getSensorCollection().isFwdLimitSwitchClosed();
		Components.write.setmessage(0, angle.toString());
		Components.write.setmessage(1, inches2.toString());
		Components.write.setmessage(2, flimit.toString());
		Components.write.setmessage(3, rlimit.toString());
		Components.write.updatedash();



		
		

		
		if (Components.driveStick.getRawButton(7)) {
			Components.gyro.reset();
		}
	}
		
		
		
	

	/*
	 * Disable all RangeFinders
	 */

	public void testInit() {
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		for (int i = 0; i < SharedStuff.cmdlist.size(); i++) {
			SharedStuff.cmdlist.get(i).teleopPeriodic();
		}
		
		
	}
}
