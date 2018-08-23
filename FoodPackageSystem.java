
public class FoodPackageSystem {
	
	private String Country;
	private String FoodType;
	private String Description;
	private float Price;
	
	
	@Override
	public String toString() {
		return FoodType + "    " + Description + "    " + Country + "    " + "$" + Price;
	}


	public FoodPackageSystem(String country, String foodType, String description, int price) {
		Country = country;
		FoodType = foodType;
		Description = description;
		Price = price;
	}
	
	
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getFoodType() {
		return FoodType;
	}
	public void setFoodType(String foodType) {
		FoodType = foodType;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}

}
