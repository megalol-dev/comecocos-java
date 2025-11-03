package Materiales;

//importaciones 
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.Graphics;
import java.util.*;
import javax.swing.Timer;



public class Enemigo {

    // Variables generales
    private int fila;
    private int columna;
    private int tamCelda;
    private String color;
    
    private GestorEnemigos gestorEnemigos;

    // Dirección actual
    private String direccion;

    // Estado del enemigo

    private boolean enModoMiedo = false;
    private boolean modoHuir = false;
    private boolean modoComido = false;

    private Timer temporizadorMiedo;
    
  
  
    

    //private int filaInicial, columnaInicial;
    private Timer temporizadorRespawn;
 

    // Sprites  
    private BufferedImage imgArriba1, imgArriba2, imgAbajo1, imgAbajo2, imgIzq1, imgIzq2, imgDer1, imgDer2;
    private BufferedImage imgOjosArriba, imgOjosAbajo, imgOjosDerecha, imgOjosIzquierda;
    private BufferedImage imgMiedoAbajo1, imgMiedoAbajo2, imgMiedoArriba1, imgMiedoArriba2, imgMiedoDerecha1, imgMiedoDerecha2, imgMiedoIzquierda1, imgMiedoIzquierda2;


    // Control animación
    private boolean alternarSprite = false;

