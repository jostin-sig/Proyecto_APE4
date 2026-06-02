import java.util.*;

public class APE4_Grafos {

    // ═══════════════════════════════════════
    // Nodo
    // ═══════════════════════════════════════
    static class Nodo {
        String id;
        String nombre;

        public Nodo(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
    }

    // ═══════════════════════════════════════
    // Arista
    // ═══════════════════════════════════════
    static class Arista {
        String destino;
        int peso;

        public Arista(String destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    // ═══════════════════════════════════════
    // Grafo
    // ═══════════════════════════════════════
    static class Grafo {

        Map<String, Nodo> nodos = new HashMap<>();
        Map<String, List<Arista>> adyacencia = new HashMap<>();

        // ═══════════════════════════════════
        // TODO 1
        // Agregar nodo al grafo
        // ═══════════════════════════════════
        public void agregarNodo(String id, String nombre) {

            // COMPLETE AQUÍ
            // Crear el nodo con su id y nombre
            Nodo nodo = new Nodo(id, nombre);
 
            // Registrar el nodo en el mapa de nodos
            nodos.put(id, nodo);
 
            // Inicializar su lista de adyacencia vacía
            adyacencia.put(id, new ArrayList<>());


        }

        // ═══════════════════════════════════
        // TODO 2
        // Agregar arista no dirigida
        // ═══════════════════════════════════
        public void agregarArista(String origen, String destino, int peso) {

            // COMPLETE AQUÍ
            // Agregar arista de origen → destino
            adyacencia.get(origen).add(new Arista(destino, peso));
 
            // Agregar arista de destino → origen (grafo no dirigido)
            adyacencia.get(destino).add(new Arista(origen, peso));


        }

        // ═══════════════════════════════════
        // TODO 3 — BFS
        // Ruta con menos paradas
        // ═══════════════════════════════════
        public List<String> bfs(String inicio, String fin) {

            // Cola para recorrer niveles
            Queue<List<String>> cola = new LinkedList<>();

            // Nodos visitados
            Set<String> visitados = new HashSet<>();

            // Camino inicial
            List<String> caminoInicial = new ArrayList<>();

            // TODO:
            // Agregar nodo inicio al camino inicial
            caminoInicial.add(inicio);


            // TODO:
            // Agregar caminoInicial a la cola
            cola.add(caminoInicial);

            // TODO:
            // Marcar inicio como visitado
                visitados.add(inicio);


            while (!cola.isEmpty()) {

                // TODO:
                // Obtener el primer camino de la cola
                List<String> camino = cola.poll();

                // Nodo actual
                String actual =
                        camino.get(camino.size() - 1);

                // Si llegamos al destino
                if (actual.equals(fin)) {
                    return camino;
                }

                // Recorrer vecinos
                for (Arista arista : adyacencia.get(actual)) {

                    // TODO:
                    // Verificar si el vecino NO fue visitado
                    if (!visitados.contains(arista.destino)) {


                        // TODO:
                        // Marcar vecino como visitado
                        visitados.add(arista.destino);


                        // Crear nuevo camino
                        List<String> nuevoCamino =
                                new ArrayList<>(camino);

                        // TODO:
                        // Agregar vecino al nuevo camino
                        nuevoCamino.add(arista.destino);


                        // TODO:
                        // Agregar nuevoCamino a la cola
                        cola.add(nuevoCamino);

                    }
                }
            }

            return null;
        }

        // ═══════════════════════════════════
        // TODO 4 — Dijkstra
        // Ruta con menor distancia
        // ═══════════════════════════════════
        public List<String> dijkstra(String inicio, String fin) {
            // Mapa de distancias mínimas conocidas desde inicio
            Map<String, Integer> distancias =
                    new HashMap<>();
             // Mapa para reconstruir el camino: guarda el nodo anterior
            Map<String, String> anteriores =
                    new HashMap<>();
            // Cola de prioridad: procesa primero el nodo con menor distancia
            PriorityQueue<String> cola =
                    new PriorityQueue<>(
                            Comparator.comparingInt(
                                    distancias::get
                            )
                    );

            // Inicializar distancias
            for (String nodo : nodos.keySet()) {

                // TODO:
                // Inicializar distancia infinita
                distancias.put(nodo, Integer.MAX_VALUE);
            }

            // TODO:
            // Distancia del inicio = 0
            distancias.put(inicio, 0);


            // TODO:
            // Agregar inicio a la cola
            cola.add(inicio);


            while (!cola.isEmpty()) {

                // TODO:
                // Obtener nodo con menor distancia
                String actual = cola.poll();

                //if (actual.equals(fin)) break;


                for (Arista arista : adyacencia.get(actual)) {

                    // TODO:
                    // Calcular nueva distancia
                    int nuevaDistancia = distancias.get(actual) + arista.peso;
                    

                    // TODO:
                    // Verificar si nuevaDistancia es menor
                    if (nuevaDistancia < distancias.get(arista.destino)) {


                        // TODO:
                        // Actualizar distancia
                        distancias.put(arista.destino, nuevaDistancia);


                        // TODO:
                        // Guardar nodo anterior
                        anteriores.put(arista.destino, actual);


                        // TODO:
                        // Agregar vecino a la cola
                        cola.add(arista.destino);

                    }
                }
            }

            // Reconstruir camino
            List<String> camino = new ArrayList<>();

            String actual = fin;

            while (actual != null) {

                camino.add(0, actual);

                actual = anteriores.get(actual);
            }

            return camino;
        }

        // ═══════════════════════════════════
        // Mostrar resultado
        // ═══════════════════════════════════
        public void mostrarRuta(List<String> ruta) {

            if (ruta == null) {
                System.out.println("No existe ruta");
                return;
            }

            for (int i = 0; i < ruta.size(); i++) {

                String idNodo = ruta.get(i);

                Nodo nodo = nodos.get(idNodo);

                System.out.print(
                    nodo.nombre + " (" + nodo.id + ")"
                );

                if (i < ruta.size() - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();
        }
    }

    // ═══════════════════════════════════════
    // MAIN
    // ═══════════════════════════════════════
    public static void main(String[] args) {

        Grafo grafo = new Grafo();

        // NODOS
        grafo.agregarNodo("uta", "Universidad");
        grafo.agregarNodo("fisei", "FISEI");
        grafo.agregarNodo("idiomas", "Idiomas");
        grafo.agregarNodo("biblioteca", "Biblioteca");
        grafo.agregarNodo("estadio", "Estadio");
        grafo.agregarNodo("comedor", "Comedor");

        // ARISTAS
        grafo.agregarArista("uta", "fisei", 50);
        grafo.agregarArista("fisei", "idiomas", 40);
        grafo.agregarArista("idiomas", "biblioteca", 30);
        grafo.agregarArista("biblioteca", "estadio", 70);

        // Ruta con menos paradas
        // pero más distancia
        grafo.agregarArista("uta", "comedor", 20);
        grafo.agregarArista("comedor", "estadio", 200);

        // ═══════════════════════════════════
        // PRUEBAS
        // ═══════════════════════════════════

        System.out.println("===== BFS =====");

        List<String> rutaBFS =
                grafo.bfs("uta", "estadio");

        grafo.mostrarRuta(rutaBFS);

        System.out.println("\n===== DIJKSTRA =====");

        List<String> rutaDijkstra =
                grafo.dijkstra("uta", "estadio");

        grafo.mostrarRuta(rutaDijkstra);
    }
}