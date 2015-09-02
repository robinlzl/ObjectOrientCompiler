package dijkstra.utility;

import static org.junit.Assert.*;

import org.junit.Test;
import static dijkstra.utility.TypeCheckRunner.*;

public class Typecheckrunnertest {

	@Test
	public void test() {
		String s = "program test\n"
				+ "fun myfunc () : int { return true  }\n"
				+ "int a\n"
				+ "a <- myfunc()";
		TypeCheckRunner typecheck = new TypeCheckRunner();
		typecheck.check(s);
	}

}
