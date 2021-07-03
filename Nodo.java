public class Nodo<Producto> {
    private Producto info;
    private Nodo<Producto> izq;
    private Nodo<Producto> der;

    public Nodo(Producto info) {
        this.info = info;
    }

    public Nodo() {
    }

    public Producto getInfo() {
        return this.info;
    }

    public void setInfo(Producto info) {
        this.info = info;
    }

    public Nodo<Producto> getIzq() {
        return this.izq;
    }

    public void setIzq(Nodo<Producto> nodo) {
        izq = nodo;
    }

    public Nodo<Producto> getDer() {
        return this.der;
    }

    public void setDer(Nodo<Producto> nodo) {
        der = nodo;
    }
}
