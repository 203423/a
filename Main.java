import java.io.*;
import java.util.*;

public class Main {
    public static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        Recorridos recorrido = new Recorridos();
        Nodo<Producto> raiz = new Nodo<Producto>();
        raiz = crearArbol(raiz);
        menu(raiz, recorrido);

    }

    public static void menu(Nodo<Producto> raiz, Recorridos recorrido) {
        int opc, valor;
        do {
            System.out.println("1. Vender\n2. Eliminar\n3. Agregar Producto\n4. Mostrar Productos\n5. Guardar y Salir");
            opc = entrada.nextInt();
            switch (opc) {
                case 1:
                    raiz = vender(raiz);
                    break;
                case 2:
                    System.out.println("introduzca el id del producto a eliminar");
                    valor = entrada.nextInt();
                    Producto eliminado = new Producto(valor, 1, "", 0.0);
                    raiz = eliminar(raiz, eliminado);
                    break;
                case 3:
                    agregarProducto(raiz);
                    break;
                case 4:
                    System.out.println("Recorrido inOrden\n");
                    recorrido.inOrden(raiz);
                    break;
                case 5:
                    escribir(raiz);
                    break;
            }
        } while (opc != 5);

    }

    public static Nodo<Producto> crearArbol(Nodo<Producto> nodoPadre) {
        int id, cantidad, contador = 0;
        Double precio;
        String nombre;
        try {
            String texto;
            String[] partes;
            BufferedReader contenedor = new BufferedReader(new FileReader("Productos.txt"));
            while ((texto = contenedor.readLine()) != null) {
                partes = texto.split("-", 4);
                id = Integer.parseInt(partes[0]);
                nombre = partes[1];
                cantidad = Integer.parseInt(partes[2]);
                precio = Double.parseDouble(partes[3]);
                Producto producto = new Producto(id, cantidad, nombre, precio);
                if (contador == 0) {
                    nodoPadre.setInfo(producto);
                } else {
                    insertarNodo(nodoPadre, producto);
                }
                contador++;
            }
            contenedor.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no fue encontrado");
        } catch (IOException e) {
            System.out.println("Error");
        }
        return nodoPadre;
    }

    private static Nodo<Producto> vender(Nodo<Producto> raiz) {
        int id, cantidad;
        System.out.println("Ingrese el id del producto a vender: ");
        id = entrada.nextInt();
        System.out.println("Cantidad a vender: ");
        cantidad = entrada.nextInt();
        Producto producto = new Producto(id, cantidad, "", 0.0);
        Nodo<Producto> validar = buscarNodo(raiz, producto, 2);
        if (validar != null) {
            if ((producto.getCantidad() <= validar.getInfo().getCantidad() && (producto.getCantidad() > 0))) {
                validar.getInfo().setCantidad(validar.getInfo().getCantidad() - producto.getCantidad());
                System.out.println("Cantidad de producto actualizada");
                if ((validar.getInfo().getCantidad() == 0)) {
                    System.out.println("Producto eliminado por falta de existencias");
                    raiz = eliminar(raiz, validar.getInfo());
                } else {
                    System.out.println("Cantidad de existencia actualizada");
                }
            } else {
                System.out.println("La cantidad del producto es insuficiente");
            }
            System.out.println("Venta realizada");
        }
        return raiz;
    }

    private static void agregarProducto(Nodo<Producto> raiz) {
        int id, cantidad;
        boolean validador = false;
        String nombre;
        Double precio;
        do {
            System.out.println("Ingrese el id: ");
            id = entrada.nextInt();
            validar(id);
            if (validador == true) {
                System.out.println("Ingrese el nombre: ");
                entrada.nextLine();
                nombre = entrada.nextLine();
                System.out.println("Ingrese el precio: ");
                precio = entrada.nextDouble();
                System.out.println("Ingrese la cantidad: ");
                cantidad = entrada.nextInt();
                Producto producto = new Producto(id, cantidad, nombre, precio);
                Nodo<Producto> validar = buscarNodo(raiz, producto, 1);
                if (validar == null) {
                    insertarNodo(raiz, producto);
                    System.out.println("El producto ha sido añadido");
                }
            }
        } while (validador == false);

    }

    private static boolean validar(int id) {
        boolean validador = false;
        if (id < 999 || id > 9999) {
            System.out.println("El codigo debe ser de 4 digitos\n");
        } else if (id > 999 && id >= 9999) {
            validador = true;
        }
        return validador;
    }

    private static Nodo<Producto> eliminar(Nodo<Producto> raiz, Producto eliminado) {
        boolean bandera;
        Nodo<Producto> anotherNodo = new Nodo<Producto>();
        Nodo<Producto> aux = new Nodo<Producto>();
        Nodo<Producto> aux1 = new Nodo<Producto>();
        if (raiz != null) {
            if (eliminado.getId() < raiz.getInfo().getId()) {
                raiz.setIzq(eliminar(raiz.getIzq(), eliminado));
            } else {
                if (eliminado.getId() > raiz.getInfo().getId()) {
                    raiz.setDer(eliminar(raiz.getDer(), eliminado));
                } else {
                    anotherNodo = raiz;
                    if (anotherNodo.getDer() == null) {
                        raiz = anotherNodo.getIzq();
                    } else {
                        if (anotherNodo.getIzq() == null) {
                            raiz = anotherNodo.getDer();
                        } else {
                            aux = raiz.getIzq();
                            bandera = false;
                            while (aux.getDer() != null) {
                                aux1 = aux;
                                aux = aux.getDer();
                                bandera = true;
                            }
                            raiz.setInfo(aux.getInfo());
                            anotherNodo = aux;
                            if (bandera == true) {
                                aux1.setDer(aux.getIzq());
                            } else {
                                raiz.setIzq(aux.getIzq());
                            }
                        }
                    }
                    anotherNodo = null;
                    System.out.println("El producto ha sido eliminado");
                }
            }
        } else {
            System.out.println("El producto no fue encontrado");
        }
        return raiz;
    }

    public static void insertarNodo(Nodo<Producto> nodoPadre, Producto producto) {
        if (producto.getId() < nodoPadre.getInfo().getId()) {
            if (nodoPadre.getIzq() == null) {
                Nodo<Producto> nuevoNodo = new Nodo<Producto>(producto);
                nodoPadre.setIzq(nuevoNodo);
            } else {
                insertarNodo(nodoPadre.getIzq(), producto);
            }
        } else {
            if (producto.getId() > nodoPadre.getInfo().getId()) {
                if (nodoPadre.getDer() == null) {
                    Nodo<Producto> nuevoNodo = new Nodo<Producto>(producto);
                    nodoPadre.setDer(nuevoNodo);
                } else {
                    insertarNodo(nodoPadre.getDer(), producto);
                }
            }
        }
    }

    private static Nodo<Producto> buscarNodo(Nodo<Producto> raiz, Producto producto, int opc) {
        if (producto.getId() < raiz.getInfo().getId()) {
            if (raiz.getIzq() == null) {
                System.out.println("Valor no encontrado");
                return null;
            } else {
                buscarNodo(raiz.getIzq(), producto, opc);
            }
        } else {
            if (producto.getId() > raiz.getInfo().getId()) {
                if (raiz.getDer() == null) {
                    System.out.println("Valor no encontrado");
                    return null;
                } else {
                    buscarNodo(raiz.getDer(), producto, opc);
                }
            } else {
                System.out.println("Valor encontrado: " + raiz.getInfo().toString());
                if (opc == 1) {
                    if ((producto.compareTo(raiz.getInfo()) == 0) && (producto.getCantidad() > 0)) {
                        raiz.getInfo().setCantidad(raiz.getInfo().getCantidad() + producto.getCantidad());
                        System.out.println("Unidades agregadas al producto");
                    } else {
                        System.out.println("El codigo ya existe");
                    }
                    return null;
                }
                if (opc == 2) {
                    return raiz;
                }
            }
        }
        return null;
    }

    private static void escribirArchivo(Nodo<Producto> raiz, BufferedWriter bw) throws IOException {
        if (raiz != null) {
            escribirArchivo(raiz.getIzq(), bw);
            bw.write(raiz.getInfo().getId() + "-" + raiz.getInfo().getNombre() + "-" + raiz.getInfo().getCantidad()
                    + "-" + raiz.getInfo().getPrecio() + "\n");
            escribirArchivo(raiz.getDer(), bw);
        }
    }

    public static void escribir(Nodo<Producto> raiz) {
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
