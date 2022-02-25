package mainpackage;

import java.util.concurrent.ThreadLocalRandom;

public class StructureGenerator {
	
	public ObjectList[] getRandomStructure(int xPos) {
		ObjectList[] struct1 = {new ObjectList(new String[] {"NONE"}, xPos, 1), 
												new ObjectList(new String[] {"BEDROCK"}, xPos + 50, 1),
												new ObjectList(new String[] {"NONE"}, xPos + 100, 1)};
		ObjectList[] struct2 = {new ObjectList(new String[] {"NONE"}, xPos, 1), 
												new ObjectList(new String[] {"BEDROCK"}, xPos + 50, 1),
												new ObjectList(new String[] {"NONE"}, xPos + 100, 1), 
												new ObjectList(new String[] {"BEDROCK", "BEDROCK"}, xPos + 150, 2),
												new ObjectList(new String[] {"NONE"}, xPos + 200, 1)};
		
		ObjectList[][] structSet = {struct1, struct2};
		
		int randomIndex = ThreadLocalRandom.current().nextInt(0, structSet.length);
		return structSet[randomIndex];
	}
	
	public ObjectList[] getRandomRoad(int xPos) {
		ObjectList[] road1 = {new ObjectList(new String[] {"ROAD"}, xPos, 0), 
												new ObjectList(new String[] {"ROAD"}, xPos + 50, 0), 
												new ObjectList(new String[] {"ROAD"}, xPos + 100, 0)};

		ObjectList[][] roadSet = {road1};

		int randomIndex = ThreadLocalRandom.current().nextInt(0, roadSet.length);
		return roadSet[randomIndex];
	}

}
