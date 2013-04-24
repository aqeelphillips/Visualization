import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class Initializer {
        private Environment e;
	private ArrayList<Organism> orgs;
	private Random r;
	int daysTilBreeding;
	int breedingTimeframe;
	int orgNum;
	
	public Initializer(String env, int o) {
		orgNum = o;
		r = new Random();
		e = new Environment(env);
		orgs = new ArrayList<Organism>();
		
		breedingTimeframe = 5;
		daysTilBreeding = breedingTimeframe;
	
		initOrganisms(o);
	}
	
	public double f(String gene) {
		char g = gene.charAt(0);
		char[] genotype = new char[6];
		double tally = 0;
		for (int i = 0; i < getOrgs().size(); i++) {
			//ugly!!!
			Organism o = getOrgs().get(i);
			genotype[0] = o.getHuntingGene().charAt(0);
			genotype[1] = o.getHuntingGene().charAt(1);
			genotype[2] = o.getRetentionGene().charAt(0);
			genotype[3] = o.getRetentionGene().charAt(1);
			genotype[4] = o.getCoatGene().charAt(0);
			genotype[5] = o.getCoatGene().charAt(1);
			for (int q = 0; q < genotype.length; q++) {
				if (genotype[q] == g)
					tally++;
			}
		}
		return round(tally / (double)(getOrgs().size()*2));
	}
	
	public void passDay() {
		for (int i = 0; i < getOrgs().size(); i++) {
			getOrgs().get(i).passDay();
			if (getOrgs().get(i).isDead()) {
				getOrgs().remove(i);
			}
		}
	        breedOrgs();
	}
	
	public void breedOrgs() {
		int p1;
		int p2;
		for (int i = 0; i < getOrgs().size() / 2; i++) {
			p1 = r.nextInt(getOrgs().size());
			p2 = r.nextInt(getOrgs().size());
			while (p2 != p1) {
				p2 = r.nextInt(getOrgs().size());
			}
			if (getOrgs().get(p1).getAge() >= 15 && getOrgs().get(p2).getAge() >= 15) {
				getOrgs().add(new Organism(e, getOrgs().get(p1).breed(), getOrgs().get(p2).breed()));
			}
		}
	}
	
	public String genePercString(double[] d, String gene) {
		double s = (double)getOrgs().size();
		return gene+": "+round(d[0]/s)+" dom, "+round(d[1]/s)+" hetero, "+round(d[2]/s)+" rec";
	}
	
	public double[] getCount(String gene) {
		int dom = 0;
		int hetero = 0;
		int rec = 0;
		if (gene.equals("Hunting Gene")) {
			for (int i = 0; i < getOrgs().size(); i++) {
				String g = getOrgs().get(i).getHuntingGene();
				if (g.equals("HH")) {
					dom++;
				} else if (g.equals("hh")) {
					rec++;
				} else {
					hetero++;
				}
			}
		} else if (gene.equals("Coat Gene")) {
			for (int i = 0; i < getOrgs().size(); i++) {
				String g = getOrgs().get(i).getCoatGene();
				if (g.equals("CC")) {
					dom++;
				} else if (g.equals("cc")) {
					rec++;
				} else {
					hetero++;
				}
			}
		} else if (gene.equals("Retention Gene")) {
			for (int i = 0; i < getOrgs().size(); i++) {
				String g = getOrgs().get(i).getRetentionGene();
				if (g.equals("WW")) {
					dom++;
				} else if (g.equals("ww")) {
					rec++;
				} else {
					hetero++;
				}
			}
		}
                //Arbitrary labeling... sorry
		double[] genes = new double[3];
		genes[0] = (double)dom;
		genes[1] = (double)hetero;
		genes[2] = (double)rec;
		return genes;
	}
	
	public void initOrganisms(int num) {
		HashMap p1 = new HashMap<String, String>();
		HashMap p2 = new HashMap<String, String>();
		
		for (int i = 0; i < num; i++) {
			p1 = generateParentGenotype(p1, "Hunting Gene", "H");
			p1 = generateParentGenotype(p1, "Retention Gene", "W");
			p1 = generateParentGenotype(p1, "Coat Gene", "C");
			
			p2 = generateParentGenotype(p2, "Hunting Gene", "H");
			p2 = generateParentGenotype(p2, "Retention Gene", "W");
			p2 = generateParentGenotype(p2, "Coat Gene", "C");
			
			getOrgs().add(new Organism(e, p1, p2));
                        //randomize age to start breeding sooner:
                        for (int q = 0; q < r.nextInt(20); q++) {
                           getOrgs().get(i).increaseAge(); 
                        }
		}
	}
	
	public boolean allDead() {
		boolean allDead = true;
		for (int i = 0; i < getOrgs().size(); i++) {
			if (!getOrgs().get(i).isDead()) {
				allDead = false;
			}
		}
		return allDead;
	}
	
	public HashMap generateParentGenotype(HashMap p, String gene, String g) {
		int rand = r.nextInt(4);
		if (rand == 0) {
			p.put(gene, g + g);
		} else if (rand == 1) {
			p.put(gene, g.toLowerCase() + g.toLowerCase());
		} else {
			p.put(gene, g + g.toLowerCase());
		}
		return p;
	}
	
	public ArrayList<Organism> getOrgs() {
		return orgs;
	}
	
	public int getDaysTilBreeding() {
		return daysTilBreeding;
	}
	public void setDaysTilBreeding(int n) {
		daysTilBreeding = n;
	}
	
	public int getBreedingTimeframe() {
		return breedingTimeframe;
	}
	
	public double round(double d) {
		return Math.round(d*1000)/1000.0d;
	}

        public Environment getEnv() {
           return e; 
        }
}
