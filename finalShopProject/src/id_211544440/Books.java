package id_211544440;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Books extends Product implements Serializable {
	private String authorName;
	private int numOfPages;

	public Books(String name, int quantity, String authorName, int numOfPages) {
		super(name, quantity);
		this.authorName = authorName;
		this.numOfPages = numOfPages;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(int numOfPages) {
		this.numOfPages = numOfPages;
	}

	@Override
	public String toString() {
		return super.toString() + "\nauthor name is: " + authorName + "\nthe number of pages are: " + numOfPages + "\n";
	}

}
