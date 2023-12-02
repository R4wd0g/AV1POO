import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaLogin extends JDialog {
    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private Repositorio repositorio;
    private JanelaPrincipal janelaPrincipal;

    public JanelaLogin(JanelaPrincipal janelaPrincipal, Repositorio repositorio) {
        super(janelaPrincipal, "Login", true);
        this.janelaPrincipal = janelaPrincipal;
        this.repositorio = repositorio;

        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Login:"));
        campoLogin = new JTextField();
        add(campoLogin);

        add(new JLabel("Senha:"));
campoSenha = new JPasswordField();
campoSenha.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            realizarLogin();
        }
    }
});        add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        add(botaoEntrar);

        setLocationRelativeTo(null); // Centraliza a janela na tela
    }

    

    private void realizarLogin() {
        String login = campoLogin.getText();
        String senha = new String(campoSenha.getPassword());

        Usuario usuario = repositorio.getUsuario(login);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            janelaPrincipal.setUsuarioLogado(usuario);
            dispose(); // Feche a janela de login
            // Mostra a mensagem de boas vindas
            JOptionPane.showMessageDialog(janelaPrincipal, "Bem-vindo(a) " + usuario.getNome() + "!", "Boas Vindas", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
