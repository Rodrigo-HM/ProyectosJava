import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static boolean huirbos = false;
    public static int piso = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        ;


        // Crear personaje


        // Crear lista de enemigos
        ArrayList<Enemigo> enemigos = new ArrayList<>();
        enemigos.add(new Enemigo("Araña", 7, 7));
        enemigos.add(new Enemigo("Mosca", 5, 5));
        enemigos.add(new Enemigo("Mini Gurdy", 9, 9));
        enemigos.add(new Enemigo("Esqueleto", 8, 8));

        Enemigo[] boss = new Enemigo[3];
        boss[1] = new Enemigo("Monstruo", 20, 20);
        boss[0] = new Enemigo("Pin", 14, 14);
        boss[2] = new Enemigo("MegaSatán", 33, 33);


        System.out.println("¡Bienvenido a The Binding of Isaac (versión demo en Java)!");
        Personajes personajeSeleccionado = Personajes.seleccionarPersonaje();
        System.out.println("Comienza tu aventura con " + personajeSeleccionado.getNombre() + "...");

        Mapa.generarmapa();

        // Juego principal
        while (personajeSeleccionado.vida > 0 && piso != 3) {


            System.out.println(personajeSeleccionado.getNombre() + " tiene " + Personajes.representarVida(personajeSeleccionado.vida, personajeSeleccionado.vidamaxima) + " (" + personajeSeleccionado.vida + "/" + personajeSeleccionado.vidamaxima + ") puntos de vida y " + personajeSeleccionado.ataque + " de ataque.");
            Mapa.movimiento();
            System.out.println("Te encuentras en una nueva habitación.");


            if (Mapa.isMapa()) {

                // Elegir un enemigo al azar
                Enemigo enemigo = enemigos.get(random.nextInt(enemigos.size()));
                System.out.println("¡Un enemigo aparece! Es un@ " + enemigo.nombre + " con " + Enemigo.representarVida(enemigo.vida, enemigo.getVidamaxima()) + " (" + enemigo.vida + "/" + enemigo.getVidamaxima() + ").");


                // Batalla
                while (enemigo.estaVivo() && personajeSeleccionado.vida > 0) {
                    System.out.println("¿Qué deseas hacer?");
                    System.out.println("1. Atacar al enemigo");
                    System.out.println("2. Huir (perderás 1 de vida)");
                    int opcion = scanner.nextInt();

                    if (opcion == 1) {
                        personajeSeleccionado.atacar(enemigo);
                        if (enemigo.estaVivo()) {

                            int ataquerandom = (int) (Math.random() * 3) + 1;
                            if (ataquerandom == 1) {
                                System.out.println("El enemigo ha fallado su ataque...");
                                System.out.println("Vida de " + enemigo.nombre + " = " + Enemigo.representarVida(enemigo.vida, enemigo.getVidamaxima()) + " (" + enemigo.vida + "/" + enemigo.getVidamaxima() + ").");
                            } else {
                                personajeSeleccionado.vida -= 1; // El enemigo contraataca
                                System.out.println("El enemigo " + enemigo.nombre + " contraataca. " + personajeSeleccionado.nombre + " pierde 1 de vida.");
                                System.out.println("Vida de " + enemigo.nombre + " = " + Enemigo.representarVida(enemigo.vida, enemigo.getVidamaxima()) + " (" + enemigo.vida + "/" + enemigo.getVidamaxima() + ").");
                            }
                        } else {
                            System.out.println("¡Has derrotado al enemigo " + enemigo.nombre + "!");


                            // Encontrar un objeto
                            int azarobjeto = (int) (Math.random() * 2) + 1;
                            if (azarobjeto == 1) {

                                Objetos objeto = Objetos.objetorandom();
                                System.out.println("¡El enemigo a dropeado un objeto: " + objeto.nombre + "!");
                                System.out.println("¿Quieres recogerlo? (1: Sí, 2: No)");
                                int opc = scanner.nextInt();
                                if (opc == 1) {
                                    personajeSeleccionado.recogerObjeto(objeto);

                                } else {
                                    System.out.println("Decidiste no recoger el objeto.");
                                }

                            } else {
                                System.out.println("No ha habido suerte...");
                            }

                        }
                    } else if (opcion == 2) {
                        personajeSeleccionado.vida -= 1;
                        System.out.println("Huiste de la batalla, pero perdiste 1 de vida.");
                        break;
                    } else {
                        System.out.println("Opción inválida.");
                    }
                }
                enemigo.setVida(enemigo.vidamaxima);

                if (personajeSeleccionado.vida <= 0) {
                    System.out.println(personajeSeleccionado.nombre + " ha muerto... Fin del juego.");
                    break;
                }

                Mapa.setMapa(false);

            } else if (Mapa.isBoss()) {

                System.out.println("¡Aparece un nuevo boss! Es " + boss[piso].nombre + " con " + Enemigo.representarVida(boss[piso].vida, boss[piso].vidamaxima) + " (" + boss[piso].vida + "/" + boss[piso].getVidamaxima() + ").");

                // Batalla
                while (boss[piso].estaVivo() && personajeSeleccionado.vida > 0) {
                    System.out.println("¿Qué deseas hacer?");
                    System.out.println("1. Atacar al enemigo");
                    System.out.println("2. Huir (perderás 1 de vida)");
                    int opcion = scanner.nextInt();

                    if (opcion == 1) {
                        personajeSeleccionado.atacar(boss[piso]);
                        if (boss[piso].estaVivo()) {
                            int ataquerandom = (int) (Math.random() * 3) + 1;
                            if (ataquerandom == 1) {
                                personajeSeleccionado.vida -= 1; // El enemigo contraataca
                                System.out.println("Vida de " + boss[piso].nombre + " = " + Enemigo.representarVida(boss[piso].vida, boss[piso].getVidamaxima()) + " (" + boss[piso].vida + "/" + boss[piso].getVidamaxima() + ").");
                            } else {
                                System.out.println("El enemigo " + boss[piso].nombre + " contraataca. " + personajeSeleccionado.nombre + " pierde 1 de vida.");
                                System.out.println("Vida de " + boss[piso].nombre + " = " + Enemigo.representarVida(boss[piso].vida, boss[piso].getVidamaxima()) + " (" + boss[piso].vida + "/" + boss[piso].getVidamaxima() + ").");
                            }
                        } else {
                            System.out.println("¡Has derrotado al enemigo " + boss[piso].nombre + "!");
                            boss[piso] = null;
                            piso++;

                            for (Enemigo enemigo : enemigos) {
                                int nuevaVida = (int) Math.round(enemigo.getVidamaxima() * 1.4); // Multiplicar por 1.3 y redondear
                                enemigo.setVidamaxima(nuevaVida);
                                enemigo.setVida(nuevaVida);
                            }


                            if (piso != 3) {
                                Mapa.generarmapa();
                            }
                            break;


                        }
                    } else if (opcion == 2) {
                        personajeSeleccionado.vida -= 1;
                        System.out.println("Huiste de la batalla, pero perdiste 1 de vida.");
                        huirbos = true;

                        break;
                    } else {
                        System.out.println("Opción inválida.");
                    }
                }

                if (personajeSeleccionado.vida <= 0) {
                    System.out.println(personajeSeleccionado.nombre + " ha muerto... Fin del juego.");
                    break;
                }
                Mapa.setBoss(false);
            } else {

                System.out.println("Sala vacía");

            }
        }

        if (personajeSeleccionado.vida > 0 && boss[2] == null) {
            System.out.println("¡Felicidades! Has derrotado a todos los enemigos y sobrevivido.");
        } else if (personajeSeleccionado.vida <= 0) {
            System.out.println(personajeSeleccionado.nombre + " ha caído en batalla. Fin del juego.");
        }

        scanner.close();
    }

}

