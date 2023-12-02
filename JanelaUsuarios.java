import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaUsuarios extends JDialog {
    private JList<Usuario> listaUsuarios;
    private JButton botaoAdicionar, botaoEditar, botaoRemover;
    private Repositorio repositorio;

    public JanelaUsuarios(JFrame parent, Repositorio repositorio) {
        super(parent, "Gerenciar usuarios", true);
        this.repositorio = repositorio;

        setSize(400, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listaUsuarios = new JList<>(new DefaultListModel<>());
        atualizarListaUsuarios();
        add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        
        botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarUsuario();
            }
        });
        painelBotoes.add(botaoAdicionar);

        botaoEditar = new JButton("Editar");
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
            }
        });
        painelBotoes.add(botaoEditar);

        botaoRemover = new JButton("Remover");
        botaoRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerUsuario();
            }
        });
        painelBotoes.add(botaoRemover);

        add(painelBotoes, BorderLayout.SOUTH);
        setLocationRelativeTo(null); // Centraliza a janela na tela
    }

    private void atualizarListaUsuarios() {
        DefaultListModel<Usuario> modelo = (DefaultListModel<Usuario>) listaUsuarios.getModel();
        modelo.clear();
        for (Usuario usuario : repositorio.getUsuarios().values()) {
            modelo.addElement(usuario);
        }
    }

    private void adicionarUsuario() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do usuario");
        String login = JOptionPane.showInputDialog(this, "Digite o login do usuario");
        String senha = JOptionPane.showInputDialog(this, "Digite a senha do usuario");
        String tipo = JOptionPane.showInputDialog(this, "Digite o tipo do usuario (admin ou oper)");
        if (nome != null && login != null && senha != null && tipo != null) {
            Usuario usuario = new Usuario(nome, login, senha, tipo);
            repositorio.addUsuario(usuario);
            atualizarListaUsuarios();
            repositorio.gravarDados();  // Grava os dados após adicionar o usuario
        }
    }

    private void editarUsuario() {
        Usuario usuarioSelecionado = listaUsuarios.getSelectedValue();
        if (usuarioSelecionado != null) {
            String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome", usuarioSelecionado.getNome());
            String novoLogin = JOptionPane.showInputDialog(this, "Digite o novo login", usuarioSelecionado.getLogin());
            String novaSenha = JOptionPane.showInputDialog(this, "Digite a nova senha", usuarioSelecionado.getSenha());
            String novoTipo = JOptionPane.showInputDialog(this, "Digite o novo tipo", usuarioSelecionado.getTipo());
            if (novoNome != null && novoLogin != null && novaSenha != null && novoTipo != null) {
                usuarioSelecionado.setNome(novoNome);
                usuarioSelecionado.setLogin(novoLogin);
                usuarioSelecionado.setSenha(novaSenha);
                usuarioSelecionado.setTipo(novoTipo);
                atualizarListaUsuarios();
                repositorio.gravarDados();  // Grava os dados após editar o usuario
            }
        }
    }

    private void removerUsuario() {
        Usuario usuarioSelecionado = listaUsuarios.getSelectedValue();
        if (usuarioSelecionado != null) {
            repositorio.removeUsuario(usuarioSelecionado.getLogin());
            atualizarListaUsuarios();
            repositorio.gravarDados();  // Grava os dados após remover o usuario
        }
    }
}
