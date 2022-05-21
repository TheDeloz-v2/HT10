import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RutasTests {

	@Test
	void modifyTest() {
		Rutas rts = new Rutas("pruebaGrafo.txt");
		rts.createRoute("Hola", "Adios", 69);
		assertEquals(true,  rts.routeExists("Hola", "Adios"));
	}
}
