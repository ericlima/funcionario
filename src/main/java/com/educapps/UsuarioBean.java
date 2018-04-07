package com.educapps;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.EmailException;
import com.educapps.Usuario.usuarioSituacao;

@ManagedBean
@SessionScoped
public class UsuarioBean {

	private Usuario usuario;

	private UsuarioDAO dao = new UsuarioDAO();

	@PostConstruct
	public void UsuarioBeanInit() {
		usuario = new Usuario();
		usuario.setSituacao(usuarioSituacao.INATIVO);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String validar() {
		Usuario usuarioTemp = dao.obterPorEmail(usuario.getEmail());

		if (usuarioTemp == null) {
			FacesMessage message = new FacesMessage("Usuario ou senha inválida");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "index";
		}
		
		if ((usuarioTemp != null) &&  (!usuarioTemp.getSenha().equals(usuario.getSenha()))) {

			FacesMessage message = new FacesMessage("Usuario ou senha inválida");
			FacesContext.getCurrentInstance().addMessage(null, message);
			
			usuario.setSituacao(usuarioSituacao.INATIVO);
			return "index";
		} else {
			usuario.setSituacao(usuarioSituacao.ATIVO);
			return "pesquisa";
		}

	}

	public void logoff() {
		usuario.setSituacao(usuarioSituacao.INATIVO);
	}

	public void esqueciSenha() {

		Usuario usuarioTemp = dao.obterPorEmail(usuario.getEmail());

		if (!usuarioTemp.equals(null)) {

			usuarioTemp.setSenha(GeradorSenha.getRandomPassword());
			(new UsuarioDAO()).salvar(usuarioTemp);

			String mensagem = "Prezado(a), " + usuarioTemp.getNomeContato() + " sua nova senha é: "
					+ usuarioTemp.getSenha();

			try {

				MailService.sendMail(mensagem, "Nova Senha de Acesso", usuario.getEmail());

				FacesMessage message = new FacesMessage("A sua nova senha foi enviada para: " + usuarioTemp.getEmail());

				FacesContext.getCurrentInstance().addMessage(null, message);

			} catch (EmailException e) {
				e.printStackTrace();
			}
		}

	}

}
