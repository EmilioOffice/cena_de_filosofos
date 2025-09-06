# 🍽️ La Cena de los Filósofos en Java

Este proyecto es una simulación gráfica e interactiva del clásico problema de concurrencia **"La Cena de los Filósofos"**. Fue desarrollado como una práctica académica para la materia de Computación Paralela, utilizando hilos (`Threads`) en Java y una interfaz gráfica construida con Swing.

---

## 📜 Descripción del Problema

Cinco filósofos se sientan alrededor de una mesa circular. Entre cada par de filósofos hay un tenedor. La vida de un filósofo consiste en alternar entre pensar y comer. Para comer, un filósofo necesita tomar los dos tenedores que tiene a su lado (el de la izquierda y el de la derecha). El desafío es diseñar un protocolo que permita a los filósofos comer sin que el sistema llegue a un estado de **interbloqueo (deadlock)**, donde todos los filósofos toman un tenedor y esperan indefinidamente por el otro.

### El Escenario del Interbloqueo

Si todos los filósofos intentan tomar su tenedor izquierdo simultáneamente, cada uno tendrá un tenedor y esperará por el derecho, que está en posesión de su vecino. Esto crea una espera circular donde nadie puede continuar, y el sistema se detiene.

---

## ✨ Características de la Simulación

Este programa no solo resuelve el problema, sino que también ofrece un entorno interactivo para visualizar y entender la concurrencia:

*   **Simulación Gráfica**: Interfaz clara y en tiempo real construida con Java Swing.
*   **Monitoreo de Estados**: Visualiza el estado actual de cada filósofo (`PENSANDO`, `HAMBRIENTO`, `COMIENDO`) y de cada tenedor (`Libre` u `Ocupado`).
*   **Prevención de Interbloqueo**: Implementa una solución clásica (el filósofo "zurdo") que se puede activar o desactivar para comparar los resultados.
*   **Controles Dinámicos**: Permite añadir más filósofos y tenedores a la simulación "al vuelo", reiniciando el entorno con la nueva configuración.
*   **Gestión Segura de Hilos**: El programa gestiona el ciclo de vida de los hilos de forma segura, incluyendo un botón de salida que los termina limpiamente.

---

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje**: Java
*   **Librerías Gráficas**: `Java AWT` y `Java Swing` para la interfaz de usuario.
*   **Concurrencia**: `java.lang.Thread` y `java.util.concurrent.locks.ReentrantLock` para la gestión de hilos y recursos compartidos.

---

## 🚀 Instrucciones de Ejecución

Para compilar y ejecutar este proyecto, necesitas tener instalado el **JDK (Java Development Kit)** en tu sistema.

### Pasos

1.  **Clonar o Descargar el Repositorio**:
    Asegúrate de tener los 4 archivos `.java` en la misma carpeta:
    *   `Main.java`
    *   `Mesa.java`
    *   `Filosofo.java`
    *   `Tenedor.java`

2.  **Abrir una Terminal**:
    Navega a través de la línea de comandos hasta el directorio donde guardaste los archivos.

3.  **Compilar el Código**:
    Ejecuta el siguiente comando para compilar todos los archivos Java.
    ```bash
    javac *.java
    ```

4.  **Ejecutar el Programa**:
    Una vez compilado, ejecuta la clase principal para iniciar la simulación.
    ```bash
    java Main
    ```

¡Listo! La ventana de la simulación aparecerá y podrás interactuar con ella.

---

## 👨‍💻 Autor y Propietario

Este proyecto y su código fuente son propiedad de:

*   **Juan Emilio Marquez Gudiño**
*   **Correo**: <emiliomrqz@gmail.com>

---

*Este proyecto fue creado con fines educativos.*```
