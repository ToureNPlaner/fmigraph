package fmi.graph.tools;

import java.security.SecureRandom;
import fmi.graph.metaio.Value;

public class General {

	private static final SecureRandom sr = new SecureRandom();

	public static String convertToHex(byte[] data) {
		final StringBuilder hexbuilder = new StringBuilder(data.length * 2);
		for (byte b : data) {
			hexbuilder.append(Integer.toHexString((b >>> 4) & 0x0F));
			hexbuilder.append(Integer.toHexString(b & 0x0F));
		}
		return hexbuilder.toString();
	}

	public static String createRandomIdValue() {
		byte[] idb = new byte[16];
		sr.nextBytes(idb);
		return convertToHex(idb);
	}

}
