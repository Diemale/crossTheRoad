 package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;
import java.awt.*;
import javax.sound.sampled.Clip;

public class Juego extends InterfaceJuego {

	private Entorno entorno;
	private Conejo conejo;
	private Kamehameha kame;
	private Auto[] autos;
	private Calle avenidaLosConejos;
	private Calle boulevardConejoSaltarin;
	private Tren tren;
	private Clip fondo;
	private int puntos;
	private int saltos;
	private boolean gameOver;

	public Juego() {
		
		this.entorno = new Entorno(this, "Juego Conejo", 800, 600);
		conejo = new Conejo(entorno.ancho() / 2, entorno.alto() - 130, 1);
		kame = null;
		autos = new Auto[25];
		avenidaLosConejos = new Calle(entorno.ancho() / 2, -600, 610, 1);
		boulevardConejoSaltarin = new Calle(entorno.ancho() / 2, 0, 610, 1);
		tren = new Tren(634, 90, -250, boulevardConejoSaltarin.alturaDelCentroDeLaCalle() - 260, -1.1, 1);
		fondo = Herramientas.cargarSonido("Efecto fondo.wav");
		puntos = 0;
		saltos = 0;
		gameOver = false;
		
		inicializarAutosEnPantalla();
		fondo.start();
		fondo.loop(5);
		this.entorno.iniciar();
	
	}

	public void tick() {

		//Primero agregamos todo lo que queremos ver en pantalla sin importar el valor de gameOver.
		
		entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, entorno.ancho(), entorno.alto(), 0,
				Color.GREEN); //Fondo verde para representar al pasto.
		avenidaLosConejos.dibujar(entorno);
		avenidaLosConejos.desplazarEnPantalla(entorno);
		boulevardConejoSaltarin.dibujar(entorno);
		boulevardConejoSaltarin.desplazarEnPantalla(entorno);
		
		if (kame != null) {
			
			kame.dibujar(entorno);
			kame.moverseHaciaAdelante();
		
		}
		
		for (Auto auto : autos) {
			
			if (auto != null) {
			
				auto.dibujar(entorno);
				auto.desplazarEnPantalla(entorno);
			
			}

		}
		
		if (tren != null) {
		
			tren.dibujar(entorno);
			tren.desplazarEnPantalla(entorno);
			tren.volverDelOtroLado(entorno);
		
		}
		
		conejo.dibujar(entorno);
		entorno.cambiarFont("Serif", 15, Color.BLACK);
		entorno.escribirTexto("Puntos acumulados: " + puntos, entorno.ancho() - 200, 50);
		entorno.escribirTexto("Cantidad de saltos: " + saltos, 50, 50);

		if (gameOver) { //Comportamiento cuando se pierde el juego
			entorno.cambiarFont("Serif", 40, Color.BLACK);
			entorno.escribirTexto("FIN DEL JUEGO", entorno.ancho() / 2 - 150, entorno.alto() / 2);
			entorno.cambiarFont("Serif", 20, Color.BLACK);
			entorno.escribirTexto("Presione ENTER para volver a jugar", entorno.ancho() / 2 - 150,
					entorno.alto() / 2 + 100);
			if (entorno.sePresiono(entorno.TECLA_ENTER)) {
				
				conejo.reposicionar(entorno,autos,tren);
				gameOver = false;
				saltos = 0;
				puntos = 0;

			}

			return;
		}
		

		conejo.esperar();
			
		for (Auto auto: autos) {
				
			if (auto!=null) {
				
				auto.avanzar();
				auto.volverDelOtroLado(entorno);
				
			}
			
		}
			
		if (entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono('w')) {
				
			conejo.saltarAdelante();
			saltos++;
			
		}
			
		if (entorno.sePresiono(entorno.TECLA_DERECHA) || entorno.sePresiono('d')) {
			
			conejo.saltarDerecha(entorno);
			
		}
			
		if (entorno.sePresiono(entorno.TECLA_IZQUIERDA) || entorno.sePresiono('a')) {
			
			conejo.saltarIzquierda();
			
		}

		if (tren != null && saltos % 50 == 0) {
			
			tren.acelerar();
		
		}

		if (tren != null && saltos >= 10) {
			
			tren.avanzar();
		
		}

		if (tren == null && saltos % 80 == 0) {
			
			tren = new Tren(634, 90, -250, boulevardConejoSaltarin.alturaDelCentroDeLaCalle() - 260, -1.1, 1);
			
		}
			
