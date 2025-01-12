import java.util.Objects;
import java.util.Scanner;

public class Mapa {

    static Scanner sc = new Scanner(System.in);
    private static boolean mapa;
    private static boolean boss;
    private static int f;
    private static int c;

    public static void setMapa(boolean mapa) {
        Mapa.mapa = mapa;
    }

    public static void setBoss(boolean boss) {
        Mapa.boss = boss;
    }

    public static boolean isBoss() {
        return boss;
    }

    public static boolean isMapa() {
        return mapa;
    }

    public static String[][] getCuadricula() {
        return cuadricula;
    }

    public static void setCuadricula(String[][] cuadricula) {
        Mapa.cuadricula = cuadricula;
    }

    private static String[][] cuadricula = new String[5][5];

    public static void mostrarmapa() {
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                cuadricula[fila][columna] = "⏹"; // Valor inicial
            }
        }
        System.out.println("Generando nuevo piso...");
        System.out.println();

        for (int i = 0; i < 5; i++) {
            int numeroAleatorio = (int) (Math.random() * 5);
            int numeroAleatorio2 = (int) (Math.random() * 5);

            if(numeroAleatorio2==2&&numeroAleatorio==2){
                i--;

            }if(Objects.equals(cuadricula[numeroAleatorio][numeroAleatorio2], "☐")){
                i--;
            }else {
            cuadricula[numeroAleatorio][numeroAleatorio2] = "☐";
            cuadricula[2][2] = "☹";
            }
        }
        Mapa.boss();




        System.out.println();
    }

    public static void imprimirCuadricula() {
        for (String[] fila : cuadricula) {
            for (String celda : fila) {
                System.out.print(celda + "\t");
            }
            System.out.println();
        }
    }
    public static void movimiento() {
        Mapa.imprimirCuadricula();
        System.out.println("¿Dónde quieres ir? (Usa el teclado numérico: 4, 6, 8, 2)");
        String movimiento = sc.nextLine();

        switch (movimiento) {
            case "4": // Izquierda
                for (int fila = 0; fila < 5; fila++) {
                    for (int columna = 0; columna < 5; columna++) {
                        if (cuadricula[fila][columna].equals("☹")) {
                            if (columna - 1 >= 0) { // Verificar límites
                                if (cuadricula[fila][columna - 1].equals("☐")) {
                                    mapa = true;
                                }if (cuadricula[fila][columna-1].equals("☠")) {
                                    boss = true;
                                }
                                cuadricula[fila][columna - 1] = "☹";
                                if(!Main.huirbos){
                                cuadricula[fila][columna] = "☑";
                                }else{
                                    cuadricula[fila][columna] = "☠";
                                    Main.huirbos = false;
                                }
                            } else {
                                System.out.println("Movimiento no válido. No puedes salir del mapa.");
                            }
                            return;
                        }
                    }
                }
                break;

            case "6": // Derecha
                for (int fila = 0; fila < 5; fila++) {
                    for (int columna = 0; columna < 5; columna++) {
                        if (cuadricula[fila][columna].equals("☹")) {
                            if (columna + 1 < 5) { // Verificar límites
                                if (cuadricula[fila][columna + 1].equals("☐")) {
                                    mapa = true;
                                }if (cuadricula[fila ][columna+1].equals("☠")) {
                                    boss = true;
                                }
                                cuadricula[fila][columna + 1] = "☹";
                                if(!Main.huirbos){
                                    cuadricula[fila][columna] = "☑";
                                }else{
                                    cuadricula[fila][columna] = "☠";
                                    Main.huirbos = false;
                                }
                            } else {
                                System.out.println("Movimiento no válido. No puedes salir del mapa.");
                            }
                            return;
                        }
                    }
                }
                break;

            case "8": // Arriba
                for (int fila = 0; fila < 5; fila++) {
                    for (int columna = 0; columna < 5; columna++) {
                        if (cuadricula[fila][columna].equals("☹")) {
                            if (fila - 1 >= 0) { // Verificar límites
                                if (cuadricula[fila - 1][columna].equals("☐")) {
                                    mapa = true;
                                }if (cuadricula[fila - 1][columna].equals("☠")) {
                                    boss = true;
                                }
                                cuadricula[fila - 1][columna] = "☹";
                                if(!Main.huirbos){
                                    cuadricula[fila][columna] = "☑";
                                }else{
                                    cuadricula[fila][columna] = "☠";
                                    Main.huirbos = false;
                                }
                            } else {
                                System.out.println("Movimiento no válido. No puedes salir del mapa.");
                            }
                            return;
                        }
                    }
                }
                break;

            case "2": // Abajo
                for (int fila = 0; fila < 5; fila++) {
                    for (int columna = 0; columna < 5; columna++) {
                        if (cuadricula[fila][columna].equals("☹")) {
                            if (fila + 1 < 5) { // Verificar límites
                                if (cuadricula[fila + 1][columna].equals("☐")) {
                                    mapa = true;
                                }if (cuadricula[fila + 1][columna].equals("☠")) {
                                    boss = true;
                                }
                                cuadricula[fila + 1][columna] = "☹";
                                if(!Main.huirbos){
                                    cuadricula[fila][columna] = "☑";
                                }else{
                                    cuadricula[fila][columna] = "☠";
                                    Main.huirbos = false;
                                }
                            } else {
                                System.out.println("Movimiento no válido. No puedes salir del mapa.");
                            }
                            return;
                        }
                    }
                }
                break;


            default:
                System.out.println("Numero erróneo");
                movimiento();
        }
        imprimirCuadricula();

    }
    public static void boss() {
        boolean colocado = false;

        while (!colocado) {
            // Generar una posición aleatoria
            int filaAleatoria = (int) (Math.random() * cuadricula.length);
            int columnaAleatoria = (int) (Math.random() * cuadricula[0].length);


            if (cuadricula[filaAleatoria][columnaAleatoria].equals("⏹")) {

                cuadricula[filaAleatoria][columnaAleatoria] = "☠";
                colocado = true;
                ubiboss();
            }
        }
    }
    public static boolean quedansalas(){

        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                if (cuadricula[fila][columna].equals("☐")) {
                    return true;
                }
            }

        }return false;
    }
    public static void ubiboss(){
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                if (cuadricula[fila][columna].equals("☠")) {
                        f=fila;
                        c=columna;

                    return;
                }
            }
        }
    }


}