    // Constructor
    public Enemigo(int fila, int columna, int tamCelda, String color) {
        this.fila = fila;
        this.columna = columna;
        this.tamCelda = tamCelda;
        this.color = color;
        this.direccion = "abajo";
        
        //this.filaInicial = fila;
        //this.columnaInicial = columna;

        cargarImagenes();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// 										METODOS														/////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Método para cargar las imágenes del enemigo según su color
    private void cargarImagenes() {
        try {
 
            imgArriba1 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "_arriba1.png"));
            imgArriba2 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "_arriba2.png"));
            imgAbajo1  = ImageIO.read(new File("img/Fantasma/enemigo" + color + "_abajo1.png"));
            imgAbajo2  = ImageIO.read(new File("img/Fantasma/enemigo" + color + "_abajo2.png"));
            imgIzq1    = ImageIO.read(new File("img/Fantasma/enemigo" + color + "_izquierda1.png"));
            imgIzq2    = ImageIO.read(new File("img/Fantasma/enemigo" + color + "_izquierda2.png"));
            imgDer1    = ImageIO.read(new File("img/Fantasma/enemigo" + color + "_derecha1.png"));
            imgDer2    = ImageIO.read(new File("img/Fantasma/enemigo" + color + "_derecha2.png"));

            imgMiedoAbajo1 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Miedo_abajo1.png"));
            imgMiedoAbajo2 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Miedo_abajo2.png"));
            imgMiedoArriba1 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Miedo_arriba1.png"));
            imgMiedoArriba2 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Miedo_arriba2.png"));
            imgMiedoDerecha1 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Miedo_derecha1.png"));
            imgMiedoDerecha2 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Miedo_derecha2.png"));
            imgMiedoIzquierda1 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Miedo_izquierda1.png"));
            imgMiedoIzquierda2 = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Miedo_izquierda2.png"));
            
            imgOjosArriba = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Comido_arriba1.png"));
            imgOjosAbajo = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Comido_abajo1.png"));
            imgOjosDerecha = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Comido_derecha1.png"));
            imgOjosIzquierda = ImageIO.read(new File("img/Fantasma/enemigo" + color + "Comido_izquierda1.png"));

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    // Método para dibujar el gráfico del enemigo
 // Método para dibujar el gráfico del enemigo
    public void dibujar(Graphics g, int offsetY) {
        BufferedImage sprite = null;

        if (modoComido) {
            // Mostrar sprite de ojos según la dirección
            switch (direccion) {
                case "arriba":
                    sprite = imgOjosArriba;
                    break;
                case "abajo":
                    sprite = imgOjosAbajo;
                    break;
                case "izquierda":
                    sprite = imgOjosIzquierda;
                    break;
                case "derecha":
                    sprite = imgOjosDerecha;
                    break;
            }
        } 
        
        else if (enModoMiedo) {
            // Mostrar sprite de miedo según la dirección
            switch (direccion) {
                case "arriba":
                    sprite = alternarSprite ? imgMiedoArriba1 : imgMiedoArriba2;
                    break;
                case "abajo":
                    sprite = alternarSprite ? imgMiedoAbajo1 : imgMiedoAbajo2;
                    break;
                case "izquierda":
                    sprite = alternarSprite ? imgMiedoIzquierda1 : imgMiedoIzquierda2;
                    break;
                case "derecha":
                    sprite = alternarSprite ? imgMiedoDerecha1 : imgMiedoDerecha2;
                    break;
            }
        } 
        
        else {
            // Mostrar sprite normal según la dirección
            switch (direccion) {
                case "arriba":
                    sprite = alternarSprite ? imgArriba1 : imgArriba2;
                    break;
                case "abajo":
                    sprite = alternarSprite ? imgAbajo1 : imgAbajo2;
                    break;
                case "izquierda":
                    sprite = alternarSprite ? imgIzq1 : imgIzq2;
                    break;
                case "derecha":
                    sprite = alternarSprite ? imgDer1 : imgDer2;
                    break;
            }
        }

        if (sprite != null) {
            g.drawImage(sprite, columna * tamCelda, fila * tamCelda + offsetY, tamCelda, tamCelda, null);
        }
    }


    
    
    //Metodo para mover al enemigo 
    //----------------------------------------------------------------------------------------------------------------------------------
    public void mover(Jugador jugador, Mapa mapa, int nivel) {
        if (modoComido) {
            moverHaciaCasillaInicial(); // Este no necesita mapa
        } else if (modoHuir) {
            huirDelJugador(jugador, mapa, nivel);
        } else {
            moverHaciaJugador(jugador, mapa, nivel);
        }
    }

    // Método para mover el enemigo estado normal 
    // (usando BFS para encontra el mejor camino)
    public void moverHaciaJugador(Jugador jugador, Mapa mapa, int nivel) {
    	if (modoHuir) {
    	    huirDelJugador(jugador, mapa, nivel); // Ya lo tienes implementado
    	    return;
    	}
        int filaObjetivo = jugador.getFila();
        int columnaObjetivo = jugador.getColumna();

        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
       

        boolean[][] visitado = new boolean[mapa.getFilas()][mapa.getColumnas()];
        Map<String, int[]> desde = new HashMap<>();

        Queue<int[]> cola = new LinkedList<>();
        cola.add(new int[]{fila, columna});
        visitado[fila][columna] = true;

        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            if (actual[0] == filaObjetivo && actual[1] == columnaObjetivo) break;

            for (int i = 0; i < 4; i++) {
                int nuevaFila = actual[0] + dirs[i][0];
                int nuevaCol = actual[1] + dirs[i][1];
             
                
                if (mapa.estaDentro(nuevaFila, nuevaCol, nivel) &&
                	    !mapa.esPared(nuevaFila, nuevaCol, nivel) &&
                	    !visitado[nuevaFila][nuevaCol] &&
                	    !casillaOcupada(nuevaFila, nuevaCol)) {
                	    
                	    visitado[nuevaFila][nuevaCol] = true;
                	    cola.add(new int[]{nuevaFila, nuevaCol});
                	    desde.put(nuevaFila + "," + nuevaCol, actual);
                	}
            }
        }

        // Reconstruir camino desde el jugador hasta el enemigo
        String clave = filaObjetivo + "," + columnaObjetivo;
        if (!desde.containsKey(clave)) return; // No hay camino

        List<int[]> camino = new ArrayList<>();
        while (desde.containsKey(clave)) {
            int[] paso = Arrays.stream(clave.split(",")).mapToInt(Integer::parseInt).toArray();
            camino.add(paso);
            int[] anterior = desde.get(clave);
            clave = anterior[0] + "," + anterior[1];
        }

        if (!camino.isEmpty()) {
            int[] siguientePaso = camino.get(camino.size() - 1);
            int nuevaFila = siguientePaso[0];
            int nuevaCol = siguientePaso[1];

            // Establecer dirección
            if (nuevaFila < fila) direccion = "arriba";
            else if (nuevaFila > fila) direccion = "abajo";
            else if (nuevaCol < columna) direccion = "izquierda";
            else if (nuevaCol > columna) direccion = "derecha";

            fila = nuevaFila;
            columna = nuevaCol;
            alternarSprite = !alternarSprite;
        }
    }//end
    
    
 // Método para huir del jugador (buscar dirección que aumente la distancia Manhattan)
    private void huirDelJugador(Jugador jugador, Mapa mapa, int nivel) {
        int filaJugador = jugador.getFila();
        int columnaJugador = jugador.getColumna();

        String[] direcciones = {"arriba", "abajo", "izquierda", "derecha"};
        int[][] movimientos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int mejorDistancia = -1;
        String mejorDireccion = null;
        int nuevaFila = fila;
        int nuevaColumna = columna;

        for (int i = 0; i < 4; i++) {
            int f = fila + movimientos[i][0];
            int c = columna + movimientos[i][1];

            if (!mapa.estaDentro(f, c, nivel) || mapa.esPared(f, c, nivel)) continue;

            int distancia = Math.abs(f - filaJugador) + Math.abs(c - columnaJugador);
            if (distancia > mejorDistancia) {
                mejorDistancia = distancia;
                mejorDireccion = direcciones[i];
                nuevaFila = f;
                nuevaColumna = c;
            }
        }

        if (mejorDireccion != null) {
            direccion = mejorDireccion;
            fila = nuevaFila;
            columna = nuevaColumna;
            alternarSprite = !alternarSprite;
        }
    }
   
    
  
    //------------------------------------------------------------------------------------------------------------------------------------
    //End metodo de movimiento del fantasma
    
    
    // Método para activar el estdo de "miedo" en los enemigos
    // Mientras los enemigos tiene este estado, son vulnerables y pueden ser comidos por el jugador
    public void activarModoMiedo() {
        enModoMiedo = true;

        // Si ya hay un temporizador en marcha, lo detenemos
        if (temporizadorMiedo != null && temporizadorMiedo.isRunning()) {
            temporizadorMiedo.stop();
        }

        // Creamos un nuevo temporizador de 3 segundos
        temporizadorMiedo = new Timer(3000, e -> {
            enModoMiedo = false;
            temporizadorMiedo.stop();
        });

        temporizadorMiedo.setRepeats(false); // Solo se ejecuta una vez
        temporizadorMiedo.start();
    }

    
    public void moverHaciaCasillaInicial() {
        int filaObjetivo = 10; // usa la fila inicial del fantasma rojo
        int columnaObjetivo = 8;

        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        boolean[][] visitado = new boolean[20][20]; // o mapa.getFilas()/getColumnas() si puedes acceder
        Map<String, int[]> desde = new HashMap<>();

        Queue<int[]> cola = new LinkedList<>();
        cola.add(new int[]{fila, columna});
        visitado[fila][columna] = true;

        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            if (actual[0] == filaObjetivo && actual[1] == columnaObjetivo) break;

            for (int[] dir : dirs) {
                int nuevaFila = actual[0] + dir[0];
                int nuevaCol = actual[1] + dir[1];

                if (nuevaFila >= 0 && nuevaFila < 20 && nuevaCol >= 0 && nuevaCol < 20 &&
                    !visitado[nuevaFila][nuevaCol]) {
                    visitado[nuevaFila][nuevaCol] = true;
                    cola.add(new int[]{nuevaFila, nuevaCol});
                    desde.put(nuevaFila + "," + nuevaCol, actual);
                }
            }
        }

        // Reconstrucción
        String clave = filaObjetivo + "," + columnaObjetivo;
        if (!desde.containsKey(clave)) return;

        List<int[]> camino = new ArrayList<>();
        while (desde.containsKey(clave)) {
            int[] paso = Arrays.stream(clave.split(",")).mapToInt(Integer::parseInt).toArray();
            camino.add(paso);
            int[] anterior = desde.get(clave);
            clave = anterior[0] + "," + anterior[1];
        }

        if (!camino.isEmpty()) {
            int[] siguientePaso = camino.get(camino.size() - 1);
            fila = siguientePaso[0];
            columna = siguientePaso[1];
            alternarSprite = !alternarSprite;

            // Si ya está en la casilla inicial, dejar de estar comido
            if (fila == filaObjetivo && columna == columnaObjetivo) {
                modoComido = false;
            }
        }
    }
    
    // Métodos para obtener posición del enemigo
    
    // Fila
    public int getFila() {
        return fila;
    }
    
    // Columna
    public int getColumna() {
        return columna;
    }
    
    // Establer posición fila <----> columna
    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }


    
    
    public void setModoHuir(boolean huir) {
        this.modoHuir = huir;
    }
    
    public void activarModoHuir() {
        modoHuir = true;
    }

    public void desactivarModoHuir() {
        modoHuir = false;
    }
    

    public boolean estaHuyendo() {
        return modoHuir;
    }
    
    
    public void comer() {
        modoComido = true;
        modoHuir = false;
        enModoMiedo = false;

        System.out.println("¡Fantasma comido!");
       

        moverHaciaCasillaInicial();

        // Tras 5 segundos, volver al estado normal
        if (temporizadorRespawn != null && temporizadorRespawn.isRunning()) {
            temporizadorRespawn.stop();
        }

        temporizadorRespawn = new Timer(5000, e -> {
            modoComido = false;
            // Se puede añadir más lógica si hace falta reiniciar otras cosas
            System.out.println("¡Fantasma respawneado!");
        });

        temporizadorRespawn.setRepeats(false);
        temporizadorRespawn.start();
    }
    
    public boolean estaEnModoComido() {
        return modoComido;
    }
    
    
    
   	private boolean casillaOcupada(int fila, int col) {
        if (gestorEnemigos == null) return false;
        for (int[] pos : gestorEnemigos.getPosicionesOcupadas(this)) {
            if (pos[0] == fila && pos[1] == col) {
                return true;
            }
        }
        return false;
    }

	
    public void setGestorEnemigos(GestorEnemigos gestor) {
        this.gestorEnemigos = gestor;
    }//end
    

    public boolean esModoComido() {
        return modoComido;
    }

    public boolean esEnModoMiedo() {
        return enModoMiedo;
    }
    
    public int getX() {
        return this.columna; // o la variable que uses internamente
    }

    public int getY() {
        return this.fila; // o la variable que uses internamente
    }
    
}//end clase
