package juego;

import java.awt.*;
import entorno.Entorno;
import entorno.Herramientas;

public class Tren {

	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	private double factorDeDesplazamiento;
	private Image imagen;

	public Tren(double ancho, double alto, double x, double y, double velocidad, double factorDesplazamiento) {
		
		this.ancho = ancho;
		this.alto = alto;
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.factorDeDesplazamiento = factorDesplazamiento;
		this.imagen=Herramientas.cargarImagen("TrenDoble.png");

	}

	public void dibujar(Entorno entorno) {
		
		entorno.dibujarImagen(imagen, x, y, velocidad < 0 ? 0 : Math.PI);
	
	}

	public void avanzar() {
	
		x += velocidad;
	
	}
//FIXME Avanzar y acelerar hacen lo mismo
	public void acelerar() {
	
		x += 1.2 * velocidad;
	
	}

	public boolean chocasteConEntorno(Entorno entorno) {
	
		return x < 0 || x > entorno.ancho();

	}

	public void volverDelOtroLado(Entorno entorno) {
		
		if (chocasteConEntorno(entorno)) {
		
			if (x + ancho / 2 < 0) {
			
				x = entorno.ancho() + ancho / 2;
			
			}
		
		}
	
	}

	public boolean siTeFuisteConLaCalle(Entorno entorno) {
		
		return y - alto / 2 == entorno.alto();
	
	}

	public void desplazarEnPantalla(Entorno entorno) {
	
		if (siTeFuisteConLaCalle(entorno)) {
		
			y = -560; // esta posicion es el y-300 que tiene la calle al volver arriba
						// sumado el -260 (distancia entre y del tren e y de la calle) de la
						// posicion inicial del tren

			return;
		
		}
		
		y += factorDeDesplazamiento;
	
	}

	public double getX() {
	
		return x;
	
	}

	public double getY() {
	
		return y;
	
	}

	public double getAlto() {
	
		return alto;
	
	}

	public double getAncho() {
	
		return ancho;
	
	}

}