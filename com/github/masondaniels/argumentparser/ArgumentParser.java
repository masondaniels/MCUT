package com.github.masondaniels.argumentparser;

import java.util.ArrayList;

public class ArgumentParser {

	private ArgumentParserStorage[] storage;

	public ArgumentParser(String[] args) {
		if (args.length < 1) {
			doTooFewArguments("To use, please add arguments to JAR.");
		}
		parseArguments(args);
	}

	private void parseArguments(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i] + " ");
		}
		String allArguments = sb.toString().trim();

		ArrayList<ArgumentParserStorage> list = new ArrayList<ArgumentParserStorage>();

		for (int i = 1; i < allArguments.split(ArgumentRegistry.PREFIX).length; i++) {
			String current = allArguments.split(ArgumentRegistry.PREFIX)[i].trim();
			String argumentKey = current.split(" ")[0];
			String argumentString = null;
			StringBuilder b = new StringBuilder();
			for (int j = 1; j < current.split(" ").length; j++) {
				b.append(current.split(" ")[j] + " ");
			}
			argumentString = b.toString().trim();
			try {
				ArgumentRegistry reg = ArgumentRegistry.getByKey(argumentKey);
				ArgumentParserStorage store = new ArgumentParserStorage(reg, argumentString);
				list.add(store);
			} catch (InvalidArgumentException e) {
				doTooFewArguments("Your string \"" + allArguments + "\" was invalid. " + (argumentKey.charAt(0) + "").toUpperCase() + argumentKey.toLowerCase().substring(1) + " does not exist.");
				// Do nothing, just ignore it
			}
		}
		storage = list.toArray(new ArgumentParserStorage[list.size()]);
	}

	public static void doTooFewArguments(String string) {
		System.out.println(string);
		StringBuilder a = new StringBuilder();
		for (int i = 0; i < ArgumentRegistry.values().length; i++) {
			if (i == ArgumentRegistry.values().length - 1) {
				a.append(ArgumentRegistry.PREFIX + ArgumentRegistry.values()[i].getKey());
			} else {
				a.append(ArgumentRegistry.PREFIX + ArgumentRegistry.values()[i].getKey() + ", ");
			}
		}
		System.out.println("Possible arguments: " + a.toString());
		for (int i = 0; i < ArgumentRegistry.values().length; i++) {
			System.out.println();
			System.out.println(ArgumentRegistry.values()[i].getKey().toUpperCase() + " ARGUMENT:");
			System.out.println("Syntax: " + ArgumentRegistry.values()[i].getSyntax());
			System.out.println("Description: " + ArgumentRegistry.values()[i].getDescription());
		}
		System.exit(0);
	}

	public ArgumentParserStorage[] getStorage() {
		return storage;
	}

	public String getArgumentsFor(ArgumentRegistry reg) throws InvalidArgumentException {
		for (int i = 0; i < storage.length; i++) {
			ArgumentRegistry reg1 = storage[i].getReg();
			if (reg1.compareTo(reg) == 0) {
				return storage[i].getArgumentValues();
			}
		}
		throw new InvalidArgumentException("Couldn't find required field " + reg.getKey() + ".");
	}

}
