package juego;

import java.awt.*;
import entorno.Entorno;
import entorno.Herramientas;

public class Conejo {

	private double x;
	private double y;
	private double tamanio;
	private double distanciaDeSalto;
	private double factorDeDesplazamiento;
	private Image imagen;

	public Conejo(double x, double y, double factorDesplazamiento) {

		this.x = x;
		this.y = y;
		this.tamanio = 20;
		this.factorDeDesplazamiento = factorDesplazamiento;
		this.imagen = Herramientas.cargarImagen("Conejo20x40.png");
		this.distanciaDeSalto = 60;

	}

	public void dibujar(Entorno entorno) {

		entorno.dibujarImagen(imagen, x, y, 0);

	}

	public void esperar() {

		y += factorDeDesplazamiento;

	}

	public void reposicionar(Entorno entorno,Auto[] autos, Tren tren) {
		
		x = entorno.ancho() / 2;
		y = entorno.alto() - 130;
		
		while (chocasteAuto(autos) || chocasteTren(tren)) {
			y -= 100;
		}

	}

	public void saltarIzquierda() {
	
		if (!chocasteIzquierda()) {
			
			x -= distanciaDeSalto;
			
		}
	}

	public void saltarDerecha(Entorno entorno) {
		
		if (!chocasteDerecha(entorno)) {
		
			x += distanciaDeSalto;
			
		}
		
	}

	public void saltarAdelante() {
		
		if (!chocasteArriba()) {
		
			y -= distanciaDeSalto;
		}
	}

	public Kamehameha lanzarKamehameha() {
		
		return new Kamehameha(x, y - 20, 5);
	
	}

	public boolean chocasteAbajo(Entorno entorno) {
		
		return y + tamanio >= entorno.alto();
	
	}

	public boolean chocasteArriba() {
	
		return y - (tamanio) < distanciaDeSalto;
	
	}

	public boolean chocasteIzquierda() {
		
		return x - (tamanio / 2) < distanciaDeSalto;
	
	}

	public boolean chocasteDerecha(Entorno entorno) {
	
		return x + (tamanio / 2) > entorno.ancho() - distanciaDeSalto;
	
	}

	public boolean chocasteAuto(Auto[] autos) {
	
		for (Auto auto : autos) {
		
			if (auto != null && (x + tamanio / 2 > auto.getX() - auto.getAncho() / 2
					&& x - tamanio / 2 < auto.getX() + auto.getAncho() / 2
					&& y + tamanio > auto.getY() - auto.getAlto() / 2
					&& y - tamanio < auto.getY() + auto.getAlto() / 2)) {
				
				return true;
		
			}
			
		}
		
		return false;
	
	}

	public boolean chocasteTren(Tren tren) {
	
		return tren != null && x + tamanio / 2 > tren.getX() - tren.getAncho() / 2
				&& x - tamanio / 2 < tren.getX() + tren.getAncho() / 2 && y + tamanio > tren.getY() - tren.getAlto() / 2
				&& y - tamanio < tren.getY() + tren.getAlto() / 2;

	}

}
