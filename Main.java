import java.io.*;
import java.util.*;

public class Main {
    public static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        int opc, valor;
        Nodo raiz = new Nodo();
        raiz = crearArbol(raiz);
        do {
            System.out.println("1. Vender\n2. Agregar Producto\n3. Eliminar\n4. Mostrar Productos\n5. Guardar y Salir");
            opc = entrada.nextInt();
            switch (opc) {
                case 1:
                    raiz = vender(raiz);

                    break;
                case 2:
                    agregarProducto(raiz);
                    break;
                case 3:
                    System.out.println("introduzca el id del producto a eliminar");
                    valor = entrada.nextInt();
                    eliminar(raiz, valor);
                    break;

                case 4:
                    System.out.println("Recorrido inOrden\n");
                    Recorridos.inOrden(raiz);
                    break;
                case 5:
                    escribir(raiz);
                    break;
            }
        } while (opc < 6);
    }

    public static Nodo crearArbol(Nodo nodoPadre) {
        int id, cantidad;
        Double precio;
        String nombre;
        try {
            String texto;
            String[] partes;
            int contador = 0;
            BufferedReader contenedor = new BufferedReader(new FileReader("Productos.txt"));
            while ((texto = contenedor.readLine()) != null) {
                partes = texto.split("-", 4);
                id = Integer.parseInt(partes[0]);
                nombre = partes[1];
                cantidad = Integer.parseInt(partes[2]);
                precio = Double.parseDouble(partes[3]);
                if (contador == 0) {
                    nodoPadre.actualizar(id, nombre, cantidad, precio);
                } else {
                    insertarNodo(nodoPadre, id, nombre, cantidad, precio);
                }
                contador++;
            }
            contenedor.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo");
        } catch (IOException e) {
            System.out.println("Error");
        }
        return nodoPadre;
    }

    private static void agregarProducto(Nodo raiz) {
        int id, cantidad;
        String nombre;
        Double precio;
        System.out.println("Ingrese el id: ");
        id = entrada.nextInt();
        System.out.println("Ingrese el nombre: ");
        entrada.nextLine();
        nombre = entrada.nextLine();
        System.out.println("Ingrese el precio: ");
        precio = entrada.nextDouble();
        System.out.println("Ingrese la cantidad: ");
        cantidad = entrada.nextInt();
        int validar = buscarNodo(raiz, id);
        if (validar == 0) {
            insertarNodo(raiz, id, nombre, cantidad, precio);
            System.out.println("El producto ha sido añadido");
        }
    }

    private static Nodo eliminar(Nodo raiz, int id) {
        boolean bandera;
        Nodo otroNodo = new Nodo();
        Nodo aux = new Nodo();
        Nodo aux1 = new Nodo();
        if (raiz != null) {
            if (id < raiz.getId()) {
                raiz.setIzq(eliminar(raiz.getIzq(), id));
            } else {
                if (id > raiz.getId()) {
                    raiz.setDer(eliminar(raiz.getDer(), id));
                } else {
                    otroNodo = raiz;
                    if (otroNodo.getDer() == null) {
                        raiz = otroNodo.getIzq();
                    } else {
                        if (otroNodo.getIzq() == null) {
                            raiz = otroNodo.getDer();
                        } else {
                            aux = raiz.getIzq();
                            bandera = false;
                            while (aux.getDer() != null) {
                                aux1 = aux;
                                aux = aux.getDer();
                                bandera = true;
                            }
                            raiz.setInfo(aux, raiz);
                            otroNodo = aux;
                            if (bandera == true) {
                                aux1.setDer(aux.getIzq());
                            } else {
                                raiz.setIzq(aux.getIzq());
                            }
                        }
                    }
                    otroNodo = null;
                }
            }
        } else {
            System.out.println("La información no se ha encontrado");
        }
        return raiz;
    }

    public static void insertarNodo(Nodo nodoPadre, int id, String nombre, int cantidad, Double precio) {
        if (id < nodoPadre.getId()) {
            if (nodoPadre.getIzq() == null) {
                Nodo nuevoNodo = new Nodo(id, nombre, cantidad, precio);
                nodoPadre.setIzq(nuevoNodo);
            } else {
                insertarNodo(nodoPadre.getIzq(), id, nombre, cantidad, precio);
            }
        } else {
            if (id > nodoPadre.getId()) {
                if (nodoPadre.getDer() == null) {
                    Nodo nuevoNodo = new Nodo(id, nombre, cantidad, precio);
                    nodoPadre.setDer(nuevoNodo);
                } else {
                    insertarNodo(nodoPadre.getDer(), id, nombre, cantidad, precio);
                }
            }
        }
    }

    private static int buscarNodo(Nodo raiz, int valorBuscar) {
        int op = 0;
        if (valorBuscar < raiz.getId()) {
            if (raiz.getIzq() == null) {
                op = 0;
            } else {
                buscarNodo(raiz.getIzq(), valorBuscar);
            }
        } else {
            if (valorBuscar > raiz.getId()) {
                if (raiz.getDer() == null) {
                    System.out.println("Valor no encontrado");
                    op = 0;
                } else {
                    buscarNodo(raiz.getDer(), valorBuscar);
                }
            } else {
                op = 1;
                System.out.println("Valor encontrado: " + raiz.getId());
            }
        }
        return op;

    }

    private static Nodo vender(Nodo raiz) {
        int id, cantidad;
        System.out.println("Ingrese el id del producto a vender: ");
        id = entrada.nextInt();
        System.out.println("Cantidad a vender: ");
        cantidad = entrada.nextInt();
        Nodo nodo = new Nodo(id, "", cantidad, 0.0);
        int validar = buscarNodo(raiz, nodo.getId());
        if (validar != 0) {
            if ((nodo.getCantidad() <= raiz.getCantidad()) && (nodo.getCantidad() > 0)) {
                raiz.setCantidad(raiz.getCantidad() - nodo.getCantidad());
                if (raiz.getCantidad() == 0) {
                    System.out.println("Producto sin unidades, este producto será eliminado");
                    raiz = eliminar(raiz, raiz.getId());
                } else {
                    System.out.println("Existencias actualizadas");
                }
            } else {
                System.out.println("El producto elegido tiene unidades insuficientes");
            }
            System.out.println("La venta se completo");
        }
        return raiz;
    }

    private static void escribirArchivo(Nodo raiz, BufferedWriter bw) throws IOException {
        if (raiz != null) {
            escribirArchivo(raiz.getIzq(), bw);
            bw.write(raiz.getInfo(raiz).getId() + "-" + raiz.getInfo(raiz).getNombre() + "-"
                    + raiz.getInfo(raiz).getCantidad() + "-" + raiz.getInfo(raiz).getPrecio() + "\n");
            escribirArchivo(raiz.getDer(), bw);
        }
    }

    public static void escribir(Nodo raiz) {
        BufferedWriter bw;
        try {
            File archivo = new File("Productos.txt");
            bw = new BufferedWriter(new FileWriter(archivo, false));
            escribirArchivo(raiz, bw);
            bw.close();
        } catch (IOException e) {
            System.out.println("No se pudo escribir dentro del archivo");
        }
    }
}
