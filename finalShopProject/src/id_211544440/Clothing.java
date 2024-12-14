package id_211544440;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Clothing extends Product implements Serializable {
	private String size;
	private String color;
	private String gender;

	public Clothing(String name, int quantity, String size, String color, String param1) {
		super(name, quantity);
		this.size = size;
		this.color = color;
		this.gender = param1;
	}

	public String getSize() {
		return size;
	}

	public String getColor() {
		return color;
	}

	public String getGender() {
		return gender;
	}

	@Override
	public String toString() {
		return super.toString() + "\nthe size is: " + size + "\nthe color is: " + color + "\nthe gender: " + gender
				+ "\n";
	}
}
