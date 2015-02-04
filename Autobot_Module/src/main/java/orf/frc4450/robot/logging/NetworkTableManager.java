package orf.frc4450.robot.logging;

import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author ShortCircuit908
 */
public class NetworkTableManager{
	private final ORFLogger logger = new ORFLogger(this);
	private Set<ORFTable> tables = new HashSet<>();
	private final int team;

	public NetworkTableManager(int team){
		this.team = team;
	}

	public void initTable(String table_name){
		NetworkTable table = NetworkTable.getTable(table_name);
		table.setTeam(team);
		tables.add(new ORFTable(table, table_name));
		logger.info("Initialized table " + table_name);
	}

	public void removeTable(String table_name){
		ORFTable to_remove = getTable(table_name);
		if(to_remove != null){
			tables.remove(to_remove);
			logger.info("Removed table " + table_name);
		}
		logger.warning("Could not remove table " + table_name);
	}

	public ORFTable getTable(String table_name){
		for(ORFTable table : tables){
			if(table.getName().equals(table_name)){
				return table;
			}
		}
		return null;
	}
}
