import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaPrincipal extends JFrame {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItemLogin, menuItemGerenciarUsuarios, menuItemGerenciarEntidade1, menuItemGerenciarEntidade2, menuItemGravarDados, menuItemRecuperarDados, menuItemLogout;
    private Repositorio repositorio;
    private Usuario usuarioLogado;
    private JLabel labelUsuarioLogado; // Nova variável para mostrar o usuario logado

    public JanelaPrincipal(Repositorio repositorio) {
        this.repositorio = repositorio;

        setTitle("Tela Principal");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Adicionado para permitir a adição da label na parte sul

        menuBar = new JMenuBar();
        menu = new JMenu("Opcoes");
        menuBar.add(menu);

        menuItemLogin = new JMenuItem("Login");
        menuItemLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirJanelaLogin();
            }
        });
        menu.add(menuItemLogin);

        menuItemGerenciarUsuarios = new JMenuItem("Gerenciar Usuarios");
        menuItemGerenciarUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarioLogado != null && "admin".equals(usuarioLogado.getTipo())) {
                    abrirJanelaUsuarios();
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso restrito a administradores!");
                }
            }
        });
        menu.add(menuItemGerenciarUsuarios);

        menuItemGerenciarEntidade1 = new JMenuItem("Gerenciar Entidade 1");
        menuItemGerenciarEntidade1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirJanelaEntidade1();
            }
        });
        menu.add(menuItemGerenciarEntidade1);

        menuItemGerenciarEntidade2 = new JMenuItem("Gerenciar Entidade 2");
        menuItemGerenciarEntidade2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirJanelaEntidade2();
            }
        });
        menu.add(menuItemGerenciarEntidade2);

        menuItemGravarDados = new JMenuItem("Gravar Dados em Arquivo");
        menuItemGravarDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gravarDados();
            }
        });
        menu.add(menuItemGravarDados);

        menuItemRecuperarDados = new JMenuItem("Recuperar Dados do Arquivo");
        menuItemRecuperarDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recuperarDados();
            }
        });
        menu.add(menuItemRecuperarDados);

        menuItemLogout = new JMenuItem("Logout");
        menuItemLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        menu.add(menuItemLogout);

         setJMenuBar(menuBar);

        labelUsuarioLogado = new JLabel("Usuario: Nao logado"); // Inicialização da label
        add(labelUsuarioLogado, BorderLayout.SOUTH); // Adicionando a label ao layout na parte inferior da janela


        // Adicionar um WindowListener para detectar o fechamento da janela
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                repositorio.gravarDados(); // Gravar dados quando a janela for fechada
            }
        });

        atualizarMenu(); // Chame este método após inicializar os componentes do menu
    }

     private void atualizarMenu() {
        if (usuarioLogado == null) {
            menuItemLogin.setEnabled(true);
            menuItemGerenciarUsuarios.setEnabled(false);
            menuItemGerenciarEntidade1.setEnabled(false);
            menuItemGerenciarEntidade2.setEnabled(false);
            menuItemGravarDados.setEnabled(false);
            menuItemRecuperarDados.setEnabled(false);
            menuItemLogout.setEnabled(false);
            // ... desabilite outros itens do menu conforme necessário ...
        } else if ("admin".equals(usuarioLogado.getTipo())) {
            menuItemLogin.setEnabled(false);
            menuItemGerenciarUsuarios.setEnabled(true);
            menuItemGerenciarEntidade1.setEnabled(true);
            menuItemGerenciarEntidade2.setEnabled(true);
            menuItemGravarDados.setEnabled(true);
            menuItemRecuperarDados.setEnabled(true);
            menuItemLogout.setEnabled(true);
            // ... habilite/desabilite outros itens do menu conforme necessário ...
        } else if ("oper".equals(usuarioLogado.getTipo())) {
            menuItemLogin.setEnabled(false);
            menuItemGerenciarUsuarios.setEnabled(false);
            menuItemGerenciarEntidade1.setEnabled(true);
            menuItemGerenciarEntidade2.setEnabled(true);
            menuItemGravarDados.setEnabled(true);
            menuItemRecuperarDados.setEnabled(true);
            menuItemLogout.setEnabled(true);
            // ... habilite/desabilite outros itens do menu conforme necessário ...
        }
    }

    private void abrirJanelaLogin() {
        JanelaLogin janelaLogin = new JanelaLogin(this, repositorio);
        janelaLogin.setVisible(true);
    }

    private void abrirJanelaUsuarios() {
        JanelaUsuarios janelaUsuarios = new JanelaUsuarios(this,repositorio);
        janelaUsuarios.setVisible(true);
    }

    private void abrirJanelaEntidade1() {
        JanelaEntidade1 janelaEntidade1 = new JanelaEntidade1(this,repositorio);
        janelaEntidade1.setVisible(true);
    }

    private void abrirJanelaEntidade2() {
        JanelaEntidade2 janelaEntidade2 = new JanelaEntidade2(this,repositorio);
        janelaEntidade2.setVisible(true);
    }

    private void gravarDados() {
        repositorio.gravarDados();
        JOptionPane.showMessageDialog(this, "Dados gravados com sucesso!");
    }

    private void recuperarDados() {
        repositorio.recuperarDadosDoArquivo();
        JOptionPane.showMessageDialog(this, "Dados recuperados com sucesso!");
    }

   private void logout() {
        usuarioLogado = null;
        labelUsuarioLogado.setText("Usuario: Nao logado"); // Resetando o texto da label
        JOptionPane.showMessageDialog(this, "Deslogado com sucesso!");
        atualizarMenu(); // Atualize o menu após o logout
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        labelUsuarioLogado.setText("usuario: " + usuarioLogado.getNome()); // Atualizando o texto da label
        atualizarMenu(); // Atualize o menu quando o usuario for definido
    }


    public static void main(String[] args) {
        Repositorio repo = new Repositorio();

        // Tente carregar os dados do arquivo
        boolean dadosCarregados = repo.recuperarDadosDoArquivo();

        // Se os dados não foram carregados (por exemplo, os arquivos não existem), adicione o usuario admin
        if (!dadosCarregados) {
            repo.addUsuario(new Usuario("Admin", "admin", "admin123", "admin"));
        }

        JanelaPrincipal janelaPrincipal = new JanelaPrincipal(repo);
        janelaPrincipal.setVisible(true);
    }
}
