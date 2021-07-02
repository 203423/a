public class Producto implements Comparable<Producto> {
    private int id;
    private int cantidad;
    private String nombre;
    private Double precio;

    public Producto(int id, int cantidad, String nombre, Double precio) {
        this.id = id;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return id + " " + nombre + " " + cantidad + " $" + precio;
    }

    public int compareTo(Producto product) {
        if (this.id == product.id) {
            return this.nombre.compareTo(product.nombre);
        } else {
            if (this.id > product.id) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
