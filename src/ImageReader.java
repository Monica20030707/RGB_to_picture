package part2;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/***********************************************************
* Date: 27 Jan 2023
* Description: This program will reads from the given input file,
* image.dat, and creates an RGB image file, output.png, 
* with the given width and height as the first 2 token from the data file.
***********************************************************/

public class ImageReader {
	static int height=0;
	static int width=0;

	
	static ArrayList<String> token= new ArrayList<>();
	static ArrayList<Integer> decinum= new ArrayList<>();
	static ArrayList<Integer> pixel= new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		
		fileReader();
		boundary();
		readValue();
		createPixel();
		writeImage();
	}
	
	// This parameter read the data file and take all the RBG decimal number to an ArrayList
	public static void fileReader() throws FileNotFoundException {
		Scanner file= new Scanner(new File("*/data/image.dat"));
		while (file.hasNext()) {
			token.add(file.next());
		}
	}
	
	// just take 2 first token from the data Array for width and length
	public static void boundary() {
		try {
			height= Integer.parseInt(token.get(0));
			width= Integer.parseInt(token.get(1));
		
		}
		catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	
	// since we input RGB value attached to each other like a String, we have to separated it to work 
	// individually.
	public static void readValue() {
		// not counting the first two since they are width and length.
		for(int i=2; i<token.size(); i++) {
			String values= token.get(i);
			String[] value= values.split(",");
			
			for (String a : value) {
				int decimal=Integer.parseInt(a);
				decinum.add(decimal);
			}
		}
		
	}
	
	// take number from the decimal array that just have been separated from each other and 
	// do all the manipulator with bitwise operator and bit mask.
	public static void createPixel() {
		
		for(int i=0; i<decinum.size(); i+=3) {
			
			int r= decinum.get(i);
			int g= decinum.get(i+1);
			int b=decinum.get(i+2);
			
			
			pixel.add( 0x00 << 24 | (r & 0xFF)<<16 | (g & 0xFF)<<8 | b & 0xFF);
		}
	}
	
	//just take the pixel decimal then print them in order
	public static void writeImage() throws IOException{
		BufferedImage img= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		File file= new File("output.png");		
		int k=1;
		// print every line horizontally then jump to the next line.
		for(int i=0; i< height; i++) {
			for (int j=0; j< width;j++) {
					img.setRGB(j, i, pixel.get(k-1));
					k++;
				
			}
		}
		ImageIO.write(img,"PNG",file);
	}
}
