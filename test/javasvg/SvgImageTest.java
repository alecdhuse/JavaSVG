/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javasvg;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alec
 */
public class SvgImageTest {
    
    public SvgImageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of parseGroup method, of class SvgImage.
     */
    @Test
    public void testParseGroup() {
        System.out.println("parseGroup");
        String graphicsXML = "<g transform=\"translate(-147.743,-657.76002)\" id=\"g6067\"> <path d=\"m 148.925,657.968 c -0.542,0 -0.981,0.439 -0.981,0.979 v 7.521 c 0,0.542 0.44,0.982 0.981,0.982 h 7.521 c 0.541,0 0.98,-0.44 0.98,-0.982 v -7.521 c 0,-0.539 -0.44,-0.979 -0.98,-0.979 h -7.521 z\" id=\"path6069\" style=\"fill:#ffffff\" /> <path d=\"m 156.446,667.65 c 0.652,0 1.181,-0.529 1.181,-1.182 v -7.521 c 0,-0.652 -0.528,-1.18 -1.181,-1.18 h -7.521 c -0.652,0 -1.182,0.527 -1.182,1.18 v 7.521 c 0,0.652 0.529,1.182 1.182,1.182 h 7.521 z\" id=\"path6071\" /> <path d=\"m 152.686,666.216 c 1.938,0 3.51,-1.57 3.51,-3.51 0,-1.938 -1.572,-3.508 -3.51,-3.508 -1.939,0 -3.512,1.57 -3.512,3.508 0,1.94 1.572,3.51 3.512,3.51 z m -4.114,-3.509 c 0,-2.271 1.842,-4.111 4.113,-4.111 2.271,0 4.113,1.84 4.113,4.111 0,2.271 -1.842,4.113 -4.113,4.113 -2.271,0 -4.113,-1.842 -4.113,-4.113 z\" id=\"path6073\" style=\"fill:#ffffff\" /> <path d=\"m 152.229,663.964 0.838,-0.002 v -0.65 c 0,-0.178 0.083,-0.346 0.302,-0.49 0.221,-0.143 0.836,-0.438 0.836,-1.207 0,-0.771 -0.646,-1.301 -1.188,-1.414 -0.543,-0.111 -1.132,-0.037 -1.549,0.42 -0.375,0.41 -0.452,0.732 -0.452,1.447 h 0.838 v -0.166 c 0,-0.379 0.044,-0.781 0.587,-0.893 0.298,-0.061 0.576,0.033 0.741,0.195 0.189,0.186 0.191,0.602 -0.111,0.807 l -0.472,0.32 c -0.275,0.18 -0.369,0.377 -0.369,0.664 v 0.969 z\" id=\"path6075\" style=\"fill:#ffffff\" /> <rect width=\"0.85000002\" height=\"0.86299998\" x=\"152.22301\" y=\"664.34302\" id=\"rect6077\" style=\"fill:#ffffff\" /></g>";
        SvgImageGroup expResult = null;
        SvgImageGroup result = SvgImage.parseGroup(graphicsXML);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parsePath method, of class SvgImage.
     */
    @Test
    public void testParsePath() {
        System.out.println("parsePath");
        String pathXMl = "";
        SvgImageElement expResult = null;
        SvgImageElement result = SvgImage.parsePath(pathXMl);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseRectangle method, of class SvgImage.
     */
    @Test
    public void testParseRectangle() {
        System.out.println("parseRectangle");
        String rectangleXML = "";
        SvgImageElement expResult = null;
        SvgImageElement result = SvgImage.parseRectangle(rectangleXML);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseSVG method, of class SvgImage.
     */
    @Test
    public void testParseSVG() {
        System.out.println("parseSVG");
        String svgXML = "";
        SvgImage expResult = null;
        SvgImage result = SvgImage.parseSVG(svgXML);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
