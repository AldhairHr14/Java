/*Nombres: Raziel López, Gabriela Salado, Aldhair Hidalgo, Edgard Cárdenas
Grupo 1S3111*/

class Tablero {
    private final int FILAS = 9;
    private final int COLUMNAS = 9;
    private char[][] tablero;
    char letra;

    public Tablero() {
        tablero = new char[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = '~';
            }
        }
    }

    public void ImprimirTablero() {
        System.out.println("  1 2 3 4 5 6 7 8 9");
        char letra = 'A';
        for (int i = 0; i < FILAS; i++) {
            System.out.print(letra + " ");
            letra++;
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }
    


    public void colocarBarco(char letra, int numero) {
        tablero[letra - 'A'][numero - 1] = 'B';
    }

    public char[][] getTablero() {
        return tablero;
    }
}