		if (kame == null && entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			
			kame = conejo.lanzarKamehameha();

		}
			
		if (kame != null && kame.chocasteEntorno()) {
			 
			kame = null;
			
		}

		if (kame != null && tren != null) {

			if (kame.chocasteTren(tren)) {

				tren = null;
				kame = null;
				puntos += 5;

				}

			}

		for (int i = 0; i < autos.length; i++) {

			if (kame != null && kame.chocasteAuto(autos[i])) {

				autos[i] = null;
				kame = null;
				puntos += 5;

			}

		}
			
		if (boulevardConejoSaltarin.alturaDelCentroDeLaCalle() == 0) {

			inicializarAutosEnPantalla();

		}
 
		
	//Condicion de fin del juego	
		if (conejo.chocasteAbajo(entorno) || conejo.chocasteAuto(autos) || conejo.chocasteTren(tren)) {

			gameOver = true;

		}
		
		
	}

	private void inicializarAutosEnPantalla() {

		int distanciaEntreAutos = 200;
		int posicionHorizontalEnCalle = 0;
		for (int i = 0; i < 2; i++) {
			
			autos[i] = new Auto(65, 30, 100 + posicionHorizontalEnCalle * distanciaEntreAutos, -735, -1.2, 1);
			posicionHorizontalEnCalle++;
		
		}

		posicionHorizontalEnCalle = 0;
		for (int i = 2; i >= 2 && i < 5; i++) {
		
			autos[i] = new Auto(65, 30, 100 + posicionHorizontalEnCalle * distanciaEntreAutos, -670, 0.5, 1);
			posicionHorizontalEnCalle++;
		
		}
		
		posicionHorizontalEnCalle = 0;
		for (int i = 5; i >= 5 && i < 7; i++) {
			
			autos[i] = new Auto(65, 30, 100 + posicionHorizontalEnCalle * distanciaEntreAutos, -600, -1.6, 1);
			posicionHorizontalEnCalle++;
		
		}
		
		posicionHorizontalEnCalle = 0;
		for (int i = 7; i >= 7 && i < 10; i++) {

			autos[i] = new Auto(65, 30, 100 + posicionHorizontalEnCalle * distanciaEntreAutos, -530, 1.0, 1);
			posicionHorizontalEnCalle++;
		
		}
		
		posicionHorizontalEnCalle = 0;
		for (int i = 10; i >= 10 && i < 12; i++) {
		
			autos[i] = new Auto(65, 30, 0 + posicionHorizontalEnCalle * distanciaEntreAutos, -460, -0.5, 1);
			posicionHorizontalEnCalle++;
		
		}
		
		posicionHorizontalEnCalle = 0;
		for (int i = 12; i >= 12 && i < 15; i++) {
		
			autos[i] = new Auto(65, 30, 0 + posicionHorizontalEnCalle * distanciaEntreAutos, -130, 1, 1);
			posicionHorizontalEnCalle++;
		
		}
		
		posicionHorizontalEnCalle = 0;
		for (int i = 15; i >= 15 && i < 17; i++) {
			
			autos[i] = new Auto(65, 30, 0 + posicionHorizontalEnCalle * distanciaEntreAutos, -70, -1.6, 1);
			posicionHorizontalEnCalle++;

		}

		posicionHorizontalEnCalle = 0;
		for (int i = 17; i >= 17 && i < 20; i++) {
		
			autos[i] = new Auto(65, 30, 0 + posicionHorizontalEnCalle * distanciaEntreAutos, 0, 1.6, 1);
			posicionHorizontalEnCalle++;
		
		}
		
		posicionHorizontalEnCalle = 0;
		for (int i = 20; i >= 20 && i < 22; i++) {
		
			autos[i] = new Auto(65, 30, 0 + posicionHorizontalEnCalle * distanciaEntreAutos, 70, -1.5, 1);
			posicionHorizontalEnCalle++;
		
		}
		
		posicionHorizontalEnCalle = 0;
		for (int i = 22; i < autos.length; i++) {
			
			autos[i] = new Auto(65, 30, 0 + posicionHorizontalEnCalle * distanciaEntreAutos, 140, 1.3, 1);
			posicionHorizontalEnCalle++;
		
		}

	}

	@SuppressWarnings("unused")

	public static void main(String[] args) {

		Juego juego = new Juego();
	}

}