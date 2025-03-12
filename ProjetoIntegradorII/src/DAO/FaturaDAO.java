package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.ibm.icu.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import Classes.Cartao;
import Classes.Despesa;
import Classes.Usuario;
import Conexao.ModuloConexao;

public class FaturaDAO {
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	// Listar os cartoes do usuário
	public List<Cartao> ListarCartoes(String cartaoSelecionado) {
		List<Cartao> cartoes = new ArrayList<>();

		String sql = "SELECT * FROM cartao WHERE CONCAT(bandeira, ' - ', numero) = ?";

		try {
			Connection conexao = ModuloConexao.conector();
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setString(1, cartaoSelecionado); // ID do usuário atual
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int numero = rs.getInt("numero");
				String tipo = rs.getString("tipo");
				String bandeira = rs.getString("bandeira");
				double limite = rs.getDouble("limite");
				double valorAtual = rs.getDouble("valor_atual");
				int data = rs.getInt("data_fechamento");

				// Cria um objeto Receita com os dados do banco de dados
				Cartao cartao = new Cartao();
				cartao.setId(id);
				cartao.setNumero(numero);
				cartao.setTipo(tipo);
				cartao.setBandeira(bandeira);
				cartao.setLimite(limite);
				cartao.setValor_atual(valorAtual);
				cartao.setFechamento(data);
				cartoes.add(cartao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartoes;
	}

	public List<Despesa> ListarDespesas(String cartaoSelecionado) {
		List<Despesa> despesas = new ArrayList<>();

		String sql = "SELECT * FROM despesa WHERE cartao = ?";

		try {
			Connection conexao = ModuloConexao.conector();
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setString(1, cartaoSelecionado); // ID do usuário atual
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				// Crie um objeto Despesa com os dados do banco de dados
				Despesa despesa = new Despesa();
				despesa.setId(rs.getInt("id"));
				despesa.setUsuario_id(rs.getInt("usuario_id"));
				despesa.setValor(rs.getDouble("valor"));
				despesa.setData(rs.getDate("data"));
				despesa.setDescricao(rs.getString("descricao"));
				despesa.setCategoria(rs.getString("categoria"));
				despesa.setFormaDePagamento(rs.getString("forma_de_pagamento"));
				despesa.setCartao(rs.getString("cartao"));
				despesa.setNumeroDeParcelas(rs.getInt("numero_de_parcelas"));
				despesa.setPago(rs.getBoolean("pago"));
				despesas.add(despesa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}

	public List<Despesa> obterDespesasDoMes(Date mesSelecionado, String cartaoSelecionado) {
		List<Despesa> despesasDoMes = new ArrayList<>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");

		// Obtém todas as despesas do cartão selecionado
		List<Despesa> todasAsDespesas = ListarDespesas(cartaoSelecionado);

		for (Despesa despesa : todasAsDespesas) {
			// Formata a data da despesa para o mesmo formato do mês selecionado
			String mesDespesa = dateFormat.format(despesa.getData());
			String mesSelecionadoStr = dateFormat.format(mesSelecionado);

			// Compara se o mês da despesa é igual ao mês selecionado
			if (mesDespesa.equals(mesSelecionadoStr)) {
				despesasDoMes.add(despesa);
			}
		}
		return despesasDoMes;
	}

	public void atualizaPago(Despesa despesa) {
		conexao = ModuloConexao.conector();
		String query = "UPDATE despesa SET pago=? where id=? and usuario_id=?"; // Usar a coluna "id" para identificar o
																				// usuário
		try {
			pst = conexao.prepareStatement(query);
			pst.setBoolean(1, true);
			pst.setInt(2, despesa.getId());
			pst.setInt(3, despesa.getUsuario_id());
			despesa.setPago(true);
			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				JOptionPane.showMessageDialog(null, "Fatura paga com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar informações do usuário: " + e.getMessage());
		}
	}

	public void atualizaValorAtual(Cartao cartao) {
		conexao = ModuloConexao.conector();
		String query = "UPDATE cartao SET valor_atual=? where id=? and usuario_id=?"; // Usar a coluna "id" para
																						// identificar o usuário
		try {
			pst = conexao.prepareStatement(query);
			pst.setDouble(1, cartao.getValor_atual());
			pst.setInt(2, cartao.getId());
			pst.setInt(3, cartao.getUsuario_id());
			int rowsUpdated = pst.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Fatura paga com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar informações do usuário: " + e.getMessage());
		}
	}

}
