import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FloydTests {
	final static int INF = 99999, V = 4;

	@Test
	void FloydTest() {
		int graph[][] = { {0, 5, INF, 10},
                {INF, 0, 3, INF},
                {INF, INF, 0,   1},
                {INF, INF, INF, 0}
              };
		
		Floyd fld = new Floyd(graph);
		
		int finalgraph[][] = { {0, 5, 8, 9},
                {INF, 0,   3, 4},
                {INF, INF, 0,   1},
                {INF, INF, INF, 0}
              };
		
		assertEquals(finalgraph[0][0], fld.getMatrix()[0][0]);
		assertEquals(finalgraph[1][1], fld.getMatrix()[1][1]);
		assertEquals(finalgraph[2][2], fld.getMatrix()[2][2]);
		assertEquals(finalgraph[3][3], fld.getMatrix()[3][3]);
		
	}

}
