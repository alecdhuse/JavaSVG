/*
 * BSD License
 * 
 * Copyright© 2012, Alec Dhuse All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * 
 * Neither Alec Dhuse nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package javasvg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class is used for testing by drawing and SVG on a JPanel.
 * When started a file chooser will be displayed select the SVG file to be
 * displayed.
 * 
 * @author Alec
 */
public class TestSVG extends JFrame {
    private JFileChooser fc;
    private SvgPanel     drawPanel;
    
    public TestSVG() {
        init();
        
        int returnVal = fc.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            openSVG(fc.getSelectedFile());
        }
        
        this.setSize(300, 300);
        this.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestSVG test = new TestSVG();
    }    
    
    private void init() {
        drawPanel = new SvgPanel();
        fc        = new JFileChooser();
        
        this.setLayout(new BorderLayout());
        this.add(drawPanel, BorderLayout.CENTER);
    }
    
    private void openSVG(File svgFile) {
        BufferedReader br;
        StringBuilder  xml;
          
        try {
            br  = new BufferedReader(new FileReader(svgFile));
            xml = new StringBuilder();
            
            while (br.ready()) {
                xml.append(br.readLine());
            }
            
            SvgImage image = SvgImage.parseSVG(xml.toString());
            drawPanel.setImage(image);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

class SvgPanel extends JPanel {
    private SvgImage    image;
    
    public SvgPanel() {
        super();        
        
        this.image = null;
        this.setBackground(Color.WHITE);
    }    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (image != null) {
            image.draw(g);
        }
    }
    
    public void setImage(SvgImage image) {
        this.image = image;
    }
}