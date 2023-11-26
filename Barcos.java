/*Nombres: Raziel López, Gabriela Salado, Aldhair Hidalgo, Edgard Cárdenas
Grupo 1S3111*/

class Barcos {
    private int tamaño;
    private String orientacion;
    private String coordenadas;

    public Barcos(int tamaño, String orientacion, String coordenadas) {
        this.tamaño = tamaño;
        this.orientacion = orientacion;
        this.coordenadas = coordenadas;
    }


    public int getTamaño() {
        return tamaño;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

}

