/*Nombres: Raziel López, Gabriela Salado, Aldhair Hidalgo, Edgard Cárdenas
Grupo 1S3111*/

import java.io.*;

public class MainBattleship {
    public static void main(String[] args) {
        try {
            //Declaracion de variables 
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            String coordenadas;
            int fila, columna;
            boolean acierto;

            Jugador jugador1 = new Jugador("");
            Jugador jugador2 = new Jugador("");

            //Inicio de programa
            System.out.print("Nombre del jugador 1: ");
            jugador1 = new Jugador(lector.readLine());
            System.out.print("Nombre del jugador 2: ");
            jugador2 = new Jugador(lector.readLine());

            System.out.println("\n*** " + jugador1.getNombre() + " ***");
            jugador1.ColocarBarcos();
            System.out.println("\n*** " + jugador2.getNombre() + " ***");
            jugador2.ColocarBarcos();

            System.out.println("\n¡Comienza el juego!");

            Jugador jugadorActual = jugador1;
            Jugador siguienteJugador = jugador2;

            while (true) {
                System.out.println("\n--- " + jugadorActual.getNombre() + " ---");
                System.out.println("Tablero de tu flota:");
                jugadorActual.getTableroFlota().ImprimirTablero();
                System.out.println("\nTablero de tus ataques:");
                jugadorActual.getTableroAtaques().ImprimirTablero();

                coordenadas = "";
                while (true) {
                    try {
                        System.out.println("Ingresa las coordenadas de ataque en este orden(letra-número Ej->B4): ");
                        coordenadas = lector.readLine();
                        if (coordenadas.equalsIgnoreCase("exit")) {
                            System.out.println("\n¡" + jugadorActual.getNombre() + " se rindió! " + siguienteJugador.getNombre() + " es el ganador.");
                            ImprimirNaves(jugador1);
                            ImprimirNaves(jugador2);
                            return;
                        }

                        if (!ValidarCoordenadas(coordenadas)) {
                            throw new Exception("Coordenadas inválidas.");
                        }

                        fila = coordenadas.charAt(0) - 'A';
                        columna = Integer.parseInt(coordenadas.substring(1)) - 1;
                        if (jugadorActual.getTableroAtaques().getTablero()[fila][columna] == 'X' ||
                                jugadorActual.getTableroAtaques().getTablero()[fila][columna] == 'O') {
                            throw new Exception("Ya has atacado esa coordenada previamente.");
                        }

                        jugadorActual.getTableroAtaques().getTablero()[fila][columna] = 'X';
                        acierto = VerificarAcierto(siguienteJugador, coordenadas);

                        if (acierto) {
                            System.out.println("¡Ataque exitoso!");

                            // Verificar si se hundió completamente un barco
                            if (VerificarHundimientoCompleto(siguienteJugador, coordenadas)) {
                                System.out.println("¡Hundiste un barco!");
                            }

                            if (VerificarVictoria(siguienteJugador, jugadorActual)) {
                                System.out.println("\n¡" + jugadorActual.getNombre() + " ha hundido toda la flota de " + siguienteJugador.getNombre() + "!");
                                System.out.println(jugadorActual.getNombre() + " es el ganador.");
                                ImprimirNaves(jugador1);
                                ImprimirNaves(jugador2);
                                return;
                            }
                        } else {
                            System.out.println("El ataque no ha dado en el blanco.");
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingresa un número válido.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                // Cambiar de jugador
                if (jugadorActual == jugador1) {
                    jugadorActual = jugador2;
                    siguienteJugador = jugador1;
                } else {
                    jugadorActual = jugador1;
                    siguienteJugador = jugador2;
                }

                // Verificar si el jugador actual ha perdido
                if (VerificarVictoria(jugadorActual, siguienteJugador)) {
                    System.out.println("\n¡" + jugadorActual.getNombre() + " ha hundido toda la flota de " + siguienteJugador.getNombre() + "!");
                    System.out.println(jugadorActual.getNombre() + " es el ganador.");
                    ImprimirNaves(jugador1);
                    ImprimirNaves(jugador2);
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        }//Fin catch
    }// Fin main

    private static boolean ValidarCoordenadas(String coordenadas) {
        if (coordenadas.matches("[A-I][1-9]")) {
            return true;
        }//fin if
        return false;
    }//Fin validar coordenadas


    private static boolean VerificarAcierto(Jugador jugador, String coordenadas) {
        int fila, columna;
         int barcoFila, barcoColumna; 
       

        fila = coordenadas.charAt(0) - 'A';
        columna = Integer.parseInt(coordenadas.substring(1)) - 1;

        for (Barcos barco : jugador.getBarcos()) {
            barcoFila = barco.getCoordenadas().charAt(0) - 'A';
            barcoColumna = Integer.parseInt(barco.getCoordenadas().substring(1)) - 1;

            if (barco.getOrientacion().equals("horizontal") && barcoFila == fila) {
                for (int i = barcoColumna; i < barcoColumna + barco.getTamaño(); i++) {
                    if (i == columna) {
                        return true;
                    }//fin if
                }//fin ciclo for
            } else if (barco.getOrientacion().equals("vertical") && barcoColumna == columna) {
                for (int i = barcoFila; i < barcoFila + barco.getTamaño(); i++) {
                    if (i == fila) {
                        return true;
                    }//fin if
                }//fin ciclo for 
            }//fin else if
        }//fin ciclo for

        return false;
    }//fin VerificarAcierto
    private static boolean VerificarHundimientoCompleto(Jugador jugador, String coordenadas) {
        int fila, columna;
    
        fila = coordenadas.charAt(0) - 'A';
        columna = Integer.parseInt(coordenadas.substring(1)) - 1;
    
        for (Barcos barco : jugador.getBarcos()) {
            int barcoFila = barco.getCoordenadas().charAt(0) - 'A';
            int barcoColumna = Integer.parseInt(barco.getCoordenadas().substring(1)) - 1;
    
            if (barco.getOrientacion().equals("horizontal")) {
                for (int i = barcoColumna; i < barcoColumna + barco.getTamaño(); i++) {
                    if (i != columna && jugador.getTableroAtaques().getTablero()[barcoFila][i] != 'X') {
                        return false;
                    }//fin if
                }//fin ciclo for
            } else if (barco.getOrientacion().equals("vertical")) {
                for (int i = barcoFila; i < barcoFila + barco.getTamaño(); i++) {
                    if (i != fila && jugador.getTableroAtaques().getTablero()[i][barcoColumna] != 'X') {
                        return false;
                    }//fin if
                }//fin ciclo for
            }//fin else if
        }//fin ciclo for
        return true;
    }//Fin VerificarHundimeintoCompleto
    
    private static boolean VerificarVictoria(Jugador jugador, Jugador siguienteJugador) {
        for (Barcos barco : siguienteJugador.getBarcos()) {
            int barcoFila = barco.getCoordenadas().charAt(0) - 'A';
            int barcoColumna = Integer.parseInt(barco.getCoordenadas().substring(1)) - 1;
    
            if (barco.getOrientacion().equals("horizontal")) {
                for (int i = barcoColumna; i < barcoColumna + barco.getTamaño(); i++) {
                    if (jugador.getTableroAtaques().getTablero()[barcoFila][i] != 'X') {
                        return false;
                    }//fin if
                }//fin ciclo for
            } else if (barco.getOrientacion().equals("vertical")) {
                for (int i = barcoFila; i < barcoFila + barco.getTamaño(); i++) {
                    if (jugador.getTableroAtaques().getTablero()[i][barcoColumna] != 'X') {
                        return false;
                    }//fin if
                }//fin ciclo for
            }//fir else if
        }//fin ciclo for

        return true;
    }//fin VerificarVictoia    
    
    

    private static void ImprimirNaves(Jugador jugador) {
        System.out.println("\nCoordenadas de las naves de " + jugador.getNombre());
        for (Barcos barco : jugador.getBarcos()) {
            System.out.println("Nave de " + barco.getTamaño() +
                    " " + barco.getCoordenadas() +
                    " " + barco.getOrientacion());
        }
    }//fin ImprimirNaves
}//Fin MainBattleship


