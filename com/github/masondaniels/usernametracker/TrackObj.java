package com.github.masondaniels.usernametracker;

import java.text.DecimalFormat;
import java.util.Date;

public class TrackObj {

	private long creationDate = 0;

	private static DecimalFormat df = new DecimalFormat("#");

	static {
		df.setMaximumFractionDigits(0);
	}

	/*
	 * This class is terrible. But I don't want to be bothered with cleaning it up
	 * and making everything static. Originally, this class was made for a
	 * MinecraftPlayer class. So it makes sense that most of this stuff has its own
	 * instance/isn't static.
	 */

	public TrackObj(String username) {
		System.out.println("Tracking " + username);
		a(username, 0, 0, 0);
	}

	@SuppressWarnings("deprecation")
	private void a(String query, double a1, double b1, final double c) {
		final double a = (a1 != 0) ? a1 : 1263146630;
		final double b = (b1 != 0) ? b1 : Math.floor(System.currentTimeMillis() / 1000);
		if (a == b) {
			b(query, a, (ok) -> {
				if (ok && (c == a - 1)) {
					creationDate = (long) (a * 1000);
					// Don't care that it's deprecated.
					System.out.println("Found: " + new Date(creationDate).toGMTString());
				}
			});
		} else {
			final double mid = a + Math.floor((b - a) / 2);
			b(query, mid, (ok) -> {
				if (ok) {
					System.out.println("Range: " + df.format(a) + "\t<-|  \t" + df.format(b));
					a(query, a, mid, c);
				} else {
					System.out.println("Range: " + df.format(a) + "\t  |->\t" + df.format(b));
					a(query, mid + 1, b, mid);
				}
			});
		}
	}

	private void b(String query, double num, Callback callback) {
		HttpRequest req = new HttpRequest(
				"https://api.mojang.com/users/profiles/minecraft/" + query + "?at=" + df.format(num));
		String a = req.getRequest();
		callback.callback((a != null && !a.isEmpty()));
	}

}

abstract interface Callback {
	public abstract void callback(boolean ok);
}