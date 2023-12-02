import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaEntidade2 extends JDialog {
    private JList<Entidade2> listaEntidades;
    private JButton botaoAdicionar, botaoEditar, botaoRemover;
    private Repositorio repositorio;

    public JanelaEntidade2(JFrame parent, Repositorio repositorio) {
        super(parent, "Gerenciar Entidade2", true);
        this.repositorio = repositorio;

        setSize(400, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        listaEntidades = new JList<>(new DefaultListModel<>());
        atualizarListaEntidades();
        add(new JScrollPane(listaEntidades), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        
        botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarEntidade();
                repositorio.gravarDados();  // Grava os dados após adicionar uma entidade
            }
        });
        painelBotoes.add(botaoAdicionar);

        botaoEditar = new JButton("Editar");
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarEntidade();
                repositorio.gravarDados();  // Grava os dados após editar uma entidade
            }
        });
        painelBotoes.add(botaoEditar);

        botaoRemover = new JButton("Remover");
        botaoRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerEntidade();
                repositorio.gravarDados();  // Grava os dados após remover uma entidade
            }
        });
        painelBotoes.add(botaoRemover);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void atualizarListaEntidades() {
        DefaultListModel<Entidade2> modelo = (DefaultListModel<Entidade2>) listaEntidades.getModel();
        modelo.clear();
        for (Entidade2 entidade : repositorio.getEntidades2().values()) {
            modelo.addElement(entidade);
        }
    }

    private void adicionarEntidade() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome da Entidade2");
        String descricao = JOptionPane.showInputDialog(this, "Digite a descrição da Entidade2");
        if (nome != null && !nome.trim().isEmpty() && descricao != null && !descricao.trim().isEmpty()) {
            Entidade2 entidade = new Entidade2(nome, descricao);
            repositorio.addEntidade2(entidade);
            atualizarListaEntidades();
        } else {
            JOptionPane.showMessageDialog(this, "Nome ou descrição inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarEntidade() {
        Entidade2 entidadeSelecionada = listaEntidades.getSelectedValue();
        if (entidadeSelecionada != null) {
            String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome", entidadeSelecionada.getNome());
            String novaDescricao = JOptionPane.showInputDialog(this, "Digite a nova descrição", entidadeSelecionada.getDescricao());
            if (novoNome != null && !novoNome.trim().isEmpty() && novaDescricao != null && !novaDescricao.trim().isEmpty()) {
                entidadeSelecionada.setNome(novoNome);
                entidadeSelecionada.setDescricao(novaDescricao);
                atualizarListaEntidades();
            } else {
                JOptionPane.showMessageDialog(this, "Nome ou descrição inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma entidade para editar!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerEntidade() {
        Entidade2 entidadeSelecionada = listaEntidades.getSelectedValue();
        if (entidadeSelecionada != null) {
            repositorio.getEntidades2().remove(entidadeSelecionada.getNome());
            atualizarListaEntidades();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma entidade para remover!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
