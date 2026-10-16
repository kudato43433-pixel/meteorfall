package com.meteorfall;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class MeteorConfig {
	public double intervalMinutes = 40.0;
	public float explosionPower = 5.0f;
	public boolean griefing = true;
	public boolean loot = true;
	public boolean overworldOnly = true;
	public double minDistance = 20.0;
	public double maxDistance = 40.0;
	public boolean announce = true;

	public int intervalTicks() {
		return (int) Math.max(20, Math.round(intervalMinutes * 60.0 * 20.0));
	}

	public static MeteorConfig load() {
		MeteorConfig cfg = new MeteorConfig();
		Path file = FabricLoader.getInstance().getConfigDir().resolve("meteorfall.properties");

		if (Files.exists(file)) {
			Properties props = new Properties();
			try (Reader reader = Files.newBufferedReader(file)) {
				props.load(reader);
				cfg.intervalMinutes = parseDouble(props, "interval_minutes", cfg.intervalMinutes);
				cfg.explosionPower = (float) parseDouble(props, "explosion_power", cfg.explosionPower);
				cfg.griefing = parseBool(props, "block_damage", cfg.griefing);
				cfg.loot = parseBool(props, "loot", cfg.loot);
				cfg.overworldOnly = parseBool(props, "overworld_only", cfg.overworldOnly);
				cfg.minDistance = parseDouble(props, "min_distance", cfg.minDistance);
				cfg.maxDistance = parseDouble(props, "max_distance", cfg.maxDistance);
				cfg.announce = parseBool(props, "announce", cfg.announce);
			} catch (IOException e) {
				MeteorFallMod.LOGGER.warn("Не удалось прочитать конфиг, используются значения по умолчанию", e);
			}
		}
		if (cfg.maxDistance < cfg.minDistance) {
			cfg.maxDistance = cfg.minDistance;
		}

		cfg.save(file);
		return cfg;
	}

	private void save(Path file) {
		try (Writer writer = Files.newBufferedWriter(file)) {
			writer.write("# Meteor Fall\n");
			writer.write("# Интервал между метеоритами для каждого игрока, в минутах\n");
			writer.write("interval_minutes=" + intervalMinutes + "\n");
			writer.write("# Мощность взрыва (TNT = 4)\n");
			writer.write("explosion_power=" + explosionPower + "\n");
			writer.write("# Ломает ли взрыв блоки\n");
			writer.write("block_damage=" + griefing + "\n");
			writer.write("# Выпадает ли добыча\n");
			writer.write("loot=" + loot + "\n");
			writer.write("# Метеориты только в Верхнем мире\n");
			writer.write("overworld_only=" + overworldOnly + "\n");
			writer.write("# Расстояние от игрока до места падения, в блоках\n");
			writer.write("min_distance=" + minDistance + "\n");
			writer.write("max_distance=" + maxDistance + "\n");
			writer.write("# Писать игроку в чат о падении метеорита\n");
			writer.write("announce=" + announce + "\n");
		} catch (IOException e) {
			MeteorFallMod.LOGGER.warn("Не удалось записать конфиг", e);
		}
	}

	private static double parseDouble(Properties props, String key, double fallback) {
		try {
			return Double.parseDouble(props.getProperty(key, String.valueOf(fallback)).trim());
		} catch (NumberFormatException e) {
			return fallback;
		}
	}

	private static boolean parseBool(Properties props, String key, boolean fallback) {
		String value = props.getProperty(key);
		return value == null ? fallback : Boolean.parseBoolean(value.trim());
	}
}
