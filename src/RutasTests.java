import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RutasTests {

	@Test
	void createTest() {
		Rutas rts = new Rutas("pruebaGrafo.txt");
		rts.createRoute("Hola", "Adios", 69);
		assertEquals(true,  rts.routeExists("Hola", "Adios"));
	}
	
	@Test
	void deleteTest() {
		Rutas rts = new Rutas("pruebaGrafo.txt");
		rts.createRoute("Dia", "Noche", 13);
		rts.deleteRoute("Dia", "Noche");
		assertEquals(false,  rts.routeExists("Dia", "Noche"));
	}
}
