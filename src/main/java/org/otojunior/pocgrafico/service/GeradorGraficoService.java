/**
 * 
 */
package org.otojunior.pocgrafico.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.otojunior.pocgrafico.dao.PopulacaoDao;

/**
 * @author otojunior
 *
 */
@Stateless
public class GeradorGraficoService {
	private static final String LABEL_TITULO = "Gráfico de Faixa Etária Populacional";
	private static final String LABEL_EIXO_X = "População (em mil)";
	private static final String LABEL_EIXO_Y = "Faixa Etária";
	private static final String LABEL_LEGENDA_MASCULINO = "Masculino";
	private static final String LABEL_LEGENDA_FEMININO = "Feminino";
	private static final Color C_VERMELHO = new Color(149, 15, 16);
	private static final Color C_AZUL = new Color(0, 92, 148);
	private static final int LARGURA = 800;
	private static final int ALTURA = 800;
	
	@EJB
	private PopulacaoDao dao;
	
	public byte[] gerarGrafico() throws IOException {
		CategoryDataset dataset = criarCategoryDataset();
		JFreeChart chart = criarChart(dataset);
		return obterArrayBytes(chart);
		
	}
	
	/**
	 * 
	 * @param chart
	 * @return
	 * @throws IOException
	 */
	private byte[] obterArrayBytes(JFreeChart chart) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsPNG(out, chart, LARGURA, ALTURA);
		out.close();
		return out.toByteArray();
	}

	/**
	 * 
	 * @return
	 */
	private CategoryDataset criarCategoryDataset() {
		final List<BigDecimal> m = dao.obterDadosPopulacaoMasculina();
		final List<BigDecimal> f = dao.obterDadosPopulacaoFeminina();

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		dataset.addValue(m.get(0), LABEL_LEGENDA_MASCULINO, "100+");
		int count = 1;
		for (int i = 99; i > 0; i-=5) {
			dataset.addValue(m.get(count++), LABEL_LEGENDA_MASCULINO, String.format("%d-%d",(i-4), i));
		}
		
		dataset.addValue(f.get(0), LABEL_LEGENDA_FEMININO, "100+");
		count = 1;
		for (int i = 99; i > 0; i-=5) {
			dataset.addValue(f.get(count++), LABEL_LEGENDA_FEMININO, String.format("%d-%d",(i-4), i));
		}
		
		return dataset;
	}
	
	/**
	 * 
	 * @param dataset
	 * @return
	 */
	private JFreeChart criarChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createStackedBarChart(
			LABEL_TITULO, 
			LABEL_EIXO_Y, 
			LABEL_EIXO_X, 
			dataset, 
			PlotOrientation.HORIZONTAL, true, true, false);
		
		definirPropriedadesChart(chart);
		definirCores(chart);
		definirEixos(chart);
		definirBarrasValores(chart);

		return chart;
	}

	/**
	 * 
	 * @param chart
	 */
	private void definirBarrasValores(JFreeChart chart) {
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, C_AZUL);
		renderer.setSeriesPaint(1, C_VERMELHO);
		renderer.setDrawBarOutline(false);
	}

	/**
	 * 
	 * @param chart
	 */
	private void definirEixos(JFreeChart chart) {
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
	}

	/**
	 * 
	 * @param chart
	 */
	private void definirCores(JFreeChart chart) {
		chart.setBackgroundPaint(Color.WHITE); // WHITE = default (nem precisava colocar isso, neste caso)
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		
	}

	/**
	 * 
	 * @param chart
	 */
	private void definirPropriedadesChart(JFreeChart chart) {
		chart.setAntiAlias(true);
	}
}
