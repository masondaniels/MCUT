package com.github.masondaniels.usernametracker;

import com.github.masondaniels.argumentparser.ArgumentParser;
import com.github.masondaniels.argumentparser.ArgumentRegistry;
import com.github.masondaniels.argumentparser.InvalidArgumentException;

public class Main {

	/*
	 * Honestly, the thing I am most proud of with this project is the argument
	 * parsing. All the other stuff I did years ago and just sort of mashed
	 * everything together without quality control/assurance.
	 */

	public static void main(String[] args) {
		ArgumentParser parser = new ArgumentParser(args);
		String username = null;
		try {
			username = parser.getArgumentsFor(ArgumentRegistry.USERNAME);
		} catch (InvalidArgumentException e) {
			ArgumentParser.doTooFewArguments("Username argument cannot be null.");
		}
		new TrackObj(username);
	}

}
