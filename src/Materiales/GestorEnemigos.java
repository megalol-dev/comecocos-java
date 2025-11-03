package Materiales;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.File;


public class GestorEnemigos {

    // Variables para cada fantasma
    private Enemigo fantasmaRojo;
    private Enemigo fantasmaVerde;
    private Enemigo fantasmaMorado;
    
   
    
    // posición inicial de los fantasmas
    private int filaInicialRojo;
    private int columnaInicialRojo;
    
    private int filaInicialVerde;
    private int columnaInicialVerde;
    
    private int filaInicialMorado;
    private int columnaInicialMorado;
    
    // Variable para controlar el estado de modo comido -> permite comerte a los fantasmas
    private boolean modoComido = false;
    


    // Constructor
    public GestorEnemigos(int tamCelda) {
    	
        // Asignar casilla de inicio
    	// Fantasma ROJO
        this.filaInicialRojo = 9;
        this.columnaInicialRojo = 8;
        
        // Fantasma VERDE
        this.filaInicialVerde = 10;
        this.columnaInicialVerde = 7;
        
        // Fantasma MORADO
        this.filaInicialMorado = 10;
        this.columnaInicialMorado = 9;

        // Crear enemigos
        fantasmaRojo = new Enemigo(filaInicialRojo, columnaInicialRojo, tamCelda, "rojo");
        fantasmaVerde = new Enemigo(filaInicialVerde, columnaInicialVerde, tamCelda, "verde");
        fantasmaMorado = new Enemigo(filaInicialMorado, columnaInicialMorado, tamCelda, "morado");

        // Establecer referencia al gestor para evitar colisiones entre ellos mismos
        fantasmaRojo.setGestorEnemigos(this);
        fantasmaVerde.setGestorEnemigos(this);
        fantasmaMorado.setGestorEnemigos(this);
    }//end constructor

    
    //metodo para usar sonidos
    private void reproducirSonido(String ruta) {
        try {
            File archivoSonido = new File(ruta);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para dibujar todos los enemigos
    public void dibujar(Graphics g, int offsetY) {
        fantasmaRojo.dibujar(g, offsetY);
        fantasmaVerde.dibujar(g, offsetY);
        fantasmaMorado.dibujar(g, offsetY);
    }

    // Getter por si lo necesitas luego
    public Enemigo getFantasmaRojo() {
        return fantasmaRojo;
    }
    
    
    // metedo para obtener donde esta el jugador en el mapa
    // despues el fantasma rojo llama al metodo de mover hacia el jugador
    public void actualizar(Jugador jugador, Mapa mapa, int nivel) {
        // Cada enemigo gestiona internamente si está en modo comido, huida o persecución
        fantasmaRojo.mover(jugador, mapa, nivel);
        fantasmaVerde.mover(jugador, mapa, nivel);
        fantasmaMorado.mover(jugador, mapa, nivel);
    }
    
    
    // Metodo que detecta si el jugador fue tocado
    public boolean jugadorHaSidoTocado(Jugador jugador, Strat_Game game) {
        boolean colision = false;

        // ==== Fantasma Rojo ====
        if (jugador.getFila() == fantasmaRojo.getFila() &&
            jugador.getColumna() == fantasmaRojo.getColumna()) {
        	
        	
        	if (jugador.estaEnPowerUp()) {
        	    // Jugador en modo PowerUp: come al fantasma
        	    if (!fantasmaRojo.esModoComido()) { 
        	        fantasmaRojo.comer(); 
        	        reproducirSonido("audio/EnemigoComido.wav"); // ✅ Ahora aquí
        	        game.score += 500; 
        	        game.popups.add(new ScorePopup(fantasmaRojo.getX() * 32, fantasmaRojo.getY() * 32, "+500"));
        	        game.checkVidasExtra();
        	    }
        	    colision = false;
        	} else {
        	    // Jugador normal: pierde vida
        	    reproducirSonido("audio/JugadorComido.wav"); // ✅ Sonido de jugador comido
        	    colision = true;
        	}
        	
        }
        
        

        // ==== Fantasma Verde ====
        if (jugador.getFila() == fantasmaVerde.getFila() &&
            jugador.getColumna() == fantasmaVerde.getColumna()) {


         	if (jugador.estaEnPowerUp()) {
        	    // Jugador en modo PowerUp: come al fantasma
        	    if (!fantasmaVerde.esModoComido()) { 
        	        fantasmaVerde.comer(); 
        	        reproducirSonido("audio/EnemigoComido.wav"); // ✅ Ahora aquí
        	        game.score += 500; 
        	        game.popups.add(new ScorePopup(fantasmaVerde.getX() * 32, fantasmaVerde.getY() * 32, "+500"));
        	        game.checkVidasExtra();
        	    }
        	    colision = false;
        	} else {
        	    // Jugador normal: pierde vida
        	    reproducirSonido("audio/JugadorComido.wav"); // ✅ Sonido de jugador comido
        	    colision = true;
        	}
        	
        }

        
        // ==== Fantasma Morado ====
        if (jugador.getFila() == fantasmaMorado.getFila() &&
            jugador.getColumna() == fantasmaMorado.getColumna()) {


         	if (jugador.estaEnPowerUp()) {
        	    // Jugador en modo PowerUp: come al fantasma
        	    if (!fantasmaMorado.esModoComido()) { 
        	        fantasmaMorado.comer(); 
        	        reproducirSonido("audio/EnemigoComido.wav"); // ✅ Ahora aquí
        	        game.score += 500; 
        	        game.popups.add(new ScorePopup(fantasmaMorado.getX() * 32, fantasmaMorado.getY() * 32, "+500"));
        	        game.checkVidasExtra();
        	    }
        	    colision = false;
        	} else {
        	    // Jugador normal: pierde vida
        	    reproducirSonido("audio/JugadorComido.wav"); // ✅ Sonido de jugador comido
        	    colision = true;
        	}
        	
        }


        return colision;
    }// end metodo JugadorHaSidoTocado




    
    
    // Metodo para las posicioens ocupadas de los enemigos
    // Comprueba si un enemgio peude moverser a una celda si superponerse con otro
    public List<int[]> getPosicionesOcupadas(Enemigo quienPregunta) {
        List<int[]> ocupadas = new ArrayList<>();

        if (fantasmaRojo != quienPregunta) {
            ocupadas.add(new int[]{fantasmaRojo.getFila(), fantasmaRojo.getColumna()});
        }
        if (fantasmaVerde != quienPregunta) {
            ocupadas.add(new int[]{fantasmaVerde.getFila(), fantasmaVerde.getColumna()});
        }
        if (fantasmaMorado != quienPregunta) {
            ocupadas.add(new int[]{fantasmaMorado.getFila(), fantasmaMorado.getColumna()});
        }

        return ocupadas;
    }//end 
    
    
    
    // Fantasma rojo vuelve a su posición inicial
    public void reiniciarEnemigos() {
        fantasmaRojo.setPosicion(filaInicialRojo, columnaInicialRojo);
        fantasmaVerde.setPosicion(filaInicialVerde, columnaInicialVerde);
        fantasmaMorado.setPosicion(filaInicialMorado, columnaInicialMorado);
    }
    
    // Activa del modo miedo de los enemgios
    public void activarModoMiedo() {
        fantasmaRojo.activarModoMiedo();
        fantasmaVerde.activarModoMiedo();
        fantasmaMorado.activarModoMiedo();
    }
    
    
    
    public void activarModoHuirEnemigos() {
        fantasmaRojo.activarModoHuir();
        fantasmaVerde.activarModoHuir();
        fantasmaMorado.activarModoHuir();
        
       
    }
    


    public void desactivarModoHuirEnemigos() {
        // Solo desactivar el modo huir si NO están en modo comido

        if (!fantasmaRojo.estaEnModoComido()) {
            fantasmaRojo.desactivarModoHuir();
        }

        if (!fantasmaVerde.estaEnModoComido()) {
            fantasmaVerde.desactivarModoHuir();
        }
        
        if (!fantasmaMorado.estaEnModoComido()) {
            fantasmaMorado.desactivarModoHuir();
        }
    }//end 

    
    public boolean estaEnModoComido() {
        return modoComido;
    }//end 
    
    


   
}//end clase

