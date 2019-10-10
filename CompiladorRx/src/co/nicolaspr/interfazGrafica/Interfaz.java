package co.nicolaspr.interfazGrafica;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import co.nicolaspr.analizadorLexico.AnalizadorLexico;
import co.nicolaspr.analizadorLexico.ErrorLexico;
import co.nicolaspr.analizadorLexico.Token;
/**
 * Clase de la interfaz principal del programa
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 *@version 1.0.0
 */
public class Interfaz extends JFrame implements ActionListener {

	private JTextArea jArea;
	private JButton btnAnalizar, btnLimpiar;
	private AnalizadorLexico analizador;
	private JTable tablaSimbolos, tablaDesconocidos, tablaErrores;
	private JScrollPane scrollTSimbolos, scrollTDesconocidos, scrollTErrores;
	private DefaultTableModel modelo, modeloDesconocido, modeloError;
	private ArrayList<Token> lista;
	private ArrayList<ErrorLexico> listaError;
	private JPanel contentPane;

	/**
	 * Costructor de la clase, se inicializan todos los componentes
	 */
	public Interfaz() {

		setLayout(null);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setTitle("Analizador Lexico");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		jArea = new JTextArea();
		jArea.setBounds(20, 60, 300, 300);
		contentPane.add(jArea);

		modelo = new DefaultTableModel();
		modeloDesconocido = new DefaultTableModel();
		modeloError = new DefaultTableModel();
		tablaSimbolos = new JTable(modelo);
		tablaDesconocidos = new JTable(modeloDesconocido);
		tablaErrores = new JTable(modeloError);

		modelo.addColumn("LEXEMA");
		modelo.addColumn("CATEGORIA");
		modelo.addColumn("FILA");
		modelo.addColumn("COLUMNA");

		modeloDesconocido.addColumn("LEXEMA");
		modeloDesconocido.addColumn("CATEGORIA");
		modeloDesconocido.addColumn("FILA");
		modeloDesconocido.addColumn("COLUMNA");

		modeloError.addColumn("MENSAJE");
		modeloError.addColumn("FILA");
		modeloError.addColumn("COLUMNA");

		JLabel lblValidos = new JLabel("TABLA DE SIMBOLOS VALIDOS");
		lblValidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblValidos.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblValidos.setBounds(277, 15, 524, 32);
		contentPane.add(lblValidos);

		/**
		 * Componentes tabla de simbolos
		 */
		tablaSimbolos.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		tablaSimbolos.setPreferredScrollableViewportSize(new Dimension(450, 63));
		tablaSimbolos.setFillsViewportHeight(true);

		scrollTSimbolos = new JScrollPane(tablaSimbolos);
		scrollTSimbolos.setBounds(350, 60, 400, 100);
		scrollTSimbolos.setVisible(true);
		contentPane.add(scrollTSimbolos);

		JLabel lblDesconocidos = new JLabel("TABLA DE SIMBOLOS DESCONOCIDOS");
		lblDesconocidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesconocidos.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDesconocidos.setBounds(277, 160, 524, 32);
		contentPane.add(lblDesconocidos);

		/**
		 * componentes tabla de desconocidos
		 */
		tablaDesconocidos.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		tablaDesconocidos.setPreferredScrollableViewportSize(new Dimension(450, 63));
		tablaDesconocidos.setFillsViewportHeight(true);

		scrollTDesconocidos = new JScrollPane(tablaDesconocidos);
		scrollTDesconocidos.setBounds(350, 200, 400, 100);
		scrollTDesconocidos.setVisible(true);
		contentPane.add(scrollTDesconocidos);

		JLabel lblErrores = new JLabel("TABLA DE ERRORES");
		lblErrores.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrores.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblErrores.setBounds(277, 305, 524, 32);
		contentPane.add(lblErrores);

		/**
		 * componentes tabla de errores
		 */
		tablaErrores.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		tablaErrores.setPreferredScrollableViewportSize(new Dimension(450, 63));
		tablaErrores.setFillsViewportHeight(true);

		scrollTErrores = new JScrollPane(tablaErrores);
		scrollTErrores.setBounds(350, 345, 400, 100);
		scrollTErrores.setVisible(true);
		contentPane.add(scrollTErrores);

		btnAnalizar = new JButton("Analizar");
		btnAnalizar.setBounds(60, 380, 100, 30);
		btnAnalizar.addActionListener(this);
		contentPane.add(btnAnalizar);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(190, 380, 100, 30);
		btnLimpiar.addActionListener(this);
		contentPane.add(btnLimpiar);


	}
/**
 * Metodo donde se le dan las acciones a los botones de la interfaz
 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnLimpiar) {
			jArea.setText("");
			modelo.setRowCount(0);
			modeloDesconocido.setRowCount(0);
			modeloError.setRowCount(0);
		}
		if (e.getSource() == btnAnalizar) {
			if (!jArea.getText().trim().equals("")) {
				analizador = new AnalizadorLexico(jArea.getText());
				analizador.analizar();
				agregarTokensATabla();

			} else {
				JOptionPane.showMessageDialog(null, "Escriba algo");
			}
		}
	}
/**
 * Metodo donde se agrega la informacion a las tablas 
 */
	private void agregarTokensATabla() {

		Object[] fila = new Object[4];
		Object[] fila1 = new Object[3];
		
		modelo.setRowCount(0);
		modeloDesconocido.setRowCount(0);

		lista = analizador.getTablaSimbolos();
		for (int i = 0; i < lista.size(); i++) {
			fila[0] = lista.get(i).getLexema();
			fila[1] = lista.get(i).getCategoria();
			fila[2] = lista.get(i).getFila();
			fila[3] = lista.get(i).getColumna();

			modelo.addRow(fila);
		}

		lista = analizador.getTablaDesconocidos();
		for (int i = 0; i < lista.size(); i++) {
			fila[0] = lista.get(i).getLexema();
			fila[1] = lista.get(i).getCategoria();
			fila[2] = lista.get(i).getFila();
			fila[3] = lista.get(i).getColumna();

			modeloDesconocido.addRow(fila);
		}

		listaError = analizador.getTablaDeErrores();
		for (int i = 0; i < listaError.size(); i++) {
			fila1[0] = listaError.get(i).getMensaje();
			fila1[1] = listaError.get(i).getFila();
			fila1[2] = listaError.get(i).getColumna();

			modeloError.addRow(fila1);
		}
	}

}

