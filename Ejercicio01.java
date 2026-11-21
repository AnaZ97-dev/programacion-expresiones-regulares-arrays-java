package tarea04;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Realización de un programa en Java que gestione el inventario de dispositivos
 * informáticos de una tienda.
 * 
 * Toda la información deberá almacenarse en una matriz bidimensional, donde cada
 * fila representará un dispositivo y cada columna cada uno de sus atributos.
 * 
 * @author Ana Zafra Vallejo
 */

public class Ejercicio01 {      
        
        public static void main(String[] args) {
        
            //----------------------------------------------
            //          Declaración de variables 
            //----------------------------------------------

            // Constantes   
            final int COLUMNAS_INVENTARIO = 5;  //Número de columnnas que almacenaremos en el inventario.
            final int COLUMNA_CODIGO = 0;       
            final int COLUMNA_NOMBRE = 1;       
            final int COLUMNA_TIPO = 2;          
            final int COLUMNA_ESTADO = 3;       
            final int COLUMNA_PRECIO = 4;       
            
            // Variables de entrada
            int numDispositivos = 0;
            int opcion = 0;
            String codigo, nombre, tipo, estado, precio;

            // Variables de salida
            String[][] inventario; // Declaración de la matriz principal 

            // Variables auxiliares
            boolean entradaValida;
            boolean codigoDuplicado;
            boolean codigoEncontrado;
            boolean salir = false;

            // Clase Scanner para petición de datos de entrada  
            Scanner teclado = new Scanner(System.in);

            //----------------------------------------------
            //                Entrada de datos 
            //----------------------------------------------  
            System.out.println("Ejercicio 1: Inventario de dispositivos");
            System.out.println("----------------------------------------");

            // Solicitar cuántos dispositivos se desea registrar. 
            // Deberá seguir solicitándolo hasta que se inserte un número positivo.
            do {
                System.out.println("¿Cuántos dispositivos desea registrar?");
                if (teclado.hasNextInt()) {
                    numDispositivos = teclado.nextInt();
                    if (numDispositivos <= 0) {
                        System.err.println("Error: Debe insertar un número positivo.");
                    }
                } else {
                    System.err.println("Error: Debe insertar un número entero.");
                    teclado.next(); // Limpiar entrada incorrecta
                }
            } while (numDispositivos <= 0); // El programa vuelve a solicitarlo.
            teclado.nextLine(); // Limpiar buffer

            // Inicializar la matriz con el tamaño especifico
            inventario = new String[numDispositivos][COLUMNAS_INVENTARIO];

            // Para cada dispositivo, se pide:
            for (int i = 0; i < numDispositivos; i++) {
                System.out.printf("\n--- Dispositivo %d ---\n", i + 1);

                // Petición del código.
                do {
                    entradaValida = true;
                    codigoDuplicado = false;
                    System.out.print("Introduzca código (DISP-XXXX): ");
                    codigo = teclado.nextLine().toUpperCase();

                    // Validación del formato mediante expresión regular
                    if (!Pattern.matches("^DISP-\\d{4}$", codigo)) {
                        entradaValida = false;
                    } else {
                        // Validación de que el código ya existe con mensaje. 
                        for (int j = 0; j < i && !codigoDuplicado; j++) {
                            if (inventario[j][COLUMNA_CODIGO].equals(codigo)) {
                                System.out.println("--Código ya existente--");
                                codigoDuplicado = true;
                                entradaValida = false;
                            }
                        }
                    }
                } while (!entradaValida || codigoDuplicado); // El programa vuelve a solicitarlo. 
                inventario[i][COLUMNA_CODIGO] = codigo; // Se guarda en la matriz.

                // Petición del nombre.
                System.out.print("Introduzca el nombre del dispositivo: ");
                nombre = teclado.nextLine();
                inventario[i][COLUMNA_NOMBRE] = nombre; // Se guarda en la matriz. 

                // Petición del tipo indicando solo la inicial. 
                do {
                    System.out.print("Tipo (O -> Ordenador / R -> Router / M -> Monitor / I -> Impresora): ");
                    tipo = teclado.nextLine().toUpperCase();
                    entradaValida = (tipo.equals("O") || tipo.equals("M") || tipo.equals("R") || tipo.equals("I"));
                } while (!entradaValida); // El programa vuelve a solicitarlo.
                inventario[i][COLUMNA_TIPO] = tipo; // Se guarda en la matriz.

                // Petición del estado indicando solo la inicial. 
                do {
                    System.out.print("Estado (D -> Disponible /  R -> Reparación / V -> Vendido): ");
                    estado = teclado.nextLine().toUpperCase();
                    entradaValida = (estado.equals("D") || estado.equals("R") || estado.equals("V"));
                } while (!entradaValida); // El programa vuelve a solicitarlo.
                inventario[i][COLUMNA_ESTADO] = estado; // Se guarda en la matriz.

                // Petición del precio
                do {
                    entradaValida = true;
                    System.out.print("Precio: ");
                    precio = teclado.nextLine(); // Se almacena en texto. 

                    try {
                        // Conversión a decimal para realizar operaciones,usando la clase wrapper.
                        double precioDecimal = Double.parseDouble(precio);
                        
                        if (precioDecimal <= 0) {
                            System.err.println("Error: El precio debe ser mayor que 0.");
                            entradaValida = false;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error: Debes introducir un número válido.");
                        entradaValida = false;
                    }
                } while (!entradaValida); // El programa vuelve a solicitarlo.
                inventario[i][COLUMNA_PRECIO] = precio; // Se guarda en la matriz.
            }
            
            //--------------------------------------------------------
            //          Procesamiento (Menú de opciones)
            //--------------------------------------------------------
            
            do {
                System.out.println("\nMenú de opciones: ");
                System.out.println("1. Mostrar el inventario completo.");
                System.out.println("2. Buscar dispositivos por código.");
                System.out.println("3. Salir");
                System.out.print("\nSeleccione una opción: ");

                opcion = teclado.nextInt();
                teclado.nextLine(); // Limpieza del buffer para próximas lecturas
                
            switch (opcion) {
                // Opción 1: Mostrar el inventario completo.
                // Se muestran todos los dispositivos registrados en formato de tabla clara y legible.
                case 1:
                    System.out.println("\n--- INVENTARIO COMPLETO ---\n");
                    System.out.printf("%-12s | %-15s | %-12s | %-12s | %-10s\n", "CÓDIGO", "NOMBRE", "TIPO", "ESTADO", "PRECIO");
                    System.out.println("---------------------------------------------------------------------------");
                    
                    // Recorremos inventario usando índice para mostrar posición
                    for ( int i = 0; i < inventario.length; i++) {
                        // Extraemos la fila actual.
                        String[] dispositivo = inventario[i];
                        
                        // Convertimos precio String a double para formatear con 1 decimal. 
                        double precioDecimal = Double.parseDouble(dispositivo[COLUMNA_PRECIO]);
                        
                        // Mostramos la línea formateada.
                        System.out.printf("%-12s | %-15s | %-12s | %-12s | %-8.1f€\n",
                                dispositivo[COLUMNA_CODIGO],
                                dispositivo[COLUMNA_NOMBRE],
                                dispositivo[COLUMNA_TIPO],
                                dispositivo[COLUMNA_ESTADO],
                           precioDecimal); // Precio convertido. 
                    }
                    break;
                
                // Opción 2: Buscar precio de un dispositivo por su código.
                // Se solicita el código y se muestra el precio del dispositivo registrado cuyo código coincida con el introducido.
                case 2:
                    System.out.print("Introduzca el código del dispositivo a buscar su precio (DISP-XXXX): ");
                    String codigoBusqueda = teclado.nextLine().toUpperCase();
                    codigoEncontrado = false;
                    
                    // Recorremos la matriz para buscar el código.
                    for (int i = 0; i < inventario.length && !codigoEncontrado; i++) {
                        // Extraemos la fila actual
                        String[] dispositivo = inventario[i];
                        
                        // Comparamos el código usando la constante de columna.
                        if (dispositivo[COLUMNA_CODIGO].equals(codigoBusqueda)) {
                            // Convertimos el precio a double para mostrar.
                            double precioDecimal = Double.parseDouble(dispositivo[COLUMNA_PRECIO]);
            
                            System.out.printf("PRECIO: %.1f €\n",precioDecimal);
                            
                            codigoEncontrado = true;
                        }
                    }
                    if (!codigoEncontrado) {
                        System.out.println("\n--- No existe ningún dispositivo con ese código ---");
                    }
                    break;
                
                // Opción 3: Salir
                // Terminar el programa. 
                case 3:
                    salir = true; // Salida del bucle.
                    System.out.println("Saliendo del programa....");
                    break;
                    
                // Opción no válida.
                default:
                    System.err.println("Opción no válida. Por favor, elige una opción correcta.");
                    System.out.println("");
                    break;
            }           
        } while (!salir); // Se repite mientras "salir" es falso.
        System.out.println("\nFin del programa.");
    }
} // Fin de la clase Ejercicio 01.