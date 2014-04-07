/**
 * 
 */
package org.otojunior.pocgrafico.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.imageio.ImageIO;

import org.otojunior.pocgrafico.service.GeradorGraficoService;

/**
 * @author otojunior
 *
 */
@Model
public class GeradorGraficoBean {
	@EJB
	private GeradorGraficoService service;
	
	public void gerarGrafico(OutputStream out, Object data) throws IOException {  
		  
		byte bytesGrafico[] = service.gerarGrafico();  
		BufferedImage result = ImageIO.read(new ByteArrayInputStream(bytesGrafico)); 
		ImageIO.write(result, "png", out);  
	}
}
