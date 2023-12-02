import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class Repositorio {
    private Map<String, Usuario> usuarios;
    private Map<String, Entidade1> entidades1;
    private Map<String, Entidade2> entidades2;
    private static final String ARQUIVO_USUARIOS = "usuarios.dat";
    private static final String ARQUIVO_ENTIDADES1 = "entidades1.dat";
    private static final String ARQUIVO_ENTIDADES2 = "entidades2.dat";

    public Repositorio() {
        this.usuarios = new HashMap<>();
        this.entidades1 = new HashMap<>();
        this.entidades2 = new HashMap<>();
    }

    public void addUsuario(Usuario usuario) {
        usuarios.put(usuario.getLogin(), usuario);
    }

    public Usuario getUsuario(String login) {
        return usuarios.get(login);
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void removeUsuario(String login) {
        usuarios.remove(login);
    }

    public void editarUsuario(String login, Usuario usuarioEditado) {
        if (usuarios.containsKey(login)) {
            usuarios.put(login, usuarioEditado);
        }
    }

    public Map<String, Entidade1> getEntidades1() {
        return entidades1;
    }

    public void addEntidade1(Entidade1 entidade) {
        entidades1.put(entidade.getNome(), entidade);
    }

    public Map<String, Entidade2> getEntidades2() {
        return entidades2;
    }

    public void addEntidade2(Entidade2 entidade) {
        entidades2.put(entidade.getNome(), entidade);
    }

    // Os métodos a seguir são para gravação e recuperação de dados de arquivos

    public void gravarDados() {
        try {
            ObjectOutputStream oosUsuarios = new ObjectOutputStream(new FileOutputStream(ARQUIVO_USUARIOS));
            oosUsuarios.writeObject(usuarios);
            oosUsuarios.close();

            ObjectOutputStream oosEntidades1 = new ObjectOutputStream(new FileOutputStream(ARQUIVO_ENTIDADES1));
            oosEntidades1.writeObject(entidades1);
            oosEntidades1.close();

            ObjectOutputStream oosEntidades2 = new ObjectOutputStream(new FileOutputStream(ARQUIVO_ENTIDADES2));
            oosEntidades2.writeObject(entidades2);
            oosEntidades2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean recuperarDadosDoArquivo() {
        try {
            ObjectInputStream oisUsuarios = new ObjectInputStream(new FileInputStream(ARQUIVO_USUARIOS));
            usuarios = (Map<String, Usuario>) oisUsuarios.readObject();
            oisUsuarios.close();

            ObjectInputStream oisEntidades1 = new ObjectInputStream(new FileInputStream(ARQUIVO_ENTIDADES1));
            entidades1 = (Map<String, Entidade1>) oisEntidades1.readObject();
            oisEntidades1.close();

            ObjectInputStream oisEntidades2 = new ObjectInputStream(new FileInputStream(ARQUIVO_ENTIDADES2));
            entidades2 = (Map<String, Entidade2>) oisEntidades2.readObject();
            oisEntidades2.close();

            return true; // Dados carregados com sucesso
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Falha ao carregar os dados
        }
    }
}
