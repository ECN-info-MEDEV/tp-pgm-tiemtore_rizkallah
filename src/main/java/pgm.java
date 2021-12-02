/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @author r_rizkallah
 */
public class pgm {
 
    // Maximum Grey Value
    public static final int VALMAX = 255;

    // Pixels inside the image
    private int[][] image;

    /**
     * Get image height
     * @return Height 
     */
    public int getHeight() {
        return image.length;
    }

    /**
     * Get image width
     * @return Width 
     */
    public int getWidth() {
        return image[0].length;
    }

    /**
     * Get image pixels
     * @return Pixels 
     */
    public int[][] getPixels() {
        return image;
    }
    
    /**
     * Set image pixels
     * @param pixels value 
     */
    public void setPixels(int[][] data) {
        image = data;
    }
    
    /**
     * Read PGM image file from a specific path and scale to 0-255 if needed
     * @param path to a PGM image
     * @return image (matrix of pixels)
     * @throws Exception if we couldn't read the PGM file from the specific path
     */
    public int[][] readPGM(String path) throws Exception {

        int width;
        int height;
        int valmax;
        Scanner in;
        try {
            in = new Scanner(new File(path));
            String magic = in.next();
            if (!magic.equals("P2")) {
                in.close();
                throw new Exception("ERROR: cannot read .pgm file " + path);
            }
            String sharp  = in.next();
            width = in.nextInt();
            height = in.nextInt();
            valmax = in.nextInt();
            image = new int[height][width];
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                    image[y][x] = in.nextInt();
            in.close();
        } catch (IOException e) {
            throw new Exception("ERROR: cannot read .pgm file " + path);
        }

        // Scale pixels values to the range 0-VALMAX in case the file has a different max value
        if (valmax != VALMAX)
            for (int j = 0; j < height; j++)
                for (int i = 0; i < width; i++)
                    image[j][i] = (image[j][i] * VALMAX) / valmax;

        return image;
    }

    /**
     * Write a PGM image from a matrix of pixels
     * @param path saving the PGM image to
     * @param file matrix of pixels used to create the PGM image
     * @throws Exception in case we could not write to the PGM image
     */
    public void writePGM(String path, int[][] file) throws Exception {
        
        int height = getHeight();
        int width  = getWidth();
        try {
            PrintStream output = new PrintStream(new FileOutputStream(path));
            output.println("P2");
            output.println("#");
            output.println(width + " " + height);
            output.println(VALMAX);
            for (int row = 0; row < height; row++)
                for (int col = 0; col < width; col++)
                    output.println(file[row][col]); // One pixel per line!
            output.close();
        } catch (IOException e) {
            throw new Exception("ERROR: cannot write .pgm file " + path);
        }
    }
    
    
    public int[][] seulliage(int seuilMin,int seuilMax)
         
    {
        int[][] imageSeuil= new int[this.getHeight()][this.getWidth()];
        for (int j = 0; j < this.getHeight(); j++)
        {
             for (int i = 0; i < this.getWidth(); i++)
                {
                    if(this.image[j][i]>=seuilMin && this.image[j][i]<=seuilMax)
                    {
                        imageSeuil[j][i]=255;
                    }
                    else
                    {
                        imageSeuil[j][i]=0;
                    }
                    
                }
        
            
        }
        return imageSeuil;
               
    }


    public static void main(String[] args) throws Exception {
        pgm file = new pgm();
        String path_to_read = "./files/baboon.pgm";
        String path_to_write = "./files/baboon_copy.pgm";
        int[][] img = file.readPGM(path_to_read);
        file.writePGM(path_to_write, img);
    }
    
    
}
