package Materiales;
import java.awt.*;

public class ScorePopup {
    private int x, y;
    private String texto;
    private long tiempoCreacion;
    private int duracion = 1000; // 1 segundo
    private int velocidadY = 1;  // píxeles por frame

    public ScorePopup(int x, int y, String texto) {
        this.x = x;
        this.y = y;
        this.texto = texto;
        this.tiempoCreacion = System.currentTimeMillis();
    }

    public boolean isAlive() {
        return System.currentTimeMillis() - tiempoCreacion < duracion;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(texto, x, y);
        y -= velocidadY; // Hace que suba poco a poco
    }
}

