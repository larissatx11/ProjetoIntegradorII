package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ModuloConexao {
//metodo responsavel por estabelecer a cpnecção com o banco
	public static Connection conector() {
		java.sql.Connection conexao = null;
		// a linha abaixo chama o driver
		String driver = "com.mysql.cj.jdbc.Driver";
		// armazenando informações referente ao banco
		String url = "jdbc:mysql://localhost:3306/projetointegradorii";
		String user = "root";
		String password = "Cibelle2013";
		// estabelecendo a conexão com o banco
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);
			return conexao;
		} catch (Exception e) {
			// linha de apoio para saber o problema
			System.out.println("COE");
			return null;
		}
	}
}
