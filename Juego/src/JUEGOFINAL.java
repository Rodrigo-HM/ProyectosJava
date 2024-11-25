import java.util.Scanner;

public class JUEGOFINAL {
    /**
     * @author: Rodrigo Hernandez
     *@version: 1.0
     *
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int hp = 0, atacgue = 0, atacmag=0,atacarq=0;
        int habilidad = 0; // Cargas de habilidad
        boolean habilidadCargada = false; // Estado de habilidad cargada
        boolean turnoPerdido = false; // Para el mago
        boolean escudoActivado = false; // Para el escudo del enemigo
        int turnosMitigados = 0; // Para el guerrero


        String eleccionUsuario;
        System.out.println("******************************");
        System.out.println("*   Elige tu personaje:      *");
        System.out.println();
        System.out.println("*   1. Mago                  *");
        String magoimg =
                "             _,._      \n" +
                        "  .||,       /_ _\\     \n" +
                        " \\.`',/      |'L'| |    \n" +
                        " = ,. =      | -,| L    \n" +
                        " / || \\    ,-'\\\"/,'`.   \n" +
                        "   ||     ,'   `,,. `.  \n" +
                        "   ,|____,' , ,;' \\| |  \n" +
                        "  (3|\\    _/|/'   _| |  \n" +
                        "   ||/,-''  | >-'' _,\\\\ \n" +
                        "   ||'      ==\\ ,-'  ,' \n" +
                        "   ||       |  V \\ ,|   \n" +
                        "   ||       |    |` |   \n" +
                        "   ||       |    |   \\  \n" +
                        "   ||       |    \\    \\ \n" +
                        "   ||       |     |    \\ \n" +
                        "   ||       |      \\_,-' \n" +
                        "   ||       |___,,--\")_\\ \n" +
                        "   ||         |_|   ccc/ \n" +
                        "             ccc/       \n" +
                        "                         \n";

        System.out.println(magoimg);
        System.out.println("*   2. Arquero               *");
        String arqueroimg =
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡶⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⢀⣴⣾⣶⡄⠀⠀⣠⡾⠋⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⠟⢀⣼⠏⠀⠀⠀⠀⢹⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⣿⣿⠟⢀⣴⠟⠁⠀⠀⠀⠀⠀⠀⢿⡄⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠈⠁⠰⠿⠋⠀⠀⠀⠀⠀⠀⠀⠀⢸⣧⠀⠀⠀⢀⠀⠀⠀⠀\n" +
                        "⠀⠀⣤⣦⣤⣤⣤⣤⣴⣶⣶⠖⠀⠶⠶⠶⠶⠶⠶⠶⠆⢠⣤⡄⠰⠶⢾⣿⠷⠂\n" +
                        "⠀⠈⠛⠛⠋⠉⢉⠙⠻⠿⠂⣰⣶⣶⣶⣶⣶⣶⣶⡆⠸⣿⡏⠀⠀⠈⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⣼⣿⣶⣄⠘⢿⣿⠀⠀⠀⠀⠀⠀⠀⢠⣤⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣧⡈⠻⣆⠀⠀⠀⠀⠀⠀⣼⠇⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⡄⠙⢷⡄⠀⠀⠀⢰⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⠀⠀⠈⠻⣦⠀⠀⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⡆⠀⠀⠀⠘⠷⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠛⠛⠛⠛⠛⠛⠓⠀⠀⠀⠀⠀⠀⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n";

        System.out.println(arqueroimg);

        System.out.println("*   3. Guerrero              *");
        String guerreroimg =
                " ,                {}\n" +
                        " / \\, | ,        .--.\n" +
                        "|    =|= >      /.--.\\\n" +
                        " \\ /` | `       |====|\n" +
                        "  `   |         |`::`|  \n" +
                        "      |     .-;`\\..../`;_.-^-._\n" +
                        "     /\\\\/  /  |...::..|`   :   `|\n" +
                        "     |:'\\ |   /'''::''|   .:.   |\n" +
                        "      \\ /\\;-,/\\   ::  |..:::::..|\n" +
                        "      |\\ <` >  >._::_.| ':::::' |\n" +
                        "      | `\"\"`  /   ^^  |   ':'   |\n" +
                        "      |       |       \\    :    /\n" +
                        "      |       |        \\   :   /\n" +
                        "      |       |___/\\___|`-.:.-`\n" +
                        "      |        \\_ || _/    `\n" +
                        "      |        <_ >< _>\n" +
                        "      |        |  ||  |\n" +
                        "      |        |  ||  |\n" +
                        "      |       _\\.:||:./_\n" +
                        "      |      /____/\\____\\\n";

        System.out.println(guerreroimg);
        System.out.println("******************************");
        System.out.print("Escribe la clase de tu personaje: ");
        eleccionUsuario = sc.nextLine();

        while (!eleccionUsuario.equals("Guerrero") &&
                !eleccionUsuario.equals("Arquero") &&
                !eleccionUsuario.equals("Mago")) {
            System.out.println("Elección no válida. Por favor elige: Mago, Arquero o Guerrero");
            eleccionUsuario = sc.nextLine();
        }

        int puntosParaCargar = 0; // Puntos necesarios

        switch (eleccionUsuario) {
            case "Guerrero":
                hp = 100;
                atacgue = 10;
                puntosParaCargar = 2; // Guerrero necesita 2 puntos
                System.out.println("******************************");
                System.out.println("*      Personaje Elegido     *");
                System.out.println("******************************");
                System.out.println("*  Nombre: " + eleccionUsuario);
                System.out.println("*  Ataque: " + atacgue);
                System.out.println("*  Tu habilidad especial tiene: " + puntosParaCargar + " cargas");
                System.out.println("*  Vida: " + hp);
                System.out.println("******************************");
                break;
            case "Mago":
                hp = 50;
                atacmag= 20;
                puntosParaCargar = 1; // Mago necesita 1 punto
                System.out.println("******************************");
                System.out.println("*      Personaje Elegido     *");
                System.out.println("******************************");
                System.out.println("*  Nombre: " + eleccionUsuario);
                System.out.println("*  Ataque: " + atacmag);
                System.out.println("*  Tu habilidad especial tiene: " + puntosParaCargar + " cargas");
                System.out.println("*  Vida: " + hp);
                System.out.println("******************************");
                break;
            case "Arquero": // Arquero
                hp = 80;
                atacarq = 15;
                puntosParaCargar = 3; // Arquero necesita 3 puntos
                System.out.println("******************************");
                System.out.println("*      Personaje Elegido     *");
                System.out.println("******************************");
                System.out.println("*  Nombre: " + eleccionUsuario);
                System.out.println("*  Ataque: " + atacarq);
                System.out.println("*  Tu habilidad especial tiene: " + puntosParaCargar + " cargas");
                System.out.println("*  Vida: " + hp);
                System.out.println("******************************");
            break;
        }

        // Lógica del enemigo generado aleatoriamente
        int hpen = 50 + (int) (Math.random() * 51);
        int defen = 3 + (int) (Math.random() * 8);
        int atacen = 10 + (int) (Math.random() * 10);
        System.out.println("******************************");
        System.out.println("*      Enemigo Generado      *");
        System.out.println("******************************");
        System.out.println("*  Estadísticas del enemigo  *");
        System.out.println("*                            *");
        System.out.println("*  Ataque: " + atacen);
        System.out.println("*  Defensa: " + defen);
        System.out.println("*  Vida: " + hpen);
        System.out.println("******************************");

        while (hp > 0 && hpen > 0) {
            System.out.println();
            System.out.println("******************************");
            System.out.println("*         Tu Turno           *");
            System.out.println("******************************");
            System.out.println("* ¿Qué quieres hacer?        *");
            System.out.println("*  1. Atacar                 *");
            System.out.println("*  2. Cargar Habilidad       *");
            System.out.println("*  3. Curar                  *");
            System.out.println();
            System.out.print("Elige una opción (1, 2 o 3): ");
            System.out.println();
            int accion = sc.nextInt();

            int dañoReal = 0;

            if (accion == 1) {
                if (habilidadCargada) {
                    switch (eleccionUsuario) {
                        case "Arquero" -> {
                            dañoReal = atacarq * 4; // Daño 4 veces mayor
                            habilidadCargada = false; // Resetear habilidad cargada
                        }
                        case "Mago" -> {
                            dañoReal = atacmag;
                            turnoPerdido = true; // Enemigo pierde un turno
                            habilidadCargada = false; // Resetear habilidad cargada
                            System.out.println("¡El enemigo pierde un turno!");
                        }
                        case "Guerrero" -> {
                            dañoReal = atacgue;
                            turnosMitigados = 2; // Mitigar daño en los siguientes turnos
                            habilidadCargada = false; // Resetear habilidad cargada
                        }
                    }
                } else {
                    dañoReal = switch (eleccionUsuario) {
                        case "Guerrero" -> atacgue;
                        case "Arquero" -> atacarq;
                        case "Mago" -> atacmag;
                        default -> 0;
                    };

                }
                // Si el escudo del enemigo está activado, se reduce a la mitad el daño
                if (escudoActivado) {
                    dañoReal /= 2;
                    System.out.println("🛡️ El enemigo tiene un escudo activo, el daño se reduce a la mitad.");
                    escudoActivado = false; // Desactivar el escudo después de su uso
                }

                hpen -= dañoReal;
                System.out.println("🔥 ¡Has hecho " + dañoReal + " puntos de daño! 🔥");
            }
            if (accion == 3) {
                hp += 7;
                System.out.println("✨ Te has curado y tu vida ahora es: " + hp + " ✨");
            }
            if (accion == 2) {
                if (habilidad < puntosParaCargar) { // Verificar si hay suficientes puntos
                    habilidad++;
                    System.out.println("✨ Has sumado una carga a tu habilidad especial. Llevas: " + habilidad + " puntos.");
                    System.out.println("Te faltan " + (puntosParaCargar - habilidad) + " puntos para cargar la habilidad completamente.");

                    // Verificar si se ha cargado la habilidad
                    if (habilidad >= puntosParaCargar) {
                        habilidadCargada = true; // Marcar habilidad como cargada
                        System.out.println("⚡ ¡Habilidad cargada! Tu próximo ataque tendrá un efecto especial.");
                    }
                } else {
                    System.out.println("⚠️ Ya has cargado tu habilidad completamente.");
                }
            }

            // Lógica del enemigo
            if (!turnoPerdido && hpen > 0) {
                int accionen = 1 + (int)(Math.random() * 3);
                int danioatacen=atacen;
                if (accionen == 2) {
                    // Activar el escudo cuando saca un 2
                    escudoActivado = true;
                    System.out.println("👾 El enemigo ha activado un escudo. Recibirá la mitad del daño en el próximo turno.");
                } if(accionen==1) {
                    // Ataque normal del enemigo, con potenciación si el escudo estuvo activo en el turno anterior

                    if (turnosMitigados > 0) {
                        danioatacen /= 2;
                        turnosMitigados--;
                        System.out.println("⚔️ El Guerrero está recibiendo la mitad de daño.");
                    }

                    // Si el escudo estuvo activado en el turno anterior, el daño del enemigo aumenta 1.5x
                    if (escudoActivado) {
                       danioatacen*=2;
                        escudoActivado = false;
                        System.out.println("👾 Con el escudo, el enemigo te hace un 100% más de daño.");

                    }
                    hp -= danioatacen;
                    System.out.println("👾 El enemigo te ha hecho " + danioatacen + " puntos de daño.");
                }
                if (accionen == 3) {
                    hpen += 5;
                    System.out.println("✨ El enemigo se ha curado. Su vida asciende a " + hpen + " puntos.");
                }
            } else if (turnoPerdido) {
                System.out.println("👾 El enemigo ha perdido su turno debido al ataque del Mago.");
                turnoPerdido = false;
            }

            // Resto del manejo de turnos
            if (turnosMitigados > 0) {
                turnosMitigados--;
                System.out.println("⚔️ El Guerrero está mirigando los ataques enemigos.");
            }

            // Mostrar estadísticas después del turno
            System.out.println("💥   Puntos Tras Este Asalto   💥");
            System.out.println("❤️ Tu vida: " + hp + "  |  🔋 Tu habilidad: " + habilidad);
            System.out.println("👾 Enemigo: vida=" + hpen);
        }

        // Resultado final
        if (hp < 1) {
            String calavera =
                    "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣿⣿⣿⡿⠛⠋⠉⠁⠀⣠⣄⠀⠈⠉⠙⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣿⡿⣿⣷⡄⠀⠀⠀⢸⣿⣿⡇⠀⠀⠀⢠⣾⣿⢿⣿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⡟⠀⠈⠙⠻⠦⠀⠀⠘⣿⣿⠃⠀⠀⠴⠟⠋⠁⠀⢻⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⡇⠀⠀⢀⣠⣤⣤⣀⠀⠈⠁⠀⣀⣤⣤⣄⡀⠀⠀⢸⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⡇⠀⠸⠋⣉⣤⣄⣉⠻⠀⠀⠟⣉⣠⣤⣉⠙⠇⠀⢸⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣷⠀⢠⣿⠏⠁⠉⢿⣧⠀⠀⣼⡿⠉⠈⠹⣿⡄⠀⣾⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⡿⠁⠘⣷⣆⡀⣀⣾⡟⠀⠀⢻⣷⣀⢀⣰⣾⠃⠈⢿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣇⠀⠀⠈⠙⠛⠛⠉⢠⡇⢸⡄⠉⠛⠛⠋⠁⠀⠀⣸⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣿⣦⡀⠀⠀⠀⠀⠀⠛⣃⣘⠛⠀⠀⠀⠀⠀⢀⣴⣿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣿⣿⣷⠀⠘⠷⠶⠶⠛⠋⠙⠛⠶⠶⠾⠃⠀⣾⣿⣿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⣤⣦⣴⣦⣴⣦⣴⣦⣴⣤⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣿⣿⣿⡆⠀⠀⠃⠘⠃⠘⠃⠘⠃⠘⠀⠀⢰⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣿⣿⣿⣧⣄⠀⠀⠀⠀⣴⣦⠀⠀⠀⠀⣠⣼⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣾⣿⣿⣷⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n";

            System.out.println(calavera);
            System.out.println("💀 ¡DERROTA! Has muerto en la batalla.");
        }
        if (hpen < 1) {
            System.out.println("      ___________");
            System.out.println("     '._==_==_=_.'");
            System.out.println("     .-\\:      /-.");
            System.out.println("    | (|:.     |) |");
            System.out.println("     '-|:.     |-'");
            System.out.println("       \\::.    /");
            System.out.println("        '::. .'");
            System.out.println("          ) (");
            System.out.println("        _.' '._");
            System.out.println("       `\"\"\"\"\"\"\"`");
            System.out.println();
            System.out.println("🏆 ¡VICTORIA! Has matado al enemigo.");
        }
    }
}
//PROBLEMAS Y COSAS QUE AÑADIR:

//Cuando el mago carga su habilidad y stunea hay que hacer que aparte hago daño normal, porque sino esta perdiendo el turno igual.