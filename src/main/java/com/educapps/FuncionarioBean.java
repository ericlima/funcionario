package com.educapps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class FuncionarioBean {

	private String numeroRg;
	private Funcionario funcionario = new com.educapps.Funcionario();

	private FuncionarioDAO dao = new FuncionarioDAO();

	public String pesquisar() {
		System.out.println("numeroRg " + numeroRg);

		funcionario = dao.obterPorRG(numeroRg);

		if (funcionario == null) {
			funcionario = new Funcionario();
			funcionario.setRg(numeroRg);
		}

		return "edita";
	}

	private UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {
		try {
			String relativeWebPath = "/images";
			ServletContext servletContext = (ServletContext) FacesContext
			        .getCurrentInstance().getExternalContext().getContext();
			String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
			String serverContext = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			
			//File file = new File(absoluteDiskPath, "imagetosave.jpg");
			
			copyFile(absoluteDiskPath + "/RG"+funcionario.getRg()+".jpg", file.getInputstream());
			
			funcionario.setFoto(serverContext + "/images/RG"+funcionario.getRg()+".jpg");
			
			FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
			
			FacesContext.getCurrentInstance().addMessage(null, message);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void copyFile(String fileName, InputStream in) {
		try {

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String gravar() {
		if (funcionario.getId() == null) {
			dao.cadastra(funcionario);
		} else {
			dao.altera(funcionario);
		}
		return "pesquisa";
	}

	public String excluir() {
		dao.exclui(funcionario);
		return "pesquisa";
	}

	public String getNumeroRg() {
		return numeroRg;
	}

	public void setNumeroRg(String numeroRg) {
		this.numeroRg = numeroRg;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}
