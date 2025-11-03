package Materiales;

/* Clase mapa, para gestionar los mapas del juego
* Cada mapa tiene 2 matices:
* Matriz 1 -> se define que es pared y que es "camino" para que el jugador pueda caminar
* Matriz 2 -> se añade bolitas (puntos) en los caminos. Si el jugador recoge toda las bolitas, completa el nivel. 
*/			  


 
public class Mapa {
	
	//Nota mapas: todos los mapas deben tener 118 puntos en total a recoger
	// 114 bolitas normales
	// 4   bolitas que son el power up 
	
	//NIVEL 1
	
	//creamos el mapa con una matriz
	//el valor 0 significara casilla vacia
	//el valor 1 significara que la casilla es pared y por tanto no se puede pasar
	

	private int[][] mapa = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,0,0,0,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	
			
	};//end matriz mapa
	
	
	//rellenamos el mapa con los puntos que podemos recoger
	// 0 = casilla vacia
	// 1 = bolita normal, 
	// 2 = power-up
	
	private int[][] puntos = {
		    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		    {0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,0},
		    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
	};
	
	//---------------------------------------------------------------------------------------------------------------
	
	
	//NIVEL 2
	//el valor 0 significara casilla vacia
	//el valor 1 significara que la casilla es pared y por tanto no se puede pasar
	private int[][] mapa2 = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	
			
	};//end matriz mapa
	
	
	//rellenamos el mapa con los puntos que podemos recoger
	// 0 = casilla vacia
	// 1 = bolita normal, 
	// 2 = power-up
	
	private int[][] puntos2 = {
		    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		    {0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,0},
		    {0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0},
		    {0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0},
		    {0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,2,1,1,0,1,1,1,1,1,1,1,0,1,1,2,0},
		    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};

	
	//---------------------------------------------------------------------------------------------------------------
	
	
	
	//NIVEL 3
	//el valor 0 significara casilla vacia
	//el valor 1 significara que la casilla es pared y por tanto no se puede pasar
	private int[][] mapa3 = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
			{1,0,1,0,0,0,1,1,1,1,1,0,0,0,1,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,1},
			{1,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,0,1,1,0,1,1,1,0,1},
			{1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1},
			{1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1},
			{1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
			{1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1},
			{1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	
			
	};//end matriz mapa
	
	
	//rellenamos el mapa con los puntos que podemos recoger
	// 0 = casilla vacia
	// 1 = bolita normal, 
	// 2 = power-up
	
	private int[][] puntos3 = {
		    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		    {0,2,0,1,1,1,0,0,0,0,0,1,1,1,0,2,0},
		    {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0},
		    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
		    {0,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,0},
		    {0,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,0},
		    {0,1,0,0,0,1,0,0,1,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,0,1,1,1,0,0,0,0,0,1,1,1,0,1,0},
		    {0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,0},
		    {0,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0},
		    {0,2,1,1,1,1,0,0,0,0,0,1,1,1,1,2,0},
		    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};

	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///
	///
	/// 												METODOS
	///
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	//Metodo para llamar al mapa
	public int [][]getMapa() {
		return mapa;
	}//end
	
	
	//Metodo para llamar a los puntos
	public int[][] getPuntos() {
	    return puntos;    
	}//end
	
	
	//Metodo para llamar al mapa
	public int [][]getMapa2() {
		return mapa2;		
	}//end
	
	
	//Metodo para llamar a los puntos
	public int[][] getPuntos2() {
	    return puntos2;	    
	}//end
	
	
	// Método get para obtener la ID de cada mapa
	public int[][] getMapaPorNivel(int nivel) {
	    return switch (nivel) {
	        case 1 -> mapa;
	        case 2 -> mapa2;
	        case 3 -> mapa3;
	        default -> mapa;
	    };    
	}//end
	
	
	
	// Método get para obtener los puntos de cada mapa
	public int[][] getPuntosPorNivel(int nivel) {
	    return switch (nivel) {
	        case 1 -> puntos;
	        case 2 -> puntos2;
	        case 3 -> puntos3;
	        default -> puntos;
	    };
	}//end 
	
	
	//Fisica de las paredes
	public boolean esPared(int fila, int columna, int nivel) {
	    return getMapaPorNivel(nivel)[fila][columna] == 1;
	}//end

	
	//Metodo para recoger puntos y actualizar el mapa
	public void recogerPunto(int fila, int columna, int nivel) {
	    getPuntosPorNivel(nivel)[fila][columna] = 0;
	}//end
	
	
	// Métodos get para las filas
	public int getFilas() {
	    return mapa.length;
	}//end
	
	
	// Método get para las columnas
	public int getColumnas() {
	    return mapa[0].length;
	}//end 

	
	// Método para ???? (preguntar a GPT)
	public boolean estaDentro(int fila, int columna, int nivel) {
	    int[][] mapaActual = getMapaPorNivel(nivel);
	    return fila >= 0 && fila < mapaActual.length &&
	           columna >= 0 && columna < mapaActual[0].length;
	}//end
	

}//end clase



