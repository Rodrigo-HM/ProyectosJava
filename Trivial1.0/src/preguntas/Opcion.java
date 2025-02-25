package preguntas;

public class Opcion {
    private String enunciado;
    private boolean correcta;

    public Opcion(String enunciado, boolean correcta) {
        this.enunciado = enunciado;
        this.correcta = correcta;
    }


    public boolean isCorrecta() {
        return correcta;
    }

    public String getEnunciado() {
        return enunciado;
    }
}
