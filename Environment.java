public class Environment {
	private String temperature;
	private String foodAmount;
	private String waterAmount;
        private String conditions;

	public Environment(String cond) {
                conditions = cond;
		//ugly case work:
		if (cond.equals("Desert")) {
			temperature = "hot";
			foodAmount = "scarce";
			waterAmount = "scarce";
		} else if (cond.equals("Arctic")) {
			temperature = "cold";
			foodAmount = "scarce";
			waterAmount = "moderate";
		} else if (cond.equals("Forest")) {
			temperature = "moderate";
			foodAmount = "abundant";
			waterAmount = "moderate";
		} else if (cond.equals("Beach")) {
			temperature = "hot";
			foodAmount = "moderate";
			waterAmount = "abundant";
		} else if (cond.equals("Mountain")) {
			temperature = "cold";
			foodAmount = "moderate";
			waterAmount = "scarce";
		} else if (cond.equals("Urban")) {
			temperature = "moderate";
			foodAmount = "abundant";
			waterAmount = "moderate";
		}
	}
	
	public String getTemperature() {
		return temperature;
	}
	
	public String getFoodAmount() {
		return foodAmount;
	}
	
	public String getWaterAmount() {
		return waterAmount;
	}

        public String getConditions() {
                return conditions;
        } 
}
