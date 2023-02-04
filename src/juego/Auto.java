package juego;

import java.awt.*;
import entorno.Entorno;
import entorno.Herramientas;

public class Auto {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	private double factorDeDesplazamiento;
	private Image imagen;

	public Auto(double ancho, double alto, double x, double y, double velocidad, double factorDesplazamiento) {
		
		this.ancho = ancho;
		this.alto = alto;
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.factorDeDesplazamiento = factorDesplazamiento;
		this.imagen = Herramientas.cargarImagen("autoRojo65x35.png");

	}

	public void dibujar(Entorno entorno) {

		entorno.dibujarImagen(imagen, x, y, velocidad < 0 ? 0 : Math.PI);

	}

	public void avanzar() {

		x += velocidad;
	
	}

	public boolean chocasteConEntorno(Entorno entorno) {
	
		return x < 0 || x > entorno.ancho();

	}

	public void volverDelOtroLado(Entorno entorno) {
		
		if (chocasteConEntorno(entorno)) {
		
			if (x < -ancho) {
			
				x = entorno.ancho() + ancho;
			
			}
			
			if (x > entorno.ancho() + ancho) {
			
				x = -ancho;
			
			}
			
			x += velocidad;
		
		}
	
	}

	public boolean siTeFuisteConLaCalle(Entorno entorno) {
	
		return y - alto / 2 == entorno.alto();
	
	}

	public void desplazarEnPantalla(Entorno entorno) {
	
		if (siTeFuisteConLaCalle(entorno)) {

			y = -590;    // esta posicion es el y-300 que tiene la calle al volver arriba
						// sumado el -290 (distancia entre y del auto e y de la calle) de la
						// posicion inicial del auto

			return;
		
		}
		
		y += factorDeDesplazamiento;
	
	}

	public double getAncho() {
	
		return ancho;
	
	}

	public double getAlto() {
	
		return alto;
	
	}

	public double getX() {
	
		return x;
	
	}

	public double getY() {
	
		return y;
	
	}

}