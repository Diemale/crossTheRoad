package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Calle {

	private double x;
	private double y;
	private int alto;
	private double factorDeDesplazamiento;
	private Image imagen;

	public Calle(double x, double y, int alto, double velocidad) {

		this.x = x;
		this.y = y;
		this.alto = alto;
		this.factorDeDesplazamiento = velocidad;
		this.imagen = Herramientas.cargarImagen("CalleEsperanzaVia.png");

	}

	public void dibujar(Entorno entorno) {

		entorno.dibujarImagen(imagen, x, y, Math.PI);

	}

	public void desplazarEnPantalla(Entorno entorno) {

		if (salisteDePantalla(entorno)) {

			y = -300; // Es un poco menos de la mitad del alto, no puede pasarse alto/2 porque se desfazan

			return;

		}

		y += factorDeDesplazamiento;

	}

	private boolean salisteDePantalla(Entorno entorno) {

		return y - alto / 2 == entorno.alto();

	}

	public double alturaDelCentroDeLaCalle() {

		return y;

	}

}
