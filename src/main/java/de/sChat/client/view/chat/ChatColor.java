package de.sChat.client.view.chat;


public class ChatColor {
	private int i = 0;

	public String getColor() {
		return color.getColorByIndex(i).getColor();
	}

	public void switchToNextColor() {
		if (i < color.getMaxIndex()) {
			i++;
			return;
		}
		i = 0;
	}
}

enum color {
	gray("gray"), black("black"), red("red"), pink("pink"), orange("orange"), yellow(
			"yellow"), green("green"), magenta("magenta"), cyan("cyan"), blue(
			"blue"), lightGray("lightgray"), darkGray("darkgray");
	private String ownCol;
	private static color[] col = color.values();

	color(String col) {
		this.ownCol = col;
	}

	public static color getColorByIndex(int i) {

		return col[i];
	}

	public static int getMaxIndex() {
		return col.length - 1;
	}

	public String getColor() {
		return ownCol;
	}
}
