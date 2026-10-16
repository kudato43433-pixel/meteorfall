package com.meteorfall;

import javax.swing.JOptionPane;
import java.awt.GraphicsEnvironment;

public final class Main {
	private static final String TEXT =
		"Meteor Fall — это мод для Minecraft (Fabric), а не отдельная программа.\n\n"
			+ "Как установить:\n"
			+ "1. Установите Fabric Loader и Fabric API.\n"
			+ "2. Скопируйте этот файл в папку mods.\n"
			+ "3. Запустите игру: каждые 40 минут на игрока будет падать метеорит.\n\n"
			+ "Настройки: config/meteorfall.properties (создаётся при первом запуске).";

	private Main() {
	}

	public static void main(String[] args) {
		if (GraphicsEnvironment.isHeadless()) {
			System.out.println(TEXT);
			return;
		}
		JOptionPane.showMessageDialog(null, TEXT, "Meteor Fall", JOptionPane.INFORMATION_MESSAGE);
	}
}
