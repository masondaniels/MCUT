package com.github.masondaniels.argumentparser;

public enum ArgumentRegistry {

	/*
	 * Modify the enum below to add arguments & their descriptions.
	 * 
	 * It doesn't matter the order users enter args in. For example: java -jar
	 * yourJar.jar -username hi -someBool true could also be java -jar yourJar.jar
	 * -someBool true -username hi
	 */

	USERNAME("username", "<username>", "The argument for the Minecraft username.");

	public static final String PREFIX = "-";

	private String key;
	private String values;
	private String syntax;
	private String description;

	ArgumentRegistry(String key, String values, String description) {
		this.key = key;
		this.values = values;
		this.description = description;
		syntax = createSyntax();
	}

	private String createSyntax() {
		StringBuilder values = new StringBuilder();
		return PREFIX + key + " " + this.values;
	}

	public String getSyntax() {
		return syntax;
	}

	public String getKey() {
		return key;
	}

	public String getValueString() {
		return values;
	}

	public static ArgumentRegistry getByKey(String key) throws InvalidArgumentException {
		for (int i = 0; i < ArgumentRegistry.values().length; i++) {
			ArgumentRegistry reg = ArgumentRegistry.values()[i];
			if (reg.getKey().equalsIgnoreCase(key)) {
				return reg;
			}
		}
		throw new InvalidArgumentException(key + " is not a valid argument");
	}

	public String getDescription() {
		return description;
	}

}
