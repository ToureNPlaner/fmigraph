package fmi.graph.metaio;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Niklas Schnelle
 */
public class MetaReaderTest {
	@Test
	public void testKVPattern() {
		Pattern p = MetaReader.kvpattern;
		Matcher m = p.matcher("");
		m.reset("# Key : Value").matches();
		assertEquals("Key", m.group(1));
		assertEquals("Value", m.group(2).trim());

		m.reset("# Key : Value ").matches();
		assertEquals("Key", m.group(1));
		assertEquals("Value", m.group(2).trim());

		m.reset("# Key :Value").matches();
		assertEquals("Key", m.group(1));
		assertEquals("Value", m.group(2).trim());

		m.reset("#Key: Value").matches();
		assertEquals("Key", m.group(1));
		assertEquals("Value", m.group(2).trim());

		m.reset("# Key: Value").matches();
		assertEquals("Key", m.group(1));
		assertEquals("Value", m.group(2).trim());
		// UTF
		m.reset("# Key: Ṽäluè").matches();
		assertEquals("Key", m.group(1));
		assertEquals("Ṽäluè", m.group(2).trim());
	}

	public String t1Normal;
	public String t2MissingEnd;
	public String t3EmptyHeader;

	@Before
	public void AddTestData() {
		t1Normal = "# Comment in first line\n" + "# timestamp : 1360159954\n"
				+ "# First Comment for version\n"
				+ "# Second Comment for version\n" + "# version : 1337\n"
				+ "# End comment\n" + "\n" + "1337\n" + "23\n";
		t2MissingEnd = "# Comment in first line\n"
				+ "# timestamp : 1360159954\n"
				+ "# First Comment for version\n"
				+ "# Second Comment for version\n" + "# version : 1337\n"
				+ "# End comment\n" + "1337\n" + "23\n";
		t3EmptyHeader = "1337\n" + "23\n";

	}

	private BufferedReader sbfr(String s) {
		return new BufferedReader(new StringReader(s));
	}

	private void checkT1MetaData(MetaData m) {
		assertEquals("1360159954", m.get("timestamp").value);
		assertNotNull(m.get("timestamp").asDate());
		assertEquals("Comment in first line", m.get("timestamp").comments[0]);
		assertEquals(2, m.get("version").comments.length);
		assertEquals("First Comment for version", m.get("version").comments[0]);
		assertEquals("Second Comment for version", m.get("version").comments[1]);

		// Test end comment is there
		assertEquals(1, m.comments.size());
		assertEquals("End comment", m.comments.get(0));
	}

	@Test
	public void testReadMetaDataCorrect() throws Exception {
		BufferedReader br = sbfr(t1Normal);
		MetaReader r = new MetaReader();
		MetaData m = r.readMetaData(br);
		checkT1MetaData(m);
		// Test whether we read too far
		assertEquals("1337", br.readLine());

	}

	@Test
	public void testReadMetaDataMissingEnd() throws Exception {
		BufferedReader br = sbfr(t2MissingEnd);
		MetaReader r = new MetaReader();
		MetaData m = r.readMetaData(br);
		checkT1MetaData(m);
		// Test whether we read too far
		assertEquals("1337", br.readLine());
	}

	@Test
	public void testEmptyHeader() throws Exception {
		BufferedReader br = sbfr(t3EmptyHeader);
		MetaReader r = new MetaReader();
		MetaData m = r.readMetaData(br);
		// Test whether we read too far
		assertEquals("1337", br.readLine());
	}

}
