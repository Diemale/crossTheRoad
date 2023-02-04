package juego;

import java.awt.*;
import entorno.Entorno;
import entorno.Herramientas;

public class Kamehameha {

	private double x;
	private double y;
	private double tamanio;
	private double velocidadDelKamehameha;
	private Image imagen;

	public Kamehameha(double x, double y, double velocidadDelKamehameha) {

		this.x = x;
		this.y = y;
		this.tamanio = 42;
		this.velocidadDelKamehameha = velocidadDelKamehameha;
		this.imagen = Herramientas.cargarImagen("Kame3.png");

	}

	public void dibujar(Entorno entorno) {
		
		entorno.dibujarImagen(imagen, x, y, 0);

	}

	public void moverseHaciaAdelante() {
		
		if (!chocasteEntorno()) {
		
			y -= velocidadDelKamehameha;
		
		}
	
	}

//Colisiones
	
	public boolean chocasteEntorno() {
	
		return y-tamanio/2<=0;
	
	}

	public boolean chocasteAuto(Auto auto) {
	
			return auto != null && (x + tamanio / 2 > auto.getX() - auto.getAncho() / 2
					&& x - tamanio / 2 < auto.getX() + auto.getAncho() / 2
					&& y + tamanio > auto.getY() - auto.getAlto() / 2
					&& y - tamanio < auto.getY() + auto.getAlto() / 2);
				
			}
	
	public boolean chocasteTren(Tren tren) {
		
		return tren != null && x + tamanio / 2 > tren.getX() - tren.getAncho()/2 
				&& x - tamanio / 2 < tren.getX() + tren.getAncho() / 2 && y + tamanio > tren.getY() - tren.getAlto() / 2 
				&& y - tamanio < tren.getY() + tren.getAlto() / 2;
		
	}

}
