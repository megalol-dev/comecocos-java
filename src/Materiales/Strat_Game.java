package Materiales;

// importaciones
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Strat_Game extends JFrame{
	
	

	// Variables del sistema
	
	private BufferedImage imgPantallaInicio;
	private boolean mostrandoPantallaInicio = true;
	

	private boolean permitirMovimiento = false; // NUEVA: solo se activa tras ENTER
	
	private BufferedImage imgEnter;
	private boolean mostrarPressEnter = true; // controla el parpadeo
	private Timer timerParpadeo;
	
	
	private boolean juegoActivo = true;
	private BufferedImage imagenGameOver;
	private JPanel panelJuego;
	
	private BufferedImage imgFelicidades;
	private boolean mostrandoFelicidades = false;
	
	private BufferedImage imgStage1, imgStage2, imgStage3, imgClear;
	private boolean mostrandoStage = false;
	private boolean mostrandoClear = false;
	private boolean finDelJuego = false;  // <-- solo true cuando se acaba de verdad
	
	private int nivelActual = 1; // Controla en qué nivel estamos

	// Variable jugador
	private Jugador jugador;
	private boolean teclaPresionada = false;

	// Varibles enemigos 
	private GestorEnemigos gestorEnemigos;

	// Variables del mapa
	private Mapa mapa;
	private Mapa mapa2;
	
	private final int Tam_Celdas = 37;

	// Espacio para el rectángulo superior que muestra contadores
	int offsetY = 32; // espacio reservado para HUD

	// Variables de los puntos
	private int puntosComidos = 0;
	private final int TOTAL_PUNTOS = 118;

	// Variables para la interfaz
	private int record = 1000;
	public int score = 0;
	private int level = 1;
	private int vidasJugador = 3;
	private String nombreRecord = "---";
	
	private Timer temporizadorPowerUp; 
	
	
	
	
	// --- Para el sistema de game over ---
	private BufferedImage imagenOpcionJugar;
	private BufferedImage imagenOpcionSalir;
	private BufferedImage imagenCursor;
	
	private boolean mostrandoMenuGameOver = false;  // Nuevo: para el menú tras el delay
	
	private boolean esperandoMenuGameOver = false;
	
	private boolean mostrandoNuevoRecord = false;


	// Control de selección
	private int opcionSeleccionada = 0; // 0 = Jugar, 1 = Salir
	private boolean mostrandoGameOver = false;
	
	// Cursor animado
	private BufferedImage cursor1;
	private BufferedImage cursor2;
	private BufferedImage cursorActual;  // Imagen que se dibuja actualmente
	private boolean animacionCursor = false;
	
	private Timer animacionCursorTimer;
	
	//---- End game over 
	
	
	// --- Para el sistema de inserción de récord ---
	private boolean insertandoRecord = false;
	private int letraSeleccionada = 0;    // 0 = primera letra, 1 = segunda, 2 = tercera
	private int[] indicesLetras = {0, 0, 0}; // Almacena el índice actual de las letras (0=A, 1=B...)
	private Image insertarRecordImg;
	private Image[] letrasImg = new Image[26]; // Alfabeto A-Z
	
	//--- end record
	
	// Variables para el sistemas de vidas extra
	private final int[] umbralesVidas = {1000, 3000, 5000};
	private int idxUmbralVida = 0;          // siguiente umbral pendiente

	// Popup “+1 VIDA” en el HUD
	private boolean mostrandoPopupVida = false;
	private long finPopupVidaMs = 0L;
	
	// end vidas extra

	
	// Variables para los sonios
	private SoundManager musicIntro = new SoundManager();
	private boolean introMusicPlaying = false;
	
	public List<ScorePopup> popups = new ArrayList<>();


	// End variables
	
	
	
	
	
	// Constructor
	
	public Strat_Game() {
		mapa = new Mapa();
		cargarRecord(); // <-- Cargamos el récord desde archivo
		jugador = new Jugador(12, 8, Tam_Celdas);
		gestorEnemigos = new GestorEnemigos(Tam_Celdas);
		
		// Ventada del juego
		setTitle("comecocos en JAVA por José Luis Escuedero Polo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(17 * Tam_Celdas + 16, 17 * Tam_Celdas + offsetY + 39);
		setLocationRelativeTo(null);
		setResizable(false);

		try {
			//imagenes del sistema
			imgPantallaInicio = ImageIO.read(new File("img/Sistema/pantalla Inicio.png"));
			imgEnter = ImageIO.read(new File("img/Sistema/Enter.png"));
			imagenGameOver = ImageIO.read(new File("img/Sistema/gameOver.png"));
		    imgStage1 = ImageIO.read(new File("img/Sistema/stage1.png"));
		    imgStage2 = ImageIO.read(new File("img/Sistema/stage2.png"));
		    imgStage3 = ImageIO.read(new File("img/Sistema/stage3.png"));
		    imgClear  = ImageIO.read(new File("img/Sistema/clear.png"));
		    imagenOpcionJugar = ImageIO.read(new File("img/Sistema/Opcion_VolverJugar.png"));
		    imagenOpcionSalir = ImageIO.read(new File("img/Sistema/Opcion_Salir.png"));
		    imagenCursor = ImageIO.read(new File("img/Sistema/Cursor.png"));
		    cursor1 = ImageIO.read(new File("img/Sistema/Cursor.png"));
		    cursor2 = ImageIO.read(new File("img/Sistema/Cursor2.png"));		    
		    cursorActual = cursor1; // Empieza con la primera
		    imgFelicidades = ImageIO.read(new File("img/Sistema/felicidades.png"));
		    insertarRecordImg = new ImageIcon("img/Sistema/InsertaRecord.png").getImage();
		    // Cargar letras A-Z
		    for (int i = 0; i < 26; i++) {
		        char letra = (char) ('A' + i);
		        letrasImg[i] = new ImageIcon("img/Sistema/letra_" + letra + ".png").getImage();
		    }

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		// Empezar a dibujar en pantalla
		panelJuego = new JPanel() {
	
			@Override
			//Dibuja todos los componestes del juego
			// 
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				// --- PANTALLA DE NUEVO RÉCORD ---
				if (mostrandoNuevoRecord) {
				    dibujarMapa(g); // fondo del juego

				    // Dibuja la imagen de nuevo récord (usa la que tengas, ejemplo insertarRecordImg)
				    int xRecord = (getWidth() - insertarRecordImg.getWidth(null)) / 2;
				    g.drawImage(insertarRecordImg, xRecord, 150, null);

				    return; // No dibujamos nada más todavía
				}
				
				
				
				 // --- PANTALLA DE INSERTAR RÉCORD ---
			    if (insertandoRecord) {

			        // Dibuja primero el juego de fondo
			        dibujarMapa(g);

			        
			        // Cartel de insertar récord
			        int xRecord = (getWidth() - insertarRecordImg.getWidth(null)) / 2;
			        g.drawImage(insertarRecordImg, xRecord, 150, null);
					
			        // Dibujar las 3 letras seleccionables
			        int startX = getWidth() / 2 - 150; // Ajusta según tamaño letras
			        int y = getHeight() / 2;

			        for (int i = 0; i < 3; i++) {
			            Image letraImg = letrasImg[indicesLetras[i]];
			            g.drawImage(letraImg, startX + i*100, y, null);

			            // Cursor debajo de la letra seleccionada
			            if (i == letraSeleccionada) {
			                g.setColor(Color.RED);
			                g.fillRect(startX + i*100 + 20, y + 80, 60, 5);
			            }
			        }
			        return; // <-- Muy importante: no dibujes nada más si estás insertando el record
			    } //end 
			    
			    
			 // Popup "+1 VIDA" en el HUD
			    if (mostrandoPopupVida) {
			        if (System.currentTimeMillis() < finPopupVidaMs) {
			            g.setFont(new Font("Arial", Font.BOLD, 22));
			            g.setColor(Color.YELLOW);
			            String txt = "+1 VIDA";
			            int w = g.getFontMetrics().stringWidth(txt);
			            g.drawString(txt, (getWidth() - w) / 2, offsetY - 6 + 22); // centrado en la barra superior
			        } else {
			            mostrandoPopupVida = false; // apagar popup cuando pase el tiempo
			        }
			    }

				
				// Pantalla de incio
				if (mostrandoPantallaInicio && imgPantallaInicio != null) {
				    g.drawImage(imgPantallaInicio, 0, 0, null);

				    if (mostrarPressEnter && imgEnter != null) {
				        int x = (getWidth() - imgEnter.getWidth()) / 2;
				        int y = (getHeight() - imgEnter.getHeight()) / 2 + 200;
				        g.drawImage(imgEnter, x, y, null);
				      

				        // Reproducir música de la pantalla intro (Se repite en bucle al terminar)
				        if (!introMusicPlaying) {
				            musicIntro.playMusic("audio/Intro_juego.wav");
				            introMusicPlaying = true;
				        }
				    }
				  
				    return;
				    
				}
			
				
				// Dibujar niveles
				dibujarMapa(g);
				if (finDelJuego) mostrarGameOver(g);
				
				// Mostrar cartel de "stage" si corresponde
				if (mostrandoStage) {
				    BufferedImage img = switch (nivelActual) {
				        case 1 -> imgStage1;
				        case 2 -> imgStage2;
				        case 3 -> imgStage3;
				        default -> null;
				    };
				    if (img != null) {
				        int x = (getWidth() - img.getWidth()) / 2;
				        int y = (getHeight() - img.getHeight()) / 2;
				        g.drawImage(img, x, y, null);
				    }
				}
				
				
				// Mostrar imagen de felicidades encima del mapa
				if (mostrandoFelicidades && imgFelicidades != null) {
				    g.drawImage(imgFelicidades,
				                (getWidth() - imgFelicidades.getWidth()) / 2,
				                (getHeight() - imgFelicidades.getHeight()) / 2,
				                null);
				    return;
				}
				

				// Mostrar cartel de "clear" si corresponde
				if (mostrandoClear && imgClear != null) {
				    int x = (getWidth() - imgClear.getWidth()) / 2;
				    int y = (getHeight() - imgClear.getHeight()) / 2;
				    g.drawImage(imgClear, x, y, null);
				    //reproducirSonido("audio/PantallaCompletada.wav"); <- error de sonido
				}
				
				
				// Dibujar popups de puntuación
				for (int i = 0; i < popups.size(); i++) {
				    ScorePopup popup = popups.get(i);
				    if (popup.isAlive()) {
				        popup.draw(g);
				    } else {
				        popups.remove(i);
				        i--; // Ajustar índice al eliminar
				    }
				}
				
				
			} // end paint componentes
		};

		setContentPane(panelJuego);
		setVisible(true);
		
		/////////////////////////////////////////////////////////
		// Temporizador para parpadear el texto de "PRESS ENTER"
		timerParpadeo = new Timer(500, e -> {
		    mostrarPressEnter = !mostrarPressEnter;
		    panelJuego.repaint();
		});
		timerParpadeo.start();
		////////////////////////////////////////////////////////
		mostrarStageConDelay();
		
		
		
		//movimientos del jugador
		addKeyListener(new java.awt.event.KeyAdapter() {
		    @Override
		    // Presiona el botón INTRO para activar el juego y las siguientes fisicas
		    public void keyPressed(KeyEvent e) {
		    	
		    	// --- CONTROLES PARA INSERTAR RECORD ---
		    	if (insertandoRecord) {
		    	    switch (e.getKeyCode()) {
		    	        case KeyEvent.VK_LEFT:
		    	        case KeyEvent.VK_A:
		    	            indicesLetras[letraSeleccionada] = (indicesLetras[letraSeleccionada] - 1 + 26) % 26;
		    	            repaint();
		    	            break;

		    	        case KeyEvent.VK_RIGHT:
		    	        case KeyEvent.VK_D:
		    	            indicesLetras[letraSeleccionada] = (indicesLetras[letraSeleccionada] + 1) % 26;
		    	            repaint();
		    	            break;

		    	        case KeyEvent.VK_ENTER:
		    	            letraSeleccionada++;
		    	            if (letraSeleccionada > 2) {
		    	                // Guardar nombre del récord
		    	                String nombre = "" +
		    	                        (char)('A' + indicesLetras[0]) +
		    	                        (char)('A' + indicesLetras[1]) +
		    	                        (char)('A' + indicesLetras[2]);

		    	                guardarNuevoRecord(score, nombre); 
		    	                insertandoRecord = false;
		    	                letraSeleccionada = 0;
		    	                

		    	                if (vidasJugador <= 0) {
		    	                    // 🔹 Venimos de un Game Over normal (por perder vidas)
		    	                    mostrandoGameOver = true;
		    	                    esperandoMenuGameOver = true;
		    	                    mostrandoMenuGameOver = false;
		    	                    repaint();

		    	                    new Timer(3000, ev -> {
		    	                        esperandoMenuGameOver = false;
		    	                        mostrandoMenuGameOver = true;
		    	                        opcionSeleccionada = 0;

		    	                        iniciarAnimacionCursor(); // ✅ Aquí arrancamos la animación

		    	                        repaint();
		    	                        ((Timer) ev.getSource()).stop();
		    	                    }).start();

		    	                } else {
		    	                    // 🔹 Venimos de final de juego por pasar el último nivel
		    	                    mostrandoFelicidades = true;
		    	                    juegoActivo = false;
		    	                    reproducirSonido("audio/JuegoCompletado.wav");
		    	                    repaint();
		    	                }
		    	            }

		    	            repaint();
		    	            break;
		    
		    	    }
		    	    return; // No procesar nada más mientras insertas el récord
		    	}


		   
		    	// --- CONTROLES PARA GAME OVER ---
		    	if (mostrandoGameOver) {
		    	    // 🔹 Si aún estamos en espera (tanto por récord como normal), ignoramos teclas
		    	    if (esperandoMenuGameOver || !mostrandoMenuGameOver) {
		    	        return;
		    	    }

		    	    switch (e.getKeyCode()) {
		    	        case KeyEvent.VK_UP:
		    	        case KeyEvent.VK_W:
		    	            opcionSeleccionada = (opcionSeleccionada - 1 + 2) % 2;
		    	            repaint();
		    	            break;

		    	        case KeyEvent.VK_DOWN:
		    	        case KeyEvent.VK_S:
		    	            opcionSeleccionada = (opcionSeleccionada + 1) % 2;
		    	            repaint();
		    	            break;

		    	        case KeyEvent.VK_ENTER:
		    	            if (opcionSeleccionada == 0) {
		    	                // VOLVER A JUGAR
		    	                mostrandoGameOver = false;
		    	                mostrandoMenuGameOver = false;
		    	                teclaPresionada = false;

		    	                detenerAnimacionCursor();
		    	                reiniciarJuego();
		    	            } else {
		    	                // SALIR
		    	                finDelJuego = true;
		    	                detenerAnimacionCursor();
		    	                System.exit(0);
		    	            }
		    	            break;
		    	    }
		    	}
		        // --------------------------------


		        // --- CONTROLES PARA PANTALLA DE INICIO ---
		        if (mostrandoPantallaInicio && e.getKeyCode() == KeyEvent.VK_ENTER) {
		            musicIntro.stopMusic();             // Apaga la música de la intro
		            mostrandoPantallaInicio = false;    // Quita la pantalla del inicio
		            juegoActivo = true;                 // Activa el juego
		            permitirMovimiento = true; // ✅ Ahora sí se puede mover el jugador
		            teclaPresionada = false;            // Reinicia la variable para evitar bugs
		            mostrarStageConDelay();             // Mostramos STAGE 1 al iniciar
		            panelJuego.repaint();
		            return;
		        }

		        // --- CONTROLES DE JUEGO ---
		        if (!juegoActivo || mostrandoStage || mostrandoClear || teclaPresionada || !permitirMovimiento) return;


		        teclaPresionada = true; // marcamos que hay una tecla en uso

		        int fila = jugador.getFila();
		        int columna = jugador.getColumna();

		        switch (e.getKeyCode()) {
		            case KeyEvent.VK_UP:
		                jugador.setDireccion("arriba");
		                jugador.mover(fila - 1, columna, mapa, nivelActual);
		                break;
		            case KeyEvent.VK_DOWN:
		                jugador.setDireccion("abajo");
		                jugador.mover(fila + 1, columna, mapa, nivelActual);
		                break;
		            case KeyEvent.VK_LEFT:
		                jugador.setDireccion("izquierda");
		                jugador.mover(fila, columna - 1, mapa, nivelActual);
		                break;
		            case KeyEvent.VK_RIGHT:
		                jugador.setDireccion("derecha");
		                jugador.mover(fila, columna + 1, mapa, nivelActual);
		                break;
		        }

		        int nuevaFila = jugador.getFila();
		        int nuevaColumna = jugador.getColumna();
		        int tipo = mapa.getPuntosPorNivel(nivelActual)[nuevaFila][nuevaColumna];

		        if (tipo == 1 || tipo == 2) {
		            puntosComidos++;
		            mapa.recogerPunto(nuevaFila, nuevaColumna, nivelActual);

		            if (tipo == 1) {
		                score += 10;
		                reproducirSonido("audio/PuntosSonido.wav");
		                checkVidasExtra();
		            } else if (tipo == 2) {
		                score += 100;
		                reproducirSonido("audio/PowerUp.wav");
		                activarPowerUp();
		                gestorEnemigos.activarModoMiedo();
		                gestorEnemigos.activarModoHuirEnemigos();
		                checkVidasExtra();
		            }

		            if (puntosComidos == TOTAL_PUNTOS) {
		                if (nivelActual < 3) {
		                    avanzarNivel();
		                } else {
		                    juegoActivo = false; // detiene todo
		                    if (score > record) {
		                        // Primero insertar el récord
		                        insertandoRecord = true;
		                        Arrays.fill(indicesLetras, 0);
		                        letraSeleccionada = 0;
		                        repaint();

		                        // Cuando termine de introducir el récord, mostramos felicidades
		                        // Esto lo controlas en la parte donde terminas el insertandoRecord:
		                        // después de guardar el récord, pones mostrandoFelicidades = true;
		                    } else {
		                        // Si no hay récord, pasamos directamente a felicidades
		                        mostrandoFelicidades = true;
		                        panelJuego.repaint();
		                    }
		                }
		            }

		        }

		        repaint();
		    }

		    @Override
		    public void keyReleased(KeyEvent e) {
		        teclaPresionada = false;
		    }

		});
	
		
		//metodo timer 
		new Timer(250, e -> {
		    if (!juegoActivo || mostrandoStage || mostrandoClear || mostrandoPantallaInicio) return;

		    // Comprobamos colisión antes de mover enemigos
		    if (gestorEnemigos.jugadorHaSidoTocado(jugador, this)) {
		        vidasJugador--;
		        jugador.setPosicion(12, 8);
		        gestorEnemigos.reiniciarEnemigos();
		        
		        if (vidasJugador <= 0) {
		            juegoActivo = false;
		            finDelJuego = true;

		            // 🔹 Caso 1: Jugador bate récord
		            if (score > record) {
		                mostrandoNuevoRecord = true;
		                insertandoRecord = false; // Aún no pedimos AAA
		                repaint();

		                // 🔹 Tras 3s mostramos AAA
		                new Timer(3000, ev -> {
		                    mostrandoNuevoRecord = false;
		                    insertandoRecord = true; // Ahora mostramos AAA
		                    ((Timer) ev.getSource()).stop();
		                    repaint();
		                }).start();

		            } 
		            // 🔹 Caso 2: Jugador NO bate récord
		            else {
		                panelJuego.repaint();
		                new Timer(3000, ev -> {
		                    mostrarOpcionesGameOver();
		                    ((Timer) ev.getSource()).stop();
		                }).start();
		            }
		        }



		    }

		    gestorEnemigos.actualizar(jugador, mapa, nivelActual);
		    if (vidasJugador <= 0) {
		        mostrarOpcionesGameOver();
		    }
		    repaint();
		}).start();

		
	}// end constructor

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///																														   ///
	///															METODOS														   ///
	///																														   ///
	///																														   ///
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	//metodo para los sonidos
	private void reproducirSonido(String rutaSonido) {
	    try {
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(rutaSonido));
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	private void detenerAnimacionCursor() {
	    if (animacionCursorTimer != null && animacionCursorTimer.isRunning()) {
	        animacionCursorTimer.stop();
	        animacionCursorTimer = null;
	    }
	}

	private void iniciarAnimacionCursor() {
	    if (animacionCursorTimer != null && animacionCursorTimer.isRunning()) {
	        return; // ya está animando
	    }

	    animacionCursorTimer = new Timer(300, e -> {
	        animacionCursor = !animacionCursor;
	        cursorActual = animacionCursor ? cursor2 : cursor1;
	        panelJuego.repaint();
	    });
	    animacionCursorTimer.start();
	}
	
	
	/// Metodo dibujar mapa
	private void dibujarMapa(Graphics g) {
		int [][] fase1 = mapa.getMapaPorNivel(nivelActual);
		for(int fila = 0; fila < fase1.length; fila++) {
			for(int col = 0; col < fase1[0].length; col++) {
				int valor = fase1[fila][col];
				if (valor == 1) {
				    switch (nivelActual) {
				    	// Cambia el color de la paredes segun el nivel
				        case 1 -> g.setColor(Color.BLUE);   // Nivel 1: azul
				        case 2 -> g.setColor(Color.GREEN);  // Nivel 2: verde
				        case 3 -> g.setColor(Color.RED);    // Nivel 3: rojo
				        default -> g.setColor(Color.GRAY);  // Seguridad
				    }
				} else {
				    g.setColor(Color.BLACK); // Fondo de casilla libre
				} 
				g.fillRect(col * Tam_Celdas, fila * Tam_Celdas + offsetY, Tam_Celdas, Tam_Celdas);
				g.setColor(Color.DARK_GRAY);
				g.drawRect(col * Tam_Celdas, fila * Tam_Celdas + offsetY, Tam_Celdas, Tam_Celdas);
			}
		}
		
		int[][] puntos = mapa.getPuntosPorNivel(nivelActual);
		for (int filaPuntos = 0; filaPuntos < puntos.length; filaPuntos++) {
			for (int columnaPuntos = 0; columnaPuntos < puntos[0].length; columnaPuntos++) {
				int tipo = puntos[filaPuntos][columnaPuntos];
				if (tipo == 1) {
					g.setColor(Color.WHITE);
					int offset = Tam_Celdas / 2 - 3;
					g.fillOval(columnaPuntos * Tam_Celdas + offset, filaPuntos * Tam_Celdas + offsetY + offset, 6, 6);
				} else if (tipo == 2){
					g.setColor(Color.RED);
					int offset = Tam_Celdas / 2 - 6;
					g.fillOval(columnaPuntos * Tam_Celdas + offset, filaPuntos * Tam_Celdas + offsetY + offset, 12, 12);
				}
			}
		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), offsetY);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Record: " + record, 10, 20);
		g.drawString("Score: " + score, 150, 20);
		g.drawString("Nivel: " + level, 280, 20);
		g.drawString("Vidas: " + vidasJugador, 380, 20);
		g.drawString("Top: " + nombreRecord, 500, 20);

		gestorEnemigos.dibujar(g, offsetY);
		jugador.dibujar(g, offsetY);
	}//end 
	
	
	// Muestra el cartel de STAGE al iniciar un nivel
	private void mostrarStageConDelay() {
	    mostrandoStage = true;
	    juegoActivo = false;
	    panelJuego.repaint();

	    new Timer(2000, e -> {
	        mostrandoStage = false;
	        juegoActivo = true;
	        ((Timer) e.getSource()).stop();
	        panelJuego.repaint();
	    }).start();
	}
	
	

	// Muestra el cartel de CLEAR al terminar un nivel
	private void mostrarClearConDelay(Runnable despues) {
	    mostrandoClear = true;
	    juegoActivo = false;
	    panelJuego.repaint();

	    reproducirSonido("audio/PantallaCompletada.wav"); // ✅ Aquí SÍ está bien

	    new Timer(3000, e -> {
	        mostrandoClear = false;
	        ((Timer) e.getSource()).stop();
	        despues.run();
	        juegoActivo = true;
	        panelJuego.repaint();
	    }).start();
	}



	
	//nuevo metodo activar power up
	public void activarPowerUp() {
	    jugador.activarModoPowerUp();  
	    gestorEnemigos.activarModoHuirEnemigos(); // Todos los enemigos cambian a modo huir

	    // Reinicia el temporizador si ya estaba corriendo
	    if (temporizadorPowerUp != null && temporizadorPowerUp.isRunning()) {
	        temporizadorPowerUp.stop();
	    }

	    temporizadorPowerUp = new Timer(3000, e -> {
	        jugador.desactivarModoPowerUp(); 

	        // Solo desactiva el modo huir si el fantasma NO está en modo comido
	        gestorEnemigos.desactivarModoHuirEnemigos();
	    });

	    temporizadorPowerUp.setRepeats(false);
	    temporizadorPowerUp.start();
	    
	    //mensaje para testear por consola
	    System.out.println("Power-Up activado");
	    
	} //end 
	
	

	
	
	// Dibuja imagen de Game Over centrada
	// Dibuja imagen de Game Over centrada
	private void mostrarGameOver(Graphics g) {
	    if (imagenGameOver != null) {
	        int x = (getWidth() - imagenGameOver.getWidth()) / 2;
	        int y = (getHeight() - imagenGameOver.getHeight()) / 4;
	        g.drawImage(imagenGameOver, x, y, null);
	    }

	    // 🔹 Solo dibujamos el menú si ya pasaron 3 segundos
	    if (mostrandoMenuGameOver && imagenOpcionJugar != null && imagenOpcionSalir != null) {
	        int centerX = getWidth() / 2;
	        int jugarY = getHeight() / 2;
	        int salirY = jugarY + 100;

	        g.drawImage(imagenOpcionJugar, centerX - imagenOpcionJugar.getWidth() / 2, jugarY, null);
	        g.drawImage(imagenOpcionSalir, centerX - imagenOpcionSalir.getWidth() / 2, salirY, null);

	        // Cursor
	        int cursorX = centerX - imagenOpcionJugar.getWidth() / 2 - 50;
	        int cursorY = (opcionSeleccionada == 0) ? jugarY : salirY;
	        g.drawImage(cursorActual, cursorX, cursorY, null);
	    }
	}



	
	
	// Muestra opciones tras Game Over
	// Al morir se comprueba tus puntos y se comparan con el record maximo, en caso de superar el record
	// este se actualiza, además se te muestra un mensaje que indica que has superado el record.
	// El record es guardado y si cierras el programa y lo habres se mantendra
	
	// Por lo demas se muestran las opcioens de volver a jugar o salir del juego
	// Si escogemos volver a jugar se llama al metodo -> reiniciarJuego() para preparar una partida de 0 otra vez
	// Muestra opciones tras Game Over
	private void mostrarOpcionesGameOver() {
	    // Si hay nuevo récord, primero entramos en la fase de introducir nombre
	    if (score > record) {
	        insertandoRecord = true;
	        Arrays.fill(indicesLetras, 0); // Reinicia a AAA
	        letraSeleccionada = 0;
	        //iniciarParpadeoLetra();
	        repaint();
	        return;
	    }

	    // 1️⃣ Solo mostramos el cartel de Game Over inicialmente
	    mostrandoGameOver = true;
	    mostrandoMenuGameOver = false; // Aún no mostramos el menú
	    //detenerAnimacionCursor(); // Por si acaso
	    repaint();

	    // 2️⃣ Esperar 3 segundos antes de mostrar el menú de opciones
	    new javax.swing.Timer(3000, e -> {
	        mostrandoMenuGameOver = true;  // Ahora sí mostramos el menú
	        opcionSeleccionada = 0;        // Reset posición del cursor
	        iniciarAnimacionCursor();      // Comenzamos animación
	        repaint();
	        ((javax.swing.Timer) e.getSource()).stop();
	    }).start();
	}


	
	
	

	
	
	
	// Guarda el record actual en un archivo
	private void guardarNuevoRecord(int nuevoRecord, String nombre) {
	    if (nombre == null || nombre.length() == 0) nombre = "---";
	    if (nombre.length() > 3) nombre = nombre.substring(0, 3);

	    record = nuevoRecord;
	    nombreRecord = nombre.toUpperCase();

	    try (PrintWriter writer = new PrintWriter("datos/record.txt")) {
	        writer.println(record);
	        writer.println(nombreRecord);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	
	
	
	// Métoo cargar record 
	private void cargarRecord() {
	    try (BufferedReader reader = new BufferedReader(new FileReader("datos/record.txt"))) {
	        String linea = reader.readLine(); // primera línea = puntos
	        if (linea != null) record = Integer.parseInt(linea);

	        String nombre = reader.readLine(); // segunda línea = nombre
	        if (nombre != null) nombreRecord = nombre;
	    } catch (IOException e) {
	        System.out.println("No se encontró archivo de récord. Se usará valor por defecto.");
	        record = 1000;
	        nombreRecord = "---";
	    }
	}
	
	


	// Reinicia el juego a su estado inicia
	private void reiniciarJuego() {
	    nivelActual = 1;
	    level = 1;
	    vidasJugador = 3;
	    score = 0;
	    puntosComidos = 0;
	    finDelJuego = false; // <-- importante
	    juegoActivo = false; // se activará tras el stage
	    idxUmbralVida = 0;          // rearmar umbrales
	    mostrandoPopupVida = false; // apagar cualquier popup pendientes
	    mapa = new Mapa();
	    jugador.setPosicion(12, 8);
	    gestorEnemigos.reiniciarEnemigos();
	    panelJuego.repaint();
	    mostrarStageConDelay();
	    

	}
	
	// Método para avanzar de nivel
	private void avanzarNivel() {
	    nivelActual++;
	    puntosComidos = 0;

	    // Mostramos cartel de "CLEAR" y luego cambiamos de nivel
	    mostrarClearConDelay(() -> {
	        mapa = new Mapa(); // siempre es el mismo, con lógica interna para cada nivel
	        jugador.setPosicion(12, 8);
	        gestorEnemigos.reiniciarEnemigos();
	        level = nivelActual;
	        mostrarStageConDelay(); // mostramos STAGE al empezar el nuevo nivel
	    });
	}
	
	
	// Otorga la vida, suena el audio y enciende el popup
	public void otorgarVidaExtra() {
	    vidasJugador++;
	    reproducirSonido("audio/VidaExtra.wav");  // sonido nuevo

	    mostrandoPopupVida = true;
	    finPopupVidaMs = System.currentTimeMillis() + 1200; // 1.2s de popup
	    mostrarPopupVidaExtra();
	} //end

	
	// Comprueba si hemos alcanzado uno o varios umbrales de vida extra
	public void checkVidasExtra() {
	    while (idxUmbralVida < umbralesVidas.length && score >= umbralesVidas[idxUmbralVida]) {
	        otorgarVidaExtra();
	        idxUmbralVida++; // avanzamos al siguiente umbral
	    }
	} //end
	
	// Muestra un popup "+1 VIDA" justo encima del jugador
	private void mostrarPopupVidaExtra() {
	    // Coordenadas en píxeles del centro de la celda del jugador
	    int px = jugador.getColumna() * Tam_Celdas + (Tam_Celdas / 2);
	    int py = jugador.getFila()    * Tam_Celdas + offsetY + (Tam_Celdas / 2) - 12; // un pelín arriba

	    // ⬇️ Usa la firma que tenga tu ScorePopup. Te dejo 3 variantes comunes:

	    // Variante A (x, y, texto)
	    popups.add(new ScorePopup(px, py, "+1 VIDA"));

	    // Variante B (x, y, texto, duraciónMs)
	    // popups.add(new ScorePopup(px, py, "+1 VIDA", 1200));

	    // Variante C (x, y, texto, color, tamañoFuente, duraciónMs)
	    // popups.add(new ScorePopup(px, py, "+1 VIDA", Color.GREEN, 18, 1200));

	    // ✅ Elige UNA de las variantes de arriba según tu constructor real:
	    popups.add(new ScorePopup(px, py, "+1 VIDA"));  // <-- ajusta si tu ScorePopup lo pide

	    panelJuego.repaint();
	}


	
	// Metodo para los sonidos
	public class SoundManager {
	    private Clip sonido;

	    // Inicia la música en bucle
	    public void playMusic(String filepath) {
	        try {
	            File file = new File(filepath);
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	            sonido = AudioSystem.getClip();
	            sonido.open(audioStream);
	            sonido.loop(Clip.LOOP_CONTINUOUSLY); // Repite indefinidamente
	            sonido.start();
	        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	            e.printStackTrace();
	        }
	    }

	    // Detiene la música
	    public void stopMusic() {
	        if (sonido != null && sonido.isRunning()) {
	            sonido.stop();
	            sonido.close();
	        }
	    }
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		new Strat_Game(); 
	}//end
	
	
	
	
}//end clase






















