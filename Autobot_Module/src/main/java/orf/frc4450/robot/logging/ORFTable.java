package orf.frc4450.robot.logging;

import edu.wpi.first.wpilibj.tables.ITable;

/**
 * @author ShortCircuit908
 */
public class ORFTable{
	private final ITable table;
	private final String name;

	public ORFTable(ITable table, String name){
		this.table = table;
		this.name = name;
	}

	public ITable getTable(){
		return table;
	}

	public String getName(){
		return name;
	}
}
