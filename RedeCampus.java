
import java.util.*;

/**
 * Dojô de Grafos — esqueleto para os grupos.
 */
public class RedeCampus {

    private final Map<String, List<String>> adjacencias = new LinkedHashMap<>();

    // TODO 1 — Cadastrar um local

    // MINHA ADIÇÃO 1
    public void adicionarLocal(String local) {
        if (!adjacencias.containsKey(local)) {
            adjacencias.put(local, new ArrayList<>());
        }
    }

    // TODO 2 — Adicionar um corredor

    // MINHA ADIÇÃO 2
    public void adicionarAresta(String a, String b) {
        adicionarLocal(a);
        adicionarLocal(b);

        adjacencias.get(a).add(b);
        adjacencias.get(b).add(a);
    }

    // Já pronto: devolve os vizinhos de um local.
    public List<String> vizinhos(String local) {
        return adjacencias.getOrDefault(local, new ArrayList<>());
    }

    // Já pronto: imprime a lista de adjacência.
    public void imprimir() {
        for (Map.Entry<String, List<String>> e : adjacencias.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }

    // Busca em largura: encontra o menor caminho.
    public List<String> menorCaminho(String origem, String destino) {

        Queue<String> fila = new ArrayDeque<>();
        Set<String> visitados = new HashSet<>();
        Map<String, String> anterior = new HashMap<>();

        // TODO 3 — Casos especiais

        // MINHA ADIÇÃO 3
        if (!adjacencias.containsKey(origem) ||
                !adjacencias.containsKey(destino)) {
            return new ArrayList<>();
        }

        if (origem.equals(destino)) {
            List<String> caminho = new ArrayList<>();
            caminho.add(origem);
            return caminho;
        }

        // TODO 4 — Preparar a busca

        // MINHA ADIÇÃO 4
        fila.add(origem);
        visitados.add(origem);

        // TODO 5 — Busca em largura (BFS)

        // MINHA ADIÇÃO 5
        while (!fila.isEmpty()) {
            String atual = fila.poll();

            for (String vizinho : vizinhos(atual)) {
                if (!visitados.contains(vizinho)) {

                    visitados.add(vizinho);
                    anterior.put(vizinho, atual);
                    fila.add(vizinho);

                    if (vizinho.equals(destino)) {
                        return reconstruirCaminho(anterior, origem, destino);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    // TODO 6 — Reconstruir o caminho

    // MINHA ADIÇÃO 6
    private List<String> reconstruirCaminho(
            Map<String, String> anterior, String origem, String destino) {

        List<String> caminho = new ArrayList<>();
        String atual = destino;

        while (atual != null) {
            caminho.add(atual);
            atual = anterior.get(atual);
        }

        Collections.reverse(caminho);
        return caminho;
    }

    // Programa principal
    public static void main(String[] args) {
        RedeCampus rede = new RedeCampus();

        // MINHA ADIÇÃO 7 — Cadastro dos locais
        rede.adicionarLocal("Entrada");
        rede.adicionarLocal("Biblioteca");
        rede.adicionarLocal("Cantina");
        rede.adicionarLocal("Bloco A");
        rede.adicionarLocal("Laboratório");
        rede.adicionarLocal("Auditório");

        // MINHA ADIÇÃO 8 — Cadastro das ligações
        rede.adicionarAresta("Entrada", "Biblioteca");
        rede.adicionarAresta("Entrada", "Cantina");
        rede.adicionarAresta("Biblioteca", "Laboratório");
        rede.adicionarAresta("Biblioteca", "Bloco A");
        rede.adicionarAresta("Cantina", "Bloco A");
        rede.adicionarAresta("Bloco A", "Laboratório");
        rede.adicionarAresta("Bloco A", "Auditório");
        rede.adicionarAresta("Laboratório", "Auditório");

        System.out.println("=== Lista de adjacência ===");
        rede.imprimir();

        System.out.println("\n=== Testes ===");
        testar(rede, "Entrada", "Auditório");
        testar(rede, "Biblioteca", "Biblioteca");
        testar(rede, "Pátio", "Auditório");
    }

    // Já pronto: executa a busca e mostra o resultado.
    private static void testar(RedeCampus rede, String origem, String destino) {
        List<String> caminho = rede.menorCaminho(origem, destino);

        if (caminho.isEmpty()) {
            System.out.println(origem + " -> " + destino + ": sem caminho");
        } else {
            System.out.println(origem + " -> " + destino + ": " +
                    String.join(" -> ", caminho)
                    + " (" + (caminho.size() - 1) + " ligações)");
        }
    }
}
