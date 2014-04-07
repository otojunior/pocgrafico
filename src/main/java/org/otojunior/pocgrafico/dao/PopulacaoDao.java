package org.otojunior.pocgrafico.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import org.otojunior.pocgrafico.mock.PopulacaoMockFactory;

/**
 * 
 * @author otojunior
 *
 */
@Stateless
public class PopulacaoDao {
	/**
	 * 
	 * @return
	 */
	public List<BigDecimal> obterDadosPopulacaoMasculina() {
		return PopulacaoMockFactory.mockDadosBrasilPopulacaoMasculina();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<BigDecimal> obterDadosPopulacaoFeminina() {
		return PopulacaoMockFactory.mockDadosBrasilPopulacaoFeminina();
	}
}
