/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.esotericsoftware.minlog.Log;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    private CANSparkMax leftMotorMaster = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax leftMotorSlave1 = new CANSparkMax(14, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax leftMotorSlave2 = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax rightMotorMaster = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax rightMotorSlave1 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax rightMotorSlave2 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);

    @Override
    public void robotInit() {
        leftMotorSlave1.follow(leftMotorMaster);
        leftMotorSlave2.follow(leftMotorMaster);
        rightMotorSlave1.follow(rightMotorMaster);
        rightMotorSlave2.follow(rightMotorMaster);

        Log.setLogger(new RobotLogger());
        Log.set(1);
    }

    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    @Override
    public void teleopPeriodic() {
        Log.debug("Limelight", "Distance: " + LimeLight.getInstance().getDistanceToTarget());
        Log.debug("Limelight", "Pitch: " + LimeLight.getInstance().getPitchToTarget());
    }

    @Override
    public void teleopInit() {
        LimeLight.getInstance().setLEDMode(LimelightControlMode.LedMode.FORCE_ON);
        LimeLight.getInstance().setCamMode(LimelightControlMode.CamMode.VISION);
        Log.trace("Robot", "Teleop Started");
    }

    @Override
    public void disabledInit() {
        LimeLight.getInstance().setLEDMode(LimelightControlMode.LedMode.FORCE_OFF);
        Log.trace("Robot", "Disabled mode started");
    }
}
