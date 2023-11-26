/*Nombres: Raziel López, Gabriela Salado, Aldhair Hidalgo, Edgard Cárdenas
Grupo 1S3111*/

import java.io.*;

class Jugador {
    private String nombre;
    private Tablero tableroFlota;
    private Tablero tableroAtaques;
    private Barcos[] barcos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        tableroFlota = new Tablero();
        tableroAtaques = new Tablero();
        barcos = new Barcos[4];
    }

    public void ColocarBarcos() throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        int tamaño;
        String orientacion, coordenadas;

        for (int i = 0; i < barcos.length; i++) {
            tamaño = 0;
            orientacion = "";
            coordenadas = "";

            while (true) {
                try {
                    System.out.println("Ingresa la información para el barco " + (i + 1) + ":");
                    System.out.print("Tamaño (2-4): ");
                    tamaño = Integer.parseInt(lector.readLine());
                    if (tamaño < 2 || tamaño > 4) {
                        throw new Exception("Tamaño inválido. Debe ser 2, 3 o 4.");
                    }

                    System.out.print("Orientación (horizontal/vertical): ");
                    orientacion = lector.readLine();
                    if (!orientacion.equals("horizontal") && !orientacion.equals("vertical")) {
                        throw new Exception("Orientación inválida. Debe ser horizontal o vertical.");
                    }

                    System.out.print("Coordenadas (Ejemplo: A1): ");
                    coordenadas = lector.readLine();
                    if (!ValidarCoordenadas(coordenadas)) {
                        throw new Exception("Coordenadas inválidas.");
                    }

                    if (!VerificarSuperposicion(coordenadas, tamaño, orientacion)) {
                        throw new Exception("No se puede colocar el barco sobre otro barco.");
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingresa un número válido.");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

            barcos[i] = new Barcos(tamaño, orientacion, coordenadas);
            ColocarBarcoEnTablero(tableroFlota, coordenadas, tamaño, orientacion);
        }
    }

    private boolean ValidarCoordenadas(String coordenadas) {
        if (coordenadas.matches("[A-I][1-9]")) {
            return true;
        }
        return false;
    }

    private boolean VerificarSuperposicion(String coordenadas, int tamaño, String orientacion) {
        int fila, columna; 
        fila = coordenadas.charAt(0) - 'A';
        columna = Integer.parseInt(coordenadas.substring(1)) - 1;

        if (orientacion.equals("horizontal")) {
            for (int i = columna; i < columna + tamaño; i++) {
                if (tableroFlota.getTablero()[fila][i] == 'B') {
                    return false;
                }
            }
        } else if (orientacion.equals("vertical")) {
            for (int i = fila; i < fila + tamaño; i++) {
                if (tableroFlota.getTablero()[i][columna] == 'B') {
                    return false;
                }
            }
        }

        return true;
    }

    private void ColocarBarcoEnTablero(Tablero tablero, String coordenadas, int tamaño, String orientacion) {
        int fila, columna;
        fila = coordenadas.charAt(0) - 'A';
        columna = Integer.parseInt(coordenadas.substring(1)) - 1;

        if (orientacion.equals("horizontal")) {
            for (int i = columna; i < columna + tamaño; i++) {
                tablero.getTablero()[fila][i] = 'B';
            }
        } else if (orientacion.equals("vertical")) {
            for (int i = fila; i < fila + tamaño; i++) {
                tablero.getTablero()[i][columna] = 'B';
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Tablero getTableroFlota() {
        return tableroFlota;
    }

    public Tablero getTableroAtaques() {
        return tableroAtaques;
    }

    public Barcos[] getBarcos() {
        return barcos;
    }
}


