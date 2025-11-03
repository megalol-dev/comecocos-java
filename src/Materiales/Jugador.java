package Materiales;

//clase jugador package Materiales;

//importaciones
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.awt.Graphics;


//clase jugador 
public class Jugador {
	
	//variables de posición
	private int fila;
	private int columna;
	private int tamCelda;
	
	
	//jugador
	private String direccion = "derecha"; // Dirección por defecto
	private boolean alternarSprite = false; // Para cambiar entre imagen 1 y 2
	
	// Sprites
	private BufferedImage imgArriba1, imgArriba2;
	private BufferedImage imgAbajo1, imgAbajo2;
 	private BufferedImage imgIzquierda1, imgIzquierda2;
 	private BufferedImage imgDerecha1, imgDerecha2;
 
 	private BufferedImage imgArriba1Power, imgArriba2Power;
 	private BufferedImage imgAbajo1Power, imgAbajo2Power;
 	private BufferedImage imgIzquierda1Power, imgIzquierda2Power;
 	private BufferedImage imgDerecha1Power, imgDerecha2Power;
 
 	// Estado PowerUp
 	private boolean enPowerUp = false;
 	private boolean modoPowerUp = false;
 	
 
 	
	// end variables
	
	
	//constructor
	public Jugador(int fila, int columna, int tamCelda){
		this.fila = fila;
		this.columna = columna;
		this.tamCelda = tamCelda;
		cargarImagenes();
		
	} //end construtor 

	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///// 									METODOS															/////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//metodo para cargar imagenes 
	private void cargarImagenes() {
		try {
			imgArriba1 = ImageIO.read(new File("img/Jugador/personaje_arriba1.png"));
	        imgArriba2 = ImageIO.read(new File("img/Jugador/personaje_arriba2.png"));
	        imgAbajo1 = ImageIO.read(new File("img/Jugador/personaje_abajo1.png"));
	        imgAbajo2 = ImageIO.read(new File("img/Jugador/personaje_abajo2.png"));
	        imgIzquierda1 = ImageIO.read(new File("img/Jugador/personaje_izquierda1.png"));
	        imgIzquierda2 = ImageIO.read(new File("img/Jugador/personaje_izquierda2.png"));
	        imgDerecha1 = ImageIO.read(new File("img/Jugador/personaje_derecha1.png"));
	        imgDerecha2 = ImageIO.read(new File("img/Jugador/personaje_derecha2.png"));
	        
	        imgArriba1Power = ImageIO.read(new File("img/jugador/personaje_arriba1power.png"));
	        imgArriba2Power = ImageIO.read(new File("img/jugador/personaje_arriba2power.png"));
	        imgAbajo1Power = ImageIO.read(new File("img/jugador/personaje_abajo1power.png"));
	        imgAbajo2Power = ImageIO.read(new File("img/jugador/personaje_abajo2power.png"));
	        imgIzquierda1Power = ImageIO.read(new File("img/jugador/personaje_izquierda1power.png"));
	        imgIzquierda2Power = ImageIO.read(new File("img/jugador/personaje_izquierda2power.png"));
	        imgDerecha1Power = ImageIO.read(new File("img/jugador/personaje_derecha1power.png"));
	        imgDerecha2Power = ImageIO.read(new File("img/jugador/personaje_derecha2power.png"));
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	    }// end cargar imagenes
	
	
	
	//metodo mover personaje
	// se comprueba mediante un if si a donde nos queremos mover es pared, si no es pared las cordenadas X e Y del
	// jugador pasar a ser nuevas y asi es como nos vamos moviendo por el mapa
	public void mover(int nuevaFila, int nuevaColumna, Mapa mapa, int nivel) {
	    if (!mapa.esPared(nuevaFila, nuevaColumna, nivel)) {
	        this.fila = nuevaFila;
	        this.columna = nuevaColumna;
	        alternarSprite = !alternarSprite;
	    }
	}//end
 
 
	// Método set par la dirección del personaje
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}//end

	
	
	// Método dibujar al jugador
	public void dibujar(Graphics g, int offsetY) {
		BufferedImage sprite = obtenerSprite();  // Delegamos la elección del sprite

     if (sprite != null) {
         g.drawImage(sprite, columna * tamCelda, fila * tamCelda + offsetY, tamCelda, tamCelda, null);
     	}
	}//end
	
	
 
	// Método para añadir el sprite correspondiente al jugador
	private BufferedImage obtenerSprite() {
     if (modoPowerUp) {
         return switch (direccion) {
             case "arriba" -> alternarSprite ? imgArriba1Power : imgArriba2Power;
             case "abajo" -> alternarSprite ? imgAbajo1Power : imgAbajo2Power;
             case "izquierda" -> alternarSprite ? imgIzquierda1Power : imgIzquierda2Power;
             case "derecha" -> alternarSprite ? imgDerecha1Power : imgDerecha2Power;
             default -> null;
         };
     } 
     else { 
    	 return switch (direccion) {
             case "arriba" -> alternarSprite ? imgArriba1 : imgArriba2;
             case "abajo" -> alternarSprite ? imgAbajo1 : imgAbajo2;
             case "izquierda" -> alternarSprite ? imgIzquierda1 : imgIzquierda2;
             case "derecha" -> alternarSprite ? imgDerecha1 : imgDerecha2;
             default -> null;
         };
     }
     
	}//end 

 
 
	//metodo obtener fila
	public int getFila () {
		return fila;
	}//end 
	
	
	
	//metodo obtener columna
	public int getColumna () {
		return columna;
	}//end
	
	
	
	//metodo para obtener la posicon del juegador
	public void setPosicion(int fila, int columna) {
	    this.fila = fila;
	    this.columna = columna;
	}//end
	
	
	
	//metodo para  activa el power up del jugador
	public void activarModoPowerUp() {
	    enPowerUp = true;
	    modoPowerUp = true;
	}//end
	
	
	
	// Método para desactivar el Power up del jugador
	public void desactivarModoPowerUp() {
	    enPowerUp = false;
	    modoPowerUp = false;
	}//end
	
	
	
	// Metodo para activar el Power up del jugador
	public boolean estaEnPowerUp() {
	    return enPowerUp;
	}//end
	
	
	
}//end clase
