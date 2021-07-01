public class Recorridos {
    public static void preOrden(Nodo raiz) {
        if (raiz!=null) {
            System.out.println(raiz.getId());
            preOrden(raiz.getIzq());
            preOrden(raiz.getDer());            
        }        
    }
    public static void postOrden(Nodo raiz) {
        if (raiz!=null) {
            postOrden(raiz.getIzq());
            postOrden(raiz.getDer());
            System.out.println(raiz.getId());
        }
    }
    public static void inOrden(Nodo raiz){
        if(raiz != null){
            inOrden(raiz.getIzq());
            System.out.print(raiz.getId()+"-");
            inOrden(raiz.getDer());
        }
    }
}
