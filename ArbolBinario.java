import java.io.*;
import java.util.*;

public class ArbolBinario {
    public static Scanner entrada = new Scanner (System.in);    
    public static void main(String[] args) {
        int opc, valor;
        
        
        Nodo raiz= new Nodo();
        raiz=crearArbol(raiz);
        Recorridos recorrido1 =new Recorridos();
        do {
            System.out.println("1. Agregar productos\n2. Eliminar\n3. Vender\n4. Mostrar Productos\n5. Guardar y Salir");        
            opc=entrada.nextInt();
            switch (opc){
                case 1: 
                    agregarProducto(raiz);
                    break;
                case 2:
                    System.out.println("introduzca el id del producto a eliminar");
                    valor=entrada.nextInt();
                    eliminar(raiz, valor);
                break;
                case 3:

                break;
                
                case 4:System.out.println("Total: "+totalNodos(raiz)+" nodos");
                    System.out.println("Total nodos hoja: "+totalHojas(raiz)+" nodo (s)");
                    break;
                case 5: System.out.println("Recorrido pre-orden\n");
                Recorridos.preOrden(raiz);
                System.out.println("Recorrido post-orden\n");
                Recorridos.postOrden(raiz);
                System.out.println("Recorrido inOrden\n");
                Recorridos.inOrden(raiz);
                break;
                case 6:
                    System.out.println("Ingrese el valor a buscar: ");
                    int valorBuscar = entrada.nextInt();
                    buscarNodo(raiz, valorBuscar);
                }
        }while(opc<5);   
    }
    
    public static Nodo crearArbol (Nodo nodoPadre){        
        int id, cantidad;
        Double precio;
        String nombre;
        try {
            String texto;
            String[] partes;
            int contador = 0;

            FileReader archivo = new FileReader("Productos.txt");
            BufferedReader contenedor = new BufferedReader(archivo);

            while((texto=contenedor.readLine()) != null){
                partes = texto.split("-",4);
                id = Integer.parseInt(partes[0]);
                nombre = partes[1];
                cantidad = Integer.parseInt(partes[2]);
                precio = Double.parseDouble(partes[3]);
                if(contador==0){
                    nodoPadre.actualizar(id, nombre, cantidad, precio);
                }else{
                    insertarNodo(nodoPadre, id, nombre, cantidad, precio);
                }
                contador ++;
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

        System.out.println("id: ");
        id = entrada.nextInt();
        System.out.println("Nombre: ");
        entrada.nextLine();
        nombre = entrada.nextLine();
        System.out.println("Precio: ");
        precio = entrada.nextDouble();
        System.out.println("Cantidad: ");
        cantidad = entrada.nextInt();
        int validar = buscarNodo(raiz, id);
        if(validar == 0){
            insertarNodo(raiz, id, nombre, cantidad, precio);
            System.out.println("Producto aniadido");
        }

    }

    private static Nodo eliminar(Nodo raiz, int id) {
        boolean bandera;
        Nodo otroNodo=new Nodo();
        Nodo aux=new Nodo();
        Nodo aux1=new Nodo();
        if (raiz!=null){
            if (id < raiz.getId()){
                raiz.setIzq(eliminar(raiz.getIzq(), id));
            }else{
                if (id > raiz.getId()){
                    raiz.setDer(eliminar(raiz.getDer(), id));
                }else{                      
                    otroNodo=raiz;
                    if(otroNodo.getDer()==null){
                        raiz=otroNodo.getIzq();
                    }else{
                        if(otroNodo.getIzq()==null){
                            raiz=otroNodo.getDer();
                        }else{
                            aux=raiz.getIzq();
                            bandera=false;
                            while (aux.getDer()!=null){
                                aux1=aux;
                                aux=aux.getDer();
                                bandera=true;
                            }
                            raiz.setInfo(aux,raiz);
                            otroNodo=aux;
                            if (bandera==true){
                                aux1.setDer(aux.getIzq());
                            }else{
                                raiz.setIzq(aux.getIzq());
                            }
                        }
                    }
                    otroNodo=null;
                }
            }
        }else{
            System.out.println("La informacion no se encuentra");
        }   
        return raiz;
    }

    public static void insertarNodo(Nodo nodoPadre, int id, String nombre, int cantidad, Double precio) {
        if (id < nodoPadre.getId()) {
            if (nodoPadre.getIzq() == null) {
                Nodo nuevoNodo = new Nodo(id, nombre,cantidad, precio);
                nodoPadre.setIzq(nuevoNodo);
            } else {
                insertarNodo(nodoPadre.getIzq(), id, nombre,cantidad, precio);
            }
        } else {
            if(id > nodoPadre.getId()){
                if (nodoPadre.getDer() == null) {
                    Nodo nuevoNodo = new Nodo (id, nombre,cantidad, precio);
                    nodoPadre.setDer(nuevoNodo);
                } else {
                    insertarNodo(nodoPadre.getDer(), id, nombre,cantidad, precio);
                }
            }
        }
    }

    public static int totalNodos(Nodo raiz){
        if (raiz != null)
            return 1 + totalNodos(raiz.getIzq()) +totalNodos(raiz.getDer());
        else
            return 0;
    }
    public static int totalHojas(Nodo raiz){
        if (raiz != null){
            if(raiz.getIzq()==null && raiz.getDer()==null){//Es un nodo hoja
               return 1;
            }
            else{
                return totalHojas(raiz.getIzq())+totalHojas(raiz.getDer()) ;
            }
        }
        else return 0;
    }
    private static int buscarNodo(Nodo raiz, int valorBuscar) {
        int  op=0;
        if (valorBuscar < raiz.getId()) {
            if (raiz.getIzq() == null) {
                op=0;
            } else {
                buscarNodo(raiz.getIzq(), valorBuscar);
            }
        } else {
            if (valorBuscar > raiz.getId()) {
                if (raiz.getDer() == null) {
                    System.out.println("Valor no encontrado");
                    op=0;
                } else {
                    buscarNodo(raiz.getDer(), valorBuscar);
                }
            } else {
                op=1;
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
        Nodo nodo= new Nodo(id, "", cantidad, 0.0);
        int validar = buscarNodo(raiz, nodo.getId());
        if(validar != 0){
            if((nodo.getCantidad() <= raiz.getCantidad()) && (nodo.getCantidad() > 0)){
                raiz.setCantidad(raiz.getCantidad() - nodo.getCantidad());
                if(raiz.getCantidad() == 0){
                    System.out.println("El producto sera eliminado ya que no tiene existencias");
                    raiz = eliminar(raiz, raiz.getId());
                }else{
                    System.out.println("Existencias actualizadas");
                }
            }else{
                System.out.println("Productos insuficientes");
            }
            System.out.println("Accion exitosa");
        }     
        return raiz; 
    }
}
