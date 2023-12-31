// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.recordrobotics.charger;

import java.util.ArrayList;
import java.util.List;

import org.recordrobotics.charger.commands.ManualDrive;
import org.recordrobotics.charger.control.DoubleControl;
import org.recordrobotics.charger.control.IControlInput;
import org.recordrobotics.charger.control.SingleControl;
import org.recordrobotics.charger.subsystems.*;
import org.recordrobotics.charger.util.Pair;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings({"PMD.SingularField","PMD.UnusedPrivateField"})
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	private IControlInput _controlInput;
	private Drive _drive;

	// Commands
	private List<Pair<Subsystem, Command>> _teleopPairs;

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer() {
		// Configure the button bindings
		_controlInput = new SingleControl(RobotMap.Control.SINGLE_GAMEPAD);
		_drive = new Drive();


		initTeleopCommands();
	}

	private void initTeleopCommands() {
		_teleopPairs = new ArrayList<>();
		_teleopPairs.add(new Pair<Subsystem, Command>(_drive, new ManualDrive(_drive, _controlInput)));
	}



	/**
	 * Executes teleop commands
	 */	
	public void teleopInit() {
		for (Pair<Subsystem, Command> c : _teleopPairs) {
			c.getKey().setDefaultCommand(c.getValue());
		}
	}
	/**
	 * Set control scheme to Single
	 */
	private void singleControl() {
		resetCommands();
		_controlInput = new SingleControl(RobotMap.Control.SINGLE_GAMEPAD);
		initTeleopCommands();
		teleopInit();
	}

	/**
	 * Set control scheme to Double
	 */
	private void doubleControl() {
		resetCommands();
		_controlInput = new DoubleControl(RobotMap.Control.DOUBLE_GAMEPAD_1,
			RobotMap.Control.DOUBLE_GAMEPAD_2);
		initTeleopCommands();
		teleopInit();
	}

	/**
	 * Clear commands
	 */
	public void resetCommands() {
		CommandScheduler.getInstance().cancelAll();
	}
}
