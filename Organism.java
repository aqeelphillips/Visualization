import java.util.HashMap;
import java.util.Random;
import java.lang.Character;

public class Organism {
        //Based off of the Critter design
   
	Random r;
	private boolean dead;
	private int age;

	String coatGene;
	String huntingGene;
	String retentionGene;
	private int health;
	
	//Scale of 0-100:
	private int huntingChance;
	
	//Purely environmentally dependant, chance of finding water:
	private int waterChance;
	
	//Either 0, 5, 15:
	private int healthLostToTemp;
	
	//Either 2 or 5:
	private int maxDaysWithoutWater;
	
	//Counter for days without water:
	private int currentDaysWithoutWater;
	
	public Organism(Environment e, HashMap p1Genotype, HashMap p2Genotype) {
		coatGene = "";
		huntingGene = "";
		retentionGene = "";
		r = new Random();
		health = 100;
		age = 0;
		dead = false;
		
		generateGenotype(p1Genotype, p2Genotype);
		
		generateHuntingChance(e);
		generateHealthLostToTemp(e);
		generateWaterChance(e);
		generateRetention();
		
		/*for (int i = 0; i < 10; i++) {
			System.out.println(breed());
			System.out.println("");
		}*/
	}
	
	public void passDay() {
		if (!isDead()) {
			huntForFood();
			searchForWater();
			climateEffects();
			increaseAge();
		}
	}
	
	public void huntForFood() {
		boolean foodFound = calcChance(getHuntingChance());
		int h = getHealth();
		if (!foodFound) {
			setHealth(h - 6);
		}
	}

	public void searchForWater() {
		boolean waterFound = calcChance(getWaterChance());
		int h = getHealth();
		if (!waterFound) {
			currentDaysWithoutWater++;
		} else {
			currentDaysWithoutWater = 0;
		}
		if (currentDaysWithoutWater >= maxDaysWithoutWater) {
			setHealth(h - 6);
		}
	}
	
	public void climateEffects() {
		setHealth(getHealth() - healthLostToTemp);
	}
	
	public void generateGenotype(HashMap p1, HashMap p2) {
		huntingGene += pickRandom(p1.get("Hunting Gene").toString());
		coatGene += pickRandom(p1.get("Coat Gene").toString());
		retentionGene += pickRandom(p1.get("Retention Gene").toString());
		
		huntingGene += pickRandom(p2.get("Hunting Gene").toString());
		coatGene += pickRandom(p2.get("Coat Gene").toString());
		retentionGene += pickRandom(p2.get("Retention Gene").toString());
	}
	
	public void generateHuntingChance(Environment e) {
		int h = 0;
		if (huntingGene.equals("hh")) {
			if (e.getFoodAmount().equals("abundant")) {
				h = 100;
			} else if (e.getFoodAmount().equals("moderate")) {
				h = 85;
			} else {
				h = 70;
			}
		} else {
			if (e.getFoodAmount().equals("abundant")) {
				h = 90;
			} else if (e.getFoodAmount().equals("moderate")) {
				h = 60;
			} else {
				h = 30;
			}
		}
		huntingChance = h;
	}
	
	public void generateHealthLostToTemp(Environment e) {
		int h = 0;
		if (coatGene.equals("CC")) {
			if (e.getTemperature().equals("hot")) {
				h = 10;
			} else if (e.getTemperature().equals("moderate")) {
				h = 5;
			} else {
				h = 0;
			}
		} else if (coatGene.equals("Cc") || coatGene.equals("cC")) {
			if (e.getTemperature().equals("hot")) {
				h = 5;
			} else if (e.getTemperature().equals("moderate")) {
				h = 0;
			} else {
				h = 5;
			}
		} else if (coatGene.equals("cc")) {
			if (e.getTemperature().equals("hot")) {
				h = 0;
			} else if (e.getTemperature().equals("moderate")) {
				h = 5;
			} else {
				h = 10;
			}
		}
		healthLostToTemp = h;
	}
	
	public void generateWaterChance(Environment e) {
		int w = 0;
		if (e.getWaterAmount().equals("abundant")) {
			w = 90;
		} else if (e.getWaterAmount().equals("moderate")) {
			w = 60;
		} else {
			w = 30;
		}
		waterChance = w;
	}
	
	public void generateRetention() {
		if (getRetentionGene() == "ww") {
			maxDaysWithoutWater = 5;
		} else {
			maxDaysWithoutWater = 2;
		}
	}
	
	public String pickRandom(String g) {
		return Character.toString(g.charAt(r.nextInt(2)));
	}
	
	public HashMap breed() {
		HashMap h = new HashMap<String, String>();
		h.put("Hunting Gene", mutate(huntingGene));
		h.put("Retention Gene", mutate(retentionGene));
		h.put("Coat Gene", mutate(coatGene));
		return h;
	}
	
	public String mutate(String g) {
		int chance = r.nextInt(100);
		char[] geno = g.toCharArray();
		if (chance < 10) {
			int index = r.nextInt(2);
			char afflicted = geno[index];
			String a = Character.toString(afflicted);
			if (!a.toLowerCase().equals(a)) {
				a = a.toLowerCase();
			} else {
				a = a.toUpperCase();
			}
			geno[index] = a.charAt(0);
			return new String(geno);
		}
		return g;
	}
	
	public boolean calcChance(int pct) {
		int rand = r.nextInt(100);
		if (rand <= pct) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getAge() {
		return age;
	}
	public void increaseAge() {
		age++;
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int h) {
		if (h < 100) {
			health = h;
		} else {
			health = 100;
		}
		if (health < 0) {
			die();
		}
	}
	
	public boolean isDead() {
		return dead;
	}
	public void die() {
		dead = true;
	}
	
	public int getHuntingChance() {
		return huntingChance;
	}
	
	public int getWaterChance() {
		return waterChance;
	}
	
	public int getMaxDaysWithoutWater() {
		return maxDaysWithoutWater;
	}
	
	public int getCurrentDaysWithoutWater() {
		return currentDaysWithoutWater;
	}
	
	public int getHealthLostToTemp() {
		return healthLostToTemp;
	}
	
	public String getCoatGene() {
		return coatGene;
	}
	
	public String getHuntingGene() {
		return huntingGene;
	}
	
	public String getRetentionGene() {
		return retentionGene;
	}
	
	public String toString() {
		return getHuntingGene()+" hunting gene, "+getRetentionGene()+" retention gene, "+getCoatGene()+" coat gene.";
	}
}
