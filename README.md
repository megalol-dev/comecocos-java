# 🟡 Comecocos en Java (Pac-Man style)

Recreación del clásico arcade **PAC-MAN** desarrollada en **Java + Swing**.  
No es una copia 1:1 del original, pero sí reproduce las mecánicas principales: comer bolitas, huir o perseguir fantasmas según el estado, avanzar de nivel y guardar récords como en las recreativas de los 80.

Este proyecto lo hice para **aprender a manejar ventanas, gráficos, eventos de teclado, sistemas de estados y lectura/escritura de ficheros** en Java. Fue un reto grande para el nivel que tenía entonces, pero lo fui sacando poco a poco y aprendiendo con cada problema 💪

---

## 🕹️ Cómo se juega

1. **Pantalla de inicio** con mensaje animado `PRESS START`.
2. Pulsas **ENTER** → empieza la partida.
3. Controlas al jugador (Pac-Man) con el teclado y debes **comer todas las bolitas** del nivel.
4. Hay **3 niveles**. Si completas los 3 → ves la **pantalla final** como en los arcades.
5. Hay **3 fantasmas**:
   - 🔴 Rojo
   - 🟢 Verde
   - 🟣 Morado  
   Siempre salen en ese orden y **persiguen al jugador** (tienen comportamiento inteligente básico).
6. Si comes una **bola power-up**:
   - El jugador pasa a **estado 2** (cambia su gráfico).
   - Los fantasmas también cambian su gráfico para indicar que **ahora pueden ser comidos**.
   - Los fantasmas dejan de perseguir y **huyen**.
   - Si tocas a un fantasma en este estado → **lo comes**, se queda en **2 ojos** y **vuelve a la base** para reaparecer.
   - Pasados unos segundos, todo vuelve al **estado normal**.
7. Si un fantasma toca al jugador en estado normal → **pierdes 1 vida** y todos vuelven a su posición inicial.
8. Si te comes **todas las bolitas del mapa** (por ejemplo, 118 en el primer nivel) → **pasas de nivel**.
9. Si bates el **récord** → al final del juego o en **Game Over** te deja introducir tus **iniciales** (como en los arcades: `JLE`, etc.).

---

## ✨ Características principales

- ✅ **3 niveles** con diseño en matriz (se dibujan desde la clase `Mapa`)
- ✅ **3 fantasmas** con comportamiento por estados
- ✅ **Power-ups** que invierten los roles (jugador persigue / fantasma huye)
- ✅ **Sistema de colisiones** jugador–pared / jugador–puntos / jugador–fantasma
- ✅ **Sistema de vidas** (pierdes vida → reseteo de posiciones)
- ✅ **Sistema de puntuación** y **puntos extra**
- ✅ **Popups de puntuación** en pantalla (`scorePopup`) cuando comes un fantasma o consigues bonus
- ✅ **Sistema de récord persistente** (se guarda en carpeta `datos/` con el nombre/ iniciales del jugador)
- ✅ **Pantalla de inicio** y **pantalla final**
- ✅ **Gráficos propios** y **música / sonidos** (música de Felipe Monzón 💿)

---

## 🏗️ Arquitectura del proyecto

El juego está organizado en **6 clases principales**:

1. **`Enemigo`**  
   Define qué es un fantasma, qué estados puede tener (normal, huir, comido) y su comportamiento base.

2. **`GestorEnemigos`**  
   Aquí se crean y controlan **cada uno de los 3 fantasmas** (rojo, verde y morado).  
   Se decide qué hace cada uno cuando cambia el estado del juego (por ejemplo, cuando el jugador come un power-up).

3. **`Jugador`**  
   Lógica y dibujo del jugador: posición, movimiento, cambio de sprite cuando come power-up, detección de colisión con fantasmas, etc.

4. **`Mapa`**  
   Encargada de **dibujar los 3 niveles** a partir de matrices.  
   También controla:
   - dónde hay paredes
   - dónde hay bolitas normales
   - dónde hay power-ups
   - cuándo se han comido todas las bolitas → pasar de nivel

5. **`scorePopup`**  
   Clase auxiliar para mostrar **puntuaciones flotantes** sobre el sitio donde se comió al fantasma o se ganó bonus.  
   Esto le da un toque más arcade.

6. **`Strat_Game`** (o `Start_Game`, según tu clase final)  
   Es la **clase principal del juego**.  
   - Arranca la ventana  
   - Dibuja todo  
   - Carga jugador, enemigos y mapa  
   - Controla el **loop del juego**  
   - Gestiona los **estados globales** (inicio, jugando, nivel completado, game over, victoria)
   - Controla también la lectura/escritura del récord

---

## 📦 Recursos y carpetas

- `img/` → sprites del jugador, fantasmas, bolitas, pantallas de inicio/final, etc.  
- `audio/` → música y efectos (`AudioSystem`, `Clip`, etc.).  
- `datos/` → aquí se guarda el **récord** y las **iniciales** del jugador en un archivo de texto.  
  Esto hace que el récord sea **persistente**: si cierras el juego, al volver sigue ahí.

---

## 🧰 Tecnologías y librerías usadas

- ☕ **Java**
- 🪟 **Swing** (`javax.swing.*`)
- 🧩 **AWT** (`java.awt.*`)
- 🖼️ **BufferedImage** para cargar y dibujar sprites
- 🎵 **javax.sound.sampled** para música y efectos
- 📁 **I/O de ficheros** (`File`, `FileReader`, `BufferedReader`, `PrintWriter`, `FileWriter`) para guardar el récord

---

## 🏁 Flujo del juego

1. **Inicio** → pantalla con “PRESS START”
2. **Juego** → jugador + 3 fantasmas + puntos + power-ups
3. **Nivel completado** → transición al siguiente nivel
4. **Nivel 3 completado** → pantalla final de victoria
5. **Game Over** (sin vidas) → pantalla final + comprobación de récord
6. **Si hay récord** → pedir iniciales → guardar en `/datos/`

---

## 🚀 Cómo ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/megalol-dev/comecocos-java.git
2. Ábrelo en Eclipse (o cualquier IDE Java).

3. Asegúrate de que las carpetas img/, audio/ y datos/ están en la ruta correcta (como en el proyecto original).

4. Ejecuta la clase principal (Strat_Game.java / la que use tu proyecto)

5. ¡Juega! 🕹️

---

# 📫 Contacto

📧 Email: **escuderopolojoseluis@gmail.com**

🌐 Portfolio: https://megalol-dev.github.io/

💼 LinkedIn: https://linkedin.com/in/jose-luis-escudero-polo

📺 YouTube: https://youtu.be/3_o1sipq6Po?si=CDCCaBfs46CEmhwv

---

## 🙏 Agradecimientos

Un agradecimiento especial a **Felipe Monzón** por la composición y creación de la música y efectos de sonido del juego.  
Su trabajo ayudó a dar al proyecto una auténtica atmósfera arcade y completar la experiencia del jugador.

-----
