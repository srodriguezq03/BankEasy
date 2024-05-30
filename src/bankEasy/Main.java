package bankEasy;

import bankEasy.vista.InterfazUsuario;
import bankEasy.vista.InterfazUsuarioColores;

public class Main {
	public static void main(String[] args) {
		InterfazUsuario ui = new InterfazUsuario();
		InterfazUsuarioColores uiC = new InterfazUsuarioColores();
		uiC.start();
	}
}
