
public class Nodo {
    private int id;
    private String nombre;
    private int cantidad;
    private Double precio;

    private Nodo izq;
    private Nodo der;

    public Nodo() {
    }

    public Nodo(int id2, String nombre2, int cantidad2, Double precio2) {
    }

    public void actualizar(int id2, String nombre2, int cantidad2, Double precio2) {
        id = id2;
        nombre = nombre2;
        cantidad = cantidad2;
        precio = precio2;
    }

    public Nodo getInfo(Nodo nodo) {
        return nodo;
    }

    public void setInfo(Nodo nodo1, Nodo raiz) {
        raiz = nodo1;
    }

    public Nodo getIzq() {
        return izq;
    }

    public Nodo getDer() {
        return der;
    }

    public void setIzq(Nodo nodo) {
        izq = nodo;
    }

    public void setDer(Nodo nodo) {
        der = nodo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecio() {
        return precio;
    }

}
