public class Recorridos {
    public void preOrden(Nodo<Producto> raiz) {
        if (raiz != null) {
            System.out.println(raiz.getInfo().toString());
            preOrden(raiz.getIzq());
            preOrden(raiz.getDer());
        }
    }

    public void postOrden(Nodo<Producto> raiz) {
        if (raiz != null) {
            postOrden(raiz.getIzq());
            postOrden(raiz.getDer());
            System.out.println(raiz.getInfo().toString());
        }
    }

    public void inOrden(Nodo<Producto> raiz) {
        if (raiz != null) {
            inOrden(raiz.getIzq());
            System.out.println(raiz.getInfo().toString());
            inOrden(raiz.getDer());
        }
    }
}